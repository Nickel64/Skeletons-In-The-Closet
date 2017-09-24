package Entities;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public interface Entity {
    enum Direction{Up,Down,Left,Right}
     boolean inAggroRange();
     boolean isDead();
     void attack(Entity entity);
     void damaged(int damage);

   // damaged
   // turn
    //move
   // chooseaction
    //attack

}
