package Map;

import Entities.Entity;

public interface Tile {

    /**
     * Sets the tiles image file to the file name given
     * @param filename: image file name given for this tile
     */
    void setImageName(String filename);

    /**
     * Returns the image name that tile is set with
     * @return image name of tile
     */
    String getImageName();


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
