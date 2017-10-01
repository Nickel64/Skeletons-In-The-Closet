package Map;

import Entities.Entity;

public class DoorTile implements Tile {

    private String filename;
    private String connectedRoomName;
    private Entity entity;
    private int level;

    @Override
    public int getLevel() {
        return level;
    }

    DoorTile(String connectedRoomName, Entity entity, int level) {
        this.level = level;
        this.entity = entity;
        this.connectedRoomName = connectedRoomName;
        filename = "Door"+level;
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
        this.entity = entity;
    }

    String nameOfNextRoom() {
        return connectedRoomName;
    }

    public String toString() {
        return connectedRoomName;
    }
}
