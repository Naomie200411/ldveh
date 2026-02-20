package graph;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        LivreHero livre = new LivreHero();
        livre.chargerDepuisFichier("/home/kotin251/Documents/ldveh/book/df-60-l-oeil-d-emeraude.txt");

        GrapheLDVEH graphe = new GrapheLDVEH(livre);

        // Afficher le graphe
        //graphe.afficherGraphe();

        // -----------------------------
        // TEST : CHEMIN LE PLUS COURT
        // -----------------------------

        int depart = 373;   // numéro du paragraphe de départ
        int arrivee = 407;  // numéro du paragraphe d'arrivée (victoire)

        List<Integer> chemin = graphe.plusCourtChemin(depart, arrivee);

        /*if (chemin.isEmpty()) {
            System.out.println("\nAucun chemin trouvé entre le paragraphe " + depart + " et le paragraphe " + arrivee);
        } else {
            System.out.println("\nChemin le plus court entre le paragraphe " + depart + " et le paragraphe " + arrivee + " :");
            for (int p : chemin) {
                System.out.print(p + " ");
            }
            System.out.println();
        }*/

        AnalyseGraphe analyseGraphe = new AnalyseGraphe(graphe);
        analyseGraphe.afficherCheminsGagnants(373);
    }
}
