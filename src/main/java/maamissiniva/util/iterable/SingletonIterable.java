package maamissiniva.util.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

import maamissiniva.util.MaamIterable;

public class SingletonIterable<A> implements MaamIterable<A> {

    public final A a;
    
    public SingletonIterable(A a) {
        this.a = a;
    }
    
    @Override
    public Iterator<A> iterator() {
        return new Iterator<A>() {
            boolean hasNext = true;
            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public A next() {
                if (hasNext) {
                    hasNext = false;
                    return a;
                }
                throw new NoSuchElementException();
            }
            
        };
    }
    
}
