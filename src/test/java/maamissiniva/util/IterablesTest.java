package maamissiniva.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class IterablesTest {

    @Test
    public void testReverse() {
        List<String> l = Arrays.asList("A","B","C");
        List<String> r = Iterables.reverse(l).asList();
        Collections.reverse(l);
        assertEquals(l, r);
    }

    @Test
    public void testFoldEquivs() {
        List<Integer> is = Iterables.range(0,10).asList();
        Integer r0 = Iterables.foldR (is, 0, (a,b) -> a + b);
        Integer r1 = Iterables.foldR_(is, 0, (a,b) -> a + b);
        Integer r2 = Iterables.foldL (is, 0, (a,b) -> a + b);
        assertEquals(Integer.valueOf(55), r0);
        assertEquals(r0, r1);
        assertEquals(r0, r2);
    }

}
