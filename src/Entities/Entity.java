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
     void setDirection(Direction dir);

    /**
     * current entity attack
     * @return current attack of entity
     */
    int getAttack();

    /**
     * current entity health
     * @return current health of entity
     */
    int getHealth();

    /**
     * maximum entity health
     * @return maximum health of entity
     */
    int getMaxHealth();

    String getImageName();

    /**
     * Starts the entity pinging
     */
    void ping();

    /**
     * Can entity move
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
