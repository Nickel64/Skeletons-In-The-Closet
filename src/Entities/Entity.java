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

     public String getImageName();

    /**
     * Starts the entity pinging
     */
    void start();

    /**
     *
     * @return whether or not the entity is able to move
     */
    boolean canMove();

    /**
     *
     * @return whether or not the entity is able to be replaced/stepped on by another entity
     */
    boolean canStepOn();

   // damaged
   // turn
    //move
   // chooseaction
    //attack

}
