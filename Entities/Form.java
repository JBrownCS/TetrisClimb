package Entities;

import java.awt.Color;
import java.awt.Graphics;
import Main.GamePanel;
import Physics.Collision;
import java.util.List;
public class Form {
    //Each form has 4 blocks
    private Block a;
    private Block b;
    private Block c;
    private Block d;
    //Name of the form, which then determines how its 4 blocks are drawn
    private final String name;
    //Its placement in the main list of forms
    int placement;
    //Initialize the block color for the blocks
    Color blockColor = Color.BLACK;
    //Number of blocks in a form
    private final int length = 4;
    //If the game over condition is met
    private boolean GameOver = false;
    //If the forms need to be frozen, which means the game is paused
    private boolean Freeze = false;
    //Determines if the form collides with another form of the same type
    private boolean SameTypeCollision = false;
    //The form of the same type that the form collides with
    private Form formItCollidedWith;
    //Returns if the form is colliding with any other forms
    private boolean isColliding = false;

    public Form(int listIndex){
        this.placement = listIndex;
        //Create an array of array names to randomize which forms are created
        String[] formNames = {"T","S", "O", "_"};

        //The form name is the string assigned by a random index in the form names list
        this.name = formNames[(int) (Math.random() * (formNames.length))];

        //X and Y coordinates for when the form is drawn on the game screen
        int xCoordinate;
        int yCoordinate = -50;
        switch(name) {
            //case O definition
            case "O":
                blockColor = Color.BLUE;
                xCoordinate = (int) (Math.random() * (GamePanel.Width -  2 * Block.blockSize));
                this.a = new Block(xCoordinate,yCoordinate);
                this.b = new Block(xCoordinate + Block.blockSize, yCoordinate);
                this.c = new Block(xCoordinate, yCoordinate + Block.blockSize + 1);
                this.d = new Block(xCoordinate + Block.blockSize, yCoordinate + Block.blockSize + 1);
                break;
            //case S definition
            case "S":
                blockColor = Color.ORANGE;
                xCoordinate = (int) (Math.random() * (GamePanel.Width -  3 * Block.blockSize - 1));
                this.a = new Block(xCoordinate,yCoordinate);
                this.b = new Block(xCoordinate + Block.blockSize, yCoordinate);
                this.c = new Block(xCoordinate + Block.blockSize, yCoordinate - Block.blockSize - 1);
                this.d = new Block(xCoordinate + (2 * Block.blockSize), yCoordinate - Block.blockSize - 1);
                break;
            //case T definition
            case "T":
                blockColor = Color.RED;
                xCoordinate = (int) (Math.random() * (GamePanel.Width -  3 * Block.blockSize));
                this.a = new Block(xCoordinate,yCoordinate);
                this.b = new Block(xCoordinate + Block.blockSize + 1, yCoordinate);
                this.c = new Block(xCoordinate + (2 * Block.blockSize) + 1, yCoordinate);
                this.d = new Block(xCoordinate + Block.blockSize + 1, yCoordinate + Block.blockSize + 1);
                break;
            //case _ definition
            case "_":
                blockColor = Color.YELLOW;
                xCoordinate = (int) (Math.random() * (GamePanel.Width -  4 * Block.blockSize));
                this.a = new Block(xCoordinate,yCoordinate);
                this.b = new Block(xCoordinate + Block.blockSize, yCoordinate);
                this.c = new Block(xCoordinate + (2 * Block.blockSize), yCoordinate);
                this.d = new Block(xCoordinate + (3 * Block.blockSize), yCoordinate);
                break;
        }
    }
    //Updates the placement of the form in the main list of forms by the specified value
    public void setPlacement(int increment){
        this.placement += increment;
    }

    //Changes SameTypeCollision to false
    public void noLongerSameTypeCollision(){ this.SameTypeCollision = false;}

    //Determines if the forms need to freeze due to pause state
    public void setFreeze(boolean choice){
        Freeze=choice;
    }

    //Sets whether the form is colliding with other forms based on a given value
    public void setCollision(boolean setter){
        this.isColliding = setter;
    }
    //Returns if the form is colliding with any other forms or not
    public boolean getIfColliding(){
        return isColliding;
    }

    //Get the number of blocks in the form
    public int getLength(){
        return length;
    }

