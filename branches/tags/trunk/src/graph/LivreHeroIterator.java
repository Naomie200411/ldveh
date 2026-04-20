/*Cette classe est un itérateur personnalisé pour parcourir les paragraphes de
 LivreHero. 
 
 it → l’iterator réel sur paragraphes.values().

courant → garde une référence au dernier paragraphe retourné, utile pour remove().
*/

package graph;
/**
       Crée un itérateur sur les paragraphes d’un livre.
       @param paragraphes est la map des paragraphes du livre
       @requires paragraphes != NULL
       @requires paragraphes.values() != NULL
       @ensures it est initialisé sur les valeurs de paragraphes
       @ensures courant == NULL
       @return un itérateur prêt à parcourir les paragraphes
    */
import java.util.*;

public class LivreHeroIterator implements Iterator<Paragraphe> {

    private Iterator<Paragraphe> it;
    private Paragraphe courant;

    public LivreHeroIterator(Map<Integer, Paragraphe> paragraphes) {
        this.it = paragraphes.values().iterator();
        this.courant = null;
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public Paragraphe next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        courant = it.next();
        return courant;
    }

    @Override
    public void remove() {
        if (courant == null) {
            throw new IllegalStateException("next() doit être appelé avant remove()");
        }
        it.remove();
        courant = null;
    }
}
