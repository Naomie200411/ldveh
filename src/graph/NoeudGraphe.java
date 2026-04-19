package graph;
/**
       Crée un nœud de graphe à partir d’un paragraphe.
       @param p est le paragraphe associé au nœud
       @requires p != NULL
       @ensures this.paragraphe == p
       @ensures this.voisins != NULL et vide
       @ensures x et y sont initialisés aléatoirement dans [0, 500]
       @return un nœud de graphe initialisé
    */
import java.util.ArrayList;
import java.util.List;

public class NoeudGraphe {
    Paragraphe paragraphe;
    List<NoeudGraphe> voisins;
    double x, y; // positions pour affichage futur (algorithme de force)

    public NoeudGraphe(Paragraphe p) {
        this.paragraphe = p;
        this.voisins = new ArrayList<>();
        this.x = Math.random() * 500; // position initiale aléatoire
        this.y = Math.random() * 500;
    }
}
