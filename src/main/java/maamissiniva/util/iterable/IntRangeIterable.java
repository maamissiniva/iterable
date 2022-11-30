package maamissiniva.util.iterable;

import java.util.Iterator;

import maamissiniva.util.MaamIterable;

public class IntRangeIterable implements MaamIterable<Integer> {

    private final int from;
    private final int to;

    public IntRangeIterable(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new IntRangeIterator(from, to);
    }

}
