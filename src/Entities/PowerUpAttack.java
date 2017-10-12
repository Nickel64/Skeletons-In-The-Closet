package Entities;

/**
 * Created by Shlomoburg on 8/10/2017.
 */
public class PowerUpAttack implements PowerUp {

    public PowerUpAttack (){

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
        return true;
    }

    public String toString() {
        return "~";
    }

    public void increase (Player p){
        p.increaseAttack(1);
    }

}
