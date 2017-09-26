package Controller;

import Entities.Entity;
import Model.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/** * * * * * * * * * * * * *
 * Controller class
 * Created 2017/09/19
 * Author: Rachel Anderson
 * * * * * * * * * * * * * * */

public class Controller implements KeyListener, MouseListener {
    private Model model;

    public Controller(Model model) {
        this.model = model;

        new Timer(100, (ev) -> {
            //ping model so enemies move, etc

            //when menu is open, pause timer? (maybe be responsibility of model?)
    });
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
            model.moveEntity(null /*player*/, Entity.Direction.Up);
        }
        else if(code == KeyEvent.VK_KP_DOWN || code == KeyEvent.VK_DOWN) {
            //move down
            model.moveEntity(null /*player*/, Entity.Direction.Down);
        }
        else if(code == KeyEvent.VK_KP_LEFT || code == KeyEvent.VK_LEFT) {
            //move left
            model.moveEntity(null /*player*/, Entity.Direction.Left);
        }
        else if(code == KeyEvent.VK_KP_RIGHT || code == KeyEvent.VK_RIGHT) {
            //move right
            model.moveEntity(null /*player*/, Entity.Direction.Right);
        }
        else if(code == KeyEvent.VK_SPACE) {
            //attack
            model.checkAttack(null, Entity.Direction.Up/*Whatever direction player is in :/*/);
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

    /* END OF MOUSE LISTENER METHODS */
}
