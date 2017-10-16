package View;

import Map.*;
import Model.*;
import Utils.*;
import Entities.*;
import Controller.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

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
    private JFrame frame;
    private PlayerPanel playerStats;
    private JPanel interfacePanel = new JPanel();
    private JPanel buttonPanel;
    private JMenuBar menuBar = new JMenuBar();
    private JPanel pauseMenu;

    //menu buttons
    private JButton pauseBtn = new JButton(Resources.PAUSE_PAUSE_BUTTON);

    //control buttons
    private JButton up = new JButton();
    private JButton left = new JButton();
    private JButton down = new JButton();
    private JButton right = new JButton();
    private JButton attack = new JButton();
    private JButton AoE = new JButton();
    private JButton defend = new JButton();

    //MVC fields
    private Controller controller;
    private Model model;

    //visual fields
    private int startX;
    private int startY;
    private int tileSize = 50;

    //other fields
    private Image border;
    private Image gameOver;
    private SaveLoad saveLoad;

    public boolean pauseMenuVisible = false;
    public boolean paused = false;
    public boolean gameDone = false;

    public View(Model m) {

        this.border = Resources.getImage("border");
        this.gameOver = Resources.getImage("GameOver");
        this.model = m;

        controller = new Controller(m, this);
        model.addObserver(this);

        saveLoad = new SaveLoad();

        //setting up the frame
        frame = new JFrame(Resources.TITLE);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(Resources.WINDOW_SIZE);
        frame.setFocusable(true);

        //setting up the other panels
        playerStats = new PlayerPanel(frame, model.getPlayer());
        model.getPlayer().addObserver(playerStats);

        //build the interface panel
        //a lot of logic in this part
        //so its a separate method
        buildInterface();
        buildControls();

        //adding the buttons to the menubar
        menuBar.add(pauseBtn);

        //adding the components
        frame.setJMenuBar(menuBar);
        frame.add(this, BorderLayout.CENTER);
        frame.add(interfacePanel, BorderLayout.SOUTH);

        //final setups
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setFocusable(true);
        this.setDoubleBuffered(true);
        frame.setLocationRelativeTo(null);

        this.getGraphics().drawImage(border, 0, this.getHeight()-border.getHeight(null),null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        long start = System.currentTimeMillis();

        Graphics2D gg = (Graphics2D) g;

        //player has died (oh no!)
        if(model.getPlayerLocation() == null){
            gameDone = true;
            showGameOverScreen(gg);
            return;
        }

        //player has won (yay!)
        else if(model.getPlayer().getBossesDefeated() >= Resources.BOSSES_TO_WIN){
            gameDone = true;
            paused = true;
            frame.setJMenuBar(null);
            frame.remove(interfacePanel);
            JOptionPane.showMessageDialog(frame, Resources.SUCCESS_MESSAGE);
            frame.dispose();
            Resources.bgm.stop();
            System.exit(0);
            return;
        }

        //player has de-paused (let's go!)
        else if(!pauseMenuVisible){
            drawWorld(gg);
            drawAllEntities(model, gg);
            drawShadows(gg, model.getPlayerLocation());
        }

        //player has paused (hold it...)
        else{
            showPauseMenu(gg);
        }

        g.drawImage(border, 0, this.getHeight()-border.getHeight(null),null);

        //the AoE should go over the top
        if(model.getPlayer().isPlayerAttackAoE()){
            int x = (model.getPlayerLocation().x*tileSize)+startX-tileSize;
            int y = (model.getPlayerLocation().y*tileSize)+startY-tileSize;
            g.drawImage(model.getPlayer().getAoE(), x, y, null);
        }

        long end = System.currentTimeMillis()-start;
        if(Resources.DEBUG) System.out.println("View update took ms " + end);
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

    private void showGameOverScreen(Graphics2D g) {
        Resources.playAudio("DeathTaunt.wav");
        this.setVisible(false);
        JPanel gameOverScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics gg) {
                gg.drawImage(gameOver, 0,0,this.getWidth(), this.getHeight(),this);
            }
        };
        gameOverScreen.setLayout(new GridLayout(0,1, 0, 50));
        gameOverScreen.setBorder(new EmptyBorder(getHeight()/2 + 50,10,25,10));

        JLabel deathInfo = new JLabel("<html><center>" + Resources.DEATH_MESSAGE+ "</center></html>");
        deathInfo.setFont(new Font("TimesNewRoman", Font.PLAIN, 25));
        deathInfo.setForeground(new Color(149,34,27));
        deathInfo.setHorizontalAlignment(JLabel.CENTER);

        JButton end = new NiceButton("Accept Your Fate");
        end.addActionListener((e) -> {
            Resources.bgm.stop();
            frame.dispose();
            this.dispose();
            System.exit(0);
        });

        if(Resources.DEBUG) System.out.println(end.getWidth());

        gameOverScreen.add(deathInfo);
        gameOverScreen.add(end);

        frame.remove(this);
        frame.setJMenuBar(null);
        frame.add(gameOverScreen);
    }

    /**
     * Will be called by a key press or a button, handled by the controller.
     * Will display a pop-up menu, with buttons on it to save, load, quit and resume.
     */

    private void showPauseMenu(Graphics2D g){
        this.setVisible(false);

        //Don't need to generate the pause menu over and over
        if(pauseMenu != null) {
            frame.remove(this);
            frame.add(pauseMenu);
            return;
        }

        pauseMenu = new JPanel() {
            @Override
            public void paintComponent(Graphics g1) {
                super.paintComponent(g1);
                g1.drawImage(Resources.manhole,0,0,1040,630,this);
            }
        };
        pauseMenu.addKeyListener(controller);
        pauseMenu.setLayout(new GridLayout(0,1,0,20));
        pauseMenu.setBorder(new EmptyBorder(10,getWidth()/3,100,getWidth()/3));
        paused = true;

        pauseMenu.setPreferredSize(new Dimension(100,250));

        JButton newGame = new NiceButton(Resources.PAUSE_NEWGAME_BUTTON);
        JButton save = new NiceButton(Resources.PAUSE_SAVE_BUTTON);
        JButton load = new NiceButton(Resources.PAUSE_LOAD_BUTTON);
        JButton help = new NiceButton(Resources.PAUSE_HELP_BUTTON);
        JButton quit = new NiceButton(Resources.PAUSE_QUIT_BUTTON);


        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ans = JOptionPane.showConfirmDialog(frame, Resources.NEWGAME_CONFIRM);
                if(ans == 0) {
                    Model m = new Model();
                    try{m.initialise();}
                    catch (IOException g){g.printStackTrace();}
                    replaceModel(m);
                    pauseMenuToggle();
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(frame, "Saving will happen here");
                if(saveLoad.save(model)) JOptionPane.showMessageDialog(frame, Resources.SAVE_SUCCESSFUL_MESSAGE);
                else JOptionPane.showMessageDialog(frame, Resources.SAVE_UNSUCCESSFUL_MESSAGE);
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(saveLoad.saves.size() >= 1) {
                    ArrayList<String> pos = new ArrayList<>();
                    pos.addAll(saveLoad.saves.keySet());
                    Collections.sort(pos);

                    Object[] possibilities = pos.toArray();

                    String result = (String) JOptionPane.showInputDialog(null, Resources.LOAD_PROMPT_MESSAGE, Resources.LOAD_TITLE_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, possibilities, possibilities[possibilities.length-1]);
                    if(result == null) return;
                    if (!replaceModel(saveLoad.load(result))) {
                        JOptionPane.showMessageDialog(frame, Resources.LOAD_UNSUCCESSFUL_MESSAGE);
                    } else pauseMenuToggle();
                }
                else{
                    JOptionPane.showMessageDialog(frame, Resources.LOAD_NOSAVES_MESSAGE);
                }
            }
        });

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, Resources.HELPDESC);
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ans = JOptionPane.showConfirmDialog(frame, Resources.EXIT_CONFIRM);
                if(ans == 0) {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });

        JLabel title = new JLabel(Resources.PAUSE_MENU_TITLE);
        title.setFont(new Font("SansSerif", Font.PLAIN, 25));
        title.setForeground(Color.white);
        title.setHorizontalAlignment(JLabel.CENTER);

        pauseMenu.add(title);
        pauseMenu.add(newGame);
        pauseMenu.add(save);
        pauseMenu.add(load);
        pauseMenu.add(help);
        pauseMenu.add(quit);

        frame.remove(this);
        frame.add(pauseMenu);
    }

    /**
     * Used to allow an event within an ActionListener to set the observer of a model
     * @param m
     */
    private boolean replaceModel(Model m){
        if(m == null) {
            return false;
        }

        model = m;
        controller.setModel(m);
        m.addObserver(this);
        return true;
    }

    /**
     * Removes the pause menu, and resets the frame back to how it was
     */
    private void removePauseMenu(){
        if(pauseMenu != null){
            frame.remove(pauseMenu);
        }

        this.setVisible(true);
        frame.add(this, BorderLayout.CENTER);
    }

    /**
     * Toggles the pause menu on/off
     */
    public void pauseMenuToggle(){
        pauseMenuVisible = !pauseMenuVisible;
        paused = !paused;
        if(!pauseMenuVisible){ removePauseMenu(); pauseBtn.setText(Resources.PAUSE_PAUSE_BUTTON);}
        else pauseBtn.setText(Resources.PAUSE_RESUME_BUTTON);
        repaint();
    }

    /**
     * Will draw the room in the game world that the player is currently in.
     *
     * @param g the graphics2D object to draw to
     */
    public void drawWorld(Graphics2D g){
        g.setColor(new Color(32,39,32));
        g.fillRect(0,0, this.getWidth(), this.getHeight()-border.getHeight(null));
        this.drawRoom(g, model.getCurrentRoom());
    }


    /**w
     * Will draw any given room to the graphics pane.
     * Should scale depending on size of window (Maybe)
     * Good for testing
     *
     * @param g the graphics2D object to draw to
     */
    public void drawRoom(Graphics2D g, Room r){
        int roomWidth = (r.getWidth()*50);
        int roomHeight = (r.getHeight()*50);

        startX = (this.getWidth()/2)-roomWidth/2;
        startY = ((this.getHeight()/2)-roomHeight/2)-tileSize/3;
        for(int y = 0; y < r.getHeight(); y++){
            for(int x = 0; x < r.getWidth(); x++){
                drawTile(g,r.getTileSet(), model.getCurrentRoom().getTileAtLocation(x,y), (x*tileSize)+startX, (y*tileSize)+startY);
            }
        }
    }

    /**
     * Will draw any given tile at its appropriate coordinates
     * Good for testing
     * Should scale based on size of window(maybe)
     *
     *
     * @param g the graphics2D object to draw to
     * @param tileSet the tileset of the current room
     * @param tile the tile to be drawn
     */
    public void drawTile(Graphics2D g, TileSet tileSet, Tile tile, int x, int y){
        Image img;

        int indexY = (y-startY)/tileSize;
        int indexX = (x-startX)/tileSize;
        DoorTile t = null;
        if(tile instanceof DoorTile)
            t = (DoorTile) tile;
            if(indexY == 0){ //draw wall at the top of the room
                if((t != null && model.getRoom(t.toString()).getLevel()-1 > model.getPlayer().getBossesDefeated()))
                    img = tileSet.getLockTop();
                else if(t == null)
                    img = tileSet.getWallTop();
                else
                    img = null;
                if(img != null)
                    g.drawImage(img, x, y-img.getHeight(null), null);
            }
            if(indexY == model.getCurrentRoom().getHeight()-1){
                if((t != null && model.getRoom(t.toString()).getLevel()-1 > model.getPlayer().getBossesDefeated()))
                    img = tileSet.getLockDown();
                else if(t == null)
                    img = tileSet.getWallBottom();
                else
                    img = null;
                if(img != null)
                    g.drawImage(img, x, y+tileSize, null);
            }
            if(indexX == 0){
                if((t != null && model.getRoom(t.toString()).getLevel()-1 > model.getPlayer().getBossesDefeated()))
                    img = tileSet.getLockLeft();
                else if(t == null)
                    img = tileSet.getWallLeft();
                else
                    img = null;
                if(img != null)
                    g.drawImage(img, x-img.getWidth(null), y, null);
            }
            if(indexX == model.getCurrentRoom().getWidth()-1){
                if((t != null && model.getRoom(t.toString()).getLevel()-1 > model.getPlayer().getBossesDefeated()))
                    img = tileSet.getLockRight();
                else if(t == null)
                    img = tileSet.getWallRight();
                else
                    img = null;
                if(img != null)
                    g.drawImage(img, x+tileSize, y, null);
            }

        img = tileSet.getFloor();
        if(img == null)
            return;
        g.drawImage(img, x,y,null);

        if(tile instanceof DoorTile){
            img = tileSet.getFloor();
            if(img == null)
                return;
            g.drawImage(img, x,y,null);
        }

        if(tile instanceof OneWayEntryTeleport){
            if(model.getCurrentRoom().isRoomCleared()){
                img = tileSet.getExit();
            }
            else{
                img = tileSet.getEntry();
            }
            g.drawImage(img, x,y,null);
        }

        if(tile instanceof OneWayExitTeleport){
            img = tileSet.getEntry();
            g.drawImage(img, x,y,null);
        }
    }

    /**
     * Will draw an entity at its appropriate coordinates
     * Should scale based on size of window(maybe)
     *
     * @param g the graphics2D object to draw to
     */

    public void drawEntity(Graphics2D g, Entity e, TileSet tileSet, int x, int y){
        Image img = null;
        Image mod = null;

        if(e instanceof Nothing)
            return;
        else if(e instanceof Decor){
            Decor d = (Decor) e;
            img = d.getImage();
        }

        else if(e instanceof Player){
            Player p = (Player) e;
            if(p.isPlayerAttack()){

                img = p.getAttack();
            }
            else {
                img = p.getIdle();
            }
            if(p.isDefending()){
                mod = p.getDefending();
            }
        }
        else if(e instanceof  Wall){
            img = tileSet.getWall();

        }
        else if (e instanceof  Boss){
            Boss b = (Boss) e;
            if(b.isEnemyAttack()){
                img = b.getAttack();
            }
            else {
                img = b.getIdle();
            }
        }

        else if(e instanceof Enemy){
            Enemy ee = (Enemy) e;
            if(ee.isEnemyAttack()){
                img = ee.getAttack();
            }
            else {
                img = ee.getIdle();
            }
        }

        else if(e instanceof PowerUp){
            PowerUp p = (PowerUp) e;
            img = p.getImage();
        }

        //shift oversize tiles
        int dx = img.getWidth(null)/2 - tileSize/2;
        int dy = img.getHeight(null)/2 - tileSize/2;

        g.drawImage(img, x - dx, y - dy, null);
        if(mod != null)
            g.drawImage(mod, x, y, null);

        if(e instanceof  Boss || e instanceof Enemy && !(e instanceof  Decor)){
            //draw the hp bar background
            g.setColor(Color.black);
            g.fillRect(x, y-tileSize/6, tileSize, tileSize/6);

            //the enemy HP bar
            Enemy temp = (Enemy) e;
            g.setColor(Color.red.darker());
            int endX =(int) Math.ceil(((double)tileSize/temp.getMaxHealth())*temp.getHealth());
            g.fillRect(x, y-tileSize/6,endX, tileSize/6);
        }
    }

    public void drawShadows(Graphics2D g, Point p){
        Point centerPoint = new Point((int) (startX+(p.getX()*tileSize) + tileSize/2), (int) ((startY+p.getY()*tileSize)) + tileSize/2);
        float[] dist = {0.0f, 0.5f, 1.0f};
        Color[] colors = {Resources.transparent, Resources.transparent, Resources.shadowBack};
        RadialGradientPaint shadow = new RadialGradientPaint(centerPoint, Resources.radius, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);
        g.setPaint(shadow);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
    }

    /////////////////////////
    //Utility Methods below//
    /////////////////////////

    public void buildControls(){
        //custom actionListeners for the menu buttons
        this.pauseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseMenuToggle();
            }
        });

        frame.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                frame.requestFocus();
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

        attack.setName("Attack");
        defend.setName("Defend");
        AoE.setName("AOE");
        up.setName("Up");
        down.setName("Down");
        left.setName("Left");
        right.setName("Right");

        attack.setRequestFocusEnabled(false);
        defend.setRequestFocusEnabled(false);
        AoE.setRequestFocusEnabled(false);
        up.setRequestFocusEnabled(false);
        down.setRequestFocusEnabled(false);
        left.setRequestFocusEnabled(false);
        right.setRequestFocusEnabled(false);

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

    public int[] getGridCoordsAt(int mouseX, int mouseY){
        Room r = model.getCurrentRoom();
        for(int y = 0; y <= r.getHeight(); y++){
            for(int x = 0; x <= r.getWidth(); x++){
                int drawX = (x*tileSize)+startX, drawY = (y*tileSize)+startY;
                if(drawX >= mouseX && drawX <= mouseX + tileSize && drawY >= mouseY && drawY <= mouseY + tileSize){
                    return new int[] {x-1,y-1};
                }
            }
        }
        return new int[] {-1, -1};
    }

    public void drawAllEntities(Model model, Graphics2D g){
        for(int y = 0; y < model.getCurrentRoom().getHeight(); y++){
            for(int x = 0; x < model.getCurrentRoom().getWidth(); x++){
                if(model.getCurrentRoom().getEntityAt(x,y) != null)
                    drawEntity(g, model.getCurrentRoom().getEntityAt(x,y),model.getCurrentRoom().getTileSet(), startX + (tileSize*x),startY + (tileSize*y));
            }
        }
    }

    public void dispose(){
        frame.dispose();
    }

    /**
     * It's a button - that is Nice (used for Main and Pause Menus)
     */
    public static class NiceButton extends JButton {

        public NiceButton(String label) {
            super(label);
        }
        private final Color TL = new Color(1f,1f,1f,.2f);
        private final Color BR = new Color(1f,1f,1f,.4f);
        private final Color ST = new Color(1f,1f,1f,.2f);
        private final Color SB = new Color(1f,1f,1f,.1f);
        private Color ssc;
        private Color bgc;

        @Override public void updateUI() {
            super.updateUI();
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
        }
        @Override protected void paintComponent(Graphics g) {
            int x = 0;
            int y = 0;
            int w = getWidth();
            int h = getHeight();
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            Shape area = new Rectangle.Float(x, y, w - 1, h - 1);
            ssc = TL;
            bgc = BR;
            ButtonModel m = getModel();
            if (m.isPressed()) {
                ssc = SB;
                bgc = ST;
            } else if (m.isRollover()) {
                ssc = ST;
                bgc = SB;
            }
            g2.setPaint(new GradientPaint(x, y, ssc, x, y + h, bgc, true));
            g2.fill(area);
            g2.setPaint(BR);
            g2.draw(area);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}