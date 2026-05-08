/**
 * WordList is a wrapper around a singly linked list of Node cells.
 *
 * It encapsulates a Node chain and exposes higher-level list operations
 * as instance methods, delegating low-level traversal to the Node class.
 * The list can hold any String values, including special tokens such as
 * Prefix.start, Prefix.end, and Prefix.par.
 */
class WordList {

    /** The first node of the underlying linked list, or null if the list is empty. */
    Node content;

    /**
     * Constructs an empty WordList.
     */
    WordList() {
        content = null;
    }

    /**
     * Constructs a WordList from a variable number of strings.
     * The order of elements is preserved.
     *
     * @param words the strings to insert, in order
     */
    WordList(String... words) {
        content = null;
        // Insert from the end to preserve order with front-insertion
        for (int i = words.length - 1; i >= 0; i--) {
            content = new Node(words[i], content);
        }
    }

    /** Example list ["foo", "bar", "baz"] for testing purposes. */
    static WordList foobar = new WordList("foo", "bar", "baz");

    // -------------------------------------------------------------------------
    // Size and display
    // -------------------------------------------------------------------------

    /**
     * Returns the number of elements in the list.
     *
     * @return the length of the list
     */
    public int length() {
        return Node.length(content);
    }

    /**
     * Returns a string representation of the list in the format:
     * [elem1, elem2, elem3]
     *
     * @return the formatted string
     */
    public String toString() {
        return Node.makeString(content);
    }

    // -------------------------------------------------------------------------
    // Addition and removal
    // -------------------------------------------------------------------------

    /**
     * Inserts a word at the beginning of the list.
     *
     * @param w the word to add
     */
    public void addFirst(String w) {
        content = new Node(w, content);
    }

    /**
     * Appends a word at the end of the list.
     *
     * @param w the word to add
     */
    public void addLast(String w) {
        if (content == null)
            content = new Node(w, null);
        else
            Node.addLast(w, content);
    }

    /**
     * Removes and returns the first element of the list.
     *
     * @return the removed string, or null if the list is empty
     */
    public String removeFirst() {
        if (content == null)
            return null;

        String elt = content.head;
        content = content.next;
        return elt;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * @return the removed string, or null if the list is empty
     */
    public String removeLast() {
        if (content == null)
            return null;

        String elt;
        // Single element case
        if (content.next == null) {
            elt = content.head;
            content = null;
            return elt;
        }
        // Traverse to the second-to-last node
        Node start = content;
        while (content.next.next != null)
            content = content.next;
        elt = content.next.head;
        content.next = null;
        content = start;
        return elt;
    }

    // -------------------------------------------------------------------------
    // Sorted insertion and sorting
    // -------------------------------------------------------------------------

    /**
     * Inserts a string into the list while maintaining lexicographic order.
     *
     * @param s the string to insert
     */
    public void insert(String s) {
        if (content == null)
            content = new Node(s, null);
        else
            content = Node.insert(s, content);
    }

    /**
     * Sorts the list in lexicographic order using insertion sort.
     * Time complexity: O(n²).
     */
    public void insertionSort() {
        content = Node.insertionSort(content);
    }

    // -------------------------------------------------------------------------
    // Conversion
    // -------------------------------------------------------------------------

    /**
     * Converts the list to a String array, preserving order.
     *
     * @return an array containing all elements of the list
     */
    public String[] toArray() {
        String[] array = new String[this.length()];
        int i = 0;
        for (Node cur = content; cur != null; cur = cur.next) {
            array[i] = cur.head;
            i++;
        }
        return array;
    }
}
