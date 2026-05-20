package main.java.com.stm.display;

import javax.swing.*;
import java.awt.*;

public class Btn {
        private JButton button;

        public Btn(String label, Color bgColor, Color txtColor) {
                button = new JButton(label);
                button.setBackground(bgColor);
                button.setForeground(txtColor);
                button.setFocusPainted(false);
                button.setFont(new Font("Arial", Font.BOLD, 14));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        public void onClick(Runnable action) {
                button.addActionListener(e -> action.run());
        }

        public JButton getBtn() {
                return this.button;
        }

        public void setSize(int wid, int hei) {
                button.setPreferredSize(new Dimension(wid, hei));
        }
}
