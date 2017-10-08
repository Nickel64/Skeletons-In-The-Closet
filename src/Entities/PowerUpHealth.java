package Entities;

/**
 * Created by Shlomoburg on 8/10/2017.
 */
public class PowerUpHealth implements PowerUp{

    PowerUpHealth(){

    }
    @Override
    public void increase(Player p) {
        p.setMaxHealth(2);
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
}
