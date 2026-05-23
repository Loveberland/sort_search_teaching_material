package main.java.com.stm.algorithms;

import main.java.com.stm.display.Bar;
import main.java.com.stm.display.BarPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BinarySearch implements Algorithms {
        private static final Color COMPARE_COLOR = new Color(255, 183, 77);
        private static final Color FIND_COLOR = new Color(102, 187, 106);
        private static final Color NOT_FOUND_COLOR = new Color(239, 83, 80);

        private final BarPanel barPanel;
        private volatile int delayMs;
        private volatile boolean running = false;
        private Thread sortThread;

        private final int number;

        public BinarySearch(BarPanel barPanel, int delayMs, int number) {
                this.barPanel = barPanel;
                this.delayMs = delayMs;
                this.number = number;
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
                int n = bars.size(), l = 0, r = n - 1;
                boolean find = false;
                while (l <= r) {
                        int mid = l + (r - l) / 2;
                        highlight(mid, COMPARE_COLOR);
                        repaintAndSleep();
                        if (bars.get(mid).getValue() == number) {
                                highlight(mid, FIND_COLOR);
                                find = true;
                                repaintAndSleep();
                                break;
                        } else if (bars.get(mid).getValue() < number) {
                                for (int i = l; i <= mid; i++)
                                        highlight(i, NOT_FOUND_COLOR);
                                repaintAndSleep();
                                l = mid + 1;
                        } else {
                                for (int i = r; i >= mid; i--)
                                        highlight(i, NOT_FOUND_COLOR);
                                repaintAndSleep();
                                r = mid - 1;
                        }
                }
                if (!find) {
                        SwingUtilities.invokeLater(() -> {
                                for (Bar b : barPanel.bars) {
                                        b.setColor(NOT_FOUND_COLOR);
                                }
                                barPanel.repaint();
                        });
                }
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
