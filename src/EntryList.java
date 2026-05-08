/**
 * EntryList is a node in a singly linked list of Entry objects.
 *
 * It is used internally by HMap to handle hash collisions via separate chaining:
 * each slot of the hash table's array holds an EntryList, which chains together
 * all entries that hash to the same index.
 */
public class EntryList {

    /** The entry stored at this node. */
    Entry head;

    /** Reference to the next node in the collision chain, or null if this is the last. */
    EntryList next;

    /**
     * Constructs a new EntryList node with the given entry and next reference.
     *
     * @param head the entry to store at this node
     * @param next the next node in the chain (can be null)
     */
    EntryList(Entry head, EntryList next) {
        this.head = head;
        this.next = next;
    }
}
