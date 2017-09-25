package View;

import Controller.*;

import javax.swing.*;
import java.awt.*;

/**
 * The game 'pause menu'
 * Still undecided how this is going to be implemented
 *
 * Created: 18/08/17
 * @author Nicholas Snellgrove
 */
public class Menu extends JComponent {

    JFrame parent;
    Controller controller;

    public Menu(JFrame parent){
        this.parent = parent;

    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(parent.getWidth(), parent.getHeight()*(4/5));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
