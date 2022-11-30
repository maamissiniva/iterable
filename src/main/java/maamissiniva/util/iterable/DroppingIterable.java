package maamissiniva.util.iterable;

import java.util.Iterator;

import maamissiniva.util.MaamIterable;

public class DroppingIterable<A> implements MaamIterable<A> {

    private final Iterable<A> i;

    private final int count;

    public DroppingIterable(Iterable<A> i, int count) {
        this.i = i;
        this.count = count;
    }

    @Override
    public Iterator<A> iterator() {
        Iterator<A> it = i.iterator();
        for (int j = 0; j < count && it.hasNext(); j++)
            it.next();
        return it;
    }

}
