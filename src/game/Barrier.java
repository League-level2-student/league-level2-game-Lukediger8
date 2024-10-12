package game;

import java.awt.Color;
import java.awt.Graphics;

public class Barrier extends GameObject {

    public Barrier(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY); // Set a color for the barrier
        g.fillRect(x, y, width, height);
    }
}
