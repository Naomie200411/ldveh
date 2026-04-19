package graph;

import java.util.*;
import java.io.File;

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
        }  // Menu interactif     //  Étape 1 : Charger un fichier
        String filePath;

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

        Random rand = new Random();
        int endurance = rand.nextInt(6) + rand.nextInt(6) + 12; 
        int habilete = rand.nextInt(6) + 6; 
        int chance = rand.nextInt(6) + 6;
        //Joueur joueur = new Joueur(endurance, habilete, chance);
        Joueur joueur = new Joueur(2, 1, 1);

        Scanner scanner = new Scanner(System.in);
        LivreHero livre = new LivreHero();
        String filePath;
       
        while (true) {
            System.out.println("Entrez le chemin du fichier LDVEH :");
            filePath = scanner.nextLine();

            File file = new File(filePath);

            if (file.exists() && file.isFile()) {
                try {
                    livre.chargerDepuisFichier(filePath);
                    System.out.println("Livre chargé avec succès !");
                    break; 
                } catch (Exception e) {
                    System.out.println("Erreur lors du chargement du fichier.");
                }
            } else {
                System.out.println("Le fichier n'existe pas. Réessayez.");
            }
        }

        GrapheLDVEH graphe = new GrapheLDVEH(livre);
        AnalyseGraphe analyseGraphe = new AnalyseGraphe(graphe);
        List<Choix> choixPossibles = new ArrayList<>();
        int first = 0;

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Afficher tous les paragraphes");
            System.out.println("2. Afficher un paragraphe spécifique");
            System.out.println("3. Afficher le graphe des paragraphes");
            System.out.println("4. Trouver les chemins menant à la victoire");
            System.out.println("5. Trouver le chemin le plus court entre deux paragraphes");
            System.out.println("6. Votre inventaire d'objets");
            System.out.println("7. Quitter");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
                case 1:
                    System.out.println("\n=== TOUS LES PARAGRAPHES ===");
                    livre.printAllParagraphs();
                    break;

                case 2:
                                        // 🔹 FIN DU JEU
                    if (choixPossibles.isEmpty() && first > 0) {
                        System.out.println("🏁 Fin du jeu !");
                        return;
                    }

                    int num = 0;

                    // 🔹 PREMIER PARAGRAPHE
                    if (first == 0) {
                        System.out.print("Entrez le numéro du paragraphe : ");
                        num = scanner.nextInt();
                    }
                    // 🔹 SINON on force un choix
                    else {
                        System.out.print("Choisissez votre destination : ");
                        int destination = scanner.nextInt();

                        boolean valide = false;
                        for (Choix c : choixPossibles) {
                            if (c.getDestination() == destination) {
                                valide = true;
                                num = destination;
                                break;
                            }
                        }

                        if (!valide) {
                            System.out.println(" Choix invalide !");
                            break;
                        }
                    }

                   
                    choixPossibles = livre.jouerParagraphe(num, joueur);

                    first++;
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
                    System.out.print("\n");
                    livre.getInventaire().afficherInventaire();
                    break;

                case 7:
                    System.out.println("Au revoir !");
                    scanner.close();
                    return;

                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }

    }
}
