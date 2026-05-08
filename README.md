# Text Generation Using Markov Chains

---

## Description

This project implements the following fundamental data structures from scratch in Java:

- **Linked lists** (`Node` cells and wrapped `WordList`)
- **Hash table** (`HMap`) with chaining collision resolution and automatic resizing
- **Random text generation** via Markov chains, applied to the 35 chapters of Gustave Flaubert's *Madame Bovary*

The final goal is to generate a pseudo-random "36th chapter" that is stylistically consistent with Flaubert's work.

> Project completed as part of the INF371 course at École Polytechnique (Promotion X2025).

---

## File Structure

```
project/
├── src/
│   ├── Node.java          # Linked list nodes
│   ├── WordList.java      # Wrapped linked list
│   ├── Prefix.java        # Hash table keys (sequences of n words)
│   ├── Entry.java         # (key, value) pair
│   ├── EntryList.java     # Entry chain for collision handling
│   ├── HMap.java          # Main hash table
│   ├── WordReader.java    # Text file reader
│   └── Bovary.java        # Main entry point
├── bovary/
│   ├── 01.txt             # Chapter 1 of Madame Bovary
│   ├── ...
│   └── 35.txt             # Chapter 35
├── README.md
├── README.fr.md
└── LICENSE
```

---

## Key Algorithms & Concepts

### 1. Linked Lists (`Node`, `WordList`)

| Operation | Complexity |
|-----------|------------|
| `length()` | O(n) |
| `addFirst()` | O(1) |
| `addLast()` | O(n) |
| `removeFirst()` | O(1) |
| `removeLast()` | O(n) |
| `insert()` (sorted) | O(n) |
| `insertionSort()` | O(n²) |
| `copy()` | O(n) |

### 2. Hash Table (`HMap`)

The table uses an array of linked lists (`EntryList[]`) to handle collisions via **separate chaining**.

| Operation | Average Complexity |
|-----------|-------------------|
| `find()` | O(1) amortized |
| `addSimple()` | O(1) amortized |
| `rehash(n)` | O(n) |
| `add()` (with auto-rehash) | O(1) amortized |

**Automatic rehashing** is triggered when the load factor exceeds **75%** (`nbEntries > 0.75 * t.length`), doubling the array size.

The **hash function** for prefixes uses Horner's method:
```
h = 37 * h + t[i].hashCode()
```

### 3. Markov Chains (`Bovary`)

Text generation is based on an **order-n Markov chain** model:

- For each sequence of `n` consecutive words (prefix), the model learns which words can follow in the source text.
- At generation time, a following word is drawn uniformly at random from the observed candidates.
- Special tokens `<START>`, `<END>` and `<PAR>` mark the beginning, end, and paragraph breaks respectively.

| Step | Complexity |
|------|------------|
| `buildTable()` | O(W) where W = total word count |
| `generate()` | O(L) where L = generated text length |

---

## Installation & Execution

### Prerequisites

- Java JDK 8 or higher
- Eclipse IDE (recommended) or any Java IDE

### Steps

**1. Clone the project**

```bash
git clone <repo-url>
cd project
```

**2. Madame Bovary corpus**

The corpus is not included in this repository. You must obtain it separately and place files `01.txt` through `35.txt` inside a `bovary/` folder at the project root.

> ⚠️ The text of *Madame Bovary* is subject to the **ABU License (Association des Bibliophiles Universels)**. Make sure to comply with its terms before any redistribution.

**3. Import `WordReader.java`** *(if needed)*

In Eclipse: right-click `src` → `Import` → `File System`

**4. Compile and run**

```bash
javac src/*.java
java -cp src Bovary
```

Or from Eclipse: right-click `Bovary.java` → `Run As` → `Java Application`

---

## Example Output

With prefix length 3 (`longueur = 3`), trained on all **35 chapters of *Madame Bovary***, the program generates text such as:

> Elle songeait quelquefois que c'étaient là pourtant les plus beaux jours de sa vie, la lune de miel, comme on disait. Pour en goûter la douceur, il eût fallu, sans doute, s'en aller vers ces pays à noms sonores où les lendemains de mariage ont de plus suaves paresses !
>
> Homais se délectait. Quoiqu'il se grisât de luxe encore plus que de grandes débauches.

---

## Author

**KAMDEM KOUAM Ezechiel**  
GitHub : https://github.com/KamdemE

---

## License

This project is distributed under the MIT License. See the [LICENSE](LICENSE) file for details.
