package Entities;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Nothing implements Entity {
    public boolean inAggroRange(){throw new Error();}
    public boolean isDead(){return false;}
    public void damaged(int damage){}

    @Override
    public void setDirection(Direction dir) {

    }

    public void attack(Entity entity){}

    public boolean canMove() {
        return true;
    }

    public boolean canStepOn() {
        return true;
    }

    public void start() {
    }

    public String getImageName(){
        return null;
    }

    public String toString() {
        return ".";
    }
    //update view, then view calls model;

}
