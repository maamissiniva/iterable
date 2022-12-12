package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.function.Predicate;

import maamissiniva.util.MaamIterable;

public class TakingWhileIterable<A> implements MaamIterable<A> {

    private final Iterable<A> i;

    private final Predicate<A> p;

    public TakingWhileIterable(Iterable<A> i, Predicate<A> p) {
        this.i = i;
        this.p = p;
    }

    @Override
    public Iterator<A> iterator() {
        return new TakingWhileIterator<>(i.iterator(), p);
    }

}