    //forms check each other for collision detection
    public void tick(List<Form> forms){
        //check collisions first
        for(int numOfForms = 0; numOfForms < forms.size(); numOfForms++ ){
            //If there is just one form (itself) then just descend
            if(forms.size() == 1){
                if(this.a.y >= GamePanel.Height - (Block.blockSize + 1)
                        || this.b.y >= GamePanel.Height - (Block.blockSize + 1)
                        || this.c.y >= GamePanel.Height - (Block.blockSize + 1)
                        || this.d.y >= GamePanel.Height - (Block.blockSize + 1) || Freeze){
                    this.a.y+=0;
                    this.b.y+=0;
                    this.c.y+=0;
                    this.d.y+=0;
                }
                else {
                    this.a.y++;
                    this.b.y++;
                    this.c.y++;
                    this.d.y++;
                }
            }
            //Check the collision otherwise as there are other forms
            else {
                int numOfCollisions = 0;
                //Check if Block a collides with any other blocks
                if(Collision.BlockCollision(this.a, forms.get(numOfForms).a)
                        || Collision.BlockCollision(this.a, forms.get(numOfForms).b)
                        || Collision.BlockCollision(this.a, forms.get(numOfForms).c)
                        || Collision.BlockCollision(this.a, forms.get(numOfForms).d)){
                    numOfCollisions+= 1;
                }
                //Check if Block b collides with any other blocks
                if(Collision.BlockCollision(this.b, forms.get(numOfForms).a)
                        || Collision.BlockCollision(this.b, forms.get(numOfForms).b)
                        || Collision.BlockCollision(this.b, forms.get(numOfForms).c)
                        || Collision.BlockCollision(this.b, forms.get(numOfForms).d)){
                    numOfCollisions+= 1;
                }
                //Check if Block c collides with any other blocks
                if(Collision.BlockCollision(this.c, forms.get(numOfForms).a)
                        || Collision.BlockCollision(this.c, forms.get(numOfForms).b)
                        || Collision.BlockCollision(this.c, forms.get(numOfForms).c)
                        || Collision.BlockCollision(this.c, forms.get(numOfForms).d)){
                    numOfCollisions+= 1;
                }
                //Check if Block d collides with any other blocks
                if(Collision.BlockCollision(this.d, forms.get(numOfForms).a)
                        || Collision.BlockCollision(this.d, forms.get(numOfForms).b)
                        || Collision.BlockCollision(this.d, forms.get(numOfForms).c)
                        || Collision.BlockCollision(this.d, forms.get(numOfForms).d)){
                    numOfCollisions+= 1;
                }

                //If any block collides with others, then all blocks within the form stop moving
                if(numOfCollisions >= 1 || Freeze || getIfColliding()){
                    setCollision(true);
                    this.a.y+=0;
                    this.b.y+=0;
                    this.c.y+=0;
                    this.d.y+=0;
                    /*
                        If the form collides with another form and it has the same name and block color
                        but not the same placement then it is colliding with a form of the same type
                     */
                    if(numOfCollisions >=1
                        && forms.get(numOfForms).name.equals(this.name)
                            && this.blockColor == forms.get(numOfForms).blockColor
                        && this.placement != forms.get(numOfForms).placement){
                        formItCollidedWith = forms.get(numOfForms);
                        SameTypeCollision = true;
                    }
                }
                //If any of the blocks reach the bottom of the stage
                else if(this.a.y >= GamePanel.Height - (Block.blockSize + 1)
                        || this.b.y >= GamePanel.Height - (Block.blockSize + 1)
                        || this.c.y >= GamePanel.Height - (Block.blockSize + 1)
                        || this.d.y >= GamePanel.Height - (Block.blockSize + 1)){
                    this.a.y+=0;
                    this.b.y+=0;
                    this.c.y+=0;
                    this.d.y+=0;
                }
                else{ //allow the blocks to descend
                    this.a.y++;
                    this.b.y++;
                    this.c.y++;
                    this.d.y++;
                }
            }
        }
    }

    public boolean ifCollideWithSameType(){
        return SameTypeCollision;
    }

    public int indexItCollidedWith(){
        return formItCollidedWith.placement;
    }

    //calls the block draw method for blocks a,b,c,d
    public void draw(Graphics g){
        this.a.draw(g, blockColor);
        this.b.draw(g, blockColor);
        this.c.draw(g, blockColor);
        this.d.draw(g, blockColor);
    }
    //returns the individual blocks of the form
    public Block getBlock(int num) {
        Block returnBlock = null;
        switch(num){
            case 0:
                returnBlock = this.a;
                break;
            case 1:
                returnBlock = this.b;
                break;
            case 2:
                returnBlock = this.c;
                break;
            case 3:
                returnBlock = this.d;
                break;
        }
        return returnBlock;
    }

    //returns the name of the form
    public String getName() {
        return name;
    }

    //returns if the Game Over conditions are met
    public boolean isGameOver(){
        return GameOver;
    }

}