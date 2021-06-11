package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Main.GamePanel;

public class HelpState extends GameState{

    public HelpState(GameStateManager gsm){
        super(gsm);
    }

    public void init() {}
    public void tick() {}

    public void draw(Graphics g){
        g.setColor(new Color(43, 179, 179));
        g.fillRect(0,0,GamePanel.Width, GamePanel.Height);
        //Draw the How To Play Text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 60));
        g.drawString("How To Play" , GamePanel.Width / 2 - 170, 50);
        //Draw win scenario text in green at size 40
        g.setFont(new Font("Ariel", Font.PLAIN, 40));
        g.setColor(Color.GREEN);
        g.drawString("Reach the top to win" , 100, 100);
        //Draw lose scenario text in red in size 40
        g.setColor(Color.RED);
        g.drawString("If you get crushed or don't" , 50, 150);
        g.drawString("reach the top then you lose" , 50, 200);
        //Draw Controls text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 60));
        g.drawString("Controls" , GamePanel.Width / 2 - 120, 260);
        //Draw instructions text
        g.setFont(new Font("Ariel", Font.PLAIN, 35));
        g.drawString("UP Arrow = Jump" , 0, 300);
        g.drawString("LEFT arrow = Move Left" , 0, 335);
        g.drawString("RIGHT arrow = Move Right" , 0, 370);
        g.drawString( "ESC key = Pause" , 0, 405);
        g.drawString( "R key = Restart", 0, 440);
        g.setFont(new Font("Ariel", Font.PLAIN, 38));
        g.drawString("Press Enter to Leave this menu" , 0, 500);
    }

    //pressing enter will return you to the Menu state(start menu)
    public void keyPressed(int k){
        //User presses the Enter key to leave the help menu
        if (k == KeyEvent.VK_ENTER){
            gsm.states.pop();
        }
    }

    public void keyReleased(int k){

    }
}
