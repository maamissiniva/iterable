package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntRangeIterator implements Iterator<Integer> {

    private int current;
    private int to;

    public IntRangeIterator(int from, int to) {
        this.current = from - 1;
        this.to      = to;
    }

    @Override
    public boolean hasNext() {
        return current < to;
    }

    @Override
    public Integer next() {
        current += 1;
        if (current > to)
            throw new NoSuchElementException("no element beyond " + to);
        return current;
    }

}