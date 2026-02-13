package graph;

import java.util.*;

// Représentation du graphe du LDVEH
public class GrapheLDVEH {
    private Map<Integer, NoeudGraphe> noeuds; // clé = id du paragraphe

    public GrapheLDVEH(LivreHero livre) {
        noeuds = new HashMap<>();

        // 🔹 Créer un noeud pour chaque paragraphe via l'iterator
        for (Paragraphe p : livre) {
            noeuds.put(p.getId(), new NoeudGraphe(p));
        }

        // 🔹 Ajouter les arêtes (voisins) pour chaque paragraphe
        for (NoeudGraphe noeud : noeuds.values()) {
            for (Choix c : noeud.paragraphe.getChoixDisponibles()) {
                NoeudGraphe destination = noeuds.get(c.getDestination());
                if (destination != null) {
                    noeud.voisins.add(destination);
                }
            }
        }
    }

    public Map<Integer, NoeudGraphe> getNoeuds() {
        return noeuds;
    }

    // Affichage simple du graphe
    public void afficherGraphe() {
        for (NoeudGraphe n : noeuds.values()) {
            System.out.print("Paragraphe " + n.paragraphe.getId() + " -> ");
            for (NoeudGraphe voisin : n.voisins) {
                System.out.print(voisin.paragraphe.getId() + " ");
            }
            System.out.println();
        }
    }

    // Retourne la liste des ids représentant le plus court chemin
    // entre departId et arriveeId
    public List<Integer> plusCourtChemin(int departId, int arriveeId) {

        if (!noeuds.containsKey(departId) || !noeuds.containsKey(arriveeId)) {
            return Collections.emptyList(); // si un des deux n'existe pas
        }

        /*Crée une file (queue) pour le BFS.
          BFS fonctionne niveau par niveau, donc on utilise une queue 
          pour traiter les noeuds dans l’ordre d’arrivée. */
        Queue<NoeudGraphe> file = new LinkedList<>();
        /*Cette map mémorise le parent de chaque noeud visité.
          Clé = noeud actuel, Valeur = noeud précédent sur le chemin.
          Permet de reconstruire le chemin après BFS.
         */
        Map<NoeudGraphe, NoeudGraphe> precedent = new HashMap<>();
        Set<NoeudGraphe> visites = new HashSet<>();

        NoeudGraphe depart = noeuds.get(departId);
        NoeudGraphe arrivee = noeuds.get(arriveeId);

        file.add(depart);
        visites.add(depart);

        // 🔹 BFS
        while (!file.isEmpty()) {
            NoeudGraphe courant = file.poll();

            if (courant == arrivee) {
                break; // trouvé
            }

            for (NoeudGraphe voisin : courant.voisins) {
                if (!visites.contains(voisin)) {
                    visites.add(voisin);
                    precedent.put(voisin, courant);
                    file.add(voisin);
                }
            }
        }

        // 🔹 Reconstruction du chemin
        List<Integer> chemin = new ArrayList<>();
        NoeudGraphe courant = arrivee;

        if (!precedent.containsKey(arrivee) && depart != arrivee) {
            return Collections.emptyList(); // aucun chemin trouvé
        }

        while (courant != null) {
            chemin.add(courant.paragraphe.getId());
            courant = precedent.get(courant);
        }

        Collections.reverse(chemin);
        return chemin;
    }

}
