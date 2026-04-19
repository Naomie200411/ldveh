/*La classe Choix sert à représenter une décision possible du lecteur dans un livre dont vous êtes le héros.

Autrement dit :

quand le texte dit
« Si tu veux ouvrir la porte, rends-toi au paragraphe 42 »
c’est un objet Choix.*/

/**
 * Invariant :
 * - description != null
 * - description non vide
 * - destination >= 0
 */

/**
 * Constructeur
 * Préconditions :
 * - description != null
 * - description non vide
 * - destination >= 0
 * Postconditions :
 * - this.description == description
 * - this.destination == destination
 */

/**
 * getDescription
 * Postconditions :
 * - retourne description (non null)
 */

/**
 * getDestination
 * Postconditions :
 * - retourne destination (>= 0)
 */

/**
 * toString
 * Postconditions :
 * - retourne une chaîne non nulle sous forme "description -> destination"
 */

package graph;
import java.util.List;

public class Choix {
    private String description;
    private int destination;

    public Choix(String description, int destination) {
        this.description = description;
        this.destination = destination;
    }

    public String getDescription() { return description; }
    public int getDestination() { return destination; }

    @Override
    public String toString() {
        return description + " -> " + destination;
    }
}