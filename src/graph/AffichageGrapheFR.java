package graph;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.Viewer;

public class AffichageGrapheFR {

    public static void afficher(GrapheLDVEH grapheLDVEH) {

        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("LDVEH");

        // Style visuel
        graph.setAttribute("ui.stylesheet",
                "node { fill-color: blue; text-size: 16; text-color: black; size: 20px; text-alignment: above; }" +
                "edge { fill-color: gray; arrow-size: 10px, 5px; }");

        // 🔹 Ajouter les noeuds
        for (NoeudGraphe n : grapheLDVEH.getNoeuds().values()) {
            String id = String.valueOf(n.paragraphe.getId());
            Node node = graph.addNode(id);
            node.setAttribute("ui.label", id);
        }

        // 🔹 Ajouter les arêtes
        int edgeId = 0;

        for (NoeudGraphe n : grapheLDVEH.getNoeuds().values()) {
            String source = String.valueOf(n.paragraphe.getId());

            for (NoeudGraphe voisin : n.voisins) {
                String dest = String.valueOf(voisin.paragraphe.getId());

                String idEdge = "E" + edgeId++;

                if (graph.getEdge(idEdge) == null) {
                    graph.addEdge(idEdge, source, dest, true);
                }
            }
        }

        // 🔹 Affichage avec layout automatique (Fruchterman-Reingold)
        Viewer viewer = graph.display();
        viewer.enableAutoLayout();
    }
}