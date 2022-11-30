package maamissiniva.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import maamissiniva.function.coc.T2;
import maamissiniva.util.iterable.DroppingIterable;
import maamissiniva.util.iterable.EmptyIterator;
import maamissiniva.util.iterable.FilteringIterable;
import maamissiniva.util.iterable.FlatteningIterable;
import maamissiniva.util.iterable.IntercalatingIterable;
import maamissiniva.util.iterable.LongRangeIterable;
import maamissiniva.util.iterable.MappingIterable;
import maamissiniva.util.iterable.IntRangeIterable;
import maamissiniva.util.iterable.RepeatingIterable;
import maamissiniva.util.iterable.RepeatingSupplierIterable;
import maamissiniva.util.iterable.SingletonIterable;
import maamissiniva.util.iterable.TakingIterable;
import maamissiniva.util.iterable.UniqueIterable;
import maamissiniva.util.iterable.ZippingIterable;

/**
 * Custom iterable implementation that provide fluent java
 * implementation through {@link MaamIterable}. Functions do
 * not fail on null arguments.
 * <p>
 * Provides a standard iterable utility set like Apache or Guava but
 * provides a fluent API. 
 */
public class Iterables {

    private Iterables() {}

    /**
     * Build a MaamIterable (empty from null iterable) from 
     * an iterable (almost free).
     * @param <A> element type
     * @param i   iterable
     * @return    Traaser iterable
     */
    public static <A> MaamIterable<A> it(Iterable<A> i) {
        if (i == null)
            return Collections::emptyIterator;
        return i::iterator;
    }
    
    @SuppressWarnings("rawtypes")
    private static final Iterator<?> emptyIterator = new EmptyIterator();
    
    @SuppressWarnings("rawtypes")
    private static final MaamIterable<?> emptyIterable = new MaamIterable() {

        @Override
        public Iterator<?> iterator() {
            return emptyIterator;
        }
        
    };

    @SuppressWarnings("unchecked")
    public static <A> MaamIterable<A> empty() {
        return (MaamIterable<A>)emptyIterable;
    }
    
    public static <A> MaamIterable<A> singleton(A a) {
        return new SingletonIterable<>(a);
    }

    public static <A> MaamIterable<A> ar() {
        return empty();
    }
    public static <A> MaamIterable<A> ar(A a) {
        return singleton(a);
    }
    
    @SafeVarargs // safe as the arguments are passed to a safe function
    public static <A> MaamIterable<A> ar(A... as) {
        return it(Arrays.asList(as));
    }

    public static MaamIterable<Integer> range(int from) {
        return range(from, Integer.MAX_VALUE);
    }

    public static MaamIterable<Integer> range(int from, int to) {
        return new IntRangeIterable(from, to);
    }

    public static <A,B> MaamIterable<B> map(Iterable<A> i, Function<A,B> f) {
        if (i == null)
            return empty();
        return new MappingIterable<>(i, f);	
    }

    /**
     * Intercalate a fixed element between an iterable elements
     * (logically like {@link String#join(CharSequence, Iterable)} but
     * produces an iterable).
     * @param <A> element types
     * @param a   element to intercalate
     * @param i   iterable
     * @return    iterable with intercalated element
     */
    public static <A> MaamIterable<A> intercalate(A a, Iterable<A> i) {
        if (i == null)
            return empty();
        return new IntercalatingIterable<>(a,i);
    }

    static class IteratorArrayIterator<A> implements Iterator<A> {
        
        private int index;
        private Iterator<A>[] iterables;
        
        @SafeVarargs
        public IteratorArrayIterator(Iterator<A>... iterables) {
            this.index = 0;
            this.iterables = iterables;
        }
        
        @Override
        public boolean hasNext() {
            while (index < iterables.length) {
                if (iterables[index].hasNext())
                    return true;
                index ++;
            }
            return false;
        }
        
        @Override
        public A next() {
            if (index >= iterables.length)
                throw new NoSuchElementException();
            return iterables[index].next();
        }
        
    }
    
    /*
     * UNTESTED
     * @param <A>
     * @param is
     * @return
     */
    @SafeVarargs
    public static <A> Iterator<A> concatIt(Iterator<A>... is) {
        return new IteratorArrayIterator<>(is);
    }
    
