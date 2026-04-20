package graph;
/**
   Construit un graphe à partir d’un livre.
   @param livre est le livre contenant les paragraphes
   @requires livre != NULL
   @requires chaque Paragraphe de livre possède un id unique
   @requires chaque Paragraphe possède une liste de choix initialisée
   @ensures tous les paragraphes du livre sont transformés en NoeudGraphe
   @ensures chaque noeud est stocké dans la structure noeuds
   @ensures les arêtes du graphe correspondent aux choix des paragraphes
   @return un graphe construit à partir du livre
*/
import java.util.*;

// Représentation du graphe du LDVEH
public class GrapheLDVEH {
    private Map<Integer, NoeudGraphe> noeuds; 

    public GrapheLDVEH(LivreHero livre) {
        noeuds = new HashMap<>();

        
        for (Paragraphe p : livre) {
            noeuds.put(p.getId(), new NoeudGraphe(p));
        }

        
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

    public void afficherplusCourtChemin(int depart , int arrivee){
         List<Integer> chemin = plusCourtChemin(depart, arrivee);
         if (chemin.isEmpty()) {
            System.out.println("\nAucun chemin trouvé entre le paragraphe " + depart + " et le paragraphe " + arrivee);
        } else {
            System.out.println("\nChemin le plus court entre le paragraphe " + depart + " et le paragraphe " + arrivee + " :");
            for (int p : chemin) {
                System.out.print(p + " ");
            }
            System.out.println();
        }

    }

}
