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
            img = ImageIO.read(Resources.class.getResource("ImgResources/"+imgDesc+".png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return img;
    }
}
