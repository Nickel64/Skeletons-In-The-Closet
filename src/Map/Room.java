package Map;

import Entities.*;
import Entities.Entity;
import Entities.Entity.Direction;

import Model.*;

import java.awt.*;
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

    public Room() {}

    /**
     * Initialises the room using information from scanner and returns whether or not this room contains the player
     * @param sc: Scanner to parse room layout
     * @return whether or not this Room is the current room
     */
    public boolean initialise(Scanner sc) {
        cleared = false;
        boolean playerFound = false;
        entities = new ArrayList<>();
        if(!sc.hasNextInt()) throw new Error("1No array size, instead: "+sc.next());
        int sizeX = sc.nextInt();
        System.out.println("size1: "+sizeX);
        if(!sc.hasNextInt()) throw new Error("2No array size, instead: "+sc.next());
        int sizeY = sc.nextInt();
        System.out.println("size2: "+sizeY);
        if(!sc.hasNextInt()) throw new Error("No room level, instead: "+sc.next());
        int level = sc.nextInt();
        System.out.println("level: "+ level);

        layout = new Tile[sizeX][sizeY];
        while(sc.hasNext("[A-Za-z]")) {
            String room = sc.next();
            System.out.println("connected: "+room);
        }
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                Entity curEntity = null;
                if(sc.hasNext("[A-Za-z]")) {             //connection to another room
                    DoorTile door = new DoorTile(sc.next());
                    layout[i][j] = door;
                } else {
                    if(sc.hasNext("\\.")) {              //open space
                        curEntity = new Nothing();
                    } else if(sc.hasNext("#")) {         //wall
                        curEntity = new Wall();
                    } else if(sc.hasNextInt()) {                 //enemy
                        //TODO: if between 1-3 norm, 4-6 agile, 7-9 strong, 10+ BOSS
                        //TODO: CHECK IF APPROPRIATE
                        int enemyID = sc.nextInt();
                        if(enemyID <= 3 && enemyID >= 1) {
                            //NORMAL ENEMY
                            curEntity = new Enemy(enemyID+"", (5+enemyID)* level, 2, 3);
                        } else if(enemyID <= 6 && enemyID >= 4) {
                            //AGILE
                            curEntity = new Enemy(enemyID+"", 3* level, 3, (4+enemyID-3)* level);
                        } else if(enemyID <= 9 && enemyID >= 7) {
                            //STRONG
                            curEntity = new Enemy(enemyID+"", 4* level, (5+enemyID-6)* level, 2* level);
                        } else {
                            //BOSS
                            curEntity = new Enemy(enemyID+"", (12+enemyID-6)* level, (8+enemyID-6)* level, (2+enemyID-6)* level);
                        }
                    } else if(sc.hasNext("\\+")) {       //player
                        playerFound = true;
                        curEntity = new Player();
                    }
                    sc.next();
                    layout[i][j] = new FloorTile(curEntity);
                }
                if(curEntity == null) throw new Error("Entity is invalid+"+sc.next());
            }
        }
        printLayout();
        return playerFound;
    }

    public void startEntities() {
        for(Entity e: entities) {
            e.start();
        }
    }

    public void printLayout() {
        System.out.println("layout:");
        for (Tile[] aLayout : layout) {
            for (Tile anALayout : aLayout) {
                System.out.print(anALayout.toString());
            }
            System.out.println("");
        }
        System.out.println("done");
    }

    /**
     * @return whether or not the room is cleared and player can progress
     */
    private boolean isRoomCleared() {return cleared;}

    public boolean containsEntity(Entity entity) {
        return entities.contains(entity);
    }

    private void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    private Point findPoint(Entity entity) {
        //finds the given entity, if not found, throw error
        for(int i = 0; i < layout.length; i++) {
            for(int j = 0; j < layout[i].length; j++) {
                if(layout[i][j].isEntity(entity)) {
                    return new Point(i, j);
                }
            }
        }
        throw new Error("Point of entity has not been found");
    }

    private Point movesTo(int x, int y, Direction direction) {
        switch (direction) {
            case Up:
                if (y - 1 < 0) throw new Error("Cannot move entity " + direction.name());
                return new Point(x, y - 1);
            case Right:
                if (x + 1 > layout.length) throw new Error("Cannot move entity " + direction.name());
                return new Point(x + 1, y);
            case Left:
                if (x - 1 < 0) throw new Error("Cannot move entity " + direction.name());
                return new Point(x - 1, y);
            case Down:
                if (y + 1 < layout[x].length) throw new Error("Cannot move entity " + direction.name());
                return new Point(x, y + 1);
        }
        throw new Error("Unknown direction: "+ direction.name());
    }

    public void moveEntity(Entity entity, Direction direction, Model model) {
        Point p = findPoint(entity);
        int x = p.x;
        int y = p.y;

        Point destP = movesTo(x, y, direction);
        int destX = destP.x;
        int destY = destP.y;

        if(!layout[destX][destY].canMoveOnto()) throw new Error("Cannot move onto tile in "+direction.name());
        if(layout[destX][destY] instanceof DoorTile) {
            if(!(entity instanceof Player)) throw new Error("An entity other than player cannot move from room to room");
            if(!isRoomCleared()) throw new Error("Player cannot progress to next room until room is cleared");
            DoorTile door = (DoorTile) layout[destX][destY];        //finds the next room and changes models cur room
            model.changeCurrentRoom(model.getRoom(door.nameOfNextRoom()));
        } else {
            Entity temp = layout[x][y].getEntity();     //finds the two movable tile entities and swaps them
            layout[x][y].setEntity(layout[destX][destY].getEntity());
            layout[destX][destY].setEntity(temp);
        }
    }

    public void checkAttack(Entity entity, Direction direction) {
        Point p = findPoint(entity);
        int x = p.x;
        int y = p.y;

        Point destP = movesTo(x, y, direction);
        int destX = destP.x;
        int destY = destP.y;

        Entity defender = layout[destX][destY].getEntity();
        entity.attack(defender);
        if(defender.isDead()) {
            removeEntity(defender);
            layout[destX][destY].setEntity(new Nothing());
        }
    }
}
