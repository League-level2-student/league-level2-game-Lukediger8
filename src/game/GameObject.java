package game;
import java.awt.Rectangle;

public class GameObject {
	int x;
	int y;
	int width;
	int height;
	public boolean isActive = true;
	Rectangle collisionBox;
	

	public GameObject(int a, int b, int c, int d) {
		x = a;
		y = b;
		width = c;
		height = d;
		collisionBox = new Rectangle(x, y, width, height);
	}

	public void update() {
        collisionBox.setBounds(x, y, width, height);
        
	}

}
	