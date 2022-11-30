package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.function.Supplier;

import maamissiniva.util.MaamIterable;

public class RepeatingSupplierIterable<A> implements MaamIterable<A> {

    private final Supplier<A> s;

    public RepeatingSupplierIterable(Supplier<A> s) {
        this.s = s;
    }

    @Override
    public Iterator<A> iterator() {
        return new RepeatingIterator<>(s);
    }

    private static final class RepeatingIterator<A> implements Iterator<A> {

        private final Supplier<A> s;

        RepeatingIterator(Supplier<A> s) {
            this.s = s;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public A next() {
            return s.get();
        }

    }

}
