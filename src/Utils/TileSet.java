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
    private Image lockUp;
    private Image lockDown;
    private Image lockLeft;
    private Image lockRight;


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
            lockUp = read(Resources.class.getResource("ImgResources/Doors/LockUp.png"));
            lockDown = read(Resources.class.getResource("ImgResources/Doors/LockDown.png"));
            lockLeft = read(Resources.class.getResource("ImgResources/Doors/LockLeft.png"));
            lockRight = read(Resources.class.getResource("ImgResources/Doors/LockRight.png"));

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
    public Image getWallTop(){return wallTop;}
    public Image getWallBottom(){return wallBottom;}
    public Image getWallLeft() {return wallLeft;}
    public Image getWallRight() {return wallRight;}
    public Image getLockTop(){ return lockUp;}
    public Image getLockDown(){ return lockDown;}
    public Image getLockLeft(){ return lockLeft;}
    public Image getLockRight(){ return lockRight;}
}
