package Map;

import Entities.Entity;

public class DoorTile implements Tile {

    private String filename = "door.jpg";
    private String connectedRoomName;
    private Entity entity;

    DoorTile(String connectedRoomName, Entity entity) {
        this.connectedRoomName = connectedRoomName;
    }

    @Override
    public void setImageName(String filename) {
        this.filename = filename;
    }

    @Override
    public String getImageName() {
        return this.filename;
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
        throw new Error("Cannot set entity to anything other than entity at door tile");
    }

    String nameOfNextRoom() {
        return connectedRoomName;
    }

    public String toString() {
        return connectedRoomName;
    }
}