    @SafeVarargs // safe as the arguments are passed to a safe function
    public static <A> MaamIterable<A> concat(Iterable<A>... is) {
        return flatMap(Arrays.asList(is), x -> x);
    }

    public static <A,B> MaamIterable<T2<A,B>> zip(Iterable<A> i, Iterable<B> j) {
        return new ZippingIterable<>(i, j);
    }

//    public static <A> MaamIterable<A> singleton(A a) {
//        return ar(a);
//    }
    
    /**
     * Zips two iterables producing a pair iterable. 
     * @param <A> first element type
     * @param <B> second element type
     * @param i first iterable
     * @param f second iterable
     * @return iterable of element pairs
     */
    public static <A,B> MaamIterable<B> zipIndex(Iterable<A> i, BiFunction<Integer,A,B> f) {
        return zipMap(ints(), i, f);
    }
    
    public static <A> MaamIterable<A> prepend(A a, Iterable<A> i) {
        if (i == null)
            return ar(a);
        return concat(ar(a), i);
    }

    public static <A> MaamIterable<A> append(Iterable<A> i, A a) {
        if (i == null)
            return ar(a);
        return concat(i, ar(a));
    }

    public static <A> MaamIterable<A> surround(A left, A between, A right, Iterable<A> i) {
        if (i == null)
            return ar(left, right);
        return intercalate(between, i)
                .prepend(left)
                .append(right);
    }

    public static <A,B,C> MaamIterable<C> zipMap(Iterable<A> i, Iterable<B> j, BiFunction<A,B,C> f) {
        return zip(i,j).map(t -> f.apply(t.a, t.b));
    }

    public static <A,B> MaamIterable<B> flatMap(Iterable<A> i, Function<A, ? extends Iterable<B>> f) {
        if (i == null)
            return empty();
        return new FlatteningIterable<>(i,f);
    }

    public static <A> MaamIterable<A> flatten(Iterable<? extends Iterable<A>> i) {
        return flatMap(i, x -> x);
    }
    
    
    public static <A> MaamIterable<A> filter(Iterable<A> i, Predicate<A> p) {
        if (i == null)
            return empty();
        return new FilteringIterable<>(i, x -> p.test(x) ? Optional.of(x) : Optional.empty());
    }

    /**
     * Map elements and keep only the non {@link Optional#empty()} ones. 
     * @param <A> source elements type
     * @param <B> mapped elements type
     * @param i   source iterable (null as empty)
     * @param f   mapping function
     * @return    elements that are mapped to non {@link Optional#empty()}
     */
    public static <A,B> MaamIterable<B> filterMap(Iterable<A> i, Function<A,Optional<B>> f) {
        if (i == null)
            return empty();
        return new FilteringIterable<>(i, f);
    }

    @SuppressWarnings("unchecked")
    public static <A,B extends A> MaamIterable<B> instancesOf(Iterable<A> i, Class<B> c) {
        if (i == null)
            return empty();
        return new FilteringIterable<>(i, x -> c.isAssignableFrom(x.getClass()) 
                ? Optional.of((B)x)
                        : Optional.empty());
    }

    /**
     * Keep at most count elements from an iterable.
     * @param <A>   element type
     * @param i     iterable
     * @param count elements to keep count
     * @return      truncated iterable
     */
    public static <A> MaamIterable<A> take(Iterable<A> i, int count) {
        if (i == null)
            return empty();
        return new TakingIterable<>(i,count);
    }

    /**
     * Drop (at most) cout element from an iterable.
     * @param <A>   element type
     * @param i     iterable 
     * @param count element count to drop
     * @return      iterable with 'count' first element removed
     */
    public static <A> MaamIterable<A> drop(Iterable<A> i, int count) {
        if (i == null)
            return empty();
        return new DroppingIterable<>(i, count);
    }

    /**
     * Infinite repetition of a sequence.
     * @param <A> element type
     * @param i   sequence to repeat
     * @return    infinite iterable
     */
    public static <A> MaamIterable<A> repeats(Iterable<A> i) {
        if (i == null)
            throw new IllegalArgumentException("cannot repeat an empty sequence");
        return new RepeatingIterable<>(i);
    }

