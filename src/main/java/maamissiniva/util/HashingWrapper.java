package maamissiniva.util;

// This would allow as simple custom definition a la comparator.
// Natural hasher would be the object itself.
public class HashingWrapper<A> {
    
    private final Hasher<A> hasher;
    private final Equatabler<A> equatabler;
    private final A value;
    
    public HashingWrapper(Hasher<A> hasher, Equatabler<A> equatabler, A value) {
        this.hasher = hasher;
        this.value = value;
        this.equatabler = equatabler;
    }
    
    @Override
    public int hashCode() {
        return hasher.hashCode(value);
    }
    
    @Override
    public boolean equals(Object o) {
        if (! (o instanceof HashingWrapper))
            return false;
        try {
            @SuppressWarnings("unchecked")
            A a = (A)((HashingWrapper<?>)o).value;
            return equatabler.equals(value, a);
        } catch (ClassCastException e) {
            return false;
        }
    }
    
}
