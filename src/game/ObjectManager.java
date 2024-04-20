package game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener {
	Bullet bullet;
	Player2 player2;
	Random random = new Random();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();


	public ObjectManager() {

	}

	public void addProjectile(Bullet projectileObject) {
		bullets.add(projectileObject);

	}


	public void update() {
	
		for(Bullet bullet : bullets) {
			bullet.update();
			if(bullet.y < 0){
				bullet.isActive = false;
			}
			
		}
		checkCollision();
		purgeObjects();
			
		}
	

	public void checkCollision() {
			for(Bullet bullet: bullets) {
				if (bullet.collisionBox.intersects(Player2.collisionBox)) {
					Player2.isActive = false;
					Player2.isActive = false;
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
			if (!bullets.get(i).isActive ) {
				bullets.remove(i);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}