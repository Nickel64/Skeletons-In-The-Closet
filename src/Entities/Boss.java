package Entities;

import Utils.Resources;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Boss extends Enemy {
    public Boss(int name, int health, int damage, int speed) {
        super(name, health, damage, speed);
    }

    @Override
    public void damaged(int damageAmount) {
        super.damaged(damageAmount);
        if(isDead())
            Resources.playAudio("DoorUnlocked.wav");
    }
}
