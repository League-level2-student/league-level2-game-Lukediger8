package game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener {
    PlayerObject person1;
    PlayerObject person2;
    Random random = new Random();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Barrier> barriers = new ArrayList<>(); // List to store barriers
    long lastShotTime = 0;

    public ObjectManager(PlayerObject person1, PlayerObject person2) {
        this.person1 = person1;
        this.person2 = person2;
        createRandomBarriers(); // Generate random barriers
    }

    // Function to create random barriers
    public void createRandomBarriers() {
        int numBarriers = random.nextInt(5) + 3; // Random number of barriers (between 3 and 7)
        for (int i = 0; i < numBarriers; i++) {
            int x = random.nextInt(Game.WIDTH - 100); // Random x position
            int y = random.nextInt(Game.HEIGHT - 300) + 100; // Random y position, avoiding the top
            int width = random.nextInt(60) + 40; // Random width between 40 and 100
            int height = random.nextInt(40) + 30; // Random height between 30 and 70
            barriers.add(new Barrier(x, y, width, height));
        }
    }

    public void addBullet(Bullet bullet) {
        long currentTime = System.currentTimeMillis();
            bullets.add(bullet);
            lastShotTime = currentTime;
        }
    
    public void update() {
        for (Bullet bullet : bullets) {
            bullet.update();
            if (bullet.y < 0) {
                bullet.isActive = false;
            }
        }

        checkCollision();
        purgeObjects();
    }

    public void checkCollision() {
        // Check for collisions with players
        for (Bullet bullet : bullets) {
            if (bullet.collisionBox.intersects(person1.collisionBox)) {
                person1.takeDamage(10);
                bullet.isActive = false;
            }
            if (bullet.collisionBox.intersects(person2.collisionBox)) {
                person2.takeDamage(10);
                bullet.isActive = false;
            }
            // Check for collisions with barriers
            for (Barrier barrier : barriers) {
                if (bullet.collisionBox.intersects(barrier.collisionBox)) {
                    bullet.isActive = false; // Bullet is destroyed when hitting a barrier
                }
            }
        }
    }

    public void draw(Graphics g) {
        // Draw bullets
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        // Draw barriers
        for (Barrier barrier : barriers) {
            barrier.draw(g);
        }
    }

    public void purgeObjects() {
        // Remove inactive bullets
        for (int i = 0; i < bullets.size(); i++) {
            if (!bullets.get(i).isActive) {
                bullets.remove(i);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // Unused but required by the interface
    }
}