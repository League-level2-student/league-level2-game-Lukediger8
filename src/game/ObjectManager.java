package game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener {
	Bullet bullet;
	PlayerObject person2;
	PlayerObject person1;
	Random random = new Random();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	long lastShotTime = 0;
    static final long SHOOT_COOLDOWN = 500;

	public ObjectManager(PlayerObject person1, PlayerObject person2) {
		this.person1 = person1;
		this.person2 = person2;
	
	}

	  public void addBullet(Bullet bullet) {
	        long currentTime = System.currentTimeMillis();
	        if (currentTime - lastShotTime >= SHOOT_COOLDOWN) {
	            bullets.add(bullet);
	            lastShotTime = currentTime;
	        }
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
		for (Bullet bullet : bullets) {
			if (bullet.collisionBox.intersects(person1.collisionBox)) {
				person1.takeDamage(10);
				bullet.isActive = false;
				System.out.println("1");
			}
			if (bullet.collisionBox.intersects(person2.collisionBox)) {
				person2.takeDamage(10);
				bullet.isActive = false;
				System.out.println("2");
			}
		}

	}

	public void draw(Graphics g) {
		// rocket.draw(g);
		for (Bullet bullet : bullets) {
			bullet.draw(g);
		}
	}

	public void purgeObjects() {
		for (int i = 0; i < bullets.size(); i++) {
			if (!bullets.get(i).isActive) {
				bullets.remove(i);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}