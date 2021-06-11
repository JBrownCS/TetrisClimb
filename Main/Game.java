package Main;

import java.awt.BorderLayout;

import javax.swing.JFrame;  //use this to make game window

public class Game {
    /**
     * This class is for making the JFrame (game window)
     * and putting other objects on it
     */
    public static void main(String[] args) {
       //Create the JFrame, the window for the game
        JFrame frame = new JFrame("TetrisClimb");
        //close when clicking x at top right corner
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Frame does not change size
        frame.setResizable(false);
        //Opens at the middle of the screen
        frame.setLayout(new BorderLayout());
        frame.add(new GamePanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
