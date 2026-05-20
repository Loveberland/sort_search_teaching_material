package main.java.com.stm.display;

import javax.swing.*;
import java.awt.*;

public class InitDisplay extends JFrame {
        private JPanel mainPanel;
        private JLabel titleLabel;

        private Btn bubble;
        private Btn selection;
        private Btn insert;
        private Btn merge; 

        public InitDisplay(String title, int wid, int hei) {
                super(title);
                initComponents();
                initLayout();
                configureWindow(wid, hei);
        }

        private void initComponents() {
                titleLabel = new JLabel("Sort", SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
                
                bubble = new Btn("Bubble sort", new Color(179, 157, 219), Color.WHITE);
                selection = new Btn("Selection sort", new Color(179, 157, 219), Color.WHITE);
                insert = new Btn("Insertion sort", new Color(179, 157, 219), Color.WHITE);
                merge = new Btn("Merge sort", new Color(179, 157, 219), Color.WHITE);

                bubble.setSize(320, 180);
                selection.setSize(320, 180);
                insert.setSize(320, 180);
                merge.setSize(320, 180);
        }

        private void initLayout() {
                mainPanel = new JPanel(new BorderLayout(10, 10));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
                btnPanel.add(bubble.getBtn());
                btnPanel.add(selection.getBtn());
                btnPanel.add(insert.getBtn());
                btnPanel.add(merge.getBtn());

                mainPanel.add(titleLabel, BorderLayout.NORTH);
                mainPanel.add(btnPanel, BorderLayout.CENTER);
                add(mainPanel);
        }

        private void configureWindow(int wid, int hei) {
                setSize(wid, hei);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setVisible(true);
        }

        public void onBubbleClicked(Runnable action) {
                bubble.onClick(action);
        }        
        
        public void onSelectionClicked(Runnable action) {
                selection.onClick(action);
        }        
        
        public void onInsertionClicked(Runnable action) {
                insert.onClick(action);
        }
  
        public void onMergeClicked(Runnable action) {
                merge.onClick(action);
        }
}
