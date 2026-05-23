package main.java.com.stm.algorithms;

import main.java.com.stm.display.Bar;
import main.java.com.stm.display.BarPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuickSort implements Algorithms {
        private static final Color DEFAULT_COLOR = new Color(179, 157, 219);
        private static final Color COMPARE_COLOR = new Color(255, 183, 77);
        private static final Color PIVOT_COLOR = new Color(66, 165, 245);
        private static final Color SWAP_COLOR = new Color(239, 83, 80);
        private static final Color SORTED_COLOR = new Color(103, 187, 106);

        private final BarPanel barPanel;
        private volatile int delayMs;
        private volatile boolean running = false;
        private Thread sortThread;
        private List<Bar> bars;

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
                bars = barPanel.bars;
                quickSort(0, bars.size() - 1);

                if (running) {
                        for (int i = 0; i < bars.size(); i++) {
                                if (!running)
                                        return;
                                highlight(i, SORTED_COLOR);
                                repaintAndSleep();
                        }
                }
        }

        private void quickSort(int l, int r) throws InterruptedException {
                if (!running || l >= r)
                        return;
                int pi = partition(l, r);
                quickSort(l, pi - 1);
                quickSort(pi + 1, r);
        }

        private int partition(int l, int r) throws InterruptedException {
                int pivot = bars.get(r).getValue();
                highlight(r, PIVOT_COLOR);
                repaintAndSleep();

                int i = l - 1;
                for (int j = l; j < r; j++) {
                        if (!running)
                                return i + 1;
                        highlight(j, COMPARE_COLOR);
                        repaintAndSleep();

                        if (bars.get(j).getValue() <= pivot) {
                                i++;
                                if (i != j) {
                                        highlight(i, SWAP_COLOR);
                                        highlight(j, SWAP_COLOR);
                                        repaintAndSleep();

                                        swap(i, j);
                                        repaintAndSleep();
                                }
                                highlight(i, DEFAULT_COLOR);
                        } else
                                highlight(j, DEFAULT_COLOR);
                }

                int pivotFinal = i + 1;
                if (pivotFinal != r) {
                        highlight(pivotFinal, SWAP_COLOR);
                        highlight(r, SWAP_COLOR);
                        repaintAndSleep();

                        swap(pivotFinal, r);
                        repaintAndSleep();
                }
                highlight(pivotFinal, SORTED_COLOR);
                repaintAndSleep();

                return pivotFinal;
        }

        private void swap(int a, int b) {
                SwingUtilities.invokeLater(() -> {
                        if (a < bars.size() && b < bars.size()) {
                                int tmp = bars.get(a).getValue();
                                bars.get(a).setValue(bars.get(b).getValue());
                                bars.get(b).setValue(tmp);
                        }
                });
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
