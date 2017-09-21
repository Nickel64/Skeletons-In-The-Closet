package Controller;

import View.*;
import Model.*;
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
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        //initialise timer loop, pinging model each iteration
    }

    public void updateView() {
        view.notify();
    }

    /* KEY LISTENER METHODS */

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        //process input from keyboard
        //e.g. move up, down, left, right, attack
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    /* END OF KEY LISTENER METHODS */

    /* MOUSE LISTENER METHODS */

    @Override
    public void mouseClicked(MouseEvent e) {
        //sets movement target (for use by pathfinding algorithms
        // refer to sokoban assignment
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
