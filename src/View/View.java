package View;

import Map.*;
import Utils.*;
import Entities.*;
import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * The main view of the game
 *
 * Created: 18/09/2017
 * @author Nicholas Snellgrove
 */
public class View extends JComponent implements Observer{

    PlayerPanel playerStats;
    Menu menu;

    Controller controller;

    public View() {
        JFrame f = new JFrame(Resources.TITLE);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        playerStats = new PlayerPanel(null);
        menu = new Menu();


        //TODO things and stuff

        f.pack();
        f.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    /**
     * Will draw the interface of the game. I.e. Menu bars, stats etc
     *
     * @param g the graphics2D object to draw to
     */

    public void drawInterface(Graphics2D g){

    }

    /**
     * Will be called by a key press or a button, handled by the controller.
     * Will display a pop-up menu, with buttons on it to save, load, quit and resume.
     */

    public void showMenu(){

    }


    /**
     * Will draw the room in the game world that the player is currently in.
     *
     * @param g the graphics2D object to draw to
     */
    public void drawWorld(Graphics2D g){

    }


    /**
     * Will draw any given room to the graphics pane.
     * Should scale depending on size of window (Maybe)
     * Good for testing
     *
     * @param g the graphics2D object to draw to
     */
    public void drawRoom(Graphics2D g, Room r){

    }

    /**
     * Will draw any given tile at its appropriate coordinates
     * Good for testing
     * Should scale based on size of window(maybe)
     *
     * @param g the graphics2D object to draw to
     * @param t the tile to be drawn
     */
    public void drawTile(Graphics2D g, Tile t){

    }

    /**
     * Will draw an entity at its appropriate coordinates
     * Should scale based on size of window(maybe)
     *
     * @param g the graphics2D object to draw to
     */

    public void drawEntity(Graphics2D g, Entity e){

    }

    /**
     * Should animate an entity doing an action (Movement, attack, death, open, close...)
     * Maybe
     *
     * @param g the graphics2D object to draw to
     */

    public void animateEntity(Graphics2D g, Entity e){

    }

    /**
     * Maybe the screen should fade out and fade back into a new room
     *
     * @param g the graphics2D object to draw to
     */

    public void changeRoom(Graphics2D g){

    }


    /**
     * If we do any cutscene type thingies, I guess we can have something like this
     *
     * @param g the graphics2D object to draw to
     */
    public void showScene(Graphics2D g){

    }

    public void showText(Graphics2D g, Entity e){

    }
}
