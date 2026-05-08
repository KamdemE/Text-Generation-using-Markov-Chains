import java.util.Random;

/**
 * Bovary is the main entry point for the Markov chain text generator.
 *
 * It builds a statistical model from the 35 chapters of Gustave Flaubert's
 * Madame Bovary, then generates a pseudo-random "36th chapter" by sampling
 * from the model using an order-n Markov chain.
 *
 * The model associates each sequence of n consecutive words (a Prefix) with
 * the list of words observed to follow it in the source text (stored in an HMap).
 * Text generation proceeds by repeatedly picking a random successor word
 * for the current prefix, then sliding the window forward.
 */
public class Bovary {

    /**
     * The prefix length (order of the Markov chain).
     * A value of 3 produces fluent-sounding text.
     */
    public static final int longueur = 3;

    // -------------------------------------------------------------------------
    // Table construction
    // -------------------------------------------------------------------------

    /**
     * Builds the Markov chain lookup table from a list of text files.
     *
     * For each file, a sliding window of size n is moved word by word.
     * At each step, the current word is added to the WordList associated
     * with the current prefix in the table. The prefix is then shifted
     * forward by one word.
     *
     * A special end token (Prefix.end) is added after the last word of each file
     * to signal a valid stopping point during generation.
     *
     * @param files an array of file paths to read (e.g. "bovary/01.txt")
     * @param n     the prefix length (order of the Markov chain)
     * @return the populated hash table mapping prefixes to successor word lists
     */
    public static HMap buildTable(String[] files, int n) {
        HMap table = new HMap(n);

        for (int i = 0; i < files.length; i++) {
            // Start each file with a fresh prefix filled with <START> tokens
            Prefix P = new Prefix(n);
            WordReader Reader = new WordReader(files[i]);
            String h = Reader.read();

            while (h != null) {
                table.add(P, h);       // Associate current word with current prefix
                P = P.addShift(h);     // Slide the window forward
                h = Reader.read();
            }

            // Mark the end of this file so generation can stop here
            table.add(P, Prefix.end);
        }
        return table;
    }

    // -------------------------------------------------------------------------
    // Text generation
    // -------------------------------------------------------------------------

    /**
     * Generates and prints a pseudo-random text using the Markov chain table.
     *
     * Starting from a prefix of n start tokens, the method repeatedly:
     *  1. Looks up the list of words that can follow the current prefix.
     *  2. Picks one uniformly at random.
     *  3. Prints it (or a newline if it is a paragraph token).
     *  4. Shifts the prefix forward by one word.
     *
     * Generation stops when the end token is drawn, or if the current prefix
     * is not found in the table (safety fallback).
     *
     * Paragraph tokens (<PAR>) are printed as newlines for readability.
     *
     * @param t the Markov chain table built by buildTable()
     * @param n the prefix length (must match the one used in buildTable)
     */
    public static void generate(HMap t, int n) {
        Prefix P = new Prefix(n);
        boolean pursue = true;
        Random rand = new Random();

        while (pursue) {
            WordList found = t.find(P);

            // Safety: unknown prefix (should not happen if buildTable is correct)
            if (found == null)
                break;

            String[] words = found.toArray();
            // Pick a successor word uniformly at random
            int pick = rand.nextInt(found.length());

            if (words[pick].equals(Prefix.end)) {
                // End token reached: stop generation
                pursue = false;
            } else {
                if (words[pick].equals(Prefix.par))
                    System.out.println();       // Paragraph break
                else
                    System.out.print(words[pick] + " ");

                P = P.addShift(words[pick]);    // Slide the window forward
            }
        }
        System.out.println(); // Final newline
    }

    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    /**
     * Builds the Markov chain table from the 35 chapters of Madame Bovary
     * and generates a pseudo-random "36th chapter".
     *
     * Expects the chapter files to be located in a "bovary/" subdirectory
     * relative to the working directory, named 01.txt through 35.txt.
     */
    public static void main(String[] args) {
        String[] files = new String[35];
        for (int i = 1; i < 36; i++) {
            if (i < 10)
                files[i - 1] = "bovary/0" + i + ".txt";
            else
                files[i - 1] = "bovary/" + i + ".txt";
        }

        HMap table = buildTable(files, longueur);
        generate(table, longueur);
    }
}
