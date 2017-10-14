package Entities;

import Utils.EntitySet;
import Utils.Resources;

import java.awt.*;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Boss extends Enemy {
    public Boss(int name, int health, int damage, int speed) {
        super(name, health, damage, speed);
        images = new EntitySet(false, true, 9);
    }

    @Override
    public void damaged(int damageAmount) {
        super.damaged(damageAmount);
        if(isDead())
            Resources.playAudio("DoorUnlocked.wav");
    }


    public EntitySet getImages(){
        return images;
    }

    public Image getIdle() {
        return images.getIdle(dir.ordinal());
    }

    public Image getAttack(){
        return images.getAttack(dir.ordinal(), animCount);
    }
}
