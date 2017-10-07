package Entities;

/**
 * Created by Shlomoburg on 8/10/2017.
 */
public class Potion implements Entity {

    Potion (){

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
        return "~";
    }

    public void restore (Player p){
        p.heal(2);
    }

}
