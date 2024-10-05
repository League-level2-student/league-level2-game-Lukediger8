package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PlayerObject extends GameObject {
    private BufferedImage image; // Instance variable for the player's image
    public int speed = 10;
    int maxHealth = 100; // Maximum health
    int currentHealth = maxHealth; // Current health
    int bulletSpeed;

    public PlayerObject(int x, int y, int width, int height, int bulletSpeed, String imageFile) {
        super(x, y, width, height);
        this.bulletSpeed = bulletSpeed;
        this.currentHealth = maxHealth;
        loadImage(imageFile); // Load the specific image for this player
    }

    public void update() {
        super.update();
    }

    public void takeDamage(int amount) {
        currentHealth -= amount;
        if (currentHealth < 0) {
            currentHealth = 0; // Ensure health doesn't go below 0
            isActive = false; // Player is dead
        }
    }

    void loadImage(String imageFile) {
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for any loading errors
        }
    }

    public void right() {
        x += speed;
    }

    public void left() {
        x -= speed;
    }

    public void up() {
        y -= speed;
    }

    public void down() {
        y += speed;
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }

        g.setColor(Color.RED);
        g.drawRect(x, y, width, height);
        int hpBarWidth = width; // Full width of the HP bar
        int hpBarHeight = 5; // Height of the HP bar
        int hpBarX = x; // X position (aligned with the player's circle)
        int hpBarY = y - hpBarHeight - 2; // Y position (above the circle)

        // Draw background for HP bar
        g.setColor(Color.RED); // Color for the HP bar background
        g.fillRect(hpBarX, hpBarY, hpBarWidth, hpBarHeight); // Draw the background bar

        // Calculate current health bar width
        int currentHpBarWidth = (int) ((currentHealth / (double) maxHealth) * hpBarWidth);

        // Draw current health bar
        g.setColor(Color.GREEN); // Color for the current health
        g.fillRect(hpBarX, hpBarY, currentHpBarWidth, hpBarHeight); // Draw current health bar
    }

    public Bullet getBullet() {
        return new Bullet(x + width / 2, y + height / 2 - bulletSpeed * 3, 10, 10, bulletSpeed);
    }
}
