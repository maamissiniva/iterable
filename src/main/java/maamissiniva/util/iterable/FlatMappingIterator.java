package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.function.Function;

import maamissiniva.util.Iterables;

/**
 * Flat map iterator.
 *
 * @param <A> source element type
 * @param <B> target element type
 */
public class FlatMappingIterator<A,B> implements Iterator<B> { 

    private Iterator<A> ia;

    private Function<A, ? extends Iterable<B>> f;

    private Iterator<B> ib;

    public FlatMappingIterator(Iterator<A> ia, Function<A, ? extends Iterable<B>> f) {
        this.ia = ia;
        this.f  = f;
        ib = Iterables.emptyIterator();
    }

    @Override
    public boolean hasNext() {
        boolean ibHasNext = ib.hasNext();
        if (ibHasNext)
            return true;
        while ((! ibHasNext) && ia.hasNext()) {
            ib = f.apply(ia.next()).iterator();
            ibHasNext = ib.hasNext();
        }
        return ibHasNext;
    }

    @Override
    public B next() {
        return ib.next();
    }

}
