# Sorting Tool

A command-line application written in Java for sorting and analyzing input data.

The tool supports multiple data types and sorting strategies, and can read from standard input or files, while writing output to standard output or a file.

---

## Features

- Supports three data types:
    - `long` (numbers)
    - `word`
    - `line`
- Two sorting modes:
    - `natural`
    - `byCount`
- Input sources:
    - standard input
    - file (`-inputFile`)
- Output targets:
    - standard output
    - file (`-outputFile`)
- Graceful handling of invalid input values
- Validation of command-line arguments

---

## Project Structure

```
sorting-tool/
│
├── src/
│   └── sorting/
│       └── Main.java
│
├── README.md
└── .gitignore
```

---

## Usage

### Compile

```bash
javac sorting/Main.java
```

### Run

```bash
java sorting.Main [options]
```

---

## Command-line options

| Option | Description | Default |
|--------|------------|--------|
| `-dataType` | `long`, `word`, `line` | `word` |
| `-sortingType` | `natural`, `byCount` | `natural` |
| `-inputFile` | Path to input file | stdin |
| `-outputFile` | Path to output file | stdout |

---

## Examples

### Natural sorting of numbers

```bash
java sorting.Main -dataType long -sortingType natural
```

Input:
```
5 3 9 3 1
```

Output:
```
Total numbers: 5.
Sorted data: 1 3 3 5 9
```

---

### Sort words by frequency

```bash
java sorting.Main -dataType word -sortingType byCount
```

Input:
```
apple banana apple kiwi banana apple
```

Output:
```
Total words: 6.
kiwi: 1 time(s), 17%
banana: 2 time(s), 33%
apple: 3 time(s), 50%
```

---

### File input and output

```bash
java sorting.Main -dataType line -sortingType byCount -inputFile input.txt -outputFile output.txt
```

---

## Design Overview

The application is structured into clear responsibilities:

- Argument parsing with validation
- Input handling (standard input or file)
- Data parsing by type
- Sorting logic:
    - natural sorting
    - frequency-based sorting
- Unified output via `PrintWriter`

The project was developed incrementally and later refactored to reduce duplication and improve clarity.
