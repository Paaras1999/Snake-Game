import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    // Inner class to represent a tile
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }  

    // Dimensions of the game board
    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    
    // Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // Food
    Tile food;
    Random random;

    // Game logic
    int velocityX;
    int velocityY;
    Timer gameLoop;

    boolean gameOver = false;

    // Constructor
    Game(int boardWidth, int boardHeight) {
        // Set the dimensions of the board
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        // Set panel properties
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        // Initialize snake head and body
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        // Initialize food and random object
        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        // Set initial velocity
        velocityX = 1;
        velocityY = 0;
        
		// Initialize game timer
		gameLoop = new Timer(100, this); // Timer interval: 100 milliseconds
        gameLoop.start();
	}	
    
    // Method to paint the graphics
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	// Method to draw game elements
	public void draw(Graphics g) {
        // Draw food
        g.setColor(Color.red);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);
    
        // Draw snake head
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);
            
        // Draw snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }
    
        // Display score or game over message
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }
    

    // Method to place food at random location
    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
		food.y = random.nextInt(boardHeight/tileSize);
	}

    // Method to move the snake
    public void move() {
        // Eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // Move snake body
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) { // Move the last part to the position of the head
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        // Move snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Check for collision with self or walls
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);

            // Collide with snake head
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        // Check if snake hits the walls
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || 
            snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight ) {
            gameOver = true;
        }
    }

    // Method to check collision between two tiles
    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    // ActionListener method - called every x milliseconds by gameLoop timer
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }  

    // KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    // Not needed KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
