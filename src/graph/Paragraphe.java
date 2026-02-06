/*La classe Paragraphe sert à représenter un paragraphe d’un livre dont vous êtes le héros.

C’est :

une portion de récit

identifiée par un numéro

depuis laquelle le lecteur peut faire un ou plusieurs choix*/

package graph;

import java.util.ArrayList;
import java.util.List;

public class Paragraphe {
    private int id;
    private String texte;
    private String type;
    private List<Choix> choixDisponibles;
    private List<Objet> objets ;

    public Paragraphe(int id, String texte) {
        this.id = id;
        this.texte = texte;
        this.choixDisponibles = new ArrayList<>();
        this.objets = new ArrayList<>();
        this.type = "Livre"
    }

    public int getId() {
        return id;
    }

    public int getId() {
        return type;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public List<Choix> getChoixDisponibles() {
        return choixDisponibles;
    }

    public void ajouterChoix(Choix choix) {
        this.choixDisponibles.add(choix);
    }

    public void ajouterObjet(Objet objet) {
        objets.add(objet);
    }

    public List<Objet> getObjets() {
        return objets;
    }

    @Override
    public String toString() {
        return "Paragraphe " + id + ": " + texte + "\nChoix: " + choixDisponibles;
    }
}