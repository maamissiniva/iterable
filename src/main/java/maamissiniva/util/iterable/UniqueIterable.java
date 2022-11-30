package maamissiniva.util.iterable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import maamissiniva.util.Equatabler;
import maamissiniva.util.Hasher;
import maamissiniva.util.HashingWrapper;
import maamissiniva.util.MaamIterable;

public class UniqueIterable<A> implements MaamIterable<A> {

    private final Iterable<A> src;
    private final Hasher<A> hasher;
    private final Equatabler<A> equatabler;
    public UniqueIterable(Iterable<A> src, Hasher<A> hasher, Equatabler<A> equatabler) {
        this.src = src;
        this.hasher = hasher;
        this.equatabler = equatabler;
    }
    @Override
    public Iterator<A> iterator() {
        return new UniqueIterable.UniqueIterator<>(src.iterator(), hasher, equatabler);
    }
    
    static class UniqueIterator<A> implements Iterator<A> {
        private final Iterator<A> src;
        private final Hasher<A> hasher;
        private final Equatabler<A> equatabler;
        private final HashSet<HashingWrapper<A>> seen;
        private Optional<A> next;
        UniqueIterator(Iterator<A> src, Hasher<A> hasher, Equatabler<A> equatabler) {
            this.src = src;
            this.hasher = hasher;
            this.equatabler = equatabler;
            seen = new HashSet<>();
            computeNext();
        }
       
        private void computeNext() {
            next = Optional.empty();
            while (src.hasNext() && ! next.isPresent()) {
                A a = src.next();
                HashingWrapper<A> w = new HashingWrapper<>(hasher, equatabler, a);
                if (! seen.contains(w)) {
                    seen.add(w);
                    next = Optional.of(a);
                }
            }
        }
        
        @Override
        public boolean hasNext() {
            return next.isPresent();
        }

        @Override
        public A next() {
            A a = next.get();
            computeNext();
            return a;
        }

    }
    
}