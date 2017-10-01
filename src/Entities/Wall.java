package Entities;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Wall implements Entity {

    private int level;

    public Wall(int level){
        this.level = level;
    }
    public boolean inAggroRange(){throw new Error();}
    public boolean isDead(){throw new Error();}

    public String getImageName(){
        return "Wall" + this.level;
    }

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
