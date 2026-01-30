/* LivreHero est le “chef d’orchestre” du livre.

Elle sert à :

lire un livre LDVEH depuis un fichier texte

créer tous les paragraphes

détecter les choix (liens)

détecter les objets récupérables

permettre de jouer / parcourir le livre

gérer l’inventaire du joueur

C’est le lien entre le fichier texte et le jeu/graphe.


🔹 chargerDepuisFichier(String cheminFichier) ⭐

👉 La plus importante

Elle fait ceci :

ouvre le fichier texte

lit ligne par ligne

quand elle voit un numéro → nouveau paragraphe

quand elle voit “rendez-vous au X” → crée un choix

quand elle voit “Vous trouvez un …” → crée un objet

range tout au bon endroit

💡 Après ça, le livre est entièrement chargé en mémoire.


🔹 jouerParagraphe(int numero)

👉 Sert à lire un paragraphe comme dans un jeu

affiche le texte

récupère les objets du paragraphe

les met dans l’inventaire

➡️ C’est une première version de gameplay.


🔹 getParagraphe(int numero)

👉 Permet d’aller chercher une page précise du livre.

🔹 getParagraphes()

👉 Permet d’avoir tout le livre
(utilisé pour le graphe et les analyses)    
 
 
 */
package graph;

import java.util.*;
import java.util.regex.*;

public class LivreHero {
    private Map<Integer, Paragraphe> paragraphes; // Tous les paragraphes du livre
    private Inventaire inventaire;                // Inventaire du joueur

    public LivreHero() {
        paragraphes = new HashMap<>();
        inventaire = new Inventaire();
    }

    // Retourne tous les paragraphes
    public Map<Integer, Paragraphe> getParagraphes() {
        return paragraphes;
    }

    // Retourne un paragraphe précis
    public Paragraphe getParagraphe(int numero) {
        return paragraphes.get(numero);
    }

    // Retourne l'inventaire du joueur
    public Inventaire getInventaire() {
        return inventaire;
    }

    // Charger le livre depuis un fichier texte
    public void chargerDepuisFichier(String cheminFichier) {
        try (Scanner scanner = new Scanner(new java.io.File(cheminFichier))) {
            Paragraphe paragrapheCourant = null;

            Pattern numeroPattern = Pattern.compile("^\\d+$");          // ligne avec juste un numéro
            Pattern choixPattern = Pattern.compile("rendez-vous au (\\d+)"); // choix
            Pattern objetPattern = Pattern.compile("Vous trouvez un (.+)"); // objet

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine().trim();
                if (ligne.isEmpty()) continue; // ignorer les lignes vides

                // Nouveau paragraphe
                Matcher mNumero = numeroPattern.matcher(ligne);
                if (mNumero.matches()) {
                    int numero = Integer.parseInt(ligne);
                    paragrapheCourant = new Paragraphe(numero, ""); // texte vide pour l'instant
                    paragraphes.put(numero, paragrapheCourant);
                    continue;
                }

                if (paragrapheCourant == null) continue; // sécurité

                // Détecter un choix
                Matcher mChoix = choixPattern.matcher(ligne);
                while (mChoix.find()) {
                    int destination = Integer.parseInt(mChoix.group(1));
                    paragrapheCourant.ajouterChoix(new Choix("Aller au " + destination, destination));
                }

                // Détecter un objet
                Matcher mObjet = objetPattern.matcher(ligne);
                if (mObjet.find()) {
                    String nomObjet = mObjet.group(1);
                    paragrapheCourant.ajouterObjet(new Objet(nomObjet, "Objet " + nomObjet + " trouvé!")); // description vide
                }

                // Ajouter le texte au paragraphe
                paragrapheCourant.setTexte(paragrapheCourant.getTexte() + ligne + "\n");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du fichier : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Jouer un paragraphe : afficher le texte, récupérer les objets, afficher les choix
    public void jouerParagraphe(int numero) {
        Paragraphe p = paragraphes.get(numero);
        if (p == null) {
            System.out.println("Paragraphe inexistant !");
            return;
        }

        // Afficher le texte du paragraphe
        System.out.println("\n--- Paragraphe " + p.getId() + " ---");
        System.out.println(p.getTexte());

        // Ajouter les objets à l'inventaire
        for (Objet o : p.getObjets()) {
            inventaire.ajouterObjet(o);
            System.out.println("Vous récupérez : " + o.getNom());
        }

        // Afficher les choix disponibles
        if (!p.getChoixDisponibles().isEmpty()) {
            System.out.println("\nChoix possibles :");
            for (Choix c : p.getChoixDisponibles()) {
                System.out.println(" - " + c.getDescription());
            }
        } else {
            System.out.println("\nAucun choix disponible ici.");
        }
    }
}
