package Utils;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

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
            "Created by Nick, Morgan, Ben, Rachel, Belle\n\n" +
            "Controls\n" +
            "Movement: Direction Pad / WASD / click-to-move/ on screen buttons\n" +
            "Attack: On screen button / spacebar\n" +
            "Defend: On screen button / 'e'\n" +
            "AoE: On screen button / 'q'";

    //LOAD AND SAVE MESSAGES
    public static String SAVE_SUCCESSFUL_MESSAGE = "Game saved successfully";
    public static String SAVE_UNSUCCESSFUL_MESSAGE = "ERROR: The game was unable to be saved";
    public static String LOAD_NOSAVES_MESSAGE = "There are no saved games to load from";
    public static String LOAD_UNSUCCESSFUL_MESSAGE = "ERROR: Unable to load this saved game";
    public static String LOAD_PROMPT_MESSAGE = "Select a saved game to Load:";
    public static String LOAD_TITLE_MESSAGE = "Load Game:";


    //MAIN MENU BUTTONS
    public static String MENU_NEWGAME_BUTTON = "New Game";
    public static String MENU_SAVEDGAME_BUTTON = "Load Game";
    public static String MENU_HELP_BUTTON = "Instructions";
    public static String MENU_QUIT_BUTTON = "Exit";


    //PAUSE MENU BUTTONS
    public static String PAUSE_LOAD_BUTTON = "Load Game";
    public static String PAUSE_SAVE_BUTTON = "Save Game";
    public static String PAUSE_HELP_BUTTON = "Help";
    public static String PAUSE_QUIT_BUTTON = "Quit Game";
    public static String PAUSE_PAUSE_BUTTON = "Pause";
    public static String PAUSE_RESUME_BUTTON = "Resume";
    public static String PAUSE_NEWGAME_BUTTON = "New Game";

    public static String PAUSE_MENU_TITLE = "Game Paused";
    public static String EXIT_CONFIRM = "Are you sure that you want to exit?";
    public static String NEWGAME_CONFIRM = "Are you sure that you want to start a new game?";
    public static String DEATH_MESSAGE = "YOU'VE DIED \n Oh no, unfortunately you have not succeeded at your quest. \n" +
            "For that, you must now die";
    public static String SUCCESS_MESSAGE = "YOU WON!\n You fought hard and have reached the point where you have slain all of the skeletons in your closet.";

    public static int BOSSES_TO_WIN = 7;

    public static Dimension WINDOW_SIZE = new Dimension(1024,768);

    public static Color transparent = new Color(0,0,0,0);
    public static Color shadowBack = new Color(32,39,32);
    public static Color doorGlow = new Color(255,200,0,50);
    public static int radius = 400;
    public static int lightRadius = 200;

    public static int levels = 7;   //zero indexed


    public static Image getImage(String imgDesc){
        if(imgDesc.equals("border") || imgDesc.equals("Manhole") || imgDesc.equals("GameOver")){
            String temp = "Decor/";
            temp += imgDesc;
            imgDesc = temp;
        }
        else if(imgDesc.startsWith("PowerUp")){
            String temp = "SpriteSets/PowerUps/";
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

    public static Image getDecorObject(int level){
        Random random = new Random();
        String path;
        if(level == 7) {
            int bound = 2;
            int n = random.nextInt(bound);
            path = "ImgResources/DecorObjects/Skull" + n + ".png";
        }
        else {
            int bound = 23;
            int n = random.nextInt(bound);
            path = "ImgResources/DecorObjects/Decor" + n + ".png";
        }
        try {
            return ImageIO.read(Resources.class.getResource(path));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /** Plays an audio clip*/
    public static void playAudio(String name) {
        try {
            Clip clip = AudioSystem.getClip();
            URL localURL = Resources.class.getResource("SoundResources/"+name);
            AudioInputStream ais = AudioSystem.getAudioInputStream(localURL);
            clip.open(ais);
            clip.start();
        } catch(Exception e) {
            e.printStackTrace();
            //throw new Error("Could not play audio");
        }
    }
}
