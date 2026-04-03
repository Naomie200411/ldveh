package graph;

public class Joueur {
    private int endurance;
    private int habilete;
    private int chance;
    private Inventaire inventaire;

    public Joueur(int endurance, int habilete, int chance) {
        this.endurance = endurance;
        this.habilete = habilete;
        this.chance = chance;
        this.inventaire = new Inventaire();
    }

    public int getEndurance() {
        return endurance;
    }

    public int getHabilete() {
        return habilete;
    }

    public int getChance() {
        return chance;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public void perdreEndurance(int nb) {
        endurance -= nb;
        if (endurance < 0) endurance = 0;
    }

    public void gagnerEndurance(int nb) {
        endurance += nb;
    }

    public void perdreHabilete(int nb) {
        habilete -= nb;
        if (habilete < 0) habilete = 0;
    }

    public void gagnerHabilete(int nb) {
        habilete += nb;
    }

    public void perdreChance(int nb) {
        chance -= nb;
        if (chance < 0) chance = 0;
    }

    public void gagnerChance(int nb) {
        chance += nb;
    }

    public boolean estVivant() {
        return endurance > 0;
    }
}