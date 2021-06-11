package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import Entities.*;
import Main.GamePanel;
import javax.swing.Timer;

public class LevelState extends GameState{
    //define the player
    private Player player;
    //Create a list of Forms to store all the forms that appear on the level
    List<Form> forms = new ArrayList<Form>();
    //if you reach the game over state
    boolean GameOver = false;
    //Create timer for setting up new forms after a certain period of time
    private final Timer timer;
    //Create a boolean List to indicate if a form needs to be removed or not
    List<Boolean> removals = new ArrayList<Boolean>();


    public LevelState(GameStateManager gsm){
        super(gsm);
        //create a form every 3 seconds
        timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forms.add(new Form(forms.size()));
                removals.add(false);

            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public void init() {
        //create the player
        player = new Player(30,30);
    }
    public void tick() {
        //Have player update with the forms
        player.tick(forms);
        //Have the forms update position and collision
        for(int index = 0; index < forms.size(); index++){
            forms.get(index).tick(forms);
            //If a form reaches the game over state
            if(forms.get(index).isGameOver()){
                GameOver = true;
                break;
            }
            /*
            If a form collides with one of the same type, set the index of where these
            two forms collide to true

             */
            if(forms.get(index).ifCollideWithSameType()) {
                removals.set(index, true);
                removals.set(forms.get(index).indexItCollidedWith(), true);
            }
            //If the player pauses, stop the creating form timer
            if(player.isPause())
                timer.stop();
                /*If the player is not paused, set the timer delay to the current delay time
                 * and then start the timer again
                 */
            else {
                timer.setInitialDelay(1500);
                timer.start();
            }
        }
        //Check loss condition
        if(player.WinOrLose()) gsm.states.push(new GameOverState(gsm));
        //Check win condition
        if(player.getY() <= 90.0) gsm.states.push(new GameWinState(gsm));
        //Check if the player presses the ESC key
        if(player.ifRestart()) {gsm.states.pop(); gsm.states.push(new LevelState(gsm));}
        //Check if the player decides to quit the game
        if(player.quitGame()) gsm.states.pop();
        //If there are more are 10 forms on the screen
        if(forms.size() > 20)
            gsm.states.push(new GameOverState(gsm));
        reOrganizeList();
    }

    //Check if a form needs to be removed, and if so, removes it
    public void reOrganizeList() {
        int i = 0;
        //Check the removals list
        while (i < removals.size()) {
            //If the form at the index needs to be removed [(removals).get(i) == true ]
            if (removals.get(i)) {
                //remove the index in the removals and forms list
                forms.remove(i);
                removals.remove(i);
                //update the placement of the forms in the list after each removal
                for(int x = i; x < forms.size(); x++){
                    forms.get(x).setPlacement(-1);
                    forms.get(x).noLongerSameTypeCollision();
                }
                //continue from where i is now in case the new form at that index needs to be deleted
                continue;
            }
            i++;
        }
    }

    public void draw(Graphics g){
        //What to draw if in the pause state
        if(player.isPause()){
            //draw the background
            g.setColor(new Color(43, 179, 179));
            g.fillRect(0,0,GamePanel.Width, GamePanel.Height);
            //draw the victory line
            g.setColor(Color.GREEN);
            for(int i = 0; i < GamePanel.Width; i+=10)
                g.drawString("- ",i,90);
            //draw the player
            player.draw(g);
            //draw the forms
            for (Form form : forms) {
                form.draw(g);
            }
            g.setColor(Color.GREEN);
            g.setFont(new Font("Times New Roman", Font.BOLD, 60));
            g.drawString("PAUSE", GamePanel.Width / 2 - 100, GamePanel.Height / 2);
            g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
            g.drawString("Press Enter to Quit or ESC to Continue", GamePanel.Width / 2 - 235, GamePanel.Height / 2 + 70);
        }
        //draw everything as if it is not in the pause state
        else{
            //draw the background
            g.setColor(new Color(43, 179, 179));
            g.fillRect(0,0,GamePanel.Width, GamePanel.Height);
            //draw the victory line
            g.setColor(Color.GREEN);
            for(int i = 0; i < GamePanel.Width; i+=10)
                g.drawString("- ",i,90);
            //draw the player
            player.draw(g);
            //draw the forms
            for (Form form : forms) {
                form.draw(g);
            }
        }
    }
    //returns key the player presses
    public void keyPressed(int k){
        player.KeyPressed(k);
    }
    //returns key the player releases
    public void keyReleased(int k){
        player.KeyReleased(k);
    }
}
