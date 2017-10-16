package Map;

import Entities.Entity;

/**
 * Tile interface
 *
 * @author Balgmi Nam
 */
public interface Tile {
    /**
     * Returns whether or not tile is occupied
     * @return whether this tile is occupied
     */
    boolean canMoveOnto();

    /**
     * @return whether or not the Tile is an entry point to another given room
     */
    boolean isEntry();

    /**
     * @param entity given to check whether the current entity is the same
     * @return whether or not the given entity is the same as the current one held in Tile
     */
    boolean isEntity(Entity entity);

    /**
     * Returns the entity that occupies the tile
     * @return the entity that occupies this tile
     */
    Entity getEntity();

    /**
     * Sets the tile to contain the given entity if tile is empty
     * @param entity: Object to set as occupying tile
     */
    void setEntity(Entity entity);
}
