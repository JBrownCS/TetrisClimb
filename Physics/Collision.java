package Physics;

import java.awt.Point;
import Entities.Block;

public class Collision {
    //check if the player collides with a block
    public static boolean PlayerBlock(Point p, Block b){
        return b.contains(p);
    }

    public static boolean BlockCollision(Block a, Block b){
        boolean collides = false;
        //check if block lands on another block
        Point choice1 = new Point(a.x + 2, a.y + a.height + 1);
        Point choice2 = new Point(a.x + Block.blockSize - 1, a.y + Block.blockSize + 2);
        //Point choice3 = new Point(a.x + a.blockSize / 2,);
        if(b.contains(choice1) ||  b.contains(choice2)){
            collides = true;
        }

        return collides;
    }
}
