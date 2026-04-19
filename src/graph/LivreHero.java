/* LivreHero est le “chef d’orchestre” du livre.

Elle sert à :

lire un livre LDVEH depuis un fichier texte

créer tous les paragraphes

détecter les choix (liens)

détecter les objets récupérables

permettre de jouer / parcourir le livre

gérer l’inventaire du joueur

C’est le lien entre le fichier texte et le jeu/graphe.


 chargerDepuisFichier(String cheminFichier) 

 La plus importante

Elle fait ceci :

ouvre le fichier texte

lit ligne par ligne

quand elle voit un numéro → nouveau paragraphe

quand elle voit “rendez-vous au X” → crée un choix

quand elle voit “Vous trouvez un …” → crée un objet

range tout au bon endroit

 Après ça, le livre est entièrement chargé en mémoire.


 jouerParagraphe(int numero)

Sert à lire un paragraphe comme dans un jeu

affiche le texte

récupère les objets du paragraphe

les met dans l’inventaire

 C’est une première version de gameplay.


 getParagraphe(int numero)

 Permet d’aller chercher une page précise du livre.

 getParagraphes()

 Permet d’avoir tout le livre
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
    /**
   Crée un livre vide prêt à être chargé.
   @requires true
   @ensures paragraphes != NULL et vide
   @ensures inventaire != NULL
   @return un LivreHero initialisé
*/

    private Map<Integer, Paragraphe> paragraphes;
    private Inventaire inventaire;


    private String titre;
    private String auteur;
    private String langue;
    private int nombrePages;

    private static final Map<String, Integer> NOMBRES = new HashMap<>();

    static {
        NOMBRES.put("un", 1);
        NOMBRES.put("une", 1);
        NOMBRES.put("deux", 2);
        NOMBRES.put("trois", 3);
        NOMBRES.put("quatre", 4);
        NOMBRES.put("cinq", 5);
        NOMBRES.put("six", 6);
        NOMBRES.put("sept", 7);
        NOMBRES.put("huit", 8);
        NOMBRES.put("neuf", 9);
        NOMBRES.put("dix", 10);
        NOMBRES.put("onze", 11);
        NOMBRES.put("douze", 12);
    };

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
        texte = texte.trim().toLowerCase();

        try {
            return Integer.parseInt(texte);
        } catch (NumberFormatException e) {
            return NOMBRES.getOrDefault(texte, -1);
        }
    }

    private void appliquerEffets(Paragraphe p, Joueur joueur) {

        appliquerEffet(joueur, p.getPerteEndurance(), "ENDURANCE", false);
        appliquerEffet(joueur, p.getGainEndurance(), "ENDURANCE", true);

        appliquerEffet(joueur, p.getPerteHabilete(), "HABILETÉ", false);
        appliquerEffet(joueur, p.getGainHabilete(), "HABILETÉ", true);

        appliquerEffet(joueur, p.getPerteChance(), "CHANCE", false);
        appliquerEffet(joueur, p.getGainChance(), "CHANCE", true);
    }

    private void appliquerEffet(Joueur joueur, int valeur, String type, boolean gain) {
        if (valeur <= 0) return;

        if (type.equals("ENDURANCE")) {
            if (gain) joueur.gagnerEndurance(valeur);
            else joueur.perdreEndurance(valeur);
        }

        if (type.equals("HABILETÉ")) {
            if (gain) joueur.gagnerHabilete(valeur);
            else joueur.perdreHabilete(valeur);
        }

        if (type.equals("CHANCE")) {
            if (gain) joueur.gagnerChance(valeur);
            else joueur.perdreChance(valeur);
        }

        System.out.println((gain ? "Vous gagnez " : "Vous perdez ") + valeur + " " + type);
    }

    private void traiterEffets(String ligne, Paragraphe p) {

        Pattern pertePattern = Pattern.compile(
            "vous perdez\\s+(\\d+)\\s+point[s]?\\s+d['’](endurance|chance|habileté)",
            Pattern.CASE_INSENSITIVE
        );

        Pattern gainPattern = Pattern.compile(
            "vous gagnez\\s+(\\d+)\\s+point[s]?\\s+d['’](endurance|chance|habileté)",
            Pattern.CASE_INSENSITIVE
        );

        Matcher mPerte = pertePattern.matcher(ligne);
        if (mPerte.find()) {
            int valeur = Integer.parseInt(mPerte.group(1));
            String type = mPerte.group(2).toLowerCase();

            if (type.contains("endurance")) p.setPerteEndurance(valeur);
            else if (type.contains("chance")) p.setPerteChance(valeur);
            else if (type.contains("habilet")) p.setPerteHabilete(valeur);
        }

        Matcher mGain = gainPattern.matcher(ligne);
        if (mGain.find()) {
            int valeur = Integer.parseInt(mGain.group(1));
            String type = mGain.group(2).toLowerCase();

            if (type.contains("endurance")) p.setGainEndurance(valeur);
            else if (type.contains("chance")) p.setGainChance(valeur);
            else if (type.contains("habilet")) p.setGainHabilete(valeur);
        }
    }

    private void traiterConditions(String ligne, Paragraphe p) {

        Pattern objetConditionPattern = Pattern.compile(
            "si vous (poss[eé]dez|avez) (un|une|des|le|la|les)?\\s*(.+?)[,.]",
            Pattern.CASE_INSENSITIVE
        );

        Matcher mCond = objetConditionPattern.matcher(ligne);

        if (mCond.find() && mCond.groupCount() >= 2 && mCond.group(2) != null) {
            String objet = mCond.group(2).trim();
            p.setObjetRequis(objet);
        }
    }

    private void traiterChoix(String ligne, Paragraphe p) {

        Pattern choixPattern = Pattern.compile(
            "rendez-vous au (\\d+)",
            Pattern.CASE_INSENSITIVE
        );

        Matcher mChoix = choixPattern.matcher(ligne);

        while (mChoix.find()) {
            int destination = Integer.parseInt(mChoix.group(1));
            p.ajouterChoix(new Choix("Aller au " + destination, destination));
        }
    }

    private void traiterMonstre(String ligne, Paragraphe p) {

        Pattern monstrePattern = Pattern.compile(
            "(.+)\\s+HABILET[ÉE]\\s*:\\s*(\\d+)\\s+ENDURANCE\\s*:\\s*(\\d+)",
            Pattern.CASE_INSENSITIVE
        );

        Matcher m = monstrePattern.matcher(ligne);

        if (m.find()) {
            String nom = m.group(1).trim();
            int habilete = Integer.parseInt(m.group(2));
            int endurance = Integer.parseInt(m.group(3));

            Monstre monstre = new Monstre(nom, habilete, endurance);
            p.setMonstre(monstre);
        }
    }
