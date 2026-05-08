/**
 * Entry represents a single (key, value) pair stored in the hash table (HMap).
 *
 * The key is a Prefix (a sequence of n words) and the value is a WordList
 * containing all words observed to follow that prefix in the source text.
 */
public class Entry {

    /** The prefix used as the key for this entry. */
    Prefix key;

    /** The list of words that have been observed to follow this prefix. */
    WordList value;

    /**
     * Constructs a new entry with the given key and value.
     *
     * @param key   the prefix key
     * @param value the associated list of successor words
     */
    Entry(Prefix key, WordList value) {
        this.key = key;
        this.value = value;
    }
}
