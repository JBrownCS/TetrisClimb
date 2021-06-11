package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Main.GamePanel;

public class GameOverState extends GameState{
    public GameOverState(GameStateManager gsm){
        super(gsm);
    }

    public void init() {}
    public void tick() {}

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,GamePanel.Width, GamePanel.Height);
        g.setFont(new Font("SansSerif", Font.BOLD, 75));
        g.setColor(Color.RED);
        g.drawString("GAME OVER", GamePanel.Width / 2 -230, GamePanel.Height / 2 - 70);
        g.setFont(new Font("SansSerif", Font.BOLD, 25));
        g.drawString("Press Enter to Return to The Start Menu", 40, 300);

    }
    public void keyPressed(int k){
        //What happens at each selection when the user presses the Enter Key
        if (k == KeyEvent.VK_ENTER){
            //Pop once to exit the game over screen
            gsm.states.pop();
            //Pop again to exit the level state, as it will be underneath it
            gsm.states.pop();
        }
    }

    public void keyReleased(int k){

    }
}
