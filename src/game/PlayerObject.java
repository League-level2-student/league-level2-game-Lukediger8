package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PlayerObject extends GameObject {
    private BufferedImage image;
    public int speed = 5;
    int maxHealth = 100; 
    int currentHealth = maxHealth;
    int bulletSpeed;
    private String name;

    public PlayerObject(int x, int y, int width, int height, int bulletSpeed, String imageFile, String playerName) {
        super(x, y, width, height);
        this.bulletSpeed = bulletSpeed;
        this.name = playerName;
        loadImage(imageFile);
    }

    public void update(boolean left, boolean right, boolean up, boolean down) {
        if (left) x -= speed;
        if (right) x += speed;
        if (up) y -= speed;
        if (down) y += speed;

        // Prevent moving out of bounds
        x = Math.max(0, Math.min(x, Game.WIDTH - width));
        y = Math.max(0, Math.min(y, Game.HEIGHT - height));

        super.update();
    }

    public void takeDamage(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            currentHealth = 0; 
            isActive = false; 
        }
    }

    private void loadImage(String imageFile) {
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }

        // Draw player's name and health bar
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString(name, x, y - 10);

        // Health bar drawing
        drawHealthBar(g);
    }

    private void drawHealthBar(Graphics g) {
        int hpBarWidth = width; 
        int hpBarHeight = 5; 
        int hpBarX = x; 
        int hpBarY = y - hpBarHeight - 2;

        g.setColor(Color.RED);
        g.fillRect(hpBarX, hpBarY, hpBarWidth, hpBarHeight); 

        int currentHpBarWidth = (int) ((currentHealth / (double) maxHealth) * hpBarWidth);
        g.setColor(Color.GREEN);
        g.fillRect(hpBarX, hpBarY, currentHpBarWidth, hpBarHeight);
    }

    public Bullet getBullet() {
        return new Bullet(x + width / 2, y + height / 2 - bulletSpeed * 3, 10, 10, bulletSpeed);
    }
}
