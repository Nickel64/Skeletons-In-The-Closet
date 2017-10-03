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

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

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
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_KP_UP || code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            //move up
            model.moveEntity(model.getPlayer(), Entity.Direction.Up);
        }
        else if(code == KeyEvent.VK_KP_DOWN || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            //move down
            model.moveEntity(model.getPlayer(), Entity.Direction.Down);
        }
        else if(code == KeyEvent.VK_KP_LEFT || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            //move left
            model.moveEntity(model.getPlayer(), Entity.Direction.Left);
        }
        else if(code == KeyEvent.VK_KP_RIGHT || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            //move right
            model.moveEntity(model.getPlayer(), Entity.Direction.Right);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
        model.checkAttack(model.getPlayer(), model.getPlayer().getDir());
    }}

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
        if(JButton.class.isInstance(e.getSource())) {
            String buttonName = ((JButton)e.getSource()).getName();
            switch(buttonName) {
                case "Up":
                    model.moveEntity(model.getPlayer(), Entity.Direction.Up);
                    break;
                case "Down":
                    model.moveEntity(model.getPlayer(), Entity.Direction.Down);
                    break;
                case "Left":
                    model.moveEntity(model.getPlayer(), Entity.Direction.Left);
                    break;
                case "Right":
                    model.moveEntity(model.getPlayer(), Entity.Direction.Right);
                    break;
                case "Attack":
                    model.checkAttack(model.getPlayer(), model.getPlayer().getDir());
                    break;
                case "Defend":
                    break;
                case "AOE":
                    break;
            }
        }
    }

    /* END OF MOUSE LISTENER METHODS */
}
