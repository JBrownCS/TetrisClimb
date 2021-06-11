package GameState;

import java.awt.*;
import java.util.*;

/*This class manages the different states of the game
 * These can include start menu, game starting, and game over state
 */
public class GameStateManager {
    //create a stack storing the types of Game states(menu,game over,etc)
    public Stack<GameState> states;

    public GameStateManager() {
        //initialize stack of Game States
        states = new Stack<GameState>();
        states.push(new MenuState(this));
    }

    public void tick() {
        states.peek().tick();
    }

    public void draw(Graphics g){
        states.peek().draw(g);
    }

    public void keyPressed(int i){
        states.peek().keyPressed(i);
    }

    public void keyReleased(int k){
        states.peek().keyReleased(k);
    }

}
