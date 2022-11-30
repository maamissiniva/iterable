package maamissiniva.util;

import java.util.ArrayList;
import java.util.List;

public class MaamiList<A> extends ArrayList<A> implements MaamIterable<A> {
    private static final long serialVersionUID = 1L;
    public MaamiList() {}
    public MaamiList(List<A> l) { super(l); }
}
