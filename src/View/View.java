package View;

import Map.*;
import Model.*;
import Utils.*;
import Entities.*;
import Controller.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * The main view of the game
 * Contained within a JFrame, the View handles the graphical representation of the game world
 * A separate JPanel is used to contain the PlayerPanel and the button controls.
 *
 * Created: 18/09/2017
 * @author Nicholas Snellgrove
 */
public class View extends JComponent implements Observer{

    //Panels of the view
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
    Model model;

    //visual fields
    int startX = 165;
    int startY = 30;
    int tileSize = 50;

    public View(Model m) {

        this.model = m;

        //setting up the frame
        frame = new JFrame(Resources.TITLE);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(Resources.WINDOW_SIZE);
        frame.setFocusable(true);

        //setting up the other panels
        playerStats = new PlayerPanel(frame);
        menu = new Menu(frame);

        //build the interface panel
        //a lot of logic in this part
        //so its a separate method
        buildInterface();
        buildControls();

        //adding the buttons to the menubar
        menuBar.add(menuBtn);
        menuBar.add(helpBtn);
        menuBar.add(quitBtn);

        //adding the components
        frame.setJMenuBar(menuBar);
        frame.add(menu, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);
        frame.add(interfacePanel, BorderLayout.SOUTH);

        //final setups

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
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


    ////////////////////////
    //Visual methods below//
    ////////////////////////

    /**
     * Will draw the interface of the game. I.e. Menu bars, stats etc
     *
     * @param g the graphics2D object to draw to
     */

    public void drawInterface(Graphics2D g){
        g.drawImage(Resources.getImage("border"), 0, this.getHeight()-Resources.getImage("border").getHeight(null), null);
        playerStats.repaint();
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
        for(int y = 0; y < r.ROOM_HEIGHT; y++){
            for(int x = 0; x < r.ROOM_WIDTH; x++){
                //just a visual thing
                //14x10 seems good to me
                //g.setColor(Color.black);
                //g.drawRect(startX+(50*x),startY+(tileSize*y),tileSize,tileSize);
                drawTile(g, model.getCurrentRoom().getTileAtLocation(x,y), x, y);
            }
        }
    }

    /**
     * Will draw any given tile at its appropriate coordinates
     * Good for testing
     * Should scale based on size of window(maybe)
     *
     * @param g the graphics2D object to draw to
     * @param t the tile to be drawn
     */
    public void drawTile(Graphics2D g, Tile t, int x, int y){
        //TODO implement with actual images
        if(t instanceof DoorTile){
            g.setColor(Color.GRAY);
        }
        else if(t instanceof FloorTile){
            g.setColor(Color.cyan);
        }
        g.fillRect(startX+(50*x),startY+(tileSize*y),tileSize,tileSize);
        if(t.getEntity() != null){
            drawEntity(g, t.getEntity(), x,y);
        }
    }

    /**
     * Will draw an entity at its appropriate coordinates
     * Should scale based on size of window(maybe)
     *
     * @param g the graphics2D object to draw to
     */

    public void drawEntity(Graphics2D g, Entity e, int x, int y){
        //TODO I'm gonna do this smarter I promise
        if(e instanceof Nothing)
            return;
        else if(e instanceof Player){
            g.setColor(Color.blue);
            g.fillOval(startX + tileSize/4+(50*x),startY+tileSize/4+(tileSize*y),tileSize/2,tileSize/2);
        }
        else if(e instanceof  Wall){
            g.setColor(Color.darkGray);
            g.fillRect(startX+(50*x),startY+(tileSize*y),tileSize,tileSize);
        }
        else if(e instanceof Enemy){
            g.setColor(Color.red);
            g.fillOval(startX + tileSize/4+(50*x),startY+tileSize/4+(tileSize*y),tileSize/2,tileSize/2);
        }
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


    /**
     * If an entity speaks, and nobody has programmed a way for it to speak out loud,
     * did it speak at all?
     *
     * @param g the plane of existence
     * @param e the entity that's having a crisis
     */
    public void showText(Graphics2D g, Entity e){

    }

    /////////////////////////
    //Utility Methods below//
    /////////////////////////

    public void buildControls(){
        //custom actionListeners for the menu buttons
        this.quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(frame, "Quit without saving? " +
                        "(All unsaved progress will be lost");
                if(check == 0)
                    frame.dispose();
                else
                    return;
            }
        });
        this.helpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "We'll have some instructions in here one day");
            }
        });
        this.menuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Steady on, mate");
            }
        });

        frame.addKeyListener(controller);
        this.addMouseListener(controller);
        left.addActionListener(controller);
        up.addActionListener(controller);
        down.addActionListener(controller);
        right.addActionListener(controller);
        attack.addActionListener(controller);
        defend.addActionListener(controller);
        AoE.addActionListener(controller);
    }


    public void buildInterface(){

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

    }
}
