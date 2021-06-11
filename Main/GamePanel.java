package Main;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

import GameState.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private static long serialVersionUID = 1L;
    //Game Window Width and Height
    public static final int Width = 550;
    public static final int Height = 550;

    //Targeted FPS and time to spit out each frame within a second
    private final int FPS = 60;
    private final long targetTime = 1000 / FPS;

    private Thread thread;
    private boolean isRunning = false;

    //create an instance of the GameStateManager
    private GameStateManager gsm;

    public GamePanel(){  //constructor sets the game window size
        setPreferredSize(new Dimension(Width, Height));
        //this will allow the panel to read keyboard inputs
        addKeyListener(this);
        setFocusable(true);
        //Starts the panel/game window
        start();
    }

    private void start(){ //initiates thread upon game startup
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        long start, elapsed, wait;
        //initialize GameStateManager
        gsm = new GameStateManager();
        while(isRunning)  //while the game is running
        {
            start = System.nanoTime();
            tick();
            repaint();
            elapsed = System.nanoTime() - start;
            //make sure the frame rate is consistent no matter the machine
            wait = targetTime - elapsed / 1000000;
            if (wait <= 0)
                wait = 5;
            try{
                Thread.sleep(wait);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void tick() {
        gsm.tick();
    }

    //Draws the game window
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.clearRect(0,0,Width,Height);
        gsm.draw(g);
    }

    //These key functions get the keys pressed/released from each game state
    public void keyPressed(KeyEvent ke) {
        gsm.keyPressed(ke.getKeyCode());
    }


    public void keyReleased(KeyEvent ke) {
        gsm.keyReleased(ke.getKeyCode());
    }

    public void keyTyped(KeyEvent ke){

    }

}
