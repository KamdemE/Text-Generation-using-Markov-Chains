# Génération de Texte grâce aux chaines de Markov 

---

## Description

Ce projet implémente en Java, depuis zéro, les structures de données fondamentales suivantes :

- **Listes chaînées** (maillons `Node` et liste enveloppée `WordList`)
- **Table de hachage** (`HMap`) avec gestion des collisions par chaînage et redimensionnement automatique
- **Génération de texte aléatoire** par chaînes de Markov, appliquée aux 35 chapitres de *Madame Bovary* de Gustave Flaubert

L'objectif final est de générer un « 36e chapitre » pseudo-aléatoire stylistiquement cohérent avec l'œuvre de Flaubert.

> Projet réalisé dans le cadre du cours INF371 à l'École Polytechnique (Promotion X2025).

---

## Structure des fichiers

```
projet/
├── src/
│   ├── Node.java          # Maillons de liste chaînée
│   ├── WordList.java      # Liste chaînée enveloppée
│   ├── Prefix.java        # Clés de la table (séquences de n mots)
│   ├── Entry.java         # Couple (clé, valeur)
│   ├── EntryList.java     # Chaîne d'entrées pour gestion des collisions
│   ├── HMap.java          # Table de hachage principale
│   ├── WordReader.java    # Lecteur de fichiers texte
│   └── Bovary.java        # Point d'entrée principal
├── bovary/
│   ├── 01.txt             # Chapitre 1 de Madame Bovary
│   ├── ...
│   └── 35.txt             # Chapitre 35
├── README.md
├── README.fr.md
└── LICENSE
```

---

## Algorithmes et concepts clés

### 1. Listes chaînées (`Node`, `WordList`)

| Opération | Complexité |
|-----------|------------|
| `length()` | O(n) |
| `addFirst()` | O(1) |
| `addLast()` | O(n) |
| `removeFirst()` | O(1) |
| `removeLast()` | O(n) |
| `insert()` (trié) | O(n) |
| `insertionSort()` | O(n²) |
| `copy()` | O(n) |

### 2. Table de hachage (`HMap`)

La table utilise un **tableau de listes chaînées** (`EntryList[]`) pour gérer les collisions (méthode du chaînage).

| Opération | Complexité moyenne |
|-----------|-------------------|
| `find()` | O(1) amorti |
| `addSimple()` | O(1) amorti |
| `rehash(n)` | O(n) |
| `add()` (avec rehash auto) | O(1) amorti |

Le **rehachage automatique** est déclenché quand le taux de remplissage dépasse **75%** (`nbEntries > 0.75 * t.length`). La taille du tableau est alors doublée.

La **fonction de hachage** des préfixes utilise un polynôme de Horner :
```
h = 37 * h + t[i].hashCode()
```

### 3. Chaînes de Markov (`Bovary`)

La génération de texte repose sur un modèle de **chaîne de Markov d'ordre n** :

- Pour chaque séquence de `n` mots consécutifs (préfixe), le modèle apprend quels mots peuvent suivre dans le texte source.
- À la génération, un mot suivant est tiré aléatoirement et uniformément parmi les candidats observés.
- Des tokens spéciaux `<START>`, `<END>` et `<PAR>` marquent respectivement le début, la fin et les changements de paragraphe.

| Étape | Complexité |
|-------|------------|
| `buildTable()` | O(W) où W = nombre total de mots |
| `generate()` | O(L) où L = longueur du texte généré |

---

## Installation et exécution

### Prérequis

- Java JDK 8 ou supérieur
- Eclipse IDE (recommandé) ou tout autre IDE Java

### Étapes

**1. Cloner le projet**

```bash
git clone https://github.com/KamdemE/Text-Generation-using-Markov-Chains
cd projet
```

**2. Corpus Madame Bovary**

Le corpus n'est pas inclus dans ce dépôt. Vous devez vous le procurer séparément et placer les fichiers `01.txt` à `35.txt` dans un dossier `bovary/` à la racine du projet.

> ⚠️ Le texte de *Madame Bovary* est soumis à la **licence ABU (Association des Bibliophiles Universels)**. Assurez-vous de respecter ses conditions avant toute redistribution.

**3. Importer `WordReader.java`** *(si nécessaire)*

Dans Eclipse : clic droit sur `src` → `Import` → `File System`

**4. Compiler et exécuter**

```bash
javac src/*.java
java -cp src Bovary
```

Ou depuis Eclipse : clic droit sur `Bovary.java` → `Run As` → `Java Application`

---

## Exemple de sortie

Avec des préfixes de taille 3 (`longueur = 3`), entraîné sur les **35 chapitres de *Madame Bovary***, le programme génère un texte du type :

> Elle songeait quelquefois que c'étaient là pourtant les plus beaux jours de sa vie, la lune de miel, comme on disait. Pour en goûter la douceur, il eût fallu, sans doute, s'en aller vers ces pays à noms sonores où les lendemains de mariage ont de plus suaves paresses !
>
> Homais se délectait. Quoiqu'il se grisât de luxe encore plus que de grandes débauches.

---

## Auteur

**KAMDEM KOUAM Ezechiel**  
GitHub : https://github.com/KamdemE

---

## Licence

Ce projet est distribué sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.
