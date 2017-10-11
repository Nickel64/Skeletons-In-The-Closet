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
    private Image OneWayEntry;
    private Image OneWayExit;
    private Image[] decor = new Image[5];


    public TileSet(int level){
        try {
            floor = ImageIO.read(Resources.class.getResource("ImgResources/Floors/" + "floor" + level + ".png"));
            wall = ImageIO.read(Resources.class.getResource("ImgResources/Walls/" + "Wall" + level + ".png"));
            wallTop = ImageIO.read(Resources.class.getResource("ImgResources/Walls/" + "WallTop" + level + ".png"));
            wallBottom = ImageIO.read(Resources.class.getResource("ImgResources/Walls/" + "WallBot" + level + ".png"));
            wallLeft = ImageIO.read(Resources.class.getResource("ImgResources/Walls/" + "WallLeft" + level + ".png"));
            wallRight = ImageIO.read(Resources.class.getResource("ImgResources/Walls/" + "WallRight" + level + ".png"));
            OneWayEntry = read(Resources.class.getResource("ImgResources/Doors/" + "OneWayEntry.png"));
            OneWayExit = read(Resources.class.getResource("ImgResources/Doors/" + "OneWayExit.png"));
            //TODO decor = ImageIO.read(Resources.class.getResource("ImgResources/DecorObjects/" + "decor_" + level + "_"+n + ".png"));
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
    public Image getEntry(){
        return OneWayEntry;
    }
    public Image getExit(){
        return OneWayExit;
    }
    //public Image getDecor(){
        //return decor;
    //}
    public Image getWallTop(){return wallTop;}
    public Image getWallBottom(){return wallBottom;}
    public Image getWallLeft() {return wallLeft;}
    public Image getWallRight() {return wallRight;}
}
