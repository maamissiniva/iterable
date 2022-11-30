package maamissiniva.util.iterable;

import java.util.Iterator;

import maamissiniva.util.MaamIterable;

public class TakingIterable<A> implements MaamIterable<A> {

    private final Iterable<A> i;

    private final int count;

    public TakingIterable(Iterable<A> i, int count) {
        this.i = i;
        this.count = count;
    }

    @Override
    public Iterator<A> iterator() {
        return new TakingIterator<>(i.iterator(), count);
    }

}
