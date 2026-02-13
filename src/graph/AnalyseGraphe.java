package graph;

import java.util.*;

public class AnalyseGraphe {

    private GrapheLDVEH graphe;
    private Set<Integer> victoires;

    public AnalyseGraphe(GrapheLDVEH graphe) {
        this.graphe = graphe;
        this.victoires = detecterParagraphesDeVictoire(); // détection automatique
    }

    /**
     * 🔹 Détecte automatiquement les paragraphes de victoire
     *     Ce sont les paragraphes qui n'ont aucun choix disponible
     */
    private Set<Integer> detecterParagraphesDeVictoire() {
        Set<Integer> gagnants = new HashSet<>();
        for (NoeudGraphe n : graphe.getNoeuds().values()) {
            if (n.paragraphe.getChoixDisponibles().isEmpty()) {
                gagnants.add(n.paragraphe.getId());
            }
        }
        return gagnants;
    }

    /**
     * 🔹 Trouve tous les chemins gagnants à partir d'un paragraphe de départ
     */
    public List<List<Integer>> trouverCheminsGagnants(int depart) {
        List<List<Integer>> chemins = new ArrayList<>();
        Stack<Integer> cheminActuel = new Stack<>();
        explorerChemins(depart, cheminActuel, chemins);
        return chemins;
    }

    /**
     * 🔹 Fonction récursive pour explorer tous les chemins
     */
    private void explorerChemins(int courantId, Stack<Integer> cheminActuel, List<List<Integer>> chemins) {
        cheminActuel.push(courantId);

        // Si on atteint une victoire, on ajoute le chemin
        if (victoires.contains(courantId)) {
            chemins.add(new ArrayList<>(cheminActuel));
        } else {
            // Explorer tous les voisins
            NoeudGraphe courant = graphe.getNoeuds().get(courantId);
            if (courant != null) {
                for (NoeudGraphe voisin : courant.voisins) {
                    if (!cheminActuel.contains(voisin.paragraphe.getId())) {
                        explorerChemins(voisin.paragraphe.getId(), cheminActuel, chemins);
                    }
                }
            }
        }

        cheminActuel.pop();
    }

    /**
     * 🔹 Affiche tous les chemins gagnants depuis un départ
     */
    public void afficherCheminsGagnants(int depart) {
        List<List<Integer>> chemins = trouverCheminsGagnants(depart);

        if (chemins.isEmpty()) {
            System.out.println("Aucun chemin gagnant trouvé depuis le paragraphe " + depart);
        } else {
            System.out.println("Chemins gagnants depuis le paragraphe " + depart + " :");
            for (List<Integer> chemin : chemins) {
                System.out.println(chemin);
            }
        }
    }
}
