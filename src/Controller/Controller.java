package Controller;

import Entities.Entity;
import Model.*;
import View.*;

import javax.swing.*;
import java.awt.event.*;

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

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
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
    }

    /* END OF KEY LISTENER METHODS */

    /* MOUSE LISTENER METHODS */

    @Override
    public void mouseClicked(MouseEvent e) {
        // sets movement target (for use by pathfinding algorithms
        // refer to sokoban assignment
        // to be implemented later (once people make up their minds about what we're even doing)
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

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
                            break;
                        case "AOE":
                            break;
                        default:
                            return;
                    }
                    timeLastAction = System.currentTimeMillis();
            }
        }
    }

    private void movePlayer(Entity.Direction dir) {
        if(model.getPlayer().getDir() == dir)
            model.moveEntity(model.getPlayer(), dir);
        else
            model.getPlayer().setDirection(dir);
        timeLastAction = System.currentTimeMillis();
    }

    /* END OF MOUSE LISTENER METHODS */
}
