package Map;

import Entities.Entity;

/**
 * Door Tile that leads to different room
 *
 * @author Balgmi Nam
 */
public class DoorTile implements Tile, java.io.Serializable {

    private String connectedRoomName;
    private Entity entity;

    DoorTile(String connectedRoomName, Entity entity) {
        this.entity = entity;
        this.connectedRoomName = connectedRoomName;
    }

    @Override
    public boolean canMoveOnto() {
        return entity.canStepOn();
    }

    @Override
    public boolean isEntry() {
        return true;
    }

    @Override
    public boolean isEntity(Entity entity) {
        return entity == this.entity;
    }

    @Override
    public Entity getEntity() {
        return this.entity;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    String nameOfNextRoom() {
        return connectedRoomName;
    }

    public String toString() {
        return connectedRoomName;
    }
}
