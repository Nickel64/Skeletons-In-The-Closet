package Utils;

import Utils.Resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

/**
 * Created by nicks on 2/10/2017.
 */
public class TileSet {

    private Image floor;
    private Image wall;
    private Image wallTop;
    private Image wallBottom;
    private Image wallLeft;
    private Image wallRight;
    //private Image door;
    private Image decor;


    public TileSet(int level){
        try {
            floor = read(Resources.class.getResource("ImgResources/Floors/" + "floor" + level + ".png"));
            wall = read(Resources.class.getResource("ImgResources/Walls/" + "wall" + level + ".png"));
            wallTop = read(Resources.class.getResource("ImgResources/Walls/" + "wallTop" + level + ".png"));
            wallBottom = read(Resources.class.getResource("ImgResources/Walls/" + "wallBot" + level + ".png"));
            wallLeft = read(Resources.class.getResource("ImgResources/Walls/" + "wallLeft" + level + ".png"));
            wallRight = read(Resources.class.getResource("ImgResources/Walls/" + "wallRight" + level + ".png"));
            //door = read(Resources.class.getResource("ImgResources/Doors/" + "door" + level + ".png"));
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
    //public Image getDoor(){
    //return door;
    //}
    public Image getDecor(){
        return decor;
    }
    public Image getWallTop(){return wallTop;}
    public Image getWallBottom(){return wallBottom;}
    public Image getWallLeft() {return wallLeft;}
    public Image getWallRight() {return wallRight;}
}