    /**
     * Infinite repetition of an element.
     * @param <A> element type
     * @param a   element to repeat
     * @return    infinite iterable of a
     */
    public static <A> MaamIterable<A> repeat(A a) {
        return repeats(Arrays.asList(a));
    }

    /**
     * Returns true if the predicate returns true for at least one element.
     * @param <A> element type
     * @param i   iterable
     * @param p   predicate
     * @return    true if the predicate is true for one element, false otherwise
     */
    public static <A> boolean contains(Iterable<A> i, Predicate<A> p) {
        if (i == null)
            return false;
        for (A a : i)
            if (p.test(a))
                return true;
        return false;
    }

    /**
     * Returns the first element for which the predicate returns true.
     * @param <A> element type
     * @param i   iterable
     * @param p   predicate
     * @return    the first element for which the predicate returns true, null otherwise
     */
    public static <A> A first(Iterable<A> i, Predicate<A> p) {
        if (i == null)
            return null;
        for (A a : i)
            if (p.test(a))
                return a;
        return null;
    }

    /**
     * Empty (no elements) or null iterable.
     * @param <A> element type
     * @param i   iterable
     * @return    true if null or empty, false otherwise
     */
    public static <A> boolean isEmpty(Iterable<A> i) {
        return i == null || (! i.iterator().hasNext());
    }

    /**
     * Head fails for an empty iterator or a null iterator.
     * @param <A> element type
     * @param i   iterator
     * @return    first element of the iterator if any, throws {@link IllegalArgumentException} otherwise
     */
    public static <A> A head(Iterable<A> i) {
        if (i == null) 
            throw new IllegalArgumentException("cannot take head of a null iterable");
        Iterator<A> it = i.iterator();
        if (!it.hasNext())
            throw new IllegalArgumentException("cannot take head of an empty iterable");
        return it.next();
    }

    /**
     * Takes the only element from an iterable, throws an exception if it
     * does not contain exactly one element.
     * @param <A> element type
     * @param i   iterable
     * @return    the iterable single element
     */
    public static <A> A unique(Iterable<A> i) {
        if (i == null) 
            throw new IllegalArgumentException("cannot take one from a null iterable");
        Iterator<A> it = i.iterator();
        if (!it.hasNext())
            throw new IllegalArgumentException("cannot take one from an empty iterable");
        A a = it.next();
        if (it.hasNext())
            throw new IllegalArgumentException("cannot take only one from an iterable that has more than one element");
        return a;
    }

    // -- shorter definition of stream-like collect methods but less generic
    //    as there is not collector definition.

    /**
     * Concatenates object string representation.
     * @param  i        iterable to build string from, null is equivalent to an empty iterable
     * @return elements string representation concatenation
     */
    public static String asString(Iterable<?> i) {
        if (i == null)
            return "";
        StringBuilder s = new StringBuilder();
        for (Object o : i)
            s.append(o);
        return s.toString();
    }

    /**
     * Concatenates object string representation using {@link #foldL}.
     * @param  i        iterable to build string from, null is equivalent to an empty iterable
     * @return elements string representation concatenation
     */
    public static String asStringFold(Iterable<?> i) {
        return foldL(i, new StringBuilder(), (s,o) -> s.append(o)).toString();
    }

    /**
     * Get modifiable list containing the iterable elements.
     * @param <A> element type
     * @param i   iterable, null is handled as an empty iterable
     * @return    modifiable list containing the elements
     */
    public static <A> List<A> asList(Iterable<A> i) {
        return asMList(i);
    }
    
    public static <A> MaamiList<A> asMList(Iterable<A> i) {
        MaamiList<A> l = new MaamiList<>();
        if (i != null)
            for (A a : i)
                l.add(a);
        return l;
    }

    /**
     * Get modifiable list containing the iterable elements sorted using 
     * the given comparator.
     * @param <A> element type
     * @param i   iterable, null is handled as an empty iterable
     * @param c   element comparator
     * @return    modifiable list containing the elements
     */
    public static <A> List<A> asList(Iterable<A> i, Comparator<A> c) {
        return asMList(i ,c);
    }
    
    public static <A> MaamiList<A> asMList(Iterable<A> i, Comparator<A> c) {
        MaamiList<A> l = asMList(i);
        l.sort(c);
        return l;
    }	

