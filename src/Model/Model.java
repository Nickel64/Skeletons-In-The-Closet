package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import Entities.*;
import Map.*;

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

    public Model() {}

    public static void main(String[] args){
        Model m = new Model();
        m.initialise();
    }

    public void initialise() {
        map = new HashMap<String, Room>();
        currentRoom = null;
        try {
            File f = new File(fileName);
            if(!f.canRead()) throw new Error("file cannot read");
            Scanner sc = new Scanner(f);
            while(sc.hasNext()) {
                String roomName = sc.next();
                System.out.println("room: "+roomName);
                Room currentRoom = new Room();
                currentRoom.initialise(sc);
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
    public void moveEntity(Entity entity, Entity.Direction dir) {
    }

//    /**
//     * Kills/destroys entity given from the current Room that player is in
//     * @param entity: object to be killed/destroyed
//     */
    public void killEntity(Entity entity) {}

    /**
     * prints room, text based
     */
    public void printRoom() {}
}
