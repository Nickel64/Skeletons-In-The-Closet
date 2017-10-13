package Map;

import Entities.Entity;

import javax.print.DocFlavor;

public class OneWayEntryTeleport implements Tile, java.io.Serializable {

    private Entity entity;
    private String connectedRoomName;

    public OneWayEntryTeleport(Entity entity, String connectedRoomName) {
        this.entity = entity;
        this.connectedRoomName = connectedRoomName.substring(1, connectedRoomName.length());
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
        return entity;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String nameOfNextRoom() {
        return connectedRoomName;
    }

    public String toString() {
        return "-"+connectedRoomName;
    }
}
