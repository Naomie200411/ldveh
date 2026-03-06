package graph;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*LivreHero livre = new LivreHero();
        livre.chargerDepuisFichier("book/exple.txt");

        GrapheLDVEH graphe = new GrapheLDVEH(livre);

        // Afficher le graphe
        graphe.afficherGraphe();

        // -----------------------------
        // TEST : CHEMIN LE PLUS COURT
       


        int depart = 373;   // numéro du paragraphe de départ
        int arrivee = 407;  // numéro du paragraphe d'arrivée (victoire)

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

        AnalyseGraphe analyseGraphe = new AnalyseGraphe(graphe);
        analyseGraphe.afficherCheminsGagnants(1);
        AffichageGraphe List<Integer> chemin = graphe.plusCourtChemin(depart, arrivee);

        if (chemin.isEmpty()) {
            System.out.println("\nAucun chemin trouvé entre le paragraphe " + depart + " et le paragraphe " + arrivee);
        } else {
            System.out.println("\nChemin le plus court entre le paragraphe " + depart + " et le paragraphe " + arrivee + " :");
            for (int p : chemin) {
                System.out.print(p + " ");
            }
            System.out.println();
        }FR.afficher(graphe);
        livre.printAllParagraphs();*/

        Scanner scanner = new Scanner(System.in);
        LivreHero livre = new LivreHero();

        //  Étape 1 : Charger un fichier
        System.out.println("Entrez le chemin du fichier LDVEH :");
        String filePath = scanner.nextLine();
        livre.chargerDepuisFichier(filePath);
        System.out.println("Livre chargé avec succès !");

        GrapheLDVEH graphe = new GrapheLDVEH(livre);
        AnalyseGraphe analyseGraphe = new AnalyseGraphe(graphe);

        while (true) {
            // Menu interactif
            System.out.println("\n=== MENU ===");
            System.out.println("1. Afficher tous les paragraphes");
            System.out.println("2. Afficher un paragraphe spécifique");
            System.out.println("3. Afficher le graphe des paragraphes");
            System.out.println("4. Trouver les chemins menant à la victoire");
            System.out.println("5. Trouver le chemin le plus court entre deux paragraphes");
            System.out.println("6. Quitter");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
                case 1:
                    System.out.println("\n=== TOUS LES PARAGRAPHES ===");
                    livre.printAllParagraphs();
                    break;

                case 2:
                    System.out.print("\nEntrez le numéro du paragraphe à afficher : ");
                    int num = scanner.nextInt();
                    Paragraphe p = livre.getParagraphe(num);
                    if (p != null) {
                        System.out.println(p);
                    } else {
                        System.out.println("Paragraphe introuvable !");
                    }
                    break;

                case 3:

                    System.out.println("\n=== AFFICHAGE DU GRAPHE Fruchterman-Reingold ===");
                    AffichageGrapheFR.afficher(graphe);
                    break;



                case 4:
                    System.out.print("\nEntrez le numéro du paragraphe de départ : ");
                    int depart = scanner.nextInt();
                    analyseGraphe.afficherCheminsGagnants(depart);
                    break;

                case 5:
                    System.out.print("\nEntrez le numéro du paragraphe de départ : ");
                    int departId = scanner.nextInt();
                    System.out.print("\nEntrez le numéro du paragraphe d'arrivé : ");
                    int arriveeId = scanner.nextInt();
                    graphe.afficherplusCourtChemin(departId, arriveeId);   
                    break;

                case 6:
                    System.out.println("Au revoir !");
                    scanner.close();
                    return;

                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }

    }
}
