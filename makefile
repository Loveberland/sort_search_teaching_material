# find OS
ifeq ($(OS), Windows_NT)
	DETECTED_OS := Windows
else
	DETECTED_OS := $(shell uname -s)
endif

# declare variable each OS
ifeq ($(DETECTED_OS), Windows)
	FIND_SOURCES = $(shell dir /s /b $(SRC_DIR)\*.java 2>NUL)
	MKDIR = if not exist "$(OUT_DIR)" mkdir "$(OUT_DIR)"
	CLEAN = if exist "$(OUT_DIR)" rmdir /s /q "$(OUT_DIR)"
	ECHO = @echo
else
	FIND_SOURCES = $(shell find $(SRC_DIR) -name "*.java")
	MKDIR = mkdir -p $(OUT_DIR)
	CLEAN = rm -rf $(OUT_DIR)
	ECHO = @echo
endif

JAVAC := javac
JAVA := java
SRC_DIR := src
OUT_DIR := out
MAIN_CLASS := main.java.com.stm.Main
SOURCES := $(FIND_SOURCES)

.DEFAULT_GOAL := compile

.PHONY: info
info:
	$(ECHO) "Detected OS: $(DETECTED_OS)"
	$(ECHO) "Sources directory: $(SRC_DIR)"
	$(ECHO) "Output directory: $(OUT_DIR)"
	$(ECHO) "Main class: $(MAIN_CLASS)"

.PHONY: compile
compile: info
	$(ECHO) "Creating output directory"
	$(MKDIR)
	$(ECHO) "Compiling sources"
	$(JAVAC) -d $(OUT_DIR) -sourcepath $(SRC_DIR) $(SOURCES)
	$(ECHO) "Compile complete"

.PHONY: run
run: compile
	$(ECHO) "Running $(MAIN_CLASS)"
	$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS)

.PHONY: clean
clean:
	$(ECHO) "Cleaning output directory"
	$(CLEAN)
	$(ECHO) "Clean complete"
