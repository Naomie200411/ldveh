package graph;

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
