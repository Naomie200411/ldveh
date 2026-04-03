package graph;

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