package Entities;

import java.awt.*;

/**
 * Created by Shlomoburg on 8/10/2017.
 */
public interface PowerUp extends Entity {
    public void increase (Player p);

    public Image getImage();

    public void resetImage();
}
