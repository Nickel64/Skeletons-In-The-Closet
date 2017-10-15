package Model;

import Entities.Enemy;
import Entities.Entity;
import Entities.Entity.Direction;
import Entities.Player;
import Map.Room;
import Utils.GameError;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Scanner;


/**
 * Model containing all rooms and game logic
 *
 * Created: 19/9/17
 * @author Balgmi Nam
 */
public class Model extends Observable implements java.io.Serializable {

    private static final String fileName = "map.txt";   //will be using txt to create layout of world
    private Map<String, Room> map;  //map of room names and rooms for easy access when moving room to room
    private Room currentRoom;

    public Model(){

    }

    /**
     * Reads the file map.txt into a scanner, precedes to read
     */
    public void initialise() throws IOException {
        try {
            InputStream in = Utils.Resources.class.getResourceAsStream(fileName);
            Scanner sc = new Scanner(in);
            read(sc);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void initialise(String str) throws IOException{
        Scanner sc = new Scanner(str);
        read(sc);
    }

    /**
     * Returns the Point of which the player is located within the 2D floor layout
     * @return Point of player in relation to Layout
     */
    public Point getPlayerLocation(){
        Point p = this.getCurrentRoom().getPlayerLocation();
        return p;
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
                    if(curRoom.getPlayer() != null) throw new GameError("There requires a player <+> placed in first room");
                    firstRoom = false;
                    currentRoom = curRoom;
                } else if(curRoom.getPlayer() != null) throw new GameError("There is a player start position found outside of first room");
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
        if(!currentRoom.containsEntity(entity)) throw new GameError("Cannot move a nonexistent entity");
        if(!entity.canMove()) throw new GameError("Cannot move an unmovable entity");
        entity.setDirection(dir);
        currentRoom.moveEntity(entity, dir, this);
        setChanged();
        notifyObservers();
    }

    /**
     * Checks and attacks whether the attack from the given single entity in the given direction is possible
     * @param entity: object to attack
     * @param dir: direction to attack in
     */
    public void checkAttack(Entity entity, Direction dir) {
        //check entity
        if(!currentRoom.containsEntity(entity)) throw new GameError("Cannot initiate attack with a nonexistent entity");
        currentRoom.checkAttack(entity, dir);
        setChanged();
        notifyObservers();
        currentRoom.setRoomClearedTo(currentRoom.getEnemies().size() == 0);
    }

    /** Attacks the entities around the given entity in all directions
     * @param entity: object that is attacking
     */
    public void checkAttackAOE(Entity entity) {
        //check entity
        if(!currentRoom.containsEntity(entity)) throw new GameError("Cannot initiate attack with a nonexistent entity");
        currentRoom.checkAttackAOE(entity);
        setChanged();
        notifyObservers();
        currentRoom.setRoomClearedTo(currentRoom.getEnemies().size() == 0);
    }

    /**
     * Changes the current room to the given parameter
     * @param room to be changes to the current room
     */
    public void changeCurrentRoom(Room room) {
        currentRoom = room;
        room.ping(this);
    }

    /**
     * Gets the room of the given name
     * @param name of room
     * @return Room correlated with name
     */
    public Room getRoom(String name) {
        if(!map.containsKey(name)) throw new GameError("No such element with key "+name+" found");
        return map.get(name);
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

    /**
     * Used to restart the game after Deserialization
     */
    public void resetGame(){
        for(Room room: map.values()){
            room.resetTileSet();
        }
        getPlayer().resetPlayer();
    }
}
