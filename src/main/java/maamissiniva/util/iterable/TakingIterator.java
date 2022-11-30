package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TakingIterator<A> implements Iterator<A> {

    private int remaining;

    private Iterator<A> i;

    public TakingIterator(Iterator<A> i, int count) {
        this.i         = i;
        this.remaining = count;
    }

    @Override
    public boolean hasNext() {
        return remaining > 0 && i.hasNext();
    }

    @Override
    public A next() {
        if (remaining <= 0)
            throw new NoSuchElementException();
        remaining --;
        return i.next();
    }

}
