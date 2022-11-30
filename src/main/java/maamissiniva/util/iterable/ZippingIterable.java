package maamissiniva.util.iterable;

import java.util.Iterator;

import maamissiniva.function.coc.T2;
import maamissiniva.util.MaamIterable;

public class ZippingIterable<A,B> implements MaamIterable<T2<A,B>> {

    private final Iterable<A> ia;

    private final Iterable<B> ib;

    public ZippingIterable(Iterable<A> ia, Iterable<B> ib) {
        this.ia = ia;
        this.ib = ib;
    }

    @Override
    public Iterator<T2<A, B>> iterator() {
        return new ZippingIterator<>(ia.iterator(), ib.iterator());			
    }

}