    /**
     * Get a modifiable set containing the iterable elements.
     * <p>
     * Equivalent to: <br> <code>new HashSet{@literal <A>}(asList(i))</code>.
     * @param <A> element type
     * @param i   iterable, null is handled as an empty iterable
     * @return    modifiable set of iterable elements
     */
    public static <A> Set<A> asSet(Iterable<A> i) {
        return asSet(i, x -> x);
    }

    public static <A,B> Set<B> asSet(Iterable<A> i, Function<A,B> f) {
        Set<B> s = new HashSet<>();
        if (i != null)
            for (A a : i)
                s.add(f.apply(a));
        return s;   
    }
    
    /**
     * Get a modifiable map containing the projections of iterable elements.
     * <p>
     * If a key is occurs more then once, the last value is kept.
     * @param <A> element type
     * @param <K> key type
     * @param <V> value type
     * @param i   iterable, null is handled as an empty iterable
     * @param kf  key project function
     * @param vf  value projection function
     * @return    modifiable map of iterable elements projections
     */	
    public static <A,K,V> Map<K,V> asMap(Iterable<A> i, Function<A,K> kf, Function<A,V> vf) {
        Map<K,V> s = new HashMap<>();
        if (i != null)
            for (A a : i)
                s.put(kf.apply(a), vf.apply(a));
        return s;
    }

    /**
     * Creates a map with keys that are projection of the value and values kept.
     * @param <A> Iterable element type and map value type
     * @param <K> key type
     * @param i   iterable
     * @param kf  element key projection function
     * @return    modifiable map iterable elements projections
     */
    public static <A,K> Map<K,A> asValueMap(Iterable<A> i, Function<A,K> kf) {
        return asMap(i, kf, v -> v);
    }

    /**
     * Fold left.
     * @param <A>          iterable elements
     * @param <B>          accumulator type
     * @param i            iterables
     * @param initialValue initial accumulator value
     * @param acc          accumulation function
     * @return             accumulated result
     */
    public static <A,B> B foldL(Iterable<A> i, B initialValue, BiFunction<B,A,B> acc) {
        B r = initialValue;
        if (i != null)
            for (A a : i)
                r = acc.apply(r,a);
        return r;
    }

    /**
     * Fold right (uses the stack). f(i0,f(i1,..f(in,fin)).
     * @param <A>        iterable elements
     * @param <B>        accumulator type
     * @param i          iterables
     * @param finalValue final accumulator value
     * @param acc        accumulation function
     * @return           accumulated result
     */
    public static <A,B> B foldR(Iterable<A> i, B finalValue, BiFunction<A,B,B> acc) {
        return foldR(i.iterator(), finalValue, acc);
    }

    public static <A,B> B foldR(Iterator<A> i, B tailValue, BiFunction<A,B,B> acc) {
        if (i.hasNext()) {
            A a = i.next();
            return acc.apply(a, foldR(i, tailValue, acc));
        }
        return tailValue;
    }

//    public static <A> A foldR1(Iterable<A> i, BiFunction<A,A,A> acc) {
//        return foldR1(i.iterator(), acc);
//    }
    
//    public static <A> A foldR1(Iterator<A> i, BiFunction<A,A,A> acc) {
//        if (! i.hasNext())
//            throw new IllegalArgumentException("cannot foldR1 on an emty iterator");
//        A a = i.next();
//        if (i.hasNext())
//            return acc.apply(a, foldR1(i, acc));
//        else
//            return a;
//    }
    
    /**
     * Fold right (uses lambdas instead of stack).
     * @param <A>        iterable elements
     * @param <B>        accumulator type
     * @param i          iterables
     * @param finalValue final accumulator value
     * @param acc        accumulation function
     * @return           accumulated result
     */
    public static <A,B> B foldR_(Iterable<A> i, B finalValue, BiFunction<A,B,B> acc) {
        return foldR_(x -> x, i.iterator(), finalValue, acc);
    }

    public static <A,B> B foldR_(Function<B,B> comp, Iterator<A> i, B tailValue, BiFunction<A,B,B> acc) {
        if (i.hasNext()) {
            A a = i.next();
            return foldR_(x -> comp.apply(acc.apply(a, x)), i, tailValue, acc);
        }
        return comp.apply(tailValue);
    }
    
    
    /**
     * Sum Float elements.
     * @param i iterable to sum elements from
     * @return  elements sum
     */
    public static float sumFloat(Iterable<Float> i) {
        return foldL(i, 0f, (a,b) -> a + b);
    }

