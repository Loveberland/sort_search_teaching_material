package main.java.com.stm.display;

import javax.swing.*;
import java.awt.*;

public class SortDisplay extends JFrame {
        private JPanel mainPanel;
        private JLabel titleLabel;
        private BarPanel barPanel;
        private GraphicsDevice device;

        public SortDisplay(String sortType, int wid, int hei) {
                super(sortType);
                initComponents(sortType);
                initLayout();
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
                add(mainPanel);
        }

        private void initComponents(String sortType) {
                titleLabel = new JLabel(sortType, SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
                barPanel = new BarPanel();
        }

        public void hideWindow() {
                if (device != null) {
                        device.setFullScreenWindow(null);
                }
                setVisible(false);
        }
}
