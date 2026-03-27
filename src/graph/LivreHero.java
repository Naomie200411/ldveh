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

    public void printAllParagraphs() {
        for (Paragraphe p : paragraphes.values()) {
            System.out.println(p); 
        }
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

    private void extraireObjetsDepuisBloc(String bloc, Paragraphe paragrapheCourant) {
        bloc = bloc.toLowerCase().trim();

        bloc = bloc.replace("\n", " ");
        bloc = bloc.replace(".", "");
        bloc = bloc.replace("ainsi qu'", ", un ");
        bloc = bloc.replace("ainsi que", ", ");

        if (bloc.contains("vous placez")) {
            bloc = bloc.substring(0, bloc.indexOf("vous placez")).trim();
        }

        String[] morceaux = bloc.split(",");

        for (String morceau : morceaux) {
            morceau = morceau.trim();

            if (morceau.isEmpty()) continue;
            if (morceau.contains("rendez-vous")) continue;
            if (morceau.contains("si vous choisissez")) continue;
            if (morceau.contains("à l'exception")) continue;
            if (morceau.length() < 3) continue;

            String[] partiesEt = morceau.split("\\s+et\\s+");

            for (String partie : partiesEt) {
                partie = partie.trim();
                if (partie.isEmpty()) continue;

                String[] mots = partie.split("\\s+");
                int nombre = 1;
                String nomObjet = partie;

                if (mots.length >= 1) {
                    int n = convertirNombre(mots[0]);
                    if (n != -1) {
                        nombre = n;
                        nomObjet = partie.substring(mots[0].length()).trim();
                    }
                }

                nomObjet = nomObjet.replaceFirst("^un\\s+", "");
                nomObjet = nomObjet.replaceFirst("^une\\s+", "");
                nomObjet = nomObjet.replaceFirst("^des\\s+", "");
                nomObjet = nomObjet.trim();

                if (!nomObjet.isEmpty()) {
                    paragrapheCourant.ajouterObjet(
                        new Objet(nomObjet, "Objet " + nomObjet + " trouvé !", nombre)
                    );
                }
            }
        }
    }

    private int convertirNombre(String texte) {

        Map<String, Integer> nombres = new HashMap<>();
        nombres.put("un", 1);
        nombres.put("une", 1);
        nombres.put("deux", 2);
        nombres.put("trois", 3);
        nombres.put("quatre", 4);
        nombres.put("cinq", 5);
        nombres.put("six", 6);
        nombres.put("sept", 7);
        nombres.put("huit", 8);
        nombres.put("neuf", 9);
        nombres.put("dix", 10);
        nombres.put("onze", 11);
        nombres.put("douze", 12);
        nombres.put("treize", 13);
        nombres.put("quatorze", 14);
        nombres.put("quinze", 15);
        nombres.put("seize", 16);
        nombres.put("vingt", 20);
        nombres.put("trente", 30);

        texte = texte.trim().toLowerCase();

        try {
            return Integer.parseInt(texte);
        } catch (NumberFormatException e) {
            return nombres.getOrDefault(texte, -1);
        }
    }

    public void chargerDepuisFichier(String cheminFichier) {
        boolean lectureObjets = false;
        StringBuilder blocObjets = new StringBuilder();

        try (Scanner scanner = new Scanner(new java.io.File(cheminFichier))) {

            Paragraphe paragrapheCourant = null;

            // ➕ pour l'en-tête
            List<String> entete = new ArrayList<>();
            boolean enteteLu = false;

            Pattern numeroPattern = Pattern.compile("^\\d+$");         
            Pattern choixPattern = Pattern.compile("rendez-vous au (\\d+)", Pattern.CASE_INSENSITIVE);
            Pattern objetPattern = Pattern.compile("Vous trouvez (un|une|des)\\s+(.+?)([.!?])?$", Pattern.CASE_INSENSITIVE);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine().trim();
                if (ligne.isEmpty()) continue;

                Matcher mNumero = numeroPattern.matcher(ligne);

                if (!enteteLu && !mNumero.matches()) {
                    entete.add(ligne);
                    continue;
                }

                if (!enteteLu) {
                    lireEntete(entete);
                    enteteLu = true;
                }

                if (mNumero.matches()) {
                    int numero = Integer.parseInt(ligne);
                    paragrapheCourant = new Paragraphe(numero, "");
                    paragraphes.put(numero, paragrapheCourant);
                    lectureObjets = false;
                    continue;
                }

                if (paragrapheCourant == null) continue;

                Matcher mChoix = choixPattern.matcher(ligne);
                while (mChoix.find()) {
                    int destination = Integer.parseInt(mChoix.group(1));
                    paragrapheCourant.ajouterChoix(
                        new Choix("Aller au " + destination, destination)
                    );
                }

                if (ligne.toLowerCase().contains("vous trouvez")) {
                    lectureObjets = true;

                    int pos = ligne.toLowerCase().indexOf("vous trouvez");
                    String suite = ligne.substring(pos + "vous trouvez".length()).trim();

                    if (!suite.isEmpty()) {
                        blocObjets.append(" ").append(suite);
                    }
                    continue;
                }

                if (lectureObjets) {
                    // si on arrive aux choix, on arrête la lecture des objets
                    if (ligne.toLowerCase().contains("rendez-vous au") ||
                        ligne.toLowerCase().contains("si vous choisissez")) {

                        extraireObjetsDepuisBloc(blocObjets.toString(), paragrapheCourant);
                        blocObjets.setLength(0);
                        lectureObjets = false;
                    } else {
                        blocObjets.append(" ").append(ligne);
                        continue;
                    }
                }

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
            //System.out.println("Vous récupérez : " + o.getNom() + " x" + o.getNombre());
        }

        if (!p.getChoixDisponibles().isEmpty()) {
            System.out.println("\nChoix possibles :");
            for (Choix c : p.getChoixDisponibles()) {
                System.out.println(" - " + c.getDestination());
            }
        }
    }
}
