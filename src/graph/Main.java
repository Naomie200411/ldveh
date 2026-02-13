package graph;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        LivreHero livre = new LivreHero();
        livre.chargerDepuisFichier("/home/khadir231/Documents/S2/projet2026/ldveh/book/de-01-le-pirate-des-sept-mers.txt");

        GrapheLDVEH graphe = new GrapheLDVEH(livre);

        // Afficher le graphe
        graphe.afficherGraphe();

        // -----------------------------
        // TEST : CHEMIN LE PLUS COURT
        // -----------------------------

        int depart = 80;   // numéro du paragraphe de départ
        int arrivee = 100;  // numéro du paragraphe d'arrivée (victoire)

        List<Integer> chemin = graphe.plusCourtChemin(depart, arrivee);

        if (chemin.isEmpty()) {
            System.out.println("\nAucun chemin trouvé entre le paragraphe " + depart + " et le paragraphe " + arrivee);
        } else {
            System.out.println("\nChemin le plus court entre le paragraphe " + depart + " et le paragraphe " + arrivee + " :");
            for (int p : chemin) {
                System.out.print(p + " ");
            }
            System.out.println();
        }
    }
}
