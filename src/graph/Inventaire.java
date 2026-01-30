/* Classe Inventaire
 À quoi ça sert ?

Garder tous les objets que tu as trouvés.

C’est ton sac.*/*


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
        objets.put(objet.getNom(), objet);
    }

    // Retirer un objet de l'inventaire
    public void retirerObjet(String nomObjet) {
        objets.remove(nomObjet);
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
                System.out.println("- " + objet);
            }
        }
    }
}