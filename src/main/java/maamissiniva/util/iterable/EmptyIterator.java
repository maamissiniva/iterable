package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EmptyIterator<A> implements Iterator<A> {
    
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public A next() {
        throw new NoSuchElementException();
    }
    
}
