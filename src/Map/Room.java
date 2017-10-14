package Map;

import Behaviour.Pathfinder;
import Entities.*;
import Entities.Entity;
import Entities.Entity.Direction;

import Model.*;
import Utils.GameError;
import Utils.Resources;
import Utils.TileSet;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Room containing room layout of Tiles
 * <p>
 * Created: 19/9/17
 *
 * @author Balgmi Nam
 */

public class Room implements java.io.Serializable {

    private Tile[][] layout;
    private Map<String, DoorTile> doors;
    private List<Entity> enemies;
    private Player player;
    private String name;
    private int level;
    private int width;
    private int height;
    private boolean cleared = false;
    private transient TileSet tiles;
    private OneWayExitTeleport exit;
    private int pingLoop = 0;

    public Room(String name) {
        this.exit = null;
        this.name = name;
    }

    /**
     * Initialises the room using information from scanner and returns whether or not this room contains the player
     *
     * @param sc: Scanner to parse room layout
     * @return whether or not this Room is the current room
     */
    public Scanner initialise(Scanner sc) {
        cleared = false;
        enemies = new ArrayList<>();
        doors = new HashMap<String, DoorTile>();
        if (!sc.hasNextInt()) throw new Error("No room level, instead: " + sc.next());
        this.level = sc.nextInt();
        tiles = new TileSet(level);

        if (!sc.hasNextInt()) throw new Error("No room size, instead: " + sc.next());
        this.width = sc.nextInt();
        if (!sc.hasNextInt()) throw new Error("No room size, instead: " + sc.next());
        this.height = sc.nextInt();

        String curString;
        layout = new Tile[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Entity curEntity = null;
                curString = sc.next();
                if (curString.matches("[A-Za-z]+")) {             //connection to another room
                    curEntity = new Nothing();
                    DoorTile door = new DoorTile(curString, curEntity);
                    doors.put(curString, door);
                    layout[i][j] = door;
                } else if (curString.equals("=")) {       //teleporter exit
                    if (this.exit != null) throw new Error("Room cannot have multiple One Way Teleport Exits");
                    curEntity = new Nothing();
                    OneWayExitTeleport teleportexit = new OneWayExitTeleport(curEntity);
                    layout[i][j] = teleportexit;
                    this.exit = teleportexit;
                } else if (curString.matches("-[A-Za-z]+")) {      //teleporter entry
                    curEntity = new Nothing();
                    OneWayEntryTeleport teleportentry = new OneWayEntryTeleport(curEntity, curString);
                    layout[i][j] = teleportentry;
                } else {
                    if (curString.matches("\\.")) {              //open space
                        curEntity = new Nothing();
                    } else if (curString.matches("\\*")) {         //wall
                        curEntity = new Wall(this.level);
                    } else if (curString.matches("~")) {
                        curEntity = new PowerUpAttack();
                    } else if (curString.matches("`")) {
                        curEntity = new PowerUpHealth();
                    } else if (curString.matches("\\+")) {       //player
                        this.player = new Player();
                        curEntity = this.player;
                    } else if (curString.matches("[0-9]+")) {                 //enemy
                        //if between 1-3 norm, 4-6 agile, 7-9 strong, 10 BOSS
                        Integer enemyID = Integer.parseInt(curString);
                        int type = enemyID % 3 + 1;
                        int lvlbonus = level + (level / 2);
                        Random r = new Random();
                        if (enemyID <= 3 && enemyID >= 1) {
                            //NORMAL ENEMY
                            curEntity = new Enemy(enemyID, r.nextInt(type) + 3 + lvlbonus,
                                    r.nextInt(type) + 2 + lvlbonus, r.nextInt(type) + 3);
                        } else if (enemyID <= 6 && enemyID >= 4) {
                            //AGILE
                            curEntity = new Enemy(enemyID, r.nextInt(type) + 2 + lvlbonus,
                                    r.nextInt(type) + 1 + lvlbonus, r.nextInt(type) + 5);
                        } else if (enemyID <= 9 && enemyID >= 7) {
                            //STRONG
                            curEntity = new Enemy(enemyID, r.nextInt(type) + 4 + lvlbonus,
                                    r.nextInt(type) + 4 + lvlbonus, r.nextInt(type) + 1);
//                            curEntity = new Enemy(enemyID, 4* level, (5+enemyID-6)* level, 2* level);
                        } else if (enemyID >= 10) {
                            //BOSS
                            curEntity = new Boss(enemyID, (12 + enemyID - 6) * level, (8 + enemyID - 6) * level, (2 + enemyID - 6) * level);
                        } else throw new Error("Invalid enemy id, must be 1+");
                        enemies.add(curEntity);
                    }
                    layout[i][j] = new FloorTile(curEntity);
                }
                if (curEntity == null) throw new Error("Entity is invalid " + curString);
                if (Resources.DEBUG) System.out.print(curString);
            }
            if (Resources.DEBUG) System.out.println();
        }
        setRoomClearedTo(enemies.size() == 0);
        return sc;
    }

    /**
     * Returns whether or not room has teleport exit
     *
     * @return
     */
    private boolean hasTeleportExit() {
        return this.exit != null;
    }

    private Tile getTeleportExit() {
        if (!hasTeleportExit()) throw new Error("Teleport entry does not have a valid destination");
        return this.exit;
    }

    /**
     * returns the player entity
     *
     * @return PLayer
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns the TileSet containing room texture information
     *
     * @return TileSet with images
     */
    public TileSet getTileSet() {
        return tiles;
    }

    /**
     * Return the Name of this room
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the point of the player within the room layout, if player is not found return null
     *
     * @return Point of player in room
     */
    public Point getPlayerLocation() {
        for (int y = 0; y < layout.length; y++) {
            for (int x = 0; x < layout[0].length; x++) {
                if (layout[y][x].getEntity() instanceof Player) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Set this room to cleared, Player is able to exit room
     *
     * @param b
     */
    public void setRoomClearedTo(boolean b) {
        cleared = b;
    }

    /**
     * @return whether or not the room is cleared and player can progress
     */
    public boolean isRoomCleared() {
        this.cleared = enemies.isEmpty();
        return cleared;
    }

    /**
     * Returns the width of the room
     *
     * @return int of width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the room
     *
     * @return int of height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Sets the player as the new given player
     *
     * @param player
     */
    private void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the entity at layout x and y.
     *
     * @param x coordinate of entity
     * @param y coordinate of entity
     * @return Entity
     */
    public Entity getEntityAt(int x, int y) {
        return layout[y][x].getEntity();
    }

    /**
     * Returns whether or not the given enemy is within the room
     *
     * @param enemy to check with
     * @return true/false boolean
     */
    public boolean containsEnemy(Entity enemy) {
        return enemies.contains(enemy);
    }

    /**
     * Returns whether or not the given entity is within the room
     *
     * @param entity to check with
     * @return true/false boolean
     */
    public boolean containsEntity(Entity entity) {
        return (enemies.contains(entity) || player == entity);
    }

    /**
     * Returns a list of all enemy entities, including: Enemy and Boss
     *
     * @return all enemies
     */
    public List<Entity> getEnemies() {
        return enemies;
    }

    /**
     * Removes entity
     *
     * @param enemy to find and remove
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
        for (int i = 0; i < layout.length; i++) {    //y
            for (int j = 0; j < layout[i].length; j++) {     //x
                if (layout[i][j].isEntity(entity)) {
                    return new Point(j, i);
                }
            }
        }
        throw new Error("Point of entity has not been found");
    }

    /**
     * Returns the Point of destination for a movement in the given direction from coordinate x, y.
     *
     * @param x         coordinate of layout
     * @param y         coordinate of layout
     * @param direction of movement
     * @return the point of destination
     */
    private Point movesTo(int x, int y, Direction direction) {
        switch (direction) {
            case Up:
                if (y - 1 < 0) throw new GameError("Cannot move entity " + direction.name());
                return new Point(x, y - 1);
            case Right:
                if (x + 1 >= width) throw new GameError("Cannot move entity " + direction.name());
                return new Point(x + 1, y);
            case Left:
                if (x - 1 < 0) throw new GameError("Cannot move entity " + direction.name());
                return new Point(x - 1, y);
            case Down:
                if ((y + 1) >= height)
                    throw new GameError("Cannot move entity " + direction.name() + " x: " + (x + 1) + " y: " + y);
                return new Point(x, y + 1);
        }
        throw new Error("Unknown direction: " + direction.name());
    }

    /**
     * Returns the point of the given tile, if tile not found, error is thrown
     *
     * @param t Tile to search for
     * @return Point of tiles position
     */
    private Point getTilePoint(Tile t) {
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] == t) {
                    return new Point(j, i);
                }
            }
        }
        return new Point(-1, -1);
    }

    /**
     * Moves the entity in the given direction if possible.
     *
     * @param entity    that is to be moved
     * @param direction of which entity wishes to move
     * @param model     of game
     */
    public void moveEntity(Entity entity, Direction direction, Model model) {
        Point p = findPoint(entity);
        int x = p.x;
        int y = p.y;


        if (layout[y][x] instanceof DoorTile) {      //if entity is currently on door tile, possible room change
            DoorTile door = (DoorTile) layout[y][x];        //gets current door tile
            Room nextRoom = model.getRoom(door.nameOfNextRoom());       //finds next room to hold player
            DoorTile endDoor = nextRoom.getDoorNamed(this.name);
            Point destDoorPoint = nextRoom.getTilePoint(endDoor);
            if (Resources.DEBUG) System.out.println(direction.name() + " at x: " + x + " and y: " + y);
            switch (direction) {
                case Down:
                    if (y + 1 >= this.height) {    //checks that direction is going out of room
                        //check that next room door tile is correctly placed
                        if (destDoorPoint.y == 0) {
                            if (!(entity instanceof Player))
                                throw new Error("An entity other than player cannot move from room to room");
                            updateRoom(model, door, endDoor, nextRoom);
                            return;
                        } else
                            throw new GameError("Cannot move outside of room boundaries in this direction, door is not located here " + name);
                    }
                    break;
                case Right:
                    if (x + 1 >= this.width) {    //checks that direction is going out of room
                        //check that next room door tile is correctly placed
                        if (destDoorPoint.x == 0) {
                            if (!(entity instanceof Player))
                                throw new Error("An entity other than player cannot move from room to room");
                            updateRoom(model, door, endDoor, nextRoom);
                            return;
                        } else
                            throw new GameError("Cannot move outside of room boundaries in this direction, door is not located here " + name);
                    }
                    break;
                case Up:
                    if (y - 1 < 0) {    //checks that direction is going out of room
                        //check that next room door tile is correctly placed
                        if (destDoorPoint.y == nextRoom.height - 1) {
                            if (!(entity instanceof Player))
                                throw new Error("An entity other than player cannot move from room to room");
                            updateRoom(model, door, endDoor, nextRoom);
                            return;
                        } else
                            throw new GameError("Cannot move outside of room boundaries in this direction, door is not located here " + name);
                    }
                    break;
                case Left:
                    if (x - 1 < 0) {    //checks that direction is going out of room
                        //check that next room door tile is correctly placed
                        if (destDoorPoint.x == nextRoom.width - 1) {
                            if (!(entity instanceof Player))
                                throw new Error("An entity other than player cannot move from room to room");
                            updateRoom(model, door, endDoor, nextRoom);
                            return;
                        } else
                            throw new GameError("Cannot move outside of room boundaries in this direction, door is not located here " + name);
                    }
                    break;
            }
        }
        Point destP = movesTo(x, y, direction);
        if (layout[destP.y][destP.x].getEntity() instanceof PowerUp) {
            if (entity instanceof Player) {
                PowerUp pUp = (PowerUp) layout[destP.y][destP.x].getEntity();
                pUp.increase((Player) entity);
                Resources.playAudio("Item.wav");
            }
            layout[destP.y][destP.x].setEntity(new Nothing());
        }
        swap(p, destP);
        if (layout[destP.y][destP.x] instanceof OneWayEntryTeleport && isRoomCleared()) {
            //find teleport exit and updateRoom
            OneWayEntryTeleport entry = (OneWayEntryTeleport) layout[destP.y][destP.x];
            Room nextRoom = model.getRoom(entry.nameOfNextRoom());
            updateRoom(model, entry, nextRoom.getTeleportExit(), nextRoom);
        }

    }

    private void updateRoom(Model model, Tile entry, Tile exit, Room nextRoom) {
        if (!isRoomCleared()) {
            throw new GameError("Player cannot progress to next room until room is cleared");
        }
        Entity temp = exit.getEntity();
        exit.setEntity(entry.getEntity());
        entry.setEntity(temp);
        model.changeCurrentRoom(nextRoom);
        nextRoom.setPlayer(this.player);        //sets the next room up with the current player
    }

    /**
     * Swaps the entities of the two given points
     *
     * @param start point of entity
     * @param end   point of entity
     */
    private void swap(Point start, Point end) {
        if (!layout[end.y][end.x].canMoveOnto()) throw new GameError("Cannot move onto tile");
        Entity temp = layout[start.y][start.x].getEntity();     //finds the two movable tile entities and swaps them
        layout[start.y][start.x].setEntity(layout[end.y][end.x].getEntity());
        layout[end.y][end.x].setEntity(temp);
    }

    /**
     * Returns whether or not the room has a door to the given room name
     *
     * @param name of next room connected
     * @return boolean true or false
     */
    public boolean doesDoorExist(String name) {
        return doors.containsKey(name);
    }

    /**
     * REturns the given Door leading to the param name
     *
     * @param name of next room connected
     * @return DoorTile of
     */
    public DoorTile getDoorNamed(String name) {
        if (doors.containsKey(name)) return doors.get(name);
        throw new Error("Door leading to room " + name + " does not exist");
    }

    /**
     * Returns Tile at the given location
     *
     * @param x coordinate
     * @param y coordinate
     * @return Tile at x and y coordinate of room
     */
    public Tile getTileAtLocation(int x, int y) {
        if (x < this.width && y < this.height) return layout[y][x];
        throw new ArrayIndexOutOfBoundsException("the coordinate " + x + ", " + y + " is out of bounds of the room layout of " +
                +width + " width and " + height + " height.");
    }

    /**
     * Attacks and damages the entity in the given direction if possible.
     *
     * @param entity    that is initiating attack
     * @param direction that entity is attacking
     */
    public void checkAttack(Entity entity, Direction direction) {
        Point p = findPoint(entity);
        Point destP = null;
        try {
            destP = movesTo(p.x, p.y, direction);
        } catch (GameError g) {
            return;
        }
        //is the attacker a player
        //if it is, then set the player attack boolean to true
        //ping will check if animation is complete when it is called
        //if it is, then trigger the attack
        //same for other entities
        //and other actions (aoe, dying etc)
        attack(p, destP);
    }

    /**
     * Attacks and damages entities surrounding attacking entity if possible
     *
     * @param entity that is initialising attack
     */
    public void checkAttackAOE(Entity entity) {
        if (getPlayer().getSpecial() - 10 >= 0) {
            getPlayer().setSpecial(getPlayer().getSpecial() - 10);
        }
        Point attacker = findPoint(entity);
        int x = attacker.x;
        int y = attacker.y;
        boolean vertUp = false;
        boolean vertDown = false;
        boolean horiLeft = false;
        boolean horiRight = false;

        if (y > 0) {             //attack up is viable
            attack(attacker, new Point(x, y - 1));        //attacks up
            vertUp = true;
        }
        if (y < this.height - 1) {      //attack down is viable
            attack(attacker, new Point(x, y + 1));        //attacks down
            vertDown = true;
        }
        if (x < this.width - 1) {      //attack right is viable
            attack(attacker, new Point(x + 1, y));        //attacks right
            horiRight = true;
        }
        if (x > 0) {             //attack left is viable
            attack(attacker, new Point(x - 1, y));      //attacks left
            horiLeft = true;
        }
        //all done with left/right/up/down cases, onto diagonal
        if (horiLeft && vertUp) attack(attacker, new Point(x - 1, y - 1));       //top left
        if (horiLeft && vertDown) attack(attacker, new Point(x - 1, y + 1));     //bottom left
        if (horiRight && vertUp) attack(attacker, new Point(x + 1, y - 1));      //top right
        if (horiRight && vertDown) attack(attacker, new Point(x + 1, y + 1));    //bottom right
        player.attackAOE();
    }

    /**
     * Attacks defender and checks for death, if there is any death then defender is taken off of layout
     *
     * @param attack Point of layout where attack occurs
     * @param defend Point of layout where defender is attacked
     */
    private void attack(Point attack, Point defend) {
        Entity attacker = layout[attack.y][attack.x].getEntity();
        Entity defender = layout[defend.y][defend.x].getEntity();
        attacker.attack(defender);
        if (defender.isDead()) {
            removeEnemy(defender);
            layout[defend.y][defend.x].setEntity(new Nothing());
        }
    }

    public String toString() {
        String str = name + " " + level + "\n" + width + " " + height;
        for (Tile[] aLayout : layout) {
            str += "\n";
            for (Tile tile : aLayout) {
                str += tile.toString() + " ";
            }
        }
        return str;
    }

    public void ping(Model m) {

        boolean actComplete = false;

        if (player != null) {
            actComplete = player.ping();
            if (actComplete) {   //some action is completed
                if (player.isPlayerAttack()) {
                    player.resetPlayerActions("atk");
                    this.checkAttack(player, player.getDir());
                }
                if (player.isPlayerAttackAoE()) {
                    player.resetPlayerActions("aoe");
                    this.checkAttackAOE(player);
                }
             }
        }
        if (isRoomCleared() && player != null)
            player.regen();
        for (Entity entity : getEnemies()) {
            Enemy e = (Enemy) entity;
            Direction playerProx = null;
            String message = "atk";
            Point p = findPoint(entity);
            // search for the player
            if (p.x > 0 && getEntityAt(p.x - 1, p.y) instanceof Player) {
                playerProx = Direction.Left;
            } else if (p.x + 1 < getWidth() && getEntityAt(p.x + 1, p.y) instanceof Player) {

                playerProx = Direction.Right;
            } else if (p.y > 0 && getEntityAt(p.x, p.y - 1) instanceof Player) {

                playerProx = Direction.Up;
            } else if (p.y + 1 < getHeight() && getEntityAt(p.x, p.y + 1) instanceof Player) {

                playerProx = Direction.Down;
            }


            this.checkEnemyAttack(e, playerProx);

            if (pingLoop++ == 15 - level) {
                if (playerProx != null) {
                    e.setDirection(playerProx);
                    if (!e.isEnemyAttack()) {
                        e.setDirection(playerProx);
                        e.startAction("atk");
                    }
                } else if (!e.isEnemyAttack()) {
                    message = "";

                    Point nextPos = Pathfinder.findNextClosestPointToGoal(this, p, getPlayerLocation());
                    if (nextPos == null)
                        continue;

                    Direction dir = Direction.Left;
                    if (p.x > nextPos.x)
                        dir = Direction.Left;
                    else if (p.x < nextPos.x)
                        dir = Direction.Right;
                    else if (p.y > nextPos.y)
                        dir = Direction.Up;
                    else if (p.y < nextPos.y)
                        dir = Direction.Down;
                    try {
                        moveEntity(entity, dir, m);
                    } catch (GameError er) {
                        continue;
                    }
                    entity.setDirection(dir);
                    if (Resources.DEBUG) System.out.println("MOVING ENEMY: " + dir);

                    if (Resources.DEBUG) System.out.println(message);

                }
            }
        }
        pingLoop %= 15;
    }

    /**
     * Used to Reset the TileSet
     * after this class is Deserialized
     */
    public void resetTileSet() {
        for (int y = 0; y < layout.length; y++) {
            for (int x = 0; x < layout[0].length; x++) {
                if (layout[y][x].getEntity() instanceof PowerUp) {
                    PowerUp powerUps = (PowerUp) layout[y][x].getEntity();
                    powerUps.resetImage();
                }
            }
        }
        this.tiles = new TileSet(level);
    }

    public void checkEnemyAttack(Enemy e, Direction dir) {
        boolean actComplete = e.ping();
        if (actComplete) {   //some action is completed
            if (e.isEnemyAttack()) {
                e.resetEnemyActions("atk");
                if(dir!=null)
                    this.checkAttack(e, dir);
            }
        }
    }

}
