package graph;
/**
       Crée un monstre avec ses caractéristiques.
       @param nom est le nom du monstre
       @param habilete est l’habileté du monstre
       @param endurance est l’endurance du monstre
       @requires nom != NULL
       @requires habilete >= 0
       @requires endurance >= 0
       @ensures this.nom == nom
       @ensures this.habilete == habilete
       @ensures this.endurance == endurance
       @return un monstre initialisé
    */

public class Monstre {
    private String nom;
    private int habilete;
    private int endurance;

    public Monstre(String nom, int habilete, int endurance) {
        this.nom = nom;
        this.habilete = habilete;
        this.endurance = endurance;
    }

    public String getNom() { return nom; }
    public int getHabilete() { return habilete; }
    public int getEndurance() { return endurance; }
}