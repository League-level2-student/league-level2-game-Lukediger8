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
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game {
    public static void main(String[] args) {
        Game object = new Game();
        object.setup();
    }

    public static BufferedImage image;
    public static boolean needImage = true;
    public static boolean gotImage = false;
    JFrame frame;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 800;

    public class GamePanel extends JPanel implements KeyListener {
        PlayerObject person1;
        PlayerObject person2;
        ObjectManager objectManager;
        Font titleFont;
        Font subTitle;
        Font subTitle2;

        int score1 = 0; // Player 1's score
        int score2 = 0; // Player 2's score
        int gameCount = 0; // Number of games played
        boolean gameOver = false; // Flag to indicate if the game is over
        final int MENU = 0;
        final int GAME = 1;
        final int END = 2;
        int currentState = MENU;
        String player1Name;
        String player2Name;
        

        public GamePanel() {
            setDoubleBuffered(true); // Enable double buffering
            titleFont = new Font("Arial", Font.PLAIN, 48);
            subTitle = new Font("Arial", Font.PLAIN, 18);
            subTitle2 = new Font("Arial", Font.PLAIN, 18);
            player1Name = JOptionPane.showInputDialog("Enter Player 1 Name:");
            player2Name = JOptionPane.showInputDialog("Enter Player 2 Name:");
            person1 = new PlayerObject(250, 700, 50, 50, 10, "human1.png",player1Name); // Player 1
            person2 = new PlayerObject(0, 0, 50, 50, -10, "human2.png",player2Name); // Player 2
            objectManager = new ObjectManager(person1, person2);
        }

        void startGameLoop() {
            long lastTime = System.nanoTime();
            double nsPerTick = 1000000000.0 / 60; // Targeting 60 FPS
            double delta = 0;

            while (true) {
                long now = System.nanoTime();
                delta += (now - lastTime) / nsPerTick;
                lastTime = now;

                if (delta >= 1) {
                    updateGameState();
                    repaint();
                    delta--;
                }
            }
        }

        void updateGameState() {
            objectManager.update();
            if (person1.isActive) person1.update();
            if (person2.isActive) person2.update();
            
            // Check for win conditions
            if (!person1.isActive && person2.isActive) {
                if (score2 == 0) {
                    score2 = 1; // Player 2 wins, set score to 1
                }
                if (!gameOver) {
                    gameOver = true; // Set game over flag
                    gameCount++; // Increment game count
                    currentState = END; // Go to end state
                }
            } else if (person1.isActive && !person2.isActive) {
                if (score1 == 0) {
                    score1 = 1; // Player 1 wins, set score to 1
                }
                if (!gameOver) {
                    gameOver = true; // Set game over flag
                    gameCount++; // Increment game count
                    currentState = END; // Go to end state
                }
            }
        }

        void reset() {
            person1 = new PlayerObject(250, 700, 50, 50, 10, "human1.png", player1Name);
            person2 = new PlayerObject(0, 0, 50, 50, -10, "human2.png", player2Name);
            objectManager.person1 = person1;
            objectManager.person2 = person2;
            
            // Reset scores
            score1 = 0;
            score2 = 0;
            
            // Reset game over flag
            gameOver = false; 
        }

        void drawMenuState(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
            g.setFont(titleFont);
            g.setColor(Color.BLACK);
            g.drawString("Shooting Game", 30, 100);
            g.setFont(subTitle);
            g.drawString("Press ENTER to Start", 150, 350);
            g.setFont(subTitle2);
            g.drawString("This is a 2 player game, try to kill each other", 100, 470);
            g.drawString("Controls: WASD and arrow keys Shooting: F and K", 80, 450);
        }

        void drawGameState(Graphics g) {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
            person1.draw(g);
            person2.draw(g);
            objectManager.draw(g);
        }

        void drawEndState(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
            g.setFont(titleFont);
            g.setColor(Color.BLACK);
            g.drawString("GAME OVER", 78, 100);
            g.setFont(subTitle);

            // Only display scores if 5 games have been played
            if (gameCount >= 1) {
                g.drawString("Player 1 Score: " + score1, 150, 250);
                g.drawString("Player 2 Score: " + score2, 150, 300);
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g); // Clear the screen first
            if (currentState == MENU) {
                drawMenuState(g);
            } else if (currentState == GAME) {
                drawGameState(g);
            } else if (currentState == END) {
                drawEndState(g);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (currentState == END) {
                    currentState = MENU;
                    reset();
                } else {
                    currentState++;
                }
            }

            if (currentState == GAME) {
                if (e.getKeyCode() == KeyEvent.VK_UP) person1.up();
                if (e.getKeyCode() == KeyEvent.VK_DOWN) person1.down();
                if (e.getKeyCode() == KeyEvent.VK_LEFT) person1.left();
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) person1.right();
                if (e.getKeyCode() == KeyEvent.VK_K) objectManager.addBullet(person1.getBullet());

                if (e.getKeyCode() == KeyEvent.VK_W) person2.up();
                if (e.getKeyCode() == KeyEvent.VK_A) person2.left();
                if (e.getKeyCode() == KeyEvent.VK_S) person2.down();
                if (e.getKeyCode() == KeyEvent.VK_D) person2.right();
                if (e.getKeyCode() == KeyEvent.VK_F) objectManager.addBullet(person2.getBullet());
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyTyped(KeyEvent e) {}
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
        game.startGameLoop(); // Start the game loop here
    }
}
