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
    private int perteEndurance;
    private int gainEndurance;
    private int perteHabilete;
    private int gainHabilete;
    private int perteChance;
    private int gainChance;
    private String objetRequis;
    private Monstre monstre;

    public Paragraphe(int id, String texte) {
        this.id = id;
        this.texte = texte;
        this.choixDisponibles = new ArrayList<>();
        this.objets = new ArrayList<>();
        this.type = "Livre";
    }

    public int getId() {
        return id;
    }

    public String getType() {
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

    public int getPerteEndurance() {
        return perteEndurance;
    }

    public void setPerteEndurance(int perteEndurance) {
        this.perteEndurance = perteEndurance;
    }

    public int getGainEndurance() {
        return gainEndurance;
    }

    public void setGainEndurance(int gainEndurance) {
        this.gainEndurance = gainEndurance;
    }

    public int getPerteHabilete() {
        return perteHabilete;
    }

    public void setPerteHabilete(int perteHabilete) {
        this.perteHabilete = perteHabilete;
    }

    public int getGainHabilete() {
        return gainHabilete;
    }

    public void setGainHabilete(int gainHabilete) {
        this.gainHabilete = gainHabilete;
    }

    public int getPerteChance() {
        return perteChance;
    }

    public void setPerteChance(int perteChance) {
        this.perteChance = perteChance;
    }

    public int getGainChance() {
        return gainChance;
    }

    public void setGainChance(int gainChance) {
        this.gainChance = gainChance;
    }

    public String getObjetRequis() {
    return objetRequis;
    }

    public void setObjetRequis(String objetRequis) {
        this.objetRequis = objetRequis;
    }

    public Monstre getMonstre() { return monstre; }
    public void setMonstre(Monstre m) { this.monstre = m; }

    public void afficherObjets() {
        if (objets.isEmpty()) {
            System.out.println("Aucun objet dans ce paragraphe.");
            return;
        }

        System.out.println("Objets du paragraphe " + id + " :");
        for (Objet o : objets) {
            System.out.println("- " + o.getNom());
        }
    }

    @Override
    public String toString() {
        return "Paragraphe " + id + ": " + texte + "\nChoix: " + choixDisponibles;
    }
}