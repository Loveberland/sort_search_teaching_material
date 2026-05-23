package main.java.com.stm.algorithms;

import main.java.com.stm.display.Bar;
import main.java.com.stm.display.BarPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SelectionSort implements Algorithms {
        private static final Color DEFAULT_COLOR = new Color(179, 157, 219);
        private static final Color COMPARE_COLOR = new Color(255, 183, 77);
        private static final Color SWAP_COLOR = new Color(239, 83, 80);
        private static final Color SORTED_COLOR = new Color(102, 187, 106);

        private final BarPanel barPanel;
        private volatile int delayMs;
        private volatile boolean running = false;
        private Thread sortThread;

        private int keepI = 0, keepJ = 0;

        public SelectionSort(BarPanel barPanel, int delayMs) {
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
                List<Bar> bars = barPanel.bars;
                int n = bars.size();

                for (int i = keepI; i < n - 1 && running; i++) {
                        highlight(i, SWAP_COLOR);
                        keepI = i;
                        int minVal = bars.get(i).getValue();
                        int minIdx = i;
                        int startJ = (keepJ > i) ? keepJ : i;
                        for (int j = startJ + 1; j < n && running; j++) {
                                keepJ = j;
                                highlight(j, COMPARE_COLOR);
                                if (bars.get(j).getValue() < minVal) {
                                        highlight(minIdx, COMPARE_COLOR);
                                        minVal = bars.get(j).getValue();
                                        minIdx = j;
                                        highlight(minIdx, SWAP_COLOR);
                                }
                                repaintAndSleep();
                        }
                        int tmp = bars.get(minIdx).getValue();
                        bars.get(minIdx).setValue(bars.get(i).getValue());
                        bars.get(i).setValue(tmp);
                        highlight(minIdx, DEFAULT_COLOR);
                        highlight(i, SORTED_COLOR);
                        repaintAndSleep();
                        for (int j = i + 1; j < n && running; j++) {
                                highlight(j, DEFAULT_COLOR);
                        }
                        if (i == n - 1)
                                break;
                        keepI = keepJ = 0;
                }

                SwingUtilities.invokeLater(() -> {
                        for (Bar b : barPanel.bars) {
                                b.setColor(SORTED_COLOR);
                        }
                        barPanel.repaint();
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
