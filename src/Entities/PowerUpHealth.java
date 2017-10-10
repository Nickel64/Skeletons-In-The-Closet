package Entities;

/**
 * Created by Shlomoburg on 8/10/2017.
 */
public class PowerUpHealth implements PowerUp{

    public PowerUpHealth(){

    }
    @Override
    public void increase(Player p) {
        p.setMaxHealth(p.getMaxHealth()+2);
        p.setHealth(p.getHealth()+2);
    }

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
    public String getImageName() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public boolean canStepOn() {
        return true;
    }
    public String toString() {
        return "`";
    }
}
