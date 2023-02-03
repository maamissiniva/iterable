package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class TakingUntilIterator<A> implements Iterator<A> {

    private Predicate<A> p;
    private Iterator<A> i;
    private Optional<A> next;
    private boolean done;
    
    public TakingUntilIterator(Iterator<A> i, Predicate<A> p) {
        this.i    = i;
        this.p    = p;
        this.next = null;
        this.done = false;
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
        if (done || ! i.hasNext()) {
            next = Optional.empty();
            return;
        }
        A a = i.next();
        if (p.test(a)) {
            next = Optional.of(a);
            done = true;
        } else {
            next = Optional.of(a);
        }
    }
    
}
