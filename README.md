# Sort & Search Teaching Material

A Java-based teaching tool for exploring and learning **sorting and searching algorithms** interactively from the command line.

---

## Prerequisites

Before running the program, make sure you have the following installed:

- **Java Development Kit (JDK) 8 or higher**
  ```bash
  java -version
  javac -version
  ```
- **Make** (GNU Make)
  - macOS / Linux: usually pre-installed
  - Windows: install via [Chocolatey](https://chocolatey.org/) (`choco install make`) or [GnuWin32](http://gnuwin32.sourceforge.net/packages/make.htm)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Loveberland/sort_search_teaching_material.git
cd sort_search_teaching_material
```

### 2. Compile the project

```bash
make compile
```

This will:
- Detect your operating system automatically (Windows, macOS, or Linux)
- Create the `out/` output directory
- Compile all `.java` source files from `src/` into `out/`

### 3. Run the program

```bash
make run
```

This compiles (if not already compiled) and then launches the main program.

---

## Available Make Targets

| Command        | Description                                               |
|----------------|-----------------------------------------------------------|
| `make compile` | Compiles all Java source files into the `out/` directory  |
| `make run`     | Compiles (if needed) and runs the main program            |
| `make clean`   | Deletes the `out/` directory and all compiled `.class` files |
| `make info`    | Prints detected OS, source directory, output directory, and main class |

### Example workflow

```bash
# Check your environment
make info

# Compile
make compile

# Run
make run

# Clean up compiled files when done
make clean
```

---

## Project Structure

```
sort_search_teaching_material/
├── makefile
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── stm/
                    └── Main.java       ← entry point
                    └── ...             ← sorting & searching algorithm implementations
```

---

## Notes

- The Makefile detects your OS automatically — the same commands work on Windows, macOS, and Linux.
- Compiled class files are placed in `out/` and are not tracked by Git. Run `make clean` to remove them.
- If you encounter a `javac: command not found` error, ensure your JDK `bin` directory is on your system `PATH`.
