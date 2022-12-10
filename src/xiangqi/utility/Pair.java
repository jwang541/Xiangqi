package xiangqi.utility;

/**
 * Represents a pair of values of arbitrary type.
 */
public class Pair<A, B> {
    public A first;
    public B second;

    public Pair(A f, B s) {
        this.first = f;
        this.second = s;
    }
}
