package main.java.com.stm;

import main.java.com.stm.display.InitDisplay;

public class Main {
        public static void main(String[] args) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                        InitDisplay window = new InitDisplay("Sort", 1024, 768);
                        window.onBubbleClicked(() -> System.out.println("Bubble start"));
                        window.onSelectionClicked(() -> System.out.println("Selection start"));
                        window.onInsertionClicked(() -> System.out.println("Insertion start"));
                        window.onMergeClicked(() -> System.out.println("Merge start"));
                });
        }
}
