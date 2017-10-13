package Entities;

/**
 * Created by nicks on 12/10/2017.
 */
public class Decor implements Entity {
    @Override
    public boolean inAggroRange() {
        return false;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void attack(Entity entity) {

    }

    @Override
    public void damaged(int damage) {

    }

    @Override
    public void setDirection(Direction dir) {

    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getDamage() {
        return -1;
    }

    @Override
    public int getHealth() {
        return -1;
    }

    @Override
    public int getMaxHealth() {
        return -1;
    }

    @Override
    public String getImageName() {
        return null;
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public boolean canStepOn() {
        return false;
    }

    public void ping(){}
}
