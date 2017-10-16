package Entities;

/**
 * Created by Shlomoburg on 19/09/2017.
 */
public interface Entity {


    enum Direction{Up,Down,Left,Right}

    /**
     *
     * @return check to see whether the entity is dead or not
     */
     boolean isDead();

    /**
     * @param entity, the entity to be attacked
     * the entity attacks the entity passed in through the parameters
     * @param aoe whether or not this is a special aoe attack
     */
     void attack(Entity entity, boolean aoe);

    /**
     * decreases the entities health, and then calls isdead, checks whether the entity has died
     */
     void damaged(int damage);

    /**
     * the level of the entity
     */
     void setDirection(Direction dir);

    /**
     *
     * @return the level of the entity
     */
    int getLevel();

    /**
     * current entity attack
     * @return current attack of entity
     */
    int getDamage();

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
     * Can entity move
     * @return whether or not the entity is able to move
     */
    boolean canMove();

    /**
     *
     * @return whether or not the entity is able to be replaced/stepped on by another entity
     */
    boolean canStepOn();

    /**
     * the boolean determines if an action is complete (AoE, attack,dying etc)
     * @return whether the action completes or not
     */
    boolean ping();


}
