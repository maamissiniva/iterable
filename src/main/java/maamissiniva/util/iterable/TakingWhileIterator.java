package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class TakingWhileIterator<A> implements Iterator<A> {

    private Predicate<A> p;
    private Iterator<A> i;
    private Optional<A> next;
    
    public TakingWhileIterator(Iterator<A> i, Predicate<A> p) {
        this.i    = i;
        this.p    = p;
        this.next = null;
    }

    @Override
    public boolean hasNext() {
        if (next == null) 
            computeNext();
        return next.isPresent();
    }

    @Override
    public A next() {
        if (next == null)
            computeNext();
        if (! next.isPresent())
            throw new NoSuchElementException();
        A a = next.get();
        next = null;
        return a;
    }

    private void computeNext() {
        if (! i.hasNext()) 
            next = Optional.empty();
        A a = i.next();
        next = p.test(a)
                ? Optional.of(a)
                : Optional.empty();
    }
    
}
