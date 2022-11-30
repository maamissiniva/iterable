package maamissiniva.util.iterable;

import java.util.Iterator;

import maamissiniva.util.MaamIterable;

public class LongRangeIterable implements MaamIterable<Long> {

    private final long from;
    private final long to;
    private final long step;
    
    public LongRangeIterable(long from, long to, long step) {
        this.from = from;
        this.to   = to;
        this.step = step;
    }

    @Override
    public Iterator<Long> iterator() {
        return new LongRangeIterator(from, to, step);
    }

}
