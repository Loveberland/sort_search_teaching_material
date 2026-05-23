package main.java.com.stm.algorithms;

import main.java.com.stm.display.Bar;
import main.java.com.stm.display.BarPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BubbleSort implements Algorithms {
	private static final Color DEFAULT_COLOR = new Color(179, 157, 219);
	private static final Color COMPARE_COLOR = new Color(255, 183, 77);
	private static final Color SWAP_COLOR = new Color(239, 83, 80);
	private static final Color SORTED_COLOR = new Color(102, 187, 106);

	private final BarPanel barPanel;
	private volatile int delayMs;
	private volatile boolean running = false;
	private Thread sortThread;

	private int keepI, keepJ;

	public BubbleSort(BarPanel barPanel, int delayMs) {
		this.barPanel = barPanel;
		this.delayMs = delayMs;
	}

	@Override
	public void start() {
		if (running) return;
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
		if (sortThread != null) sortThread.interrupt();
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
			keepI = i;

			boolean swapped = false;	
			for (int j = keepJ; j < n - i - 1 && running; j++) {
				keepJ = j;

				highlight(j, COMPARE_COLOR);
				highlight(j + 1, COMPARE_COLOR);
				repaintAndSleep();
				if (bars.get(j).getValue() > bars.get(j + 1).getValue()) {
					highlight(j, SWAP_COLOR);
					highlight(j + 1, SWAP_COLOR);
					repaintAndSleep();

					int tmp = bars.get(j).getValue();
					bars.get(j).setValue(bars.get(j + 1).getValue());
					bars.get(j + 1).setValue(tmp);
					swapped = true;
				}
				highlight(j, DEFAULT_COLOR);
				highlight(j + 1, DEFAULT_COLOR);
				repaintAndSleep();
			}
			highlight(n - i - 1, SORTED_COLOR);
			repaintAndSleep();
			if (!swapped) break;
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

