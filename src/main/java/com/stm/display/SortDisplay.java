package main.java.com.stm.display;

import main.java.com.stm.algorithms.BubbleSort;
import main.java.com.stm.algorithms.SelectionSort;
import main.java.com.stm.algorithms.InsertionSort;
import main.java.com.stm.algorithms.MergeSort;
import main.java.com.stm.algorithms.SortAlgorithms;

import javax.swing.*;
import java.awt.*;

public class SortDisplay extends JFrame {
	private JPanel mainPanel;
	private JLabel titleLabel;
	private BarPanel barPanel;
	private GraphicsDevice device;

	private JButton startBtn;
	private JButton resetBtn;
	private JButton backBtn;
	private JSlider speedSlider;
	private JLabel speedLabel;

	private SortAlgorithms algorithm;
	private final String sortType;
	private final Runnable onBack;

	private static final int DELAY_MIN = 10;
	private static final int DELAY_MAX = 500;
	private static int DELAY_DEFAULT = 150;

	public SortDisplay(String sortType, int wid, int hei, Runnable onBack) {
		super(sortType);
		this.sortType = sortType;
		this.onBack = onBack;
		initComponents();
		initLayout();
		initAlgorithm();
		configureWindow(wid, hei);
	}

	private void configureWindow(int wid, int hei) {
		device = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		setUndecorated(true);
		setResizable(false);
		if (device.isFullScreenSupported()) {
			device.setFullScreenWindow(this);
		} else {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initLayout() {
		mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		mainPanel.add(titleLabel, BorderLayout.NORTH);
		mainPanel.add(barPanel, BorderLayout.CENTER);
		mainPanel.add(buildControlPanel(), BorderLayout.SOUTH);
		add(mainPanel);
	}

	private void initComponents() {
		titleLabel = new JLabel(sortType, SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

		barPanel = new BarPanel();

		startBtn = createStyleBtn("Start", new Color(102, 187, 106), Color.WHITE);
		resetBtn = createStyleBtn("Reset", new Color(102, 187, 106), Color.WHITE);
		backBtn = createStyleBtn("Back", new Color(102, 187, 106), Color.WHITE);

		startBtn.addActionListener(e -> onStartClicked());
		resetBtn.addActionListener(e -> onResetClicked());
		backBtn.addActionListener(e -> onBackClicked());

		speedLabel = new JLabel("Speed: Medium", SwingConstants.CENTER);
		speedLabel.setFont(new Font("Arial", Font.PLAIN, 13));

		speedSlider = new JSlider(DELAY_MIN, DELAY_MAX, DELAY_DEFAULT);
		speedSlider.setInverted(true);
		speedSlider.setOpaque(false);
		speedSlider.addChangeListener(e -> onSpeedChanged());
	}

	private JPanel buildControlPanel() {
		JPanel speedPanel = new JPanel(new BorderLayout(4, 4));
		speedPanel.setOpaque(false);
		speedPanel.add(speedLabel, BorderLayout.NORTH);
		speedPanel.add(speedSlider, BorderLayout.CENTER);
		speedPanel.setPreferredSize(new Dimension(260, 55));

		JPanel ctrl = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 12));
		ctrl.add(backBtn);
		ctrl.add(resetBtn);
		ctrl.add(startBtn);
		ctrl.add(speedPanel);
		return ctrl;
	}

	private void initAlgorithm() {
		switch (sortType) {
			case "Bubble sort":
				algorithm = new BubbleSort(barPanel, speedSlider.getValue());
				break;
			case "Selection sort":
				algorithm = new SelectionSort(barPanel, speedSlider.getValue());	
				break;
			case "Insertion sort":
				algorithm = new InsertionSort(barPanel, speedSlider.getValue());	
				break;
			case "Merge sort":
				algorithm = new MergeSort(barPanel, speedSlider.getValue());	
				break;
			default:
				algorithm = null;
		}
	}

	public void hideWindow() {
		if (device != null) {
			device.setFullScreenWindow(null);
		}
		setVisible(false);
	}

	private void onStartClicked() {
		if (algorithm == null)
			return;

		if (algorithm.isRunning()) {
			algorithm.stop();
			startBtn.setText("Resume");

		} else {
			algorithm.start();
			startBtn.setText("Pause");
		}
	}

	private void onResetClicked() {
		if (algorithm != null)
			algorithm.stop();
		startBtn.setText("Start");

		int count = barPanel.bars.size();
		barPanel.generateBars(count);
		initAlgorithm();
	}

	private void onBackClicked() {
		if (algorithm != null)
			algorithm.stop();
		hideWindow();
		if (onBack != null)
			SwingUtilities.invokeLater(onBack);
	}

	private void onSpeedChanged() {
		int delay = speedSlider.getValue();
		if (delay < 150) {
			speedLabel.setText("Speed: Fast");
		} else if (delay < 400) {
			speedLabel.setText("Speed: Medium");
		} else {
			speedLabel.setText("SPeed: Slow");
		}

		if (algorithm != null)
			algorithm.setDelay(delay);
	}

	private static JButton createStyleBtn(String label, Color bg, Color fg) {
		JButton btn = new JButton(label);
		btn.setBackground(bg);
		btn.setForeground(fg);
		btn.setFocusPainted(false);
		btn.setFont(new Font("Arial", Font.BOLD, 14));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setPreferredSize(new Dimension(145, 45));
		return btn;
	}
}
