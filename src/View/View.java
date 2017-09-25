package View;

import Map.*;
import Model.*;
import Utils.*;
import Entities.*;
import Controller.*;

import javax.swing.*;
import javax.swing.border.Border;
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

    JFrame frame;
    PlayerPanel playerStats;
    Menu menu;
    JPanel interfacePanel = new JPanel();
    JPanel buttonPanel;
    JMenuBar menuBar = new JMenuBar();

    //menu buttons
    JButton menuBtn = new JButton("Menu");
    JButton helpBtn = new JButton("Help");
    JButton quitBtn = new JButton("Quit");

    //control buttons
    JButton up = new JButton();
    JButton left = new JButton();
    JButton down = new JButton();
    JButton right = new JButton();
    JButton attack = new JButton();
    JButton AoE = new JButton();
    JButton defend = new JButton();

    //MVC fields
    Controller controller;
    Model model = new Model();

    public View() {

        //setting up the frame
        frame = new JFrame(Resources.TITLE);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(Resources.WINDOW_SIZE);
        frame.setFocusable(true);

        //setting up the other panels
        playerStats = new PlayerPanel(frame);
        menu = new Menu(frame);

        //setting up the movement buttons
        up.setIcon(new ImageIcon(Resources.getImage("up")));
        up.setBackground(Color.black);
        down.setIcon(new ImageIcon(Resources.getImage("down")));
        down.setBackground(Color.black);
        left.setIcon(new ImageIcon(Resources.getImage("left")));
        left.setBackground(Color.black);
        right.setIcon(new ImageIcon(Resources.getImage("right")));
        right.setBackground(Color.black);

        //setting up the action buttons
        attack.setIcon(new ImageIcon(Resources.getImage("attack")));
        attack.setBackground(Color.black);
        AoE.setIcon(new ImageIcon(Resources.getImage("aoe")));
        AoE.setBackground(Color.black);
        defend.setIcon(new ImageIcon(Resources.getImage("defend")));
        defend.setBackground(Color.black);


        //Setting up the gridBagLayout
        GridBagConstraints c = new GridBagConstraints();


        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        JPanel movePanel = new JPanel(new GridBagLayout());
        movePanel.setBackground(Color.BLACK);
        buttonPanel.add(movePanel);


        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 1.0;
        //set the constraints for each button before adding it to the panel
        c.gridx = 1;
        c.gridy = 0;
        //c.ipadx = 50;
        movePanel.add(up, c);
        c.gridx = 1;
        c.gridy = 2;
        movePanel.add(down, c);
        c.gridx = 0;
        c.gridy = 1;
        movePanel.add(left, c);
        c.gridx = 2;
        c.gridy = 1;
        movePanel.add(right, c);
        buttonPanel.add(attack);
        buttonPanel.add(AoE);
        buttonPanel.add(defend);


        //setting up the interface panel
        interfacePanel.setBackground(Color.black);
        interfacePanel.setLayout(new BorderLayout());
        interfacePanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()/5));
        interfacePanel.add(buttonPanel, BorderLayout.WEST);
        interfacePanel.add(playerStats, BorderLayout.EAST);


        //adding the buttons to the menubar
        menuBar.add(menuBtn);
        menuBar.add(helpBtn);
        menuBar.add(quitBtn);

        //adding the components
        frame.setJMenuBar(menuBar);
        frame.add(menu, BorderLayout.NORTH);
        //menu.setVisible(false);
        frame.add(this, BorderLayout.CENTER);
        //this.setVisible(false);
        frame.add(interfacePanel, BorderLayout.SOUTH);

        //final setups

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        this.drawRoom(gg, model.getCurrentRoom());
        this.drawInterface(gg);
    }

    @Override
    public Dimension getPreferredSize() {
        int width = frame.getWidth();
        int height = frame.getHeight()*4/5;
        Dimension screen = new Dimension(width, height);
        return screen;
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    /**
     * Will draw the interface of the game. I.e. Menu bars, stats etc
     *
     * @param g the graphics2D object to draw to
     */

    public void drawInterface(Graphics2D g){
        g.drawImage(Resources.getImage("border"), 0, this.getHeight()-Resources.getImage("border").getHeight(null), null);
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
