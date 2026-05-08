/**
 * Prefix represents a fixed-size sequence of n words used as a key
 * in the hash table (HMap).
 *
 * Prefixes are built by sliding a window of size n over a word stream.
 * Special sentinel tokens mark the start, end, and paragraph boundaries
 * of a text. A custom hash function is provided for use with HMap.
 */
public class Prefix {

    /** The array of n words forming this prefix. */
    String[] t;

    /** Sentinel token marking the beginning of a text (used to initialize prefixes). */
    final static String start = "<START>";

    /** Sentinel token marking the end of a text. */
    final static String end = "<END>";

    /** Sentinel token marking a paragraph break in the text. */
    final static String par = "<PAR>";

    /**
     * Constructs a Prefix of size n, initialized with n copies of the start token.
     *
     * @param n the number of words in the prefix
     */
    Prefix(int n) {
        t = new String[n];
        for (int i = 0; i < n; i++)
            t[i] = start;
    }

    // -------------------------------------------------------------------------
    // Equality
    // -------------------------------------------------------------------------

    /**
     * Checks whether two prefixes are equal, i.e. they have the same size
     * and the same words at every position.
     *
     * @param p1 the first prefix
     * @param p2 the second prefix
     * @return true if p1 and p2 are equal, false otherwise
     */
    public static boolean eq(Prefix p1, Prefix p2) {
        boolean equal = true;
        // Both null: equal
        if (p1 == p2 && p1 == null)
            return true;
        // Exactly one is null: not equal
        else if (p1 == null && p2 != null || p2 == null && p1 != null)
            return false;
        // Different sizes: not equal
        if (p1.t.length != p2.t.length)
            return false;
        else {
            // Compare element by element
            for (int i = 0; i < p1.t.length; i++)
                if (!(p1.t[i].equals(p2.t[i]))) {
                    equal = false;
                    break;
                }
        }
        return equal;
    }

    // -------------------------------------------------------------------------
    // Sliding window
    // -------------------------------------------------------------------------

    /**
     * Returns a new Prefix obtained by shifting the current prefix left by one
     * and appending word w at the end.
     *
     * Example: if this = ["a", "b", "c"] and w = "d",
     * the result is ["b", "c", "d"].
     *
     * @param w the new word to append
     * @return the shifted prefix
     */
    public Prefix addShift(String w) {
        int j = t.length;
        Prefix p = new Prefix(j);
        // Shift all words one position to the left
        for (int i = 0; i < j - 1; i++)
            p.t[i] = t[i + 1];
        // Place the new word at the last position
        p.t[j - 1] = w;
        return p;
    }

    // -------------------------------------------------------------------------
    // Hashing
    // -------------------------------------------------------------------------

    /**
     * Computes an integer hash code for this prefix using Horner's method.
     * The polynomial base is 37.
     *
     * @return the hash code (may be negative due to integer overflow)
     */
    public int hashCode() {
        int h = 0;
        if (t == null)
            return h;
        for (int i = 0; i < this.t.length; i++) {
            h = 37 * h + t[i].hashCode();
        }
        return h;
    }

    /**
     * Computes the index of this prefix in a hash table of size n.
     * The result is always in [0, n-1].
     *
     * @param n the size of the hash table
     * @return a valid index in [0, n-1]
     */
    public int hashCode(int n) {
        int h = this.hashCode();
        h = h % n;
        // Ensure non-negative index
        if (h < 0)
            h = h + n;
        return h;
    }
}
