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

    public static final Integer ROOM_WIDTH = 5;
    public static final Integer ROOM_HEIGHT = 5;
    private Tile[][] layout;
    private List<Entity> enemies;
    private Player player;
    private String name;
    private int level;
    private boolean cleared = false;

    public Room(String name) {
        this.name = name;
    }

    /**
     * Initialises the room using information from scanner and returns whether or not this room contains the player
     * @param sc: Scanner to parse room layout
     * @return whether or not this Room is the current room
     */
    public Scanner initialise(Scanner sc) {
        cleared = false;
        enemies = new ArrayList<>();
        if(!sc.hasNextInt()) throw new Error("No room level, instead: "+sc.next());
        this.level = sc.nextInt();

        String curString;
        layout = new Tile[ROOM_WIDTH][ROOM_HEIGHT];
        for(int i = 0; i < ROOM_WIDTH; i++) {
            for(int j = 0; j < ROOM_HEIGHT; j++) {
                Entity curEntity = null;
                curString = sc.next();
                if(curString.matches("[A-Za-z]")) {             //connection to another room
                    curEntity = new Nothing();
                    DoorTile door = new DoorTile(curString, curEntity);
                    layout[i][j] = door;
                } else {
                    if(curString.matches("\\.")) {              //open space
                        curEntity = new Nothing();
                    } else if(curString.matches("\\*")) {         //wall
                        curEntity = new Wall();

                    } else if(curString.matches("\\+")) {       //player
                        this.player = new Player();
                        curEntity = this.player;

                    } else if(curString.matches("[0-9]")){                 //enemy
                        //TODO: if between 1-3 norm, 4-6 agile, 7-9 strong, 10 BOSS
                        //TODO: CHECK IF APPROPRIATE
                        int enemyID = Integer.parseInt(curString);
                        if(enemyID <= 3 && enemyID >= 1) {
                            //NORMAL ENEMY
                            curEntity = new Enemy(enemyID, (5+enemyID)* level, 2, 3);
                        } else if(enemyID <= 6 && enemyID >= 4) {
                            //AGILE
                            curEntity = new Enemy(enemyID, 3* level, 3, (4+enemyID-3)* level);
                        } else if(enemyID <= 9 && enemyID >= 7) {
                            //STRONG
                            curEntity = new Enemy(enemyID, 4* level, (5+enemyID-6)* level, 2* level);
                        } else {
                            //BOSS
                            curEntity = new Enemy(enemyID, (12+enemyID-6)* level, (8+enemyID-6)* level, (2+enemyID-6)* level);
                        }
                        enemies.add(curEntity);
                    }
                    layout[i][j] = new FloorTile(curEntity);
                }
                if(curEntity == null) throw new Error("Entity is invalid+"+curString);
                System.out.print(curString);
            }
            System.out.println();
        }
        return sc;
    }

    public Player getPlayer() {
        return this.player;
    }

    /**
     * Starts the enemies ping
     */
    public void startEnemies() {
        for(Entity e: enemies) {
            e.start();
        }
    }

    public Tile getTileAtLocation(int x, int y){
        return layout[y][x];
    }

    /**
     * @return whether or not the room is cleared and player can progress
     */
    private boolean isRoomCleared() {return cleared;}

    public Entity getEntityAt(int x, int y) {
        return layout[y][x].getEntity();
    }

    public boolean containsEnemy(Entity enemy) {
        return enemies.contains(enemy);
    }

    public List<Entity> getEnemies() {
        return enemies;
    }

    /**
     * Removes entity
     * @param enemy
     */
    private void removeEnemy(Entity enemy) {
        enemies.remove(enemy);
    }

    /**
     * Finds the coordinate of which the entity is currently at
     *
     * @param entity to find coordinate of
     * @return Point of entity coordinate
     */
    private Point findPoint(Entity entity) {
        //finds the given entity, if not found, throw error
        for(int i = 0; i < layout.length; i++) {    //y
            for(int j = 0; j < layout[i].length; j++) {     //x
                if(layout[i][j].isEntity(entity)) {
                    return new Point(j, i);
                }
            }
        }
        throw new Error("Point of entity has not been found");
    }

    /**
     * Returns the Point of destination for a movement in the given direction from coordinate x, y.
     *
     * @param x coordinate of layout
     * @param y coordinate of layout
     * @param direction of movement
     * @return the point of destination
     */
    private Point movesTo(int x, int y, Direction direction) {
        switch (direction) {
            case Up:
                if (y - 1 < 0) throw new Error("Cannot move entity " + direction.name());
                return new Point(x, y - 1);
            case Right:
                if (x + 1 >= ROOM_WIDTH) throw new Error("Cannot move entity " + direction.name());
                return new Point(x + 1, y);
            case Left:
                if (x - 1 < 0) throw new Error("Cannot move entity " + direction.name());
                return new Point(x - 1, y);
            case Down:
                if ((y +1) >= ROOM_HEIGHT) throw new Error("Cannot move entity " + direction.name()+" x: "+(x+1)+" y: "+y);
                return new Point(x, y + 1);
        }
        throw new Error("Unknown direction: "+ direction.name());
    }

    /**
     * Moves the entity in the given direction if possible.
     *
     * @param entity that is to be moved
     * @param direction of which entity wishes to move
     * @param model of game
     */
    public void moveEntity(Entity entity, Direction direction, Model model) {
        Point p = findPoint(entity);
        int x = p.x;
        int y = p.y;

        System.out.println("enity is at: "+x+", "+y);

        Point destP = movesTo(x, y, direction);
        int destX = destP.x;
        int destY = destP.y;

        System.out.println("to move onto coord "+destX+", "+destY);
        System.out.println("where :"+layout[destY][destX].getEntity().toString());

        if(!layout[destY][destX].canMoveOnto()) throw new Error("Cannot move onto tile in "+direction.name());
        if(layout[destY][destX] instanceof DoorTile) {
            if(!(entity instanceof Player)) throw new Error("An entity other than player cannot move from room to room");
            if(!isRoomCleared()) throw new Error("Player cannot progress to next room until room is cleared");
            DoorTile door = (DoorTile) layout[destX][destY];        //finds the next room and changes models cur room
            model.changeCurrentRoom(model.getRoom(door.nameOfNextRoom()));
        } else {
            System.out.println("Swapping tiles from "+x+", "+y+" to "+destX+", "+destY);
            System.out.println("swap"+layout[y][x].getEntity().toString()+" and "+layout[destY][destX].getEntity().toString());
            Entity temp = layout[y][x].getEntity();     //finds the two movable tile entities and swaps them
            layout[y][x].setEntity(layout[destY][destX].getEntity());
            layout[destY][destX].setEntity(temp);
        }
    }

    /**
     * Attacks and damages the entity in the given direction if possible.
     *
     * @param entity that is initiating attack
     * @param direction that entity is attacking
     */
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
            removeEnemy(defender);
            layout[destX][destY].setEntity(new Nothing());
        }
    }

    /**
     * Gets the image file name at layout x and y for tile.
     *
     * @param x coordinate of layout
     * @param y coordinate of layout
     * @return String of file name
     */
    public String getImageFileNameAt(int x, int y) {
        return layout[x][y].getImageName();
    }

    public String toString() {
        String str = name+" "+level;
        for (Tile[] aLayout : layout) {
            str += "\n";
            for (Tile anALayout : aLayout) {
                str += anALayout.toString()+" ";
            }
        }
        return str;
    }
}
