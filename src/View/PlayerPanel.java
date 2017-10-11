package View;

import Entities.Player;
import Utils.Resources;

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
    JFrame parent;

    //drawing calc fields
    int startX = 60;
    int startY = 30;
    int barWidth = 350;
    int buffer = 50;
    int barHeight = 20;
    int dif = 15;

    public PlayerPanel(JFrame parent, Player player) {
        this.parent = parent;
        this.player = player;
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics _g) {

        _g.setColor(Color.orange);
        _g.drawString("HP", startX,startY);
        _g.drawString("MP", startX,startY+(40));
        _g.drawString("XP", startX,startY+(80));
        _g.setColor(Color.red.darker().darker().darker());
        _g.fillRect(startX + buffer, startY-(dif), barWidth, barHeight);

        _g.setColor(Color.blue.darker().darker().darker());
        _g.fillRect(startX + buffer, startY + 40 -(dif), barWidth, barHeight);

        _g.setColor(Color.green.darker().darker().darker());
        _g.fillRect(startX + buffer, startY + 80 -(dif), barWidth, barHeight);
        drawCurHealth(_g);
        drawCurSpecial(_g);
        drawCurExp(_g);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(parent.getWidth()/2, parent.getHeight()/5);
    }

    public void drawCurHealth(Graphics g){
        //player.setHealth(50);
        int endX = (int) Math.ceil(((double)barWidth/player.getMaxHealth())*player.getHealth());
        g.setColor(Color.red);
        g.fillRect(startX + buffer, startY-(dif), endX, barHeight);
    }

    public void drawCurSpecial(Graphics g){
        //player.setSpecial(10);
        int endX =(int) Math.ceil((((double)barWidth/player.getMaxSpecial())*player.getSpecial()));
        g.setColor(Color.blue);
        g.fillRect(startX + buffer, startY + 40-(dif), endX, barHeight);
    }

    public void drawCurExp(Graphics g){
        //player.incExp(40);
        if(Resources.DEBUG) System.out.println(player.getExp());
        int endX =(int) (((double)barWidth/player.getMaxExp())*player.getExp());
        g.setColor(Color.green.darker());
        g.fillRect(startX + buffer, startY-(dif) + 80, endX, barHeight);
    }
}
