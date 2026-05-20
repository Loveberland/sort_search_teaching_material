package main.java.com.stm.display;

import java.awt.*;

public class Bar {
        private int value;
        private Color color;

        private static final Color DEFAULT_COLOR = new Color(179, 157, 219);

        public Bar(int value) {
                this.value = value;
                this.color = DEFAULT_COLOR;
        }

        public int getValue() {
                return value;
        }

        public Color getColor() {
                return color;
        }

        public void setValue(int value) {
                this.value = value;
        }

        public void setColor(Color color) {
                this.color = color;
        }
}
