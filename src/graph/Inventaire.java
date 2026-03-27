/* Classe Inventaire
 À quoi ça sert ?

Garder tous les objets que tu as trouvés.

C’est ton sac.*/


package graph;

import java.util.HashMap;
import java.util.Map;

class Inventaire {
    private Map<String, Objet> objets;

    public Inventaire() {
        this.objets = new HashMap<>();
    }

    // Ajouter un objet à l'inventaire
    public void ajouterObjet(Objet objet) {
        if (objets.containsKey(objet.getNom())) {
            Objet existant = objets.get(objet.getNom());
            existant.ajouterNombre(objet.getNombre());
        } else {
            objets.put(objet.getNom(), objet);
        }
    }

    // Retirer un objet de l'inventaire
    public void retirerObjet(String nomObjet, int quantite) {
        if (objets.containsKey(nomObjet)) {
            Objet objet = objets.get(nomObjet);

            if (objet.getNombre() > quantite) {
                objet.ajouterNombre(-quantite); // on diminue
            } else {
                objets.remove(nomObjet); // plus rien → on supprime
            }
        } else {
            System.out.println("Objet non présent dans l'inventaire.");
        }
    }

    // Vérifier si un objet est présent
    public boolean possedeObjet(String nomObjet) {
        return objets.containsKey(nomObjet);
    }

    // Afficher les objets de l'inventaire
    public void afficherInventaire() {
        if (objets.isEmpty()) {
            System.out.println("Inventaire vide.");
        } else {
            System.out.println("Inventaire :");
            for (Objet objet : objets.values()) {
                System.out.println("- " + objet.getNom() + " x" + objet.getNombre());
            }
        }
    }
}