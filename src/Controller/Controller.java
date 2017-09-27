package Controller;

import Entities.Entity;
import Model.*;
import View.*;

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
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        //process input from keyboard
        //e.g. move up, down, left, right, attack
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_KP_UP || code == KeyEvent.VK_UP) {
            //move up
            model.moveEntity(model.getPlayer(), Entity.Direction.Up);
        }
        else if(code == KeyEvent.VK_KP_DOWN || code == KeyEvent.VK_DOWN) {
            //move down
            model.moveEntity(model.getPlayer(), Entity.Direction.Down);
        }
        else if(code == KeyEvent.VK_KP_LEFT || code == KeyEvent.VK_LEFT) {
            //move left
            model.moveEntity(model.getPlayer(), Entity.Direction.Left);
        }
        else if(code == KeyEvent.VK_KP_RIGHT || code == KeyEvent.VK_RIGHT) {
            //move right
            model.moveEntity(model.getPlayer(), Entity.Direction.Right);
        }
        else if(code == KeyEvent.VK_SPACE) {
            model.getPlayer().attack();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

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
    }

    /* END OF MOUSE LISTENER METHODS */
}
