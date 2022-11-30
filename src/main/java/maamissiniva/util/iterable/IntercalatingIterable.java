package maamissiniva.util.iterable;

import java.util.Iterator;

import maamissiniva.util.MaamIterable;


public class IntercalatingIterable<A> implements MaamIterable<A> {

	private final Iterable<A> i;
	
	private final A a;
	
	public IntercalatingIterable(A a, Iterable<A> i) {
		this.i = i;
		this.a = a;
	}

	@Override
	public Iterator<A> iterator() {
		return new IntercalatingIterator<>(i.iterator(), a);
	}
	
	private static final class IntercalatingIterator<A> implements Iterator<A> {
		
		private Iterator<A> i;
		
		private A a;
		
		private boolean intercalate;

		IntercalatingIterator(Iterator<A> i, A a) {
			this.i = i;
			this.a = a;
			intercalate = false;
		}
			
		@Override
		public boolean hasNext() {
			return i.hasNext();
		}

		@Override
		public A next() {
			if (intercalate) {
				intercalate = false;
				return a;
			}
			intercalate = true;
			return i.next();
		}
			
	}
		
}
