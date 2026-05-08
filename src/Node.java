/**
 * Node represents a single cell (node) in a singly linked list.
 * Each node holds a String value and a reference to the next node.
 *
 * This class also provides all the static utility functions
 * operating directly on raw node chains.
 */
public class Node {

    /** The string value stored in this node. */
    String head;

    /** Reference to the next node in the chain, or null if this is the last node. */
    Node next;

    /**
     * Constructs a new node with the given value and next reference.
     *
     * @param head the string value to store
     * @param next the next node in the chain (can be null)
     */
    Node(String head, Node next) {
        this.head = head;
        this.next = next;
    }

    // -------------------------------------------------------------------------
    // Length
    // -------------------------------------------------------------------------

    /**
     * Recursively computes the length of a node chain.
     * Note: may cause a StackOverflowError on very long chains.
     *
     * @param l the first node of the chain (can be null)
     * @return the number of nodes in the chain
     */
    static int lengthRec(Node l) {
        if (l == null)
            return 0;
        else if (l.next == null)
            return 1;

        return (1 + lengthRec(l.next));
    }

    /**
     * Iteratively computes the length of a node chain.
     * Preferred over lengthRec for long chains.
     *
     * @param l the first node of the chain (can be null)
     * @return the number of nodes in the chain
     */
    static int length(Node l) {
        int lengths = 0;
        for (Node cur = l; cur != null; cur = cur.next)
            lengths++;

        return lengths;
    }

    // -------------------------------------------------------------------------
    // Display
    // -------------------------------------------------------------------------

    /**
     * Returns a string representation of the chain in the format:
     * [elem1, elem2, elem3]
     *
     * @param l the first node of the chain (can be null)
     * @return the formatted string
     */
    static String makeString(Node l) {
        String list = "[";
        for (Node cur = l; cur != null; cur = cur.next) {
            list = list + cur.head;
            if (cur.next != null)
                list = list + ", ";
        }
        list = list + "]";
        return list;
    }

    // -------------------------------------------------------------------------
    // Insertion
    // -------------------------------------------------------------------------

    /**
     * Appends a new node with value s at the end of the chain.
     * Assumes l is not null.
     *
     * @param s the string to append
     * @param l the first node of the chain (must not be null)
     */
    static void addLast(String s, Node l) {
        Node cur;
        for (cur = l; cur.next != null; cur = cur.next) {
            // Traverse to the last node
        }
        cur.next = new Node(s, null);
    }

    // -------------------------------------------------------------------------
    // Copy
    // -------------------------------------------------------------------------

    /**
     * Returns a shallow copy of the node chain.
     * The new chain contains the same string values, in the same order.
     * Time complexity: O(n).
     *
     * @param l the first node of the chain to copy (can be null)
     * @return the first node of the copied chain
     */
    static Node copy(Node l) {
        if (l == null) return null;

        Node copies = new Node(l.head, l.next);
        Node start = copies;
        for (Node cur = l.next; cur != null; cur = cur.next) {
            copies.next = new Node(cur.head, cur.next);
            copies = copies.next;
        }
        copies.next = null;
        return start;
    }

    // -------------------------------------------------------------------------
    // Sorted insertion and sorting
    // -------------------------------------------------------------------------

    /**
     * Inserts string s into the sorted chain l, preserving lexicographic order.
     * The chain l may be modified in place.
     * Time complexity: O(n).
     *
     * @param s the string to insert
     * @param l the first node of a sorted chain
     * @return the first node of the resulting sorted chain
     */
    public static Node insert(String s, Node l) {
        Node start = l;
        Node elt = new Node(s, null);

        // Insert at the front if s comes before the first element
        if (l.head.compareTo(s) > 0) {
            elt.next = l;
            return elt;
        }

        boolean inserted = false;

        while (l.next != null) {
            // Insert after a duplicate
            if (l.head.equals(s)) {
                elt.next = l.next;
                l.next = elt;
                inserted = true;
                break;
            }
            // Insert between l and l.next
            if (l.head.compareTo(s) < 0 && l.next.head.compareTo(s) > 0) {
                elt.next = l.next;
                l.next = elt;
                inserted = true;
                break;
            }
            l = l.next;
        }

        // Insert at the end if not yet placed
        if (!inserted) {
            l.next = elt;
        }
        return start;
    }

    /**
     * Sorts a node chain in lexicographic order using insertion sort.
     * Works on a copy of the chain so the original structure is not reused.
     * Time complexity: O(n²).
     *
     * @param l the first node of the chain to sort (can be null)
     * @return the first node of the sorted chain
     */
    public static Node insertionSort(Node l) {
        if (l == null)
            return null;

        Node c = copy(l);
        l.next = null;
        for (Node cur = c.next; cur != null; cur = cur.next)
            l = insert(cur.head, l);

        return l;
    }

    // -------------------------------------------------------------------------
    // Main (test)
    // -------------------------------------------------------------------------

    /**
     * Simple test method demonstrating basic Node operations.
     */
    public static void main(String[] args) {
        Node foobar = new Node("foo", null);
        System.out.println(lengthRec(foobar));
        System.out.println(makeString(foobar));
        insert("aban", foobar);
        System.out.println(makeString(insertionSort(foobar)));
    }
}
