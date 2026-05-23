package main.java.com.stm.algorithms;

import main.java.com.stm.display.Bar;
import main.java.com.stm.display.BarPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MergeSort implements Algorithms {
        private static final Color COMPARE_COLOR = new Color(255, 183, 77);
        private static final Color SWAP_COLOR = new Color(239, 83, 80);
        private static final Color SORTED_COLOR = new Color(102, 187, 106);

        private final BarPanel barPanel;
        private volatile int delayMs;
        private volatile boolean running = false;
        private Thread sortThread;

        private List<Bar> bars;

        public MergeSort(BarPanel barPanel, int delayMs) {
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
                merge(0, bars.size() - 1);

                if (running) {
                        for (int i = 0; i < bars.size(); i++) {
                                if (!running)
                                        return;
                                highlight(i, SORTED_COLOR);
                                repaintAndSleep();
                        }
                }

        }

        private void merge(int l, int r) throws InterruptedException {
                if (!running || l >= r)
                        return;
                int m = l + (r - l) / 2;
                merge(l, m);
                merge(m + 1, r);
                subMerge(l, m, r);
        }

        private void subMerge(int l, int m, int r) throws InterruptedException {
                if (!running)
                        return;
                int n1 = m - l + 1;
                int n2 = r - m;
                int[] L = new int[n1];
                int[] R = new int[n2];
                for (int i = 0; i < n1; i++)
                        L[i] = bars.get(l + i).getValue();
                for (int j = 0; j < n2; j++)
                        R[j] = bars.get(m + 1 + j).getValue();
                for (int idx = l; idx <= r; idx++)
                        highlight(idx, COMPARE_COLOR);
                repaintAndSleep();

                int i = 0, j = 0, k = l;

                while (i < n1 && j < n2) {
                        if (!running)
                                return;

                        highlight(l + i, COMPARE_COLOR);
                        highlight(m + 1 + j, COMPARE_COLOR);
                        repaintAndSleep();

                        if (L[i] <= R[j]) {
                                placeValue(k, L[i]);
                                i++;
                        } else {
                                placeValue(k, R[j]);
                                j++;
                        }
                        k++;
                }

                while (i < n1) {
                        if (!running)
                                return;
                        placeValue(k++, L[i++]);
                }
                while (j < n2) {
                        if (!running)
                                return;
                        placeValue(k++, R[j++]);
                }

        }

        private void placeValue(int index, int value) throws InterruptedException {
                SwingUtilities.invokeLater(() -> {
                        if (index < barPanel.bars.size()) {
                                barPanel.bars.get(index).setValue(value);
                                barPanel.bars.get(index).setColor(SWAP_COLOR);
                        }
                });
                repaintAndSleep();

                highlight(index, SORTED_COLOR);
                repaintAndSleep();
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
