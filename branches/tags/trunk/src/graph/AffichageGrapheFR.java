package graph;
 /**
 * CONTRAT DE LA CLASSE AffichageGrapheFR
 
 */

/**
 * Affiche graphiquement un graphe LDVEH en utilisant l'algorithme de layout
 * automatique Fruchterman-Reingold de GraphStream.
 *
 * @param grapheLDVEH le graphe LDVEH à visualiser
 *
 * @pre grapheLDVEH != null
 * @pre grapheLDVEH.getNoeuds() != null
 * @pre tous les noeuds du graphe ont un paragraphe non null
 * @pre tous les paragraphes ont un id valide (non null)
 * @pre l’environnement supporte GraphStream avec interface Swing
 *
 * @post une fenêtre graphique est affichée à l’écran
 * @post le nombre de nœuds affichés = grapheLDVEH.getNoeuds().size()
 * @post chaque nœud affiché possède un label correspondant à son id
 * @post toutes les relations du graphe sont représentées par des arêtes
 * @post le layout Fruchterman-Reingold est activé
 *
 * @throws NullPointerException si grapheLDVEH == null
 * @throws NullPointerException si grapheLDVEH.getNoeuds() == null
 * @throws NullPointerException si un noeud contient un paragraphe null
*/
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.Viewer;




/*  Cette classe sert à visualiser le graphe LDVEH avec la bibliothèque GraphStream, 
    qui utilise un algorithme de placement automatique proche de Fruchterman–Reingold force-directed algorithm.

    les imports viennent de GraphStream :

    Graph → structure du graphe

    Node → un sommet

    Edge → une arête

    SingleGraph → implémentation simple d’un graphe

    Viewer → fenêtre graphique qui affiche le graphe
*/ 

public class AffichageGrapheFR {

    public static void afficher(GrapheLDVEH grapheLDVEH) {

        //On indique à GraphStream d’utiliser Swing pour afficher la fenêtre graphique.
        System.setProperty("org.graphstream.ui", "swing");
        
        /*
            On crée un graphe nommé LDVEH.
            Ce graphe est séparé de GrapheLDVEH.
            Ton programme a donc :
            GrapheLDVEH (logique)
                    ↓ conversion
            GraphStream Graph (visualisation)
        */ 

        Graph graph = new SingleGraph("LDVEH");

        // Style visuel
        /*  On définit le style :
            Noeuds
            couleur = bleu
            taille = 20px
            texte = id du paragraphe
            texte au-dessus du noeud
            Arêtes
            couleur = gris
            flèche = graphe orienté
         */
        
        graph.setAttribute("ui.stylesheet",
                "node { fill-color: blue; text-size: 16; text-color: black; size: 20px; text-alignment: above; }" +
                "edge { fill-color: gray; arrow-size: 10px, 5px; }");

        //Ajouter les noeuds
        for (NoeudGraphe n : grapheLDVEH.getNoeuds().values()) {
            String id = String.valueOf(n.paragraphe.getId());
            Node node = graph.addNode(id);
            node.setAttribute("ui.label", id);
        }

        //Ajouter les arêtes
        int edgeId = 0;

        for (NoeudGraphe n : grapheLDVEH.getNoeuds().values()) {
            String source = String.valueOf(n.paragraphe.getId());

            for (NoeudGraphe voisin : n.voisins) {
                String dest = String.valueOf(voisin.paragraphe.getId());

                String idEdge = "E" + edgeId++;

                if (graph.getNode(source) == null) continue;
                if (graph.getNode(dest) == null) {
                    System.out.println(" Noeud manquant: " + dest);
                    continue;
                }

                if (source.equals(dest)) continue;

                if (graph.getEdge(source + "_" + dest) != null) continue;

                graph.addEdge(source + "_" + dest, source, dest, true);
            }
        }

        //  Affichage avec layout automatique (Fruchterman-Reingold)
        Viewer viewer = graph.display();
        viewer.enableAutoLayout();
    }
}