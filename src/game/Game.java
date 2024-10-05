package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game {
	public static void main(String[] args) {
		 int player1Score = 0;
		 int player2Score = 0;

		Game object = new Game();

		object.setup();

	}

	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	JFrame frame;
	
	

	public static final int WIDTH = 500;
	public static final int HEIGHT = 800;

	public class GamePanel extends JPanel implements ActionListener, KeyListener {
		PlayerObject person1;
		PlayerObject person2;
		final int MENU = 0;
		final int GAME = 1;
		final int END = 2;
		ObjectManager objectManager;
		Timer frameDraw;
		Font titleFont;
		Font subTitle;
		Font subTitle2;
		int currentState = MENU;
		
		public GamePanel() {
			    titleFont = new Font("Arial", Font.PLAIN, 48);
			    subTitle = new Font("Arial", Font.PLAIN, 18);
			    subTitle2 = new Font("Arial", Font.PLAIN, 18);
			    frameDraw = new Timer(1000 / 60, this);
			    frameDraw.start();

			    // Create player objects with image file names
			    person1 = new PlayerObject(250, 700, 50, 50, 10, "human1.png"); // Player 1
			    person2 = new PlayerObject(0, 0, 50, 50, -10, "human2.png"); // Player 2
			    
			    objectManager = new ObjectManager(person1, person2);
			    startGame();
			}
		
		

		void startGame() {
			frameDraw = new Timer(1000 / 60, this);
			frameDraw.start();


			
		}

		void updateMenuState() {

		}

		void updateGameState() {
			objectManager.update();
			person1.update();
			person2.update();
			
		}

		void updateEndState() {
			
		}

	void reset() {
		startGame();
		 person1 = new PlayerObject(250, 700, 50, 50, 10, "human1.png");
		    person2 = new PlayerObject(0, 0, 50, 50, -10, "human2.png");
		objectManager.person1 = person1;
		objectManager.person2 = person2;

		
		}
		
		void drawMenuState(Graphics g) {
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
			g.setFont(titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Shooting Game", 30, 100);
			g.setFont(subTitle);
			g.setColor(Color.BLACK);
			g.drawString("Press ENTER to Start", 150, 350);
			g.setFont(subTitle2);
			g.setColor(Color.BLACK);
			g.drawString("This is a 2 player game, try to kill each other", 100, 470);
			g.setColor(Color.BLACK);
			g.drawString("Controls:WASD and arrow keys Shooting:F and K", 80, 450);
			
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

		void drawGameState(Graphics g) {

			if (gotImage) {
				g.drawImage(image, 0, 0, game.WIDTH, game.HEIGHT, null);
			} else {
				g.setColor(Color.GRAY);
				g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

			}
			person1.draw(g);
			person2.draw(g);
			objectManager.draw(g);
			
			if(person1.isActive == false) {
				currentState = END;
			}
			if(person2.isActive == false) {
				currentState = END;
			
			}
			
		
		}

		void drawEndState(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
			g.setFont(titleFont);
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER", 78, 100);
			g.setFont(subTitle);
			g.setColor(Color.BLACK);
			g.drawString("You Killed Enemies", 150, 350);
			g.setFont(subTitle2);
			g.setColor(Color.BLACK);
			
		}

		@Override
		public void paintComponent(Graphics g) {

			if (currentState == MENU) {
				drawMenuState(g);
			} else if (currentState == GAME) {
				drawGameState(g);
			} else if (currentState == END) {
				drawEndState(g);
			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (currentState == MENU) {
				updateMenuState();
			} else if (currentState == GAME) {
				updateGameState();
			} else if (currentState == END) {
				updateEndState();
				
			}
			repaint();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (currentState == END) {
					currentState = MENU;
					reset();
				} else {
					currentState++;
				}
				
			}

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (person1.y > 0) {
					person1.up();
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (person1.y < 750) {
					person1.down();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (person1.x > 0) {
					person1.left();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (person1.x < 450) {
					person1.right();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_K) {
				objectManager.addBullet(person1.getBullet());
				
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				if (person2.y > 0) {
					person2.up();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				if (person2.y < 750) {
					person2.left();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				if (person2.y < 750) {
					person2.down();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				if (person2.y < 750) {
					person2.right();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_F) {
				objectManager.addBullet(person2.getBullet());
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	GamePanel game;

	Game() {
		frame = new JFrame();
		game = new GamePanel();
	}

	void setup() {
		frame.add(game);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(game);
	}
}