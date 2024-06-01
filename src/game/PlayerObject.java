package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PlayerObject extends GameObject {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	
	
	public int speed = 10;
	int hp = 100;
	int bulletSpeed;
	
	public PlayerObject(int x, int y, int width, int height, int bulletSpeed) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		if (needImage) {
		    loadImage ("human1.png");
		}
		this.bulletSpeed = bulletSpeed;
	}

	public void update() {
		super.update();
	}
	
	public void doDamage(int damage) {
		if(hp<=0) {
			isActive = false;
			
		}
		
		hp -= damage;
		
		
	}
			

	
	void loadImage(String imageFile) {
	    if (needImage) {
	        try {
	            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
	            gotImage = true;
 
	        } catch (Exception e) {
	        
	        }
	        needImage = false;
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
		//g.setColor(Color.BLUE);
		//g.fillRect(x, y, width, height);
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		} else {
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
		}
		g.setColor(Color.RED);
		g.drawRect(x, y, width, height);
	}


	public Bullet getBullet() {
		return new Bullet(x+width/2, y+height/2 - bulletSpeed*3, 10, 10, bulletSpeed);
	}	

}