package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.function.Function;

import maamissiniva.util.MaamIterable;

public class MappingIterable<A,B> implements MaamIterable<B> {

    private final Iterable<A> i;

    private final Function<A,B> f;

    public MappingIterable(Iterable<A> i, Function<A,B> f) {
        this.i = i;
        this.f = f;
    }

    @Override
    public Iterator<B> iterator() {
        return new MappingIterator<>(f, i.iterator());
    }

}
