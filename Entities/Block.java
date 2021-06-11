package Entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class Block extends Rectangle{
    private static final long SerialVersionUID = 1;
    //set the block size. Will be constant value
    public static final int blockSize = 40;
    public Block(int x, int y){
        setBounds(x,y,blockSize - 1, blockSize - 1);
    }

    public void draw(Graphics g, Color blockColor){
        g.setColor(blockColor);
        g.fillRect(x, y, width, height);
    }

}
