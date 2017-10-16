package Map;

import Entities.Entity;

/**
 * Tile that is exit teleport, cannot have multiple exits in one room
 *
 * @author Balgmi Nam
 */
public class OneWayExitTeleport implements Tile, java.io.Serializable {

    private Entity entity;

    public OneWayExitTeleport(Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean canMoveOnto() {
        return entity.canStepOn();
    }

    @Override
    public boolean isEntry() {
        return false;
    }

    @Override
    public boolean isEntity(Entity entity) {
        return entity == this.entity;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String toString() {
        return "+";
    }
}
