/* Classe Objet
 À quoi ça sert ?

 Représenter UNE chose que tu peux trouver dans le jeu.

Exemples :

une clé 

une épée 

une potion */

package graph;

/**
       Crée un objet du jeu.
       @param nom est le nom de l’objet
       @param description est la description de l’objet
       @param nombre est la quantité de l’objet
       @requires nom != NULL
       @requires description != NULL
       @requires nombre >= 0
       @ensures this.nom == nom
       @ensures this.description == description
       @ensures this.nombre == nombre
       @return un objet initialisé
    */

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