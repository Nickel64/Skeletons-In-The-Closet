package Map;

import Entities.Entity;

public class DoorTile implements Tile {

    private String filename = "door.jpg";
    private String connectedRoomName;

    DoorTile(String connectedRoomName) {
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
        return false;
    }

    @Override
    public Entity getEntity() {
        return null;
    }

    @Override
    public void setEntity(Entity entity) {
        throw new Error("Cannot set entity at door tile");
    }

    String nameOfNextRoom() {
        return connectedRoomName;
    }

    public String toString() {
        return connectedRoomName;
    }
}
