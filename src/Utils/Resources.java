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
        if(imgDesc.equals("border")){
            String temp = "Decor/";
            temp += imgDesc;
            imgDesc = temp;
        }
        else if(imgDesc.contains("wall")){

        }
        else if(imgDesc.contains("floor")){

        }
        else{   //an icon
            String temp = "Icons/";
            temp += imgDesc;
            imgDesc = temp;
        }
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
