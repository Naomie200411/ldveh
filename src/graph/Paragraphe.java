/*La classe Paragraphe sert à représenter un paragraphe d’un livre dont vous êtes le héros.

C’est :

une portion de récit

identifiée par un numéro

depuis laquelle le lecteur peut faire un ou plusieurs choix*/

package graph;

import java.util.ArrayList;
import java.util.List;

public class Paragraphe {
    private int numero;
    private String texte;
    private List<Choix> choixDisponibles;

    public Paragraphe(int numero, String texte) {
        this.numero = numero;
        this.texte = texte;
        this.choixDisponibles = new ArrayList<>();
    }

    public void ajouterChoix(Choix choix) {
        this.choixDisponibles.add(choix);
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public int getnumero() { 
        return numero; 
    }

    public String getTexte() { 
        return texte; 
    }

    public List<Choix> getChoixDisponibles() { 
        return choixDisponibles; 
    }

    public void ajouterObjet(Objet objet) {
        objets.add(objet);
    }

    public List<Objet> getObjets() {
        return objets;
    }

   @Override
    public String toString() {
        return "Paragraphe " + numero + ": " + texte + "\nChoix: " + choixDisponibles;
    }
}