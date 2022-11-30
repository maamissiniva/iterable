package maamissiniva.util.iterable;

import java.util.Iterator;

import maamissiniva.util.MaamIterable;

public class RepeatingIterable<A> implements MaamIterable<A> {

    private final Iterable<A> i;

    public RepeatingIterable(Iterable<A> i) {
        if (!i.iterator().hasNext())
            throw new IllegalArgumentException("iterator must have at least one element to repeat");
        this.i = i;
    }

    @Override
    public Iterator<A> iterator() {
        return new RepeatingIterator<>(i);
    }

    private static final class RepeatingIterator<A> implements Iterator<A> {

        private Iterable<A> i;

        private Iterator<A> it;

        RepeatingIterator(Iterable<A> i) {
            this.i = i;
            it = i.iterator();
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public A next() {
            if (it.hasNext()) 
                return it.next();
            it = i.iterator();
            return it.next();
        }

    }

}
