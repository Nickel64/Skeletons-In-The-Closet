package Model;

import Entities.Entity;
import Entities.Entity.Direction;
import Map.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Map containing all rooms
 *
 * Created: 19/9/17
 * @author Balgmi Nam
 */
public class Model {

    private static final String fileName = "map.txt";   //will be using txt to create layout of world
    private Map<String, Room> map;  //map of room names and rooms for easy access when moving room to room
    private Room currentRoom;

    public void initialise() {
        map = new HashMap<String, Room>();
        try {
            File f = new File(fileName);
            if(!f.canRead()) throw new Error("file cannot read");
            Scanner sc = new Scanner(f);
            while(sc.hasNext()) {
                String roomName = sc.next();
                Room curRoom = new Room();
                if(curRoom.initialise(sc)) {
                    currentRoom = curRoom;
                }
                map.put(roomName, curRoom);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File scan error! "+e.getMessage());
        }
    }

    /**
     * Moves the given entity in the given direction if possible
     * @param entity: object to be moved
     * @param dir: direction to be moved in
     */
    public void moveEntity(Entity entity, Direction dir) {
        //check entity
        if(!currentRoom.containsEntity(entity)) throw new Error("Cannot move a nonexistent entity");
        if(!entity.canMove()) throw new Error("Cannot move an unmovable entity");
        currentRoom.moveEntity(entity, dir, this);
    }

    /**
     * Moves the given entity in the given direction if possible
     * @param entity: object to be moved
     * @param dir: direction to be moved in
     */
    public void checkAttack(Entity entity, Direction dir) {
        //check entity
        if(!currentRoom.containsEntity(entity)) throw new Error("Cannot initiate attack with a nonexistent entity");
        currentRoom.checkAttack(entity, dir);
    }

    public void changeCurrentRoom(Room room) {
        currentRoom = room;
        room.startEntities();
    }

    public Room getRoom(String name) {
        if(!map.containsKey(name)) throw new Error("No such element with key "+name+" found");
        return map.get(name);
    }

    /**
     * Removes entity given from the current Room that player is in
     * @param entity: object to be killed/destroyed
     */
    public void removeEntity(Entity entity) {}

    /**
     * prints room, text based
     */
    public void printRoom() {
        currentRoom.printLayout();
    }

    public Room getCurrentRoom(){
        return this.currentRoom;
    }
}
