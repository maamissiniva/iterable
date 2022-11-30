package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.function.Function;

public class MappingIterator<A,B> implements Iterator<B> {

    private final Function<A,B> f;

    private Iterator<A> i;

    public MappingIterator(Function<A,B> f, Iterator<A> i) {
        this.f = f;
        this.i = i;
    }

    @Override
    public boolean hasNext() {
        return i.hasNext();
    }

    @Override
    public B next() {
        return f.apply(i.next());
    }

}
