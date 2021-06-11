package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.Point;
import Main.GamePanel;
import Physics.Collision;
import java.util.List;

public class Player {
    //variables for if the player is moving right or left, jumping, or falling
    private boolean movingRight = false, movingLeft = false, jumping = false, falling = false;
    //The x and y coordinate the player spawns at
    private double x,y;
    //The width and height of the player rectangle
    private final int playerWidth, playerHeight;
    //The jump speed and current jump speed(which would decrease if falling)
    private final double jumpSpeed = 5;
    private double currentJumpSpeed = jumpSpeed;
    private double currentFallSpeed = 0.1;
    //private final double startingY = y; probably won't need once blocks are placed
    private boolean topCollision = false;
    private boolean rCollision = false;
    private boolean lCollision = false;
    private boolean GameOver = false;
    private boolean restart = false;
    //Create variable to determine if the game is paused or not
    private boolean pause = false;
    //Create a variable to store if the user presses Enter to quit from the pause screen
    private boolean enterPressed = false;

    //Player constructor
    public Player(int playerWidth, int playerHeight){
        //spawn at middle of screen at the bottom
        x = GamePanel.Width / 2;
        y = GamePanel.Height - (playerHeight + 2);
        this.playerWidth = playerWidth;
        this.playerHeight = playerHeight;
    }

    //return if GameOver
    public boolean WinOrLose() {
        return GameOver;
    }
    //get the y value of the player
    public double getY() {
        return y;
    }
    //returns if the escape key is pressed to the level state
    public boolean ifRestart() {
        return restart;
    }

    //return if the game is paused or not
    public boolean isPause(){
        return pause;
    }

    //determine if the user quits the game from the pause screen or not
    public boolean quitGame(){
        return enterPressed;
    }

