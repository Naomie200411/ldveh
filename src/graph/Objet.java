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
    private int nombre ;

    public Objet(String nom, String description , int nombre) {
        this.nom = nom;
        this.description = description;
        this.nombre = nombre;
    }

    public String getNom() {
        return nom;
    }

     public int getNombre() {
        return nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public void ajouterNombre(int quantite) {
        this.nombre += quantite;
    }

    @Override
    public String toString() {
        return nom + " (x" + nombre + ") : " + description;
    }
}