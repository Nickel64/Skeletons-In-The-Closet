package Map;
/**
 * Room containing room layout of Tiles
 *
 * Created: 19/9/17
 * @author Balgmi Nam
 */

public class Room {

    private Tile[][] layout;
//    private List<Entity> entities;
    private boolean cleared = false;
    private int level;

    public Room() {}

//    /**
//     * Initialises the room using information from scanner
//     * @param sc: Scanner to parse room layout
//     */
//    public void initialise(Scanner sc) {
//        entities = new ArrayList<Entity>();
//    }

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
    public void updateEntityList() {//MAY NOT NEED THIS}

//    /**
//     *
//     * @return List of all entities within room
//     */
//    public List<Entity> getEntityList() {
//        return entities;
//    }

//    public void removeEntity(Entity entity) {
//        entities.remove(entity);
    }
}
