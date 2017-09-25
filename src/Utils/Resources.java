package Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Used to store things
 *
 * @author Morgan French-Stagg
 */
public class Resources {

    //A collection of various game resources
    public static String TITLE = "Skeletons In The Closet";

    public static Dimension WINDOW_SIZE = new Dimension(1024,720);

    public static Image getImage(String imgDesc){
        Image img = null;

        try {
            switch (imgDesc) {
                case "attack":
                    img = ImageIO.read(Resources.class.getResource("ImgResources/crossed-swords2.png"));
                    break;
                case "defend":
                    img = ImageIO.read(Resources.class.getResource("ImgResources/vibrating-shield.png"));
                    break;
                case "aoe":
                    img = ImageIO.read(Resources.class.getResource("ImgResources/cycle.png"));
                    break;
                case "up":
                    img = ImageIO.read(Resources.class.getResource("ImgResources/upArrow.png"));
                    break;
                case "down":
                    img = ImageIO.read(Resources.class.getResource("ImgResources/downArrow.png"));
                    break;
                case "left":
                    img = ImageIO.read(Resources.class.getResource("ImgResources/leftArrow.png"));
                    break;
                case "right":
                    img = ImageIO.read(Resources.class.getResource("ImgResources/rightArrow.png"));
                    break;
                case "border":
                    img = ImageIO.read(Resources.class.getResource("ImgResources/border.png"));
                default:
                    throw new Error("Invalid image request");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            return img;
        }
    }
}
