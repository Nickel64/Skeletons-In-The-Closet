package View;

import Entities.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * An extension of JComponent to control the display of things that
 * won't be interacted with (Like health bar, special power bar, experience bar etc)
 *
 * Created: 18/08/17
 * @author Nicholas Snellgrove
 */
public class PlayerPanel extends JComponent implements Observer {

    Player player;

    public PlayerPanel(Player player) {
        this.player = player;
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics _g) {
        super.paintComponent(_g);
    }
}
