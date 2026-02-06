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




 
paragraphes → contient tous les paragraphes du livre.

La clé est le numéro du paragraphe, la valeur est l’objet Paragraphe.

LinkedHashMap est utilisé pour préserver l’ordre d’insertion.

inventaire → l’inventaire du joueur, qui stocke les objets récupérés.

titre, auteur, langue, nombrePages → métadonnées du livre.
 
 */
package graph;

import java.util.*;
import java.util.regex.*;

public class LivreHero implements Iterable<Paragraphe> {

    private Map<Integer, Paragraphe> paragraphes;
    private Inventaire inventaire;


    private String titre;
    private String auteur;
    private String langue;
    private int nombrePages;

    public LivreHero() {
        paragraphes = new LinkedHashMap<>();
        inventaire = new Inventaire();
    }


    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public String getLangue() { return langue; }
    public int getNombrePages() { return nombrePages; }



    public Paragraphe getParagraphe(int numero) {
        return paragraphes.get(numero);
    }

    public Map<Integer, Paragraphe> getParagraphes() {
        return paragraphes;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }



    @Override
    public Iterator<Paragraphe> iterator() {
        return new LivreHeroIterator(paragraphes);
    }


    private void lireEntete(List<String> lignes) {
        if (lignes.size() > 0) auteur = lignes.get(0);
        if (lignes.size() > 1) titre = lignes.get(1);

        langue = "Inconnue";
        for (String l : lignes) {
            if (l.toLowerCase().contains("traduit")) {
                langue = "Français";
            }
        }
    }


    public void chargerDepuisFichier(String cheminFichier) {
        try (Scanner scanner = new Scanner(new java.io.File(cheminFichier))) {

            Paragraphe paragrapheCourant = null;

            // ➕ pour l'en-tête
            List<String> entete = new ArrayList<>();
            boolean enteteLu = false;

            Pattern numeroPattern = Pattern.compile("^\\d+$");          // ligne avec juste un numéro
            Pattern choixPattern = Pattern.compile("rendez-vous au (\\d+)", Pattern.CASE_INSENSITIVE);
            Pattern objetPattern = Pattern.compile("Vous trouvez un (.+)", Pattern.CASE_INSENSITIVE);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine().trim();
                if (ligne.isEmpty()) continue;

                Matcher mNumero = numeroPattern.matcher(ligne);

                // 🔹 EN-TÊTE DU LIVRE (avant le paragraphe 1)
                if (!enteteLu && !mNumero.matches()) {
                    entete.add(ligne);
                    continue;
                }

                // 🔹 fin de l'en-tête
                if (!enteteLu) {
                    lireEntete(entete);
                    enteteLu = true;
                }

                // ===== TA LOGIQUE ORIGINALE (inchangée) =====

                // Nouveau paragraphe
                if (mNumero.matches()) {
                    int numero = Integer.parseInt(ligne);
                    paragrapheCourant = new Paragraphe(numero, "");
                    paragraphes.put(numero, paragrapheCourant);
                    continue;
                }

                if (paragrapheCourant == null) continue;

                // Détecter un choix
                Matcher mChoix = choixPattern.matcher(ligne);
                while (mChoix.find()) {
                    int destination = Integer.parseInt(mChoix.group(1));
                    paragrapheCourant.ajouterChoix(
                        new Choix("Aller au " + destination, destination)
                    );
                }

                // Détecter un objet
                Matcher mObjet = objetPattern.matcher(ligne);
                if (mObjet.find()) {
                    String nomObjet = mObjet.group(1);
                    paragrapheCourant.ajouterObjet(
                        new Objet(nomObjet, "Objet " + nomObjet + " trouvé!")
                    );
                }

                // Ajouter le texte au paragraphe
                paragrapheCourant.setTexte(
                    paragrapheCourant.getTexte() + ligne + "\n"
                );
            }

            // ➕ nombre de pages = nombre de paragraphes
            nombrePages = paragraphes.size();

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du fichier : " + e.getMessage());
            e.printStackTrace();
        }
    }


   /*Affiche le texte du paragraphe.
     Ajoute tous les objets du paragraphe à l’inventaire du joueur.

     Affiche les choix disponibles. */

    public void jouerParagraphe(int numero) {
        Paragraphe p = paragraphes.get(numero);

        if (p == null) {
            System.out.println("Paragraphe inexistant !");
            return;
        }

        System.out.println("\n--- Paragraphe " + p.getId() + " ---");
        System.out.println(p.getTexte());

        for (Objet o : p.getObjets()) {
            inventaire.ajouterObjet(o);
            System.out.println("Vous récupérez : " + o.getNom());
        }

        if (!p.getChoixDisponibles().isEmpty()) {
            System.out.println("\nChoix possibles :");
            for (Choix c : p.getChoixDisponibles()) {
                System.out.println(" - " + c.getDescription());
            }
        }
    }
}
