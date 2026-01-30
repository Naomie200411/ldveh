/*La classe Choix sert à représenter une décision possible du lecteur dans un livre dont vous êtes le héros.

Autrement dit :

quand le texte dit
« Si tu veux ouvrir la porte, rends-toi au paragraphe 42 »
c’est un objet Choix.*/

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