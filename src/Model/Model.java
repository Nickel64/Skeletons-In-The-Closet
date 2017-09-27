package Model;

import Entities.Entity;
import Entities.Entity.Direction;
import Entities.Player;
import Map.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Model containing all rooms and game logic
 *
 * Created: 19/9/17
 * @author Balgmi Nam
 */
public class Model {

    private static final String fileName = "map.txt";   //will be using txt to create layout of world
    private Map<String, Room> map;  //map of room names and rooms for easy access when moving room to room
    private Room currentRoom;

    public Model(){

    }

    /**
     * Reads the file map.txt into a scanner, precedes to read
     */
    public void initialise() {
        try {
            File f = new File(fileName);
            if (!f.canRead()) throw new Error("file cannot read");
            Scanner sc = new Scanner(f);
            read(sc);
        } catch (FileNotFoundException e) {
            System.out.println("File scan error! " + e.getMessage());
        }
    }

    /**
     * Reads from the scanner to initialise rooms until no map information left
     * @param sc Scanner of the map information to read
     */
    public void read(Scanner sc) {
        map = new HashMap<String, Room>();
        boolean firstRoom = true;
            while(sc.hasNext()) {
                String roomName = sc.next();
                Room curRoom = new Room(roomName);
                if(firstRoom) {
                    firstRoom = false;
                    currentRoom = curRoom;
                    if(currentRoom.getPlayer() != null) throw new Error("There requires a player <+> placed in first room");
                } else if(currentRoom.getPlayer() != null) throw new Error("There is a player start position found outside of first room");
                map.put(roomName, curRoom);
                sc = curRoom.initialise(sc);
            }
    }

    /**
     * Moves the given entity in the given direction if possible
     * @param entity: object to be moved
     * @param dir: direction to be moved in
     */
    public void moveEntity(Entity entity, Direction dir) {
        //check entity
        if(!currentRoom.containsEnemy(entity)) throw new Error("Cannot move a nonexistent entity");
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
        if(!currentRoom.containsEnemy(entity)) throw new Error("Cannot initiate attack with a nonexistent entity");
        currentRoom.checkAttack(entity, dir);
    }

    /**
     * Changes the current room to the given parameter
     * @param room to be changes to the current room
     */
    public void changeCurrentRoom(Room room) {
        currentRoom = room;
        room.startEnemies();
    }

    /**
     * Gets the room of the given name
     * @param name of room
     * @return Room correlated with name
     */
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
        System.out.println(currentRoom.toString());
    }

    /**
     * Returns the player
     * @return Player
     */
    public Player getPlayer() {
        return currentRoom.getPlayer();
    }

    /**
     * Returns the current room
     * @return current Room
     */
    public Room getCurrentRoom(){
        return this.currentRoom;
    }
}
