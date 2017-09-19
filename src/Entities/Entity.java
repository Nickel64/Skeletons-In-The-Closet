package Entities;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public interface Entity {
    enum Direction{Up,Down,Left,Right}
    public boolean inAggroRange();
    public boolean isDead();
    public void attack(Entity entity);
    public void damaged(int damage);

   // damaged
   // turn
    //move
   // chooseaction
    //attack

}
