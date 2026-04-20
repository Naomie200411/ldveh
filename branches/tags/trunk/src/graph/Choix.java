 /**
       Crée un choix reliant une description à une destination.
       @param description est le texte du choix
       @param destination est l’identifiant de la destination
       @requires description != NULL
       @requires destination >= 0
       @ensures this.description == description
       @ensures this.destination == destination
       @return un choix initialisé
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