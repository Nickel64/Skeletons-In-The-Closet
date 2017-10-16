package Controller;

import Behaviour.Pathfinder;
import Entities.Entity;
import Map.DoorTile;
import Map.Tile;
import Model.*;
import Utils.GameError;
import Utils.Resources;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Queue;

/** * * * * * * * * * * * * *
 * Controller class
 * Created 2017/09/19
 * Author: Rachel Anderson
 * * * * * * * * * * * * * * */

public class Controller implements KeyListener, MouseListener, ActionListener {
    private Model model;
    private View view;
    private static final int COOLDOWN = 300;
    private long timeLastAction = -COOLDOWN;
    private int bgmIteration = 0;

    private boolean inAutoMovement = false;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        startLoop();
    }

    private void startLoop() {
        new Timer(50, (e) -> {
            if((bgmIteration++ % 800) == 0) { //bgm is 40s * 20 pings/sec
                bgmIteration = 1;
                Resources.bgm.setFramePosition(0);
                Resources.bgm.start();
            }
            if(!view.pauseMenuVisible && !view.paused) {
                this.model.getCurrentRoom().ping(model);
                view.repaint();
            }
        }).start();
    }

    public void setModel(Model m) {this.model = m;}

    /* KEY LISTENER METHODS */

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        //process input from keyboard
        //e.g. move up, down, left, right, attack

        if(view.pauseMenuVisible || inAutoMovement || view.paused) return;
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_KP_UP || code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            movePlayer(Entity.Direction.Up);
        }
        else if(code == KeyEvent.VK_KP_DOWN || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            movePlayer(Entity.Direction.Down);
        }
        else if(code == KeyEvent.VK_KP_LEFT || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            movePlayer(Entity.Direction.Left);
        }
        else if(code == KeyEvent.VK_KP_RIGHT || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            movePlayer(Entity.Direction.Right);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ESCAPE) {
            view.pauseMenuToggle();
        }
        else if(!inAutoMovement && !view.paused) {
            if(code == KeyEvent.VK_SPACE) {
                if(view.pauseMenuVisible || System.currentTimeMillis() - timeLastAction < COOLDOWN)
                    return;
                model.getPlayer().startAction("atk");
                timeLastAction = System.currentTimeMillis();
            }
            else if(code == KeyEvent.VK_Q){
                if(view.pauseMenuVisible || System.currentTimeMillis() - timeLastAction < COOLDOWN)
                    return;
                if(model.getPlayer().getSpecial() >= 10) {
                    model.getPlayer().startAction("aoe");
                }
                timeLastAction = System.currentTimeMillis();
            }
            else if(code == KeyEvent.VK_E) {
                model.getPlayer().toggleGuard();
                view.repaint();
            }
        }
    }

    /* END OF KEY LISTENER METHODS */

    /* MOUSE LISTENER METHODS */

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if(inAutoMovement || view.paused) return;
        int x = e.getX(), y = e.getY();
        int[] toGoArr = view.getGridCoordsAt(x,y);
        Point toGo = new Point(toGoArr[0], toGoArr[1]);

        if(toGo.x == -1 || toGo.y == -1) return;
        if(Resources.DEBUG) System.out.println("Heading toward x:" + toGo.x + " y:" + toGo.y);

        Point goFrom = new Point(model.getPlayerLocation().x, model.getPlayerLocation().y);
        Queue<Point> pathToGo = Pathfinder.findPath(goFrom, toGo, model.getCurrentRoom());

        if(Resources.DEBUG) System.out.println("PATH:");
        for(Point point: pathToGo){
            if(Resources.DEBUG) System.out.println(" x:" + point.x + " y:" + point.y);
        }

        //use timer to slowly step the player along each of the steps required
        Timer timer = new Timer(500, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                inAutoMovement = true;
                if(view.pauseMenuVisible)
                    return;
                Point point = pathToGo.poll();

                if(point == null){
                    //if finished on a Door tile - take that door!
                    Point playerLocation = model.getPlayerLocation();
                    Tile currentTile = model.getCurrentRoom().getTileAtLocation(playerLocation.x, playerLocation.y);
                    if(currentTile instanceof DoorTile){
                        if(playerLocation.y+1 >= model.getCurrentRoom().getHeight()){ //bottom edge - need to move down
                            movePlayerPathFind(Entity.Direction.Down);
                        }
                        else if(playerLocation.y-1 < 0){ //top edge - need to move up
                            movePlayerPathFind(Entity.Direction.Up);
                        }
                        else if(playerLocation.x-1 < 0){ //left edge - need to move left
                            movePlayerPathFind(Entity.Direction.Left);
                        }
                        else if(playerLocation.x+1 >= model.getCurrentRoom().getWidth()){ //right edge - need to move right
                            movePlayerPathFind(Entity.Direction.Right);
                        }
                    }
                    inAutoMovement = false;
                    ((Timer) e.getSource()).stop();
                    return;
                }

                if(Resources.DEBUG) System.out.println("x:" + point.x + " y:" + point.y);

                int playerX = model.getPlayerLocation().x, playerY = model.getPlayerLocation().y;
                if(point.x < playerX) movePlayerPathFind(Entity.Direction.Left);
                else if(point.x > playerX) movePlayerPathFind(Entity.Direction.Right);
                else if(point.y > playerY) movePlayerPathFind(Entity.Direction.Down);
                else if(point.y < playerY) movePlayerPathFind(Entity.Direction.Up);
            }
        });
        timer.start();}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        //used for buttons
        if(view.pauseMenuVisible) return;
        if(JButton.class.isInstance(e.getSource())) {
            String buttonName = ((JButton)e.getSource()).getName();
            switch(buttonName) {
                case "Up":
                    movePlayer(Entity.Direction.Up);
                break;
                case "Down":
                    movePlayer(Entity.Direction.Down);
                    break;
                case "Left":
                    movePlayer(Entity.Direction.Left);
                    break;
                case "Right":
                    movePlayer(Entity.Direction.Right);
                    break;
                default:
                     if(System.currentTimeMillis() - timeLastAction < COOLDOWN) return;
                    switch(buttonName) {
                        case "Attack":
                            //model.checkAttack(model.getPlayer(), model.getPlayer().getDir());
                            model.getPlayer().startAction("atk");
                            break;
                        case "Defend":
                            model.getPlayer().toggleGuard();
                            view.repaint();
                            break;
                        case "AOE":
                            if(model.getPlayer().getSpecial() >= 10) {
                                model.getPlayer().startAction("aoe");
                            }
                            if(Resources.DEBUG){
                                System.out.println(model.getPlayer().getSpecial());
                            }
                            break;
                        default:
                            return;
                    }
                    timeLastAction = System.currentTimeMillis();
            }
        }
    }

    private void movePlayer(Entity.Direction dir) {
        try {
            if (model.getPlayer().getDir() == dir) {
                model.moveEntity(model.getPlayer(), dir);
                Resources.playAudio("footstep.wav");
            }
            else {
                model.getPlayer().setDirection(dir);
                view.repaint();
            }
            timeLastAction = System.currentTimeMillis();
        } catch(GameError e) {
            Resources.playAudio("bump.wav");
        }
    }

    private void movePlayerPathFind(Entity.Direction dir) {
        try {
            if (model.getPlayer().getDir() == dir) {
                model.moveEntity(model.getPlayer(), dir);
                Resources.playAudio("footstep.wav");
            }
            else {
                model.getPlayer().setDirection(dir);
                model.moveEntity(model.getPlayer(), dir);
                view.repaint();
            }
            timeLastAction = System.currentTimeMillis();
        } catch(GameError e) {
            Resources.playAudio("bump.wav");
        }
    }

    /* END OF MOUSE LISTENER METHODS */
}
