package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class FilteringIterator<A, B> implements Iterator<B> {

    private final Iterator<A> i;

    private final Function<A, Optional<B>> f;

    private Optional<B> next;

    public FilteringIterator(Iterator<A> i, Function<A, Optional<B>> f) {
        this.i = i;
        this.f = f;
        computeNext();
    }

    private void computeNext() {
        next = Optional.empty();
        while ((! next.isPresent()) && i.hasNext())
            next = f.apply(i.next());				
    }

    @Override
    public boolean hasNext() {
        return next.isPresent();
    }

    @Override
    public B next() {
        Optional<B> value = next;
        computeNext();
        if (value.isPresent())
            return value.get();
        throw new NoSuchElementException();
    }

}
