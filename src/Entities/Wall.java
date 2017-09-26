package Entities;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Wall implements Entity {
    public boolean inAggroRange(){throw new Error();}
    public boolean isDead(){throw new Error();}

    @Override
    public void attack(Entity entity) {throw new Error();}

    @Override
    public void damaged(int damage) {throw new Error();}

    public void start() {}

    public boolean canMove() {
        return false;
    }

    public boolean canStepOn() {
        return false;
    }

    public String toString() {
        return "*";
    }
}
