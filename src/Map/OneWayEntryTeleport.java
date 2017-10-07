package Map;

import Entities.Entity;

public class OneWayEntryTeleport implements Tile {

    private Entity entity;
    private String connectedRoomName;

    public OneWayEntryTeleport(Entity entity, String connectedRoomName) {
        this.entity = entity;
        this.connectedRoomName = connectedRoomName.substring(1, connectedRoomName.length());
        System.out.println(connectedRoomName+" to... "+this.connectedRoomName);
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
}
