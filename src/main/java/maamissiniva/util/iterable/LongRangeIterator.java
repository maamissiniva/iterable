package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class LongRangeIterator implements Iterator<Long> {

    private long current;
    private long to;
    private long step;
    
    public LongRangeIterator(long from, long to, long step) {
        this.current = from;
        this.to      = to;
        this.step    = step;
    }

    @Override
    public boolean hasNext() {
        return current <= to;
    }

    @Override
    public Long next() {
        if (step > 0 && current > to)
            throw new NoSuchElementException("no element beyond " + to);
        if (step < 0 && current < to)
            throw new NoSuchElementException("no element beyond " + to);
        long r = current;
        current += step;
        return r;
    }

}
