import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * WordReader reads words one at a time from a text file or any Readable source.
 *
 * Words are separated by whitespace. The reader returns null when the source
 * is exhausted. It is used by Bovary.buildTable() to stream words from each
 * chapter of Madame Bovary.
 */
class WordReader {

    /** The underlying scanner used to tokenize the input. */
    private Scanner scanner;

    /**
     * Constructs a WordReader that reads from the specified file.
     * The file is expected to be UTF-8 encoded.
     *
     * @param filename the path to the file to read
     * @throws RuntimeException if the file is not found
     */
    public WordReader(String filename) {
        try {
            this.scanner = new Scanner(new File(filename), "UTF-8");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructs a WordReader that reads from any Readable source
     * (e.g. a StringReader, for testing purposes).
     *
     * @param in the readable input source
     */
    public WordReader(Readable in) {
        this.scanner = new Scanner(in);
    }

    /**
     * Reads and returns the next word from the source.
     *
     * @return the next whitespace-delimited word, or null if the source is exhausted
     */
    public String read() {
        if (this.scanner == null)
            return null;

        if (this.scanner.hasNext())
            return scanner.next();

        return null;
    }

    /**
     * Simple test: prints each word of the given file surrounded by brackets,
     * then prints the total word count.
     *
     * Usage: java WordReader <filename>
     * Example: java WordReader bovary/01.txt
     */
    public static void main(String[] arg) {
        final WordReader wr = new WordReader(arg[0]);
        int counter = 0;

        for (String w = wr.read(); w != null; w = wr.read()) {
            System.out.println("[" + w + "]");
            counter++;
        }
        System.out.println(counter);
    }
}
