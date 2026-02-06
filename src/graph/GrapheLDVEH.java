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
}
