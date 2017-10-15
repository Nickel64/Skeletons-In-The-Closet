package Entities;

import Utils.Resources;

import java.awt.*;

/**
 * Created by nicks on 12/10/2017.
 */
public class Decor extends Enemy implements java.io.Serializable {

    private transient Image image;

    public Decor(int level, int health, int damage, int speed) {
        super(0, 1, 0, 0);
        image = Resources.getDecorObject(level);
    }
}