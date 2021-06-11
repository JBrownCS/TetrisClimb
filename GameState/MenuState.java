package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Main.GamePanel;

public class MenuState extends GameState{
    //Make an array of strings for the different menu options(start, help, quit)
    private String[] options = {"Start", "Help", "Quit"};
    private int currentSelection = 0; //set the current selected index to 0
    private Color[] colors = {Color.RED, Color.orange, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
    public MenuState(GameStateManager gsm){
        super(gsm);
    }

    public void init() {}
    public void tick() {}

    public void draw(Graphics g){
        //Fill the menu background with a blue color
        g.setColor(new Color(43, 179, 179));
        g.fillRect(0,0,GamePanel.Width, GamePanel.Height);
        //Draw Rectangles in the background
        for(int height = 0; height <= GamePanel.Height; height+=105)
        {
            for(int x = 10; x <= GamePanel.Width; x+=50)
            {
                g.drawRect(x, height, 25, 25);
                g.setColor(colors[(x / 50) % 6]);
                g.fillRect(x, height, 25, 25);
            }
        }
        /*Draw Rectangles across screen and then overlay text on top
         * g.drawRect(x,y,width,height) with 8 rectangles per 100 height
         * something cool to work on then
         */
        //Draw the Title String in red, bold, and with font size 75
        g.setFont(new Font("Ariel", Font.BOLD, 75));
        g.setColor(Color.RED);
        g.drawString("Tetris Climb", GamePanel.Width / 2 -215, 90);
        /*Draw the other selections on the menu
         * with the current selection in green and others in black
         */
        for(int i = 0; i < options.length; i++){
            if(i == currentSelection)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.BLACK);
            //g.drawLine(GamePanel.Width / 2, 0, GamePanel.Width / 2, GamePanel.Height);draws a line to see if centered
            g.setFont(new Font("Ariel", Font.PLAIN, 50));
            g.drawString(options[i], GamePanel.Width / 2 - 50, 110 * i + 195); //110 * i + 100
        }
    }

    public void keyPressed(int k){
        //Allows for scrolling up and down the menu
        if (k == KeyEvent.VK_DOWN){
            currentSelection++;
            if(currentSelection == options.length)
                currentSelection = 0;
        }
        else if (k == KeyEvent.VK_UP){
            currentSelection--;
            if(currentSelection < 0)
                currentSelection = options.length - 1;
        }
        //What happens at each selection when the user presses the Enter Key
        if (k == KeyEvent.VK_ENTER){
            if (currentSelection == 0) { //Start: Starts the Game
                gsm.states.push(new LevelState(gsm));
            }
            else if (currentSelection == 1){ // Help: Opens Help/Controls menu
                gsm.states.push(new HelpState(gsm));
            }
            else if (currentSelection == 2){ // Quit: Exits the game
                System.exit(0);
            }
        }
    }

    public void keyReleased(int k){

    }

}
