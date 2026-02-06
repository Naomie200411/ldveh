package graph;

public class Main {
    public static void main(String[] args) {
    LivreHero livre = new LivreHero();
    livre.chargerDepuisFichier("/home/khadir231/Documents/S2/projet2026/ldveh/book/de-01-le-pirate-des-sept-mers.txt");

    GrapheLDVEH graphe = new GrapheLDVEH(livre);

    // Afficher le graphe
    graphe.afficherGraphe();
}

}
