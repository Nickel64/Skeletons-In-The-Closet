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
    private static final int COOLDOWN = 150;
    private long timeLastAction = -COOLDOWN;

    private boolean inAutoMovement = false;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        new Timer(150, (e) -> {
            this.model.getCurrentRoom().ping();
        });
    }

    public void setModel(Model m) {this.model = m;}

    /* KEY LISTENER METHODS */

    @Override
    public void keyTyped(KeyEvent e) {
        //if(e.getKeyCode() == KeyEvent.VK_SPACE) {
        //    model.checkAttack(model.getPlayer(), model.getPlayer().getDir());
        //}
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //process input from keyboard
        //e.g. move up, down, left, right, attack

        if(view.pauseMenuVisible) return;
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
        if(code == KeyEvent.VK_SPACE) {
            if(view.pauseMenuVisible || System.currentTimeMillis() - timeLastAction < COOLDOWN)
                return;
            model.checkAttack(model.getPlayer(), model.getPlayer().getDir());
            timeLastAction = System.currentTimeMillis();
        }
        else if(code == KeyEvent.VK_ESCAPE){
            view.pauseMenuToggle();
        }
        else if(code == KeyEvent.VK_Q){
            if(view.pauseMenuVisible || System.currentTimeMillis() - timeLastAction < COOLDOWN)
                return;
            model.checkAttackAOE(model.getPlayer());
            timeLastAction = System.currentTimeMillis();
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
        if(inAutoMovement) return;
        int x = e.getX(), y = e.getY();
        int[] toGo = view.getGridCoordsAt(x,y);

        if(toGo[0] == -1 || toGo[1] == -1) throw new Error("Cannot move outside the board");
        System.out.println("Heading toward x:" + toGo[0] + " y:" + toGo[1]);

        int[] goFrom = new int[] {model.getPlayerLocation().x, model.getPlayerLocation().y};
        Tile[][] tileGrid = new Tile[model.getCurrentRoom().getWidth()][model.getCurrentRoom().getHeight()];
        Queue<int[]> pathToGo = Pathfinder.findPath(goFrom, toGo, model.getCurrentRoom());

        //use timer to slowly step the player along each of the steps required
        Timer timer = new Timer(500, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                inAutoMovement = true;
                int[] point = pathToGo.poll();
                if(point == null){
                    //if finished on a Door tile - take that door!
                    System.out.println("FINISHED");
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

                int playerX = model.getPlayerLocation().x, playerY = model.getPlayerLocation().y;
                if(point[0] < playerX) movePlayerPathFind(Entity.Direction.Left);
                else if(point[0] > playerX) movePlayerPathFind(Entity.Direction.Right);
                else if(point[1] > playerY) movePlayerPathFind(Entity.Direction.Down);
                else if(point[1] < playerY) movePlayerPathFind(Entity.Direction.Up);
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
                            model.checkAttack(model.getPlayer(), model.getPlayer().getDir());
                            break;
                        case "Defend":
                            model.getPlayer().toggleGuard();
                            view.repaint();
                            break;
                        case "AOE":
                            model.checkAttackAOE(model.getPlayer());
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
        model.getPlayer().setDirection(dir);
        model.moveEntity(model.getPlayer(), dir);
        view.repaint();
        timeLastAction = System.currentTimeMillis();
    }

    /* END OF MOUSE LISTENER METHODS */
}
