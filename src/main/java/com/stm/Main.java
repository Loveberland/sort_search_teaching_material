package main.java.com.stm;

import main.java.com.stm.display.InitDisplay;
import main.java.com.stm.display.SortDisplay;

public class Main {
        public static void main(String[] args) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                        InitDisplay menu = new InitDisplay("Sort", 1920, 1080);
                        menu.onBubbleClicked(() -> {
                                menu.hideWindow();
                                new SortDisplay("Bubble sort", 1920, 1080, menu::showWindow);
                        });

                        menu.onSelectionClicked(() -> {
                                menu.hideWindow();
                                new SortDisplay("Selection sort", 1920, 1080, menu::showWindow);
                        });

                        menu.onInsertionClicked(() -> {
                                menu.hideWindow();
                                new SortDisplay("Insertion sort", 1920, 1080, menu::showWindow);
                        });

                        menu.onMergeClicked(() -> {
                                menu.hideWindow();
                                new SortDisplay("Merge sort", 1920, 1080, menu::showWindow);
                        });
                });
        }
}
