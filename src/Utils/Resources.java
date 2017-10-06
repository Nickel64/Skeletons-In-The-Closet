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

    public static boolean DEBUG = false; //used to enable/disable debug messages


    //A collection of various game resources
    public static String TITLE = "Skeletons In The Closet";

    public static String HELPDESC = "Skeletons in the Closet! \n\n" +
            "Welcome to our game. More information about this game will be avaliable here\n\n" +
            "Created by Nick, Morgan, Ben, Rachel, Belle";

    public static String SAVE_SUCCESSFUL_MESSAGE = "Game saved successfully";
    public static String SAVE_UNSUCCESSFUL_MESSAGE = "ERROR: The game was unable to be saved";

    public static Dimension WINDOW_SIZE = new Dimension(1024,720);

    public static Color transparent = new Color(0,0,0,0);
    public static Color shadowBack = new Color(32,39,32);
    public static Color doorGlow = new Color(255,200,0,50);
    public static int radius = 400;
    public static int lightRadius = 200;


    public static Image getImage(String imgDesc){
        if(imgDesc.equals("border")){
            String temp = "Decor/";
            temp += imgDesc;
            imgDesc = temp;
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
