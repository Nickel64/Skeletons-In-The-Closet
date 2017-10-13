package Map;
import Entities.*;

/**
 * Tile containing information on entities occupying it
 *
 * Created: 19/9/17
 * @author Balgmi Nam
 */
public class FloorTile implements Tile, java.io.Serializable {

    private Entity entity;

    FloorTile(Entity e) {
        entity = e;
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
