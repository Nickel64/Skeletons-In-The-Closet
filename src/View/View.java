package View;

import Utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by nicks on 18/09/2017.
 */
public class View extends JComponent implements Observer{

    public View() {
        JFrame f = new JFrame(Resources.TITLE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        //TODO things and stuff

        f.pack();
        f.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void drawInterface(){

    }

    public void showMenu(){

    }

    public void drawWorld(){

    }

    public void drawRoom(){

    }

    public void drawTile(){

    }

    public void drawEntity(){

    }

    public void animateEntity(){

    }
}