    public void tick(List<Form> f) {
        //checks blocks for collision status
        int pX = (int) x;
        int pY = (int) y;
        for (Form form : f) {
            //check if the player is in the pause state
            if (isPause()) {
                form.setFreeze(true);
                continue;
            } else {
                form.setFreeze(false);
            }
            for (int j = 0; j < form.getLength(); j++) {
                //check player's right collision
                if (Collision.PlayerBlock(new Point(pX + playerWidth + 3, pY + 2), form.getBlock(j))
                        || Collision.PlayerBlock(new Point(pX + playerWidth + 3, pY + playerHeight - 1), form.getBlock(j))) {
                    movingRight = false;
                    rCollision = true;
                    lCollision = false;
                }
                //check player's left collision
                if (Collision.PlayerBlock(new Point(pX - 3, pY + 3), form.getBlock(j))
                        || Collision.PlayerBlock(new Point(pX - 3, pY - 2), form.getBlock(j))) {
                    movingLeft = false;
                    lCollision = true;
                    rCollision = false;
                }
                //check if a block is landing on the player (collides with top of player)
                if (Collision.PlayerBlock(new Point(pX + 2, pY + 3), form.getBlock(j))
                        || Collision.PlayerBlock(new Point(pX + playerWidth - 2, pY + 3), form.getBlock(j))) {
                    jumping = false;
                    falling = true;
                }
                //If the form crushes the player, then Game Over
                boolean choice1 = form.getBlock(0).contains(new Point(pX, pY + playerHeight - 2)) && form.getBlock(1).contains(new Point(pX + playerWidth + 1, pY + playerHeight - 2));
                boolean choice2 = form.getBlock(1).contains(new Point(pX, pY + playerHeight - 2)) && form.getBlock(2).contains(new Point(pX + playerWidth + 1, pY + playerHeight - 2));
                boolean choice3 = form.getBlock(2).contains(new Point(pX, pY + playerHeight - 2)) && form.getBlock(3).contains(new Point(pX + playerWidth + 1, pY + playerHeight - 2));
                boolean choice4 = form.getBlock(3).contains(new Point(pX, pY + playerHeight - 2));
                if ((choice1 || choice2 || choice3 || choice4)
                        && !jumping && (rCollision || lCollision) && !movingRight && !movingLeft) {
                    GameOver = true;
                }
                //check if player lands on a block (bottom of player)
                if (Collision.PlayerBlock(new Point(pX + 2, pY + playerHeight + 1), form.getBlock(j))
                        || Collision.PlayerBlock(new Point(pX + playerWidth - 1, pY + playerHeight + 2), form.getBlock(j))) {
                    y = form.getBlock(j).getY() - playerHeight - 1;
                    falling = false;
                    topCollision = true;
                    currentFallSpeed = 0.1;

                    if (form.getName().equals("T")) { //check T form collision
                        if (topCollision && ((pX + playerWidth < form.getBlock(0).width - 1)
                                || pX + playerWidth > form.getBlock(2).width + 1)) {
                            falling = true;
                            topCollision = false;
                        }
                        falling = false;
                    }
                    if (form.getName().equals("S")) { //check S form collision
                        if (topCollision && (pX + playerWidth < form.getBlock(0).width - 1
                                || pX + playerWidth > form.getBlock(3).width + 1
                                || pX + playerWidth < form.getBlock(2).width - 1)) {
                            falling = true;
                            topCollision = false;
                        }
                        falling = false;
                    }
                    if (form.getName().equals("O")) { //check O form collision
                        if (topCollision && ((pX + playerWidth < form.getBlock(0).width - 1)
                                || pX + playerWidth > form.getBlock(1).width + 1)) {
                            falling = true;
                            topCollision = false;
                        }
                        falling = false;
                    }
                    if (form.getName().equals("_")) { //check _ form collision
                        if (topCollision && ((pX + playerWidth < form.getBlock(0).width - 1)
                                || pX + playerWidth > form.getBlock(3).width + 1)) {
                            falling = true;
                            topCollision = false;
                        }
                        falling = false;
                    }

                } else {//check if the player goes off of a block it collides with
                    if (!topCollision && !jumping)
                        falling = true;
                }
                if (rCollision) x += 0;
                if (lCollision) x -= 0;


            }

        }
        if(movingRight){
            //right limit to player movement is Panel width - player Width
            if (x >= GamePanel.Width - playerWidth || isPause())
                x = x + 0;
            else{
                x+=2;  //otherwise move right by 2
                lCollision = false;
            }
        }
        if(movingLeft){
            //left limit to player movement occurs when x is less than or equal to 0
            if (x <= 0 || isPause()) x = x + 0;
            else {
                x-=2; // otherwise move left by 2
                rCollision = false;
            }
        }
        if(jumping){
            if(isPause())
                y+=0;
            else{
                y-=currentJumpSpeed;
                currentJumpSpeed-=0.16; //decelerate due to gravity

                if(currentJumpSpeed <= 0){
                    currentJumpSpeed = jumpSpeed;
                    jumping = false;
                    falling = true;
                }
            }
        }
        if(falling){
            //player stops falling if they reach the bottom border of the game
            if( y > GamePanel.Height - (playerHeight + 2) || topCollision || isPause()) {
                y += 0;
                falling = false;
                currentFallSpeed = 0.16;
            }
            //else if they collide then stop
            else {
                y+= currentFallSpeed;
                //The fall speed and current fall speed(acceleration due to gravity)
                double maxFallSpeed = 5;
                if(currentFallSpeed < maxFallSpeed){
                    currentFallSpeed+=0.16;
                }
            }
        }
    }

    public void draw(Graphics g){
        //draw the player rectangle
        g.setColor(Color.BLACK);
        g.fillRect((int)x,(int)y,playerWidth,playerHeight);
    }

    public void KeyPressed(int k){
        //Making sure you move right/left when right/left arrows are pressed
        if(k == KeyEvent.VK_RIGHT ) {
            movingRight = true;
            lCollision = false;
        }
        if(k == KeyEvent.VK_LEFT ) {
            movingLeft = true;
            rCollision = false;
        }
        if(k == KeyEvent.VK_UP) jumping = true;
        if(k == KeyEvent.VK_R) restart = true;
        if(k == KeyEvent.VK_ESCAPE) pause=!pause;
        if(k == KeyEvent.VK_ENTER && isPause()) enterPressed = true;
    }

    public void KeyReleased(int k){
        //Making sure you stop moving right/left when right/left arrows are released
        if(k == KeyEvent.VK_RIGHT) {
            movingRight = false;
        }
        if(k == KeyEvent.VK_LEFT) {
            movingLeft = false;
        }
    }
}
