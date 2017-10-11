package Entities;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Wall implements Entity {

    private int level;

    @Override
    public void setDirection(Direction dir) {

    }
    @Override
    public int getLevel() {
        return 0;
    }
    public Wall(int level){
        this.level = level;
    }
    public boolean inAggroRange(){throw new Error();}
    public boolean isDead(){return false;}

    public String getImageName(){
        return "Wall" + this.level;
    }

    @Override
    public void attack(Entity entity) {/*throw new Error();*/}

    @Override
    public void damaged(int damage) {/*throw new Error();*/}

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
