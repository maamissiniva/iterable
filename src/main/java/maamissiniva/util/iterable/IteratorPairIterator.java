package maamissiniva.util.iterable;

import java.util.Iterator;

public class IteratorPairIterator<A> implements Iterator<A> {
    
    private Iterator<A> ia;
    private Iterator<A> ib;
    private Iterator<A> current;
    
    public IteratorPairIterator(Iterator<A> ia, Iterator<A> ib) {
        this.ia = ia;
        this.ib = ib;
        current = ia;
    }

    @Override
    public boolean hasNext() {
        if (current == null)
            return false;
        if (! current.hasNext()) {
            if (current == ia) {
                current = ib;
                if (ib.hasNext()) {
                    return true;
                } else {
                    current = null;
                    return false;
                }
            } else {
                current = null;
                return false;
            }
        }
        return true;
    }

    @Override
    public A next() {
        return current.next();
    }

}
