package main.java.com.stm.display;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BarPanel extends JPanel {
        public List<Bar> bars;
        private static final int MIN_VAL = 10;
        private static final int MAX_VAL = 100;
        private static Random rand = new Random();
        private static final int RANDOM_AMOUNT = rand.nextInt(MAX_VAL - MIN_VAL + 1) + MIN_VAL;
        private static final int GAP = 4;
        private static final int BOT_PAD = 20;
        private static final double BAR_MAX_HEI_RATIO = 0.85;

        public BarPanel() {
                setBackground(Color.WHITE);
                bars = new ArrayList<>();
                generateBars(RANDOM_AMOUNT);
        }

        public BarPanel(String searchType) {
                setBackground(Color.WHITE);
                bars = new ArrayList<>();
                generateBars(searchType);
        }

        public void generateBars(int amount) {
                bars.clear();
                for (int i = 0; i < amount; i++) {
                        int value = rand.nextInt(MAX_VAL - MIN_VAL + 1) + MIN_VAL;
                        bars.add(new Bar(value));
                }
                repaint();
        }

        public void generateBars(String searchType) {
                bars.clear();
                List<Integer> tmp = new ArrayList<>();
                if (searchType.equals("Linear search")) {
                        for (int i = 1; i <= MAX_VAL; i++)
                                tmp.add(i);
                        Collections.shuffle(tmp);
                        for (int i = 0; i < MAX_VAL; i++)
                                bars.add(new Bar(tmp.get(i)));
                } else {
                        for (int i = 1; i <= MAX_VAL; i++)
                                bars.add(new Bar(i));
                }
                repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bars.isEmpty())
                        return;

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(
                                RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

                int panelWid = getWidth();
                int panelHei = getHeight();
                int totalBars = bars.size();

                int barWid = (panelWid - (totalBars + 1) * GAP) / totalBars;
                if (barWid < 1)
                        barWid = 1;

                int maxBarHei = (int) (panelHei * BAR_MAX_HEI_RATIO);

                for (int i = 0; i < totalBars; i++) {
                        Bar bar = bars.get(i);
                        int barHei = (int) ((bar.getValue() / 100.0) * maxBarHei);
                        int x = GAP + i * (barWid + GAP);
                        int y = panelHei - barHei - BOT_PAD;

                        g2d.setColor(bar.getColor());
                        g2d.fillRect(x, y, barWid, barHei);

                        if (barWid >= 20) {
                                g2d.setColor(Color.DARK_GRAY);
                                g2d.setFont(new Font("Arial", Font.PLAIN, 11));
                                String val = String.valueOf(bar.getValue());
                                FontMetrics fm = g2d.getFontMetrics();
                                int textX = x + (barWid - fm.stringWidth(val)) / 2;
                                g2d.drawString(val, textX, y - 4);
                        }
                }
        }
}
