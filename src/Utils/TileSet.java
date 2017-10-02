package Utils;

import Utils.Resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by nicks on 2/10/2017.
 */
public class TileSet {

    private Image floor;
    private Image wall;
    private Image door;
    private Image decor;


    public TileSet(int level){
        try {
            floor = ImageIO.read(Resources.class.getResource("ImgResources/Floors/" + "floor" + level + ".png"));
            wall = ImageIO.read(Resources.class.getResource("ImgResources/Walls/" + "wall" + level + ".png"));
            door = ImageIO.read(Resources.class.getResource("ImgResources/Doors/" + "door" + level + ".png"));
            //TODO decor = ImageIO.read(Resources.class.getResource("ImgResources/DecorObjects/" + "decor" + level + ".png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public Image getFloor(){
        return floor;
    }
    public Image getWall(){
        return wall;
    }
    public Image getDoor(){
        return door;
    }
    public Image getDecor(){
        return decor;
    }

}
