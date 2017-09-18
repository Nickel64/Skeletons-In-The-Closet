package View;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by nicks on 18/09/2017.
 */
public class View extends JComponent implements Observer{

    private final String TITLE = "Skeletons In The Closet";

    public View() {
        JFrame f = new JFrame(TITLE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());


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

    //drawUI
        ////drawCurrentRoom
            //drawRoom
                //drawTile
                //drawEntity
                    //draw entity.getTiles
    //animateEntity

}
