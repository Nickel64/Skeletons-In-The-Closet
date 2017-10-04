package Map;

import Entities.Entity;

public class DoorTile implements Tile {

    private String connectedRoomName;
    private Entity entity;

    DoorTile(String connectedRoomName, Entity entity) {
        this.entity = entity;
        this.connectedRoomName = connectedRoomName;
    }

    @Override
    public boolean canMoveOnto() {
        return true;
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
