/* Classe Objet
 À quoi ça sert ?

 Représenter UNE chose que tu peux trouver dans le jeu.

Exemples :

une clé 

une épée 

une potion */

package graph;

import java.util.HashMap;
import java.util.Map;

// Classe représentant un objet du jeu
public class Objet {
    private String nom;
    private String description;

    public Objet(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return nom + ": " + description;
    }
}