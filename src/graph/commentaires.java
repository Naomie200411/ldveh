package graph;

public class commentaires {
    /*La classe LinkedList en Java fait partie du Java 
    Collections Framework et met en œuvre les interfaces 
    List et Deque. Il s'agit d'une liste doublement liée
    qui fournit une structure de données dynamique pour 
    stocker des éléments. Contrairement aux tableaux, LinkedList permet d'insérer et de supprimer efficacement des éléments aux deux extrémités et au milieu de la liste.
    
    Paragraphe 10 → 16 ,11 , 20 ,50
    Paragraphe 16 → 1
    Paragraphe 11 → 2
    Paragraphe 20 → 1
    Paragraphe 50 → (victoire)

    départ 10 arrivée 50

    Tour 1

    Courant = 10

    Voisins = 16, 11, 20, 50

    Tous non visités → ajout à la queue

    Queue = [16,11,20,50]

    Visités = {10,16,11,20,50}

    Précédents = {16→10, 11→10, 20→10, 50→10}

    🔹 On trouve 50 immédiatement dans les voisins de 10*/



    /*
    Trouver tous les chemins gagnants à partir d'un départ

    un exemple concret avec les noeuds :   Paragraphe 10 → 16 ,11 , 20 ,50
    Paragraphe 16 → 1
    Paragraphe 11 → 2
    Paragraphe 20 → (victoire)
    Paragraphe 50 → (victoire) 10 est le départ 50 une victoire , 20 aussi une victoire les flèches à droite donnent les voisins de chauque noeud


    Le backtracking signifie :

    👉 "Je descends dans un chemin…
    si j’arrive au bout, je reviens en arrière pour essayer un autre chemin."

    C’est exactement comme dans un labyrinthe :

    Tu avances

    Tu arrives dans un cul-de-sac

    Tu reviens au dernier croisement

    Tu prends un autre chemin

    chemin.pop();

    javac -d build src/graph/*.java

    java -cp build graph.Main
    
    */
}


