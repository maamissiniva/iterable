package maamissiniva.util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import maamissiniva.function.coc.T2;

/**
 * Fluent java iterable.
 *
 * @param <A> element type
 */
public interface MaamIterable<A> extends Iterable<A> {

    default <B> MaamIterable<B> map(Function<A,B> f) {
        return Iterables.map(this, f);
    }

    default MaamIterable<A> concat(Iterable<A> i) {
        return Iterables.concat(this, i);
    }

    default MaamIterable<A> intercalate(A a) {
        return Iterables.intercalate(a, this);
    }

    default MaamIterable<A> prepend(A a) {
        return Iterables.prepend(a, this);
    }

    default MaamIterable<A> append(A a) {
        return Iterables.append(this, a);
    }

    default MaamIterable<A> surround(A left, A between, A right) {
        return Iterables.surround(left, between, right, this);
    }

    default <B> MaamIterable<T2<A,B>> zip(Iterable<B> i) {
        return Iterables.zip(this, i);
    }

    default MaamIterable<A> filter(Predicate<A> p) {
        return Iterables.filter(this, p);
    }

    default <B> MaamIterable<B> filterMap(Function<A,Optional<B>> f) {
        return Iterables.filterMap(this, f);
    }

    default <B extends A> MaamIterable<B> filterType(Class<B> c) {
        return Iterables.filterType(this ,c);
    }
    
    /**
     * Take (at most) count element from this iterable, does
     * not fail if not enough elements are available.
     * @param count maximum element count to keep
     * @return      truncated iterable
     */
    default MaamIterable<A> take(int count) {
        return Iterables.take(this, count);
    }

    /**
     * Drop (at most) cout element from this iterable. 
     * @param count element count to drop
     * @return      iterable with 'count' first element removed
     */
    default MaamIterable<A> drop(int count) {
        return Iterables.drop(this, count);
    }

    /**
     * Does this iterable have an element for which the given predicate
     * evaluates to true ?.
     * @param p predicate
     * @return  true if the predicate evaluates to true for an element, false otherwise
     */
    default boolean contains(Predicate<A> p) {
        return Iterables.contains(this, p);
    }
    default boolean containsObject(A a) {
        return Iterables.containsObject(this, a);
    }
    default A first(Predicate<A> p) {
        return Iterables.first(this, p);
    }

    default <B> MaamIterable<B> flatMap(Function<A, ? extends Iterable<B>> f) {
        return Iterables.flatMap(this, f);
    }

    default boolean isEmpty() {
        return Iterables.isEmpty(this);
    }

    default A unique() {
        return Iterables.unique(this);
    }

    // --------------------------------------
    // Collector like conversions

    /**
     * Concatenates object string representation.
     * @return elements string representation concatenation
     */
    default String asString() {
        return Iterables.asString(this);
    }

    default MaamiList<A> asMList() { 
        return Iterables.asMList(this);
    }

    default MaamiList<A> asMList(Comparator<A> c) {
        return Iterables.asMList(this, c);
    }
    
    default List<A> asList() { 
        return Iterables.asList(this);
    }

    default List<A> asList(Comparator<A> c) {
        return Iterables.asList(this, c);
    }

    default Set<A> asSet() { 
        return Iterables.asSet(this);
    }
    
    default <B> Set<B> asSet(Function<A,B> f) { 
        return Iterables.asSet(this, f);
    }

    default A head() {
        return Iterables.head(this);
    }

    default <B> B foldL(B initial, BiFunction<B,A,B> f) {
        return Iterables.foldL(this, initial, f);
    }

    default <B> B foldR(B finalValue, BiFunction<A,B,B> f) {
        return Iterables.foldR(this, finalValue, f);
    }

    default <B> B foldR_(B finalValue, BiFunction<A,B,B> f) {
        return Iterables.foldR_(this, finalValue, f);
    }

    default <K,V> Map<K,V> asMap(Function<A,K> kf, Function<A,V> vf) {
        return Iterables.asMap(this, kf, vf);
    }

    default <K> Map<K,A> asMap(Function<A,K> kf) {
        return Iterables.asMap(this, kf, x -> x);
    }

    default <K> Map<K,A> asValueMap(Function<A,K> kf) {
        return Iterables.asValueMap(this, kf);
    }

    default MaamIterable<A> uniq(Hasher<A> hasher, Equatabler<A> equatabler) {
        return Iterables.uniq(this, hasher, equatabler);
    }
    
    default MaamIterable<A> uniqH() {
        return Iterables.uniqH(this);
    }
    
    default <B> MaamIterable<A> uniqP(Function<A,B> f) {
        return Iterables.uniqP(this, f);
    }

    default MaamIterable<A> takeWhile(Predicate<A> p) {
        return Iterables.takeWhile(this, p);
    }
    
    default MaamIterable<A> takeUntil(Predicate<A> p) {
        return Iterables.takeUntil(this, p);
    }

    default <B> MaamIterable<A> concatMap(Iterable<B> i, Function<B,A> f) {
        return concat(Iterables.map(i, f));
    }

    default <B> MaamIterable<A> concatFlatMap(Iterable<B> i, Function<B,? extends Iterable<A>> f) {
        return concat(Iterables.flatMap(i, f));
    }
    
}
