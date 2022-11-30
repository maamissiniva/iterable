package maamissiniva.util.iterable;

import java.util.Iterator;

import maamissiniva.function.coc.T2;

public class ZippingIterator<A,B> implements Iterator<T2<A,B>> {

    private Iterator<A> iat;
    private Iterator<B> ibt;

    public ZippingIterator(Iterator<A> iat, Iterator<B> ibt) {
        this.iat = iat;
        this.ibt = ibt;
    }

    @Override
    public boolean hasNext() {
        return iat.hasNext() && ibt.hasNext();	
    }

    @Override
    public T2<A, B> next() {
        return new T2<>(iat.next(), ibt.next());
    }

}
