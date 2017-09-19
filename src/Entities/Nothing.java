package Entities;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public class Nothing implements Entity {
    public boolean inAggroRange(){throw new Error();}
    public boolean isDead(){throw new Error();}
    public void damaged(int damage){}
    public void attack(Entity entity){}

    //update view, then view calls model;

}
