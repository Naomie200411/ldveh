/* Classe Inventaire
 À quoi ça sert ?

Garder tous les objets que tu as trouvés.

C’est ton sac.*/

/**
       Crée un inventaire vide.
       @requires true
       @ensures this.objets != NULL
       @ensures this.objets est vide
       @return un inventaire initialisé vide
    */


package graph;

import java.util.HashMap;
import java.util.Map;

class Inventaire {
    private Map<String, Objet> objets;

    public Inventaire() {
        this.objets = new HashMap<>();
    }
/**
       Ajoute un objet dans l’inventaire.
       @param objet est l’objet à ajouter
       @requires objet != NULL
       @requires objet.getNom() != NULL
       @requires objet.getNombre() > 0
       @ensures si l’objet existait déjà alors sa quantité est augmentée
       @ensures sinon un nouvel objet est ajouté dans l’inventaire
    */

 
    public void ajouterObjet(Objet objet) {
        if (objets.containsKey(objet.getNom())) {
            Objet existant = objets.get(objet.getNom());
            existant.ajouterNombre(objet.getNombre());
        } else {
            objets.put(objet.getNom(), objet);
        }
    }
/**
       Retire une quantité d’un objet de l’inventaire.
       @param nomObjet est le nom de l’objet à retirer
       @param quantite est la quantité à retirer
       @requires nomObjet != NULL
       @requires quantite > 0
       @ensures si l’objet existe et quantite < stock alors stock diminué
       @ensures si l’objet existe et quantite >= stock alors objet supprimé
       @ensures si l’objet n’existe pas alors aucun changement
    */
  
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
 /**
       Vérifie si un objet est présent dans l’inventaire.
       @param nomObjet est le nom de l’objet
       @requires nomObjet != NULL
       @ensures resultat == true si l’objet est présent
       @ensures resultat == false sinon
       @return true si l’objet est présent, false sinon
    */

    
    public boolean possedeObjet(String nomObjet) {
        return objets.containsKey(nomObjet);
    }

 /**
       Affiche le contenu de l’inventaire.
       @requires true
       @ensures si l’inventaire est vide alors affiche "Inventaire vide."
       @ensures sinon affiche la liste des objets avec leur quantité
    */
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