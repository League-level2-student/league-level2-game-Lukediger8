package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PlayerObject extends GameObject {
    private BufferedImage image; // Instance variable for the player's image
    public int speed = 5;
    int maxHealth = 100; // Maximum health
    int currentHealth = maxHealth; // Current health
    int bulletSpeed;
    private String name; // Field for the player's name
    //private int movementTimer = 0; // Timer to control movement speed
    //private final int MOVEMENT_DELAY = 1; // Delay in ticks before moving

    public PlayerObject(int x, int y, int width, int height, int bulletSpeed, String imageFile, String playerName) {
        super(x, y, width, height);
        this.bulletSpeed = bulletSpeed;
        this.currentHealth = maxHealth;
        this.name = playerName; // Initialize the player's name
        loadImage(imageFile); // Load the specific image for this player
    }

    public void update(boolean left, boolean right, boolean up, boolean down) {
    	//movementTimer++;  
    	//if (movementTimer >= MOVEMENT_DELAY) {
    	if (left) {
            x -= speed;
        }
        if (right) {
            x += speed;
        }
        if (up) {
            y -= speed;
        }
        if (down) {
            y += speed;
        }
        //movementTimer = 0;
    	//}
        // Prevent moving out of bounds
        if (x < 0) x = 0;
        if (x > Game.WIDTH - width) x = Game.WIDTH - width;
        if (y < 0) y = 0;
        if (y > Game.HEIGHT - height) y = Game.HEIGHT - height;

        super.update();
    	}
    
    public void update1(boolean a, boolean d, boolean w, boolean s) {
    	//movementTimer++;  
    	//if (movementTimer >= MOVEMENT_DELAY) {
    	if (a) {
             x -= speed;
         }
         if (d) {
             x += speed;
         }
         if (w) {
             y -= speed;
         }
         if (s) {
             y += speed;
         }
        // movementTimer = 0;
    	//}
         // Prevent moving out of bounds
         if (x < 0) x = 0;
         if (x > Game.WIDTH - width) x = Game.WIDTH - width;
         if (y < 0) y = 0;
         if (y > Game.HEIGHT - height) y = Game.HEIGHT - height;

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
    
    public void a() {
        x += speed;
    }

    public void d() {
        x -= speed;
    }

    public void w() {
        y -= speed;
    }

    public void s() {
        y += speed;
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }

        // Draw the player's name above the character
        g.setColor(Color.BLACK); // Set color for the name
        g.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font and size
        g.drawString(name,x,y-10); 

        g.setColor(Color.RED);
        g.drawRect(x, y, width, height);
        int hpBarWidth = width; // Full width of the HP bar
        int hpBarHeight = 5; // Height of the HP bar
        int hpBarX = x; // X position (aligned with the player's character)
        int hpBarY = y - hpBarHeight - 2; // Y position (above the character)

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