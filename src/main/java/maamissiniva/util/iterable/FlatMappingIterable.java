package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.function.Function;

import maamissiniva.util.MaamIterable;


public class FlatMappingIterable<A,B> implements MaamIterable<B> {

    private final Iterable<A> i;

    private final Function<A, ? extends Iterable<B>> f;

    public FlatMappingIterable(Iterable<A> i, Function<A, ? extends Iterable<B>> f) {
        this.i = i;
        this.f = f;
    }

    @Override
    public Iterator<B> iterator() {
        return new FlatMappingIterator<>(i.iterator(), f);
    }

}
