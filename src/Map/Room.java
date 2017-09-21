package Map;

import Entities.*;
import Entities.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Room containing room layout of Tiles
 *
 * Created: 19/9/17
 * @author Balgmi Nam
 */

public class Room {

    private Tile[][] layout;
    private List<Entity> entities;
    private boolean cleared = false;
    private int level;

    public Room() {}

    /**
     * Initialises the room using information from scanner and returns whether or not this room contains the player
     * @param sc: Scanner to parse room layout
     * @return whether or not this Room is the current room
     */
    public boolean initialise(Scanner sc) {
        boolean playerFound = false;
        entities = new ArrayList<Entity>();
        if(!sc.hasNextInt()) throw new Error("1No array size, instead: "+sc.next());
        int sizeX = sc.nextInt();
        System.out.println("size1: "+sizeX);
        if(!sc.hasNextInt()) throw new Error("2No array size, instead: "+sc.next());
        int sizeY = sc.nextInt();
        System.out.println("size2: "+sizeY);
        if(!sc.hasNextInt()) throw new Error("No room level, instead: "+sc.next());
        this.level = sc.nextInt();
        System.out.println("level: "+this.level);

        layout = new Tile[sizeX][sizeY];
        List<String> connectedRooms = new ArrayList<String>();
        while(sc.hasNext("[A-Za-z]")) {
            String room = sc.next();
            connectedRooms.add(room);
            System.out.println("connected: "+room);
        }
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                Entity curEntity = null;
                if(sc.hasNext("[A-Za-z]")) {             //connection to another room
                    curEntity = new Player();
                } else if(sc.hasNext("\\.")) {              //open space
                    curEntity = new Nothing();
                } else if(sc.hasNext("#")) {              //wall
                    curEntity = new Wall();
                } else if(sc.hasNextInt()) {         //enemy
                    curEntity = new Enemy();
                } else if(sc.hasNext("\\+")) {              //player
                    playerFound = true;
                    curEntity = new Player();
                }
                if(curEntity == null) throw new Error("Entity is invalid+"+sc.next());
                sc.next();
                layout[i][j] = new Tile(curEntity);
            }
        }
        printLayout();
        return playerFound;
    }

    private void printLayout() {
        System.out.println("layout:");
        for(int i = 0; i < layout.length; i++) {
            for(int j = 0; j < layout[i].length; j++) {
                System.out.print(layout[i][j].toString());
            }
            System.out.println("");
        }
        System.out.println("done");
    }

    /**
     * @return whether or not the room is cleared and player can progress
     */
    public boolean isRoomCleared() {return false;}

    /**
     *
     * @return the level(difficulty) of the room
     */
    public int getLevel() {return level;}

    /**
     * Looks through layout updating the list of entities within room
     */
    public void updateEntityList() {
        //MAY NOT NEED THIS}
    }

    /**
     *
     * @return List of all entities within room
     */
    public List<Entity> getEntityList() {
        return entities;
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public void moveEntity(Entity entity, Entity.Direction direction) {
        if (!entities.contains(entity)) {
            System.out.println("ERROR: Entity not available to move");
            return;
        }

        //WILL HAVE TO ITERATE THROUGH ARRAY TO FIND TILE WITH CORRECT ENT
        int x = -1;
        int y = -1;
        for(int i = 0; i < layout.length; i++) {
            for(int j = 0; j < layout[i].length; j++) {
                if(layout[i][j].isEntity(entity)) {
                    x = i;
                    y = j;
                }
            }
        }
    }
}
