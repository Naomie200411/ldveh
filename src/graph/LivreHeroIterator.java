package graph;

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
