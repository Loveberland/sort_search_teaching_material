package main.java.com.stm.algorithms;

import main.java.com.stm.display.Bar;
import main.java.com.stm.display.BarPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuickSort implements SortAlgorithms {
        private static final Color DEFAULT_COLOR = new Color(179, 157, 219);
        private static final Color COMPARE_COLOR = new Color(255, 183, 77);
        private static final Color SWAP_COLOR = new Color(239, 83, 80);
        private static final Color SORTED_COLOR = new Color(102, 187, 106);

        private final BarPanel barPanel;
        private volatile int delayMs;
        private volatile boolean running = false;
        private Thread sortThread;

        public QuickSort(BarPanel barPanel, int delayMs) {
                this.barPanel = barPanel;
                this.delayMs = delayMs;
        }

        @Override
        public void start() {
                if (running)
                        return;
                running = true;
                sortThread = new Thread(() -> {
                        try {
                                sort();
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                        } finally {
                                running = false;
                        }
                });
                sortThread.setDaemon(true);
                sortThread.start();
        }

        @Override
        public void stop() {
                running = false;
                if (sortThread != null)
                        sortThread.interrupt();
        }

        @Override
        public boolean isRunning() {
                return running;
        }

        @Override
        public void setDelay(int delayMs) {
                this.delayMs = delayMs;
        }

        private void sort() throws InterruptedException {

        }

        private void highlight(int index, Color color) {
                SwingUtilities.invokeLater(() -> {
                        if (index < barPanel.bars.size()) {
                                barPanel.bars.get(index).setColor(color);
                        }
                });
        }

        private void repaintAndSleep() throws InterruptedException {
                SwingUtilities.invokeLater(barPanel::repaint);
                Thread.sleep(delayMs);
        }
}
