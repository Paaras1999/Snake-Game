import javax.swing.*;

public class Board {
    public static void main(String[] args) throws Exception {
        // Define the dimensions of the game board
        int boardWidth = 600;
        int boardHeight = boardWidth;

        // Create a new JFrame to hold the game
        JFrame frame = new JFrame("Snake Game");
        
        // Make the frame visible
        frame.setVisible(true);
        
        // Set the dimensions of the frame
        frame.setSize(boardWidth, boardHeight);
        
        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        
        // Disable resizing of the frame
        frame.setResizable(false);
        
        // Specify what happens when the frame is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of the Game class, passing in the board dimensions
        Game snakeGame = new Game(boardWidth, boardHeight);
        
        // Add the game instance to the frame
        frame.add(snakeGame);
        
        // Pack the frame to ensure all components are sized properly
        frame.pack();
        
        // Set focus to the game instance so that it can receive key events
        snakeGame.requestFocus();
    }
}
