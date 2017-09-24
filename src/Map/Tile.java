package Map;
import Entities.*;
import Map.*;

/**
 * Tile containing information on entities occupying it
 *
 * Created: 19/9/17
 * @author Balgmi Nam
 */
public class Tile {

    private String imgName;     //image file name for the given tile
    private Entity entity;

    public Tile(Entity e) {
        entity = e;
    }

    /**
     * Sets the tiles image file to the file name given
     * @param filename: image file name given for this tile
     */
    public void setImageName(String filename) {
        imgName = filename;
    }

    /**
     * Returns the image name that tile is set with
     * @return image name of tile
     */
    public String getImageName() {
        return imgName;
    }

    //SHOULD BE IN ENTITY?
//    /**
//     * Returns whether or not an entity can move onto this tile
//     * @return whether an entity is able to move to this tile
//     */
//    public boolean canMoveOnto() {return false;}

    /**
     * Returns whether or not tile is occupied
     * @return whether this tile is occupied
     */
    public boolean hasEntity() {
        return false;
    }

    public boolean isEntity(Entity entity) {
        return (entity == this.entity);
    }

    /**
     * Returns the entity that occupies the tile
     * @return the entity that occupies this tile
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Sets the tile to contain the given entity if tile is empty
     * @param entity: Object to set as occupying tile
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String toString() {
        return entity.toString();
    }
}
