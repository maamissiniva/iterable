package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

import maamissiniva.util.MaamIterable;


public class FilteringIterable<A, B> implements MaamIterable<B> {

    private Iterable<A> i;

    private Function<A, Optional<B>> f;

    public FilteringIterable(Iterable<A> i, Function<A, Optional<B>> f) {
        this.i = i;
        this.f = f;
    }

    @Override
    public Iterator<B> iterator() {
        return new FilteringIterator<>(i.iterator(), f);
    }

}
