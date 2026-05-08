/**
 * HMap is a hash table mapping Prefix keys to WordList values.
 *
 * It uses an array of EntryList chains to handle collisions via separate chaining.
 * The table automatically resizes (rehashes) when the load factor exceeds 75%,
 * doubling the array size to maintain efficient average-case O(1) operations.
 *
 * This implementation is tailored for the Markov chain text generation use case:
 * entries are only added or updated, never deleted.
 */
public class HMap {

    /** The array of entry chains. Each slot holds a linked list of entries (for collision handling). */
    EntryList[] t;

    /** The number of distinct keys (prefixes) currently stored in the table. */
    int nbEntries;

    /**
     * Constructs a hash table with an initial array of size n.
     *
     * @param n the initial number of slots in the table
     */
    public HMap(int n) {
        t = new EntryList[n];
        nbEntries = 0;
    }

    /**
     * Constructs a hash table with a default initial size of 20 slots.
     */
    public HMap() {
        this(20);
    }

    // -------------------------------------------------------------------------
    // Lookup
    // -------------------------------------------------------------------------

    /**
     * Finds and returns the WordList associated with the given prefix key.
     * Searches through all slots and their collision chains.
     *
     * @param key the prefix to look up
     * @return the associated WordList, or null if the key is not found
     */
    WordList find(Prefix key) {
        EntryList start;
        if (key == null)
            return null;

        for (int i = 0; i < t.length; i++) {
            start = t[i];
            if (start == null)
                continue;

            // Walk the collision chain at this slot
            while (start != null) {
                if (Prefix.eq(start.head.key, key))
                    return start.head.value;
                else
                    start = start.next;
            }
        }
        return null;
    }

    // -------------------------------------------------------------------------
    // Insertion
    // -------------------------------------------------------------------------

    /**
     * Adds word w to the list associated with the given prefix key.
     * If the key does not yet exist in the table, a new entry is created.
     * Does not trigger automatic resizing — use add() for that.
     *
     * @param key the prefix key
     * @param w   the word to associate with this prefix
     */
    public void addSimple(Prefix key, String w) {
        if (find(key) == null) {
            // Create a new entry and prepend it to the chain at the key's slot
            EntryList newbie = new EntryList(
                new Entry(key, new WordList(w)),
                t[key.hashCode(t.length)]
            );
            t[key.hashCode(t.length)] = newbie;
            nbEntries++;
        } else {
            // Key already exists: just append the word to its list
            find(key).addLast(w);
        }
    }

    // -------------------------------------------------------------------------
    // Resizing
    // -------------------------------------------------------------------------

    /**
     * Rebuilds the hash table with a new array of size n.
     * All existing entries are redistributed into the new array
     * according to their hash codes modulo n.
     *
     * @param n the new size of the table array
     */
    public void rehash(int n) {
        EntryList[] rehashed = new EntryList[n];

        for (int i = 0; i < t.length; i++) {
            EntryList cur = t[i];
            // Re-insert each entry of the current chain into the new table
            while (cur != null) {
                int idx = cur.head.key.hashCode(n);
                // Prepend to the chain at the new index
                rehashed[idx] = new EntryList(cur.head, rehashed[idx]);
                cur = cur.next;
            }
        }
        t = rehashed;
    }

    // -------------------------------------------------------------------------
    // Insertion with auto-resizing
    // -------------------------------------------------------------------------

    /**
     * Adds word w to the list associated with the given prefix key,
     * and triggers a rehash if the load factor exceeds 75%.
     * The array size is doubled on rehash.
     *
     * @param key the prefix key
     * @param w   the word to associate with this prefix
     */
    public void add(Prefix key, String w) {
        addSimple(key, w);
        // Rehash if more than 75% of slots are occupied
        if (nbEntries > 0.75 * t.length)
            this.rehash(2 * t.length);
    }
}