    public static <T> Iterator<T> generator(int from, Function<Integer,T> f) {
        return generator(range(from), f);
    }	

    public static Iterator<Integer> generator(int from) {
        return range(from).iterator();
    }
    
    public static <T> Iterator<T> generator(Function<Integer,T> f) {
        return generator(range(0), f);
    }
    
    /**
     * Positive integer iterator [0..].
     * @return Positive integer iterator
     */
    public static Iterator<Integer> generator() {
        return range(0).iterator();
    }

    /**
     * Positive integer iterator [0..].
     * @return Positive integer iterator
     */
    public static Iterable<Integer> ints() {
        return range(0);
    }

    public static <T> Iterator<T> generator(Iterable<Integer> source, Function<Integer,T> f) {
        return Iterables.it(source).map(f).iterator();
    }

    /**
     * Uses a {@link ListIterator} the iterate.
     * @param <T> element type
     * @param l   list to get a reverse iterator from
     * @return    reverse list iterator
     */
    public static <T> MaamIterable<T> reverse(List<T> l) {   
//        return new MaamIterable<T>() {
//
//            @Override
//            public Iterator<T> iterator() {
//                return new Iterator<T>() {
//
//                    private final ListIterator<T> it = l.listIterator(l.size());
//
//                    @Override
//                    public boolean hasNext() {
//                        return it.hasPrevious();
//                    }
//
//                    @Override
//                    public T next() { 
//                        return it.previous(); 
//                    }
//
//                };
//            }
//
//        };
        return new MaamIterable<T>() {

            @Override
            public Iterator<T> iterator() {
                return new  Iterator<T>() {

                    private int index = l.size() - 1;
                    
                    @Override
                    public boolean hasNext() {
                        return index >= 0;
                    }

                    @Override
                    public T next() {
                        return l.get(index--);
                    }
                    
                };
            }
            
        };
    }

    public static MaamIterable<Character> str(String value) {
        return new MaamIterable<Character>() {

            @Override
            public Iterator<Character> iterator() {
                return new Iterator<Character>() {
                    int index = 0;

                    @Override
                    public boolean hasNext() {
                        return index < value.length();
                    }

                    @Override
                    public Character next() {
                        return value.charAt(index++);
                    }

                };
            }

        };

    }

    // Could probably be a flatten(repeat(i))
    public static <A> MaamIterable<A> repeatS(Supplier<A> s) {
        return new RepeatingSupplierIterable<>(s);
    }

    // Should define some commn cases iterators (singleton, pair).
    
    @SuppressWarnings("unchecked")
    public static <A> Iterator<A> emptyIterator() {
        return (Iterator<A>)emptyIterator;
    }

    /**
     * Filter elements that are of class B or subclass of B.
     * @param <A> type of elements to filter
     * @param <B> type of filtered elements
     * @param i   iterable to filter
     * @param c   class of filtered elements
     * @return    iterable of filtered elements
     */
    // The isAssignable call always makes the cast valid 
    @SuppressWarnings("unchecked")
    public static <A, B extends A> MaamIterable<B> filterType(Iterable<A> i, Class<B> c) {
        return filterMap(i, x -> c.isAssignableFrom(x.getClass()) ? Optional.of((B)x) : Optional.empty());
    }

    // Filter elements using a set and the natural definition of the hash and equality.
    // should filter elements using an incremented set.
    public static <A> MaamIterable<A> uniqH(Iterable<A> i) {
        return uniq(i, o -> o.hashCode(), (a,b) -> a.equals(b));
    }
    
    // Filter elements using a set and the provided definition of the hash and equality.
    // Should create a side effect iterator filter
    public static <A> MaamIterable<A> uniq(Iterable<A> i, Hasher<A> h, Equatabler<A> e) {
        return new UniqueIterable<>(i, h, e);
    }

    public static MaamIterable<Long> rangeL(long from) {
        return rangeL(from, Long.MAX_VALUE);
    }
    
    public static MaamIterable<Long> rangeL(long from, long to) {
        return new LongRangeIterable(from, to, 1L);
    }
    
}