/**
   Charge un livre depuis un fichier texte.
   @param cheminFichier chemin du fichier source
   @requires cheminFichier != NULL
   @requires fichier lisible et format valide LDVEH
   @ensures tous les paragraphes sont créés en mémoire
   @ensures tous les choix sont reliés correctement
   @ensures tous les objets sont extraits et associés
   @ensures nombrePages == nombre de paragraphes chargés
*/
    public void chargerDepuisFichier(String cheminFichier) {
        boolean lectureObjets = false;
        StringBuilder blocObjets = new StringBuilder();

        try (Scanner scanner = new Scanner(new java.io.File(cheminFichier))) {

            Paragraphe paragrapheCourant = null;

            List<String> entete = new ArrayList<>();
            boolean enteteLu = false;
            Pattern numeroPattern = Pattern.compile("^(\\d+)");

            while (scanner.hasNextLine()) {

                String ligne = scanner.nextLine().trim();
                if (ligne.isEmpty()) continue;

                Matcher mNumero = numeroPattern.matcher(ligne);
                boolean estNumero = mNumero.find();

                if (!enteteLu) {
                    if (!estNumero) {
                        entete.add(ligne);
                        continue;
                    } else {
                        lireEntete(entete);
                        enteteLu = true;
                    }
                }

                if (estNumero) {
                    int numero = Integer.parseInt(mNumero.group(1));
                    paragrapheCourant = new Paragraphe(numero, "");
                    paragraphes.put(numero, paragrapheCourant);

                    String reste = ligne.substring(mNumero.end()).trim();
                    if (!reste.isEmpty()) {
                        paragrapheCourant.setTexte(reste + "\n");
                    }

                    continue;
                }

                if (paragrapheCourant == null) continue;

                traiterConditions(ligne, paragrapheCourant);
                traiterEffets(ligne, paragrapheCourant);
                traiterChoix(ligne, paragrapheCourant);
                traiterMonstre(ligne, paragrapheCourant);

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

            
            nombrePages = paragraphes.size();

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du fichier : " + e.getMessage());
            e.printStackTrace();
        }
    }


   /*Affiche le texte du paragraphe.
     Ajoute tous les objets du paragraphe à l’inventaire du joueur.

     Affiche les choix disponibles. */
/**
   Permet de jouer un paragraphe.
   @param numero identifiant du paragraphe
   @param joueur joueur courant
   @requires joueur != NULL
   @requires paragraphe existe ou NULL
   @ensures affiche le texte du paragraphe
   @ensures ajoute les objets au joueur
   @ensures applique les effets du paragraphe
   @ensures vérifie combat et conditions
   @return la liste des choix disponibles ou liste vide
*/
    public List<Choix> jouerParagraphe(int numero, Joueur joueur) {

        Paragraphe p = paragraphes.get(numero);

        if (p == null) {
            System.out.println("Paragraphe inexistant !");
            return Collections.emptyList();
        }

        System.out.println("\n--- Paragraphe " + p.getId() + " ---");
        System.out.println(p.getTexte());

       
        for (Objet o : p.getObjets()) {
            joueur.getInventaire().ajouterObjet(o);
            System.out.println("Vous récupérez : " + o.getNom() + " x" + o.getNombre());
        }

        
        appliquerEffets(p, joueur);

       
        if (p.getMonstre() != null) {
            Monstre m = p.getMonstre();

            System.out.println("⚔️ Combat contre : " + m.getNom());

            if (joueur.getEndurance() < m.getEndurance()) {
                System.out.println(" Vous êtes trop faible !");
                return Collections.emptyList(); 
            } else {
                System.out.println(" Vous battez le monstre !");
            }
        }

        
        if (p.getObjetRequis() != null &&
            !joueur.getInventaire().possedeObjet(p.getObjetRequis())) {

            System.out.println(" Objet requis : " + p.getObjetRequis());
            return Collections.emptyList(); 
        }

       
        afficherChoix(p);

        return p.getChoixDisponibles(); 
    }

    private void afficherChoix(Paragraphe p) {
        if (!p.getChoixDisponibles().isEmpty()) {
            System.out.println("\nChoix possibles :");
            for (Choix c : p.getChoixDisponibles()) {
                System.out.println(" - " + c.getDestination());
            }
        }
    }                                    
}
