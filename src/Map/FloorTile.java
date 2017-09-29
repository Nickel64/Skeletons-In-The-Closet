package Map;
import Entities.*;

/**
 * Tile containing information on entities occupying it
 *
 * Created: 19/9/17
 * @author Balgmi Nam
 */
public class FloorTile implements Tile {

    private int level;

    @Override
    public int getLevel() {
        return level;
    }

    private String imgName;     //image file name for the given tile
    private Entity entity;

    FloorTile(Entity e, int level) {
        this.level = level;
        imgName = "Floor"+level;
        entity = e;
    }

    public void setImageName(String filename) {
        imgName = filename;
    }

    public String getImageName() {
        return imgName;
    }

    public boolean canMoveOnto() {
        return entity.canStepOn();
    }

    @Override
    public boolean isEntry() {
        return false;
    }

    public boolean isEntity(Entity entity) {
        return (entity == this.entity);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String toString() {
        return entity.toString();
    }
}
