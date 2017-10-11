package Tests;

import Behaviour.Pathfinder;
import Entities.Enemy;
import Entities.Entity;
import Entities.Player;
import Entities.PowerUp;
import Map.Room;
import Map.Tile;
import Model.Model;
import Utils.GameError;
import org.junit.*;

import java.util.Scanner;
import java.util.Stack;

import static org.junit.Assert.*;

public class TestAll {
    public String[] tests = {"ModelTests", "PathfinderTests"};


    /**
     * Used to test that a path between a point and itself just returns itself (a.k.a no movement)
     */
    /**
    @Test
    public void test_pathfind_EmptyGridSuccess1(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{0,0}, grid);
        Stack<int[]> expected = createExpected(new int[][]{{0,0}});
        checkEmptyGrid(expected, path);
    }
    **/

    /**
     * Tests a path between 0,0 and 2,2 on an empty board
     */
    /**
    @Test
    public void test_pathfind_EmptyGridSuccess2(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{2,2}, grid);
        Stack<int[]> expected = createExpected(new int[][]{{0,0}, {1,0}, {2,0}, {2,1}, {2,2}});
        checkEmptyGrid(expected, path);
    }
    **/

    /**
     * Tests a path between 0,0 and 3,3 on an empty board
     */
    /**
    @Test
    public void test_pathfind_EmptyGridSuccess3(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{3,3}, grid);
        Stack<int[]> expected = createExpected(new int[][]{{0,0}, {1,0}, {2,0}, {3,0}, {3,1}, {3,2}, {3,3}});
        checkEmptyGrid(expected, path);
    }
    **/

    /**
     * Used to test that the pathfinder will not attempt to move into negative positions
     */
    /**
    @Test
    public void test_pathfind_EmptyGridFail1(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{-1,-1}, grid);
        //Stack<int[]> expected = createExpected(new int[][]{{0,0}, {1,0}, {2,0}, {3,0}, {3,1}, {3,2}, {3,3}});
        Stack<int[]> expected = new Stack<>();
        checkEmptyGrid(expected, path);
    }
    **/

    /**
     * Used to check that the pathfinder will not move into positions outside the right edge of the board
     */
    /**
    @Test
    public void test_pathfind_EmptyGridFail2(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{11,11}, grid);
        //Stack<int[]> expected = createExpected(new int[][]{{0,0}, {1,0}, {2,0}, {3,0}, {3,1}, {3,2}, {3,3}});
        Stack<int[]> expected = new Stack<>();
        checkEmptyGrid(expected, path);
    }
    **/

    /**
     * Used to check that the pathfinder will not move into positions outside the right edge of the board
     */
    /**
    @Test
    public void test_pathfind_EmptyGridFail3(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{10,10}, grid);
        Stack<int[]> expected = new Stack<>();
        checkEmptyGrid(expected, path);
    }
    **/

    /**
     * Helper method used to compare two Stacks of integer arrays
     * @param expected
     *      Expected path between two points
     *      Stack of int array objects
     *      each int array {x,y}
     * @param path
     *      Path to compare between two points
     *      Stack of int array objects
     *      each int array {x,y}
     */
    public void checkEmptyGrid(Stack<int[]> expected, Stack<int[]> path){
        if(expected.size() != path.size()) fail("Expected path not the same length as actual path");

        System.out.println();
        for(int[] pathPiece: path){
            System.out.println(pathPiece[0] + " " + pathPiece[1]);
        }

        for(int i = 0; i < expected.size(); i++){
            int[] exp = expected.pop();
            int[] act = path.pop();
            assertEquals(exp[0], act[0]);
            assertEquals(exp[1], act[1]);
        }
    }

    /**
     * Used to convert a 2D array of ints into a stack of 1D int arrays
     *
     * Used to make created the 'expected' array eaiser
     * @param toUse
     *      2D array of ints
     *      outer array -> collection of inner arrays
     *      inner array -> format {x,y}
     * @return
     *      Stack containing all of the inner arrays
     */
    public Stack<int[]> createExpected(int[][] toUse){
        Stack<int[]> expected = new Stack<>();
        for(int i = 0; i < toUse.length; i++){
            expected.add(toUse[i]);
        }
        return expected;
    }

    // ######################## MODEL TESTS ########################

    @Test
    public void test_model_initialise_1() {
        //tests that model initialises rooms and entities properly
        //correct number of enemy entities made, room layout should be the same to simpleMap
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . + . 2 \n" +
                        "1 . 2 . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            assertNotNull(m.getCurrentRoom());
            Room r = m.getCurrentRoom();
            assertNotNull(r.getEnemies());
            assertEquals(3, r.getEnemies().size());
            assertEquals(simpleMap, r.toString());
        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_initialise_2() {
        //tests that model initialises rooms and entities properly
        //correct number of enemy entities made, change in room layout should initialise fine
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "6 5\n" +
                        "* * . . . . \n" +
                        ". . + . 2 * \n" +
                        "1 . 2 . . * \n" +
                        "* * * . . . \n" +
                        ". . . . * . ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            assertNotNull(m.getCurrentRoom());
            Room r = m.getCurrentRoom();
            assertNotNull(r.getEnemies());
            assertEquals(3, r.getEnemies().size());
            assertEquals(simpleMap, r.toString());
        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_initialise_3() {
        //tests that model initialises rooms and entities properly
        //all is correct in format except no player found in first room
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . 2 \n" +
                        "1 . 2 . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            fail();
        } catch (Error error) {
        }
    }

    @Test
    public void test_model_initialise_4() {
        //tests that model initialises rooms and entities properly
        //all is correct in format except no player found in first room
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . + . \n" +
                        ". . . . 2 \n" +
                        "1 . 2 . . \n" +
                        "* * * . . \n" +
                        ". . . . * "+
                        "RoomB 1\n" +
                        "5 5 " +
                        "* * . . . \n" +
                        ". . + . 2 \n" +
                        "1 . 2 . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            fail();
        } catch (Error error) {
        }
    }

    @Test
    public void test_model_initialise_5() {
        //tests that model initialises rooms and entities properly
        //incorrect number of enemy entities made, room layout is the same to simpleMap
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "1 5\n" +
                        "* \n" +
                        ". \n" +
                        "1 \n" +
                        "* \n" +
                        ". ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_move_DOWN_1() {
        //tests that model moves entities to a given direction where tile in empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . 2 \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". . + . 2 \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(4, 1);
            assertEquals(2 + "", e.toString());
            m.moveEntity(e, Entity.Direction.Down);
            assertEquals(e, r.getEntityAt(4, 2));
            assertEquals(endMap, r.toString());
        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_move_DOWN_2() {
        //tests that model moves entities to a given direction where tile is not empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". 4 + . . \n" +
                        "* * * . 1 \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity e = r.getEntityAt(4, 3);     //enemy 1
        assertEquals("1", e.toString());
        try {
            m.moveEntity(e, Entity.Direction.Down);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(4, 3));
            assertEquals(simpleMap, r.toString());
        }
    }

    @Test
    public void test_model_move_DOWN_3() {
        //tests that model moves entities to a given direction where tile is non existent
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". 4 + . . \n" +
                        "* * * . . \n" +
                        "1 . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity e = r.getEntityAt(0, 4);     //enemy 1
        assertEquals("1", e.toString());
        try {
            m.moveEntity(e, Entity.Direction.Down);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(0, 4));
            assertEquals(simpleMap, r.toString());
        }
    }

    @Test
    public void test_model_move_UP_1() {
        //tests that model moves entities to a given direction where tile in empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . 2 \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . 2 \n" +
                        ". . . . . \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(4, 1);
            assertEquals(2 + "", e.toString());
            m.moveEntity(e, Entity.Direction.Up);
            assertEquals(e, r.getEntityAt(4, 0));
            assertEquals(endMap, r.toString());
        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_move_UP_2() {
        //tests that model moves entities to a given direction where tile is not empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". 4 + . . \n" +
                        "* * * . . \n" +
                        "1 . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity e = r.getEntityAt(0, 4);     //enemy 1
        assertEquals("1", e.toString());
        try {
            m.moveEntity(e, Entity.Direction.Up);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(0, 4));
            assertEquals(simpleMap, r.toString());
        }
    }

    @Test
    public void test_model_move_UP_3() {
        //tests that model moves entities to a given direction where tile is non existent
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . + . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        "1 . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity e = r.getEntityAt(3, 0);     //enemy 1
        assertEquals("+", e.toString());
        try {
            m.moveEntity(e, Entity.Direction.Up);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(3, 0));
            assertEquals(simpleMap, r.toString());
        }
    }

    @Test
    public void test_model_move_LEFT_1() {
        //tests that model moves entities to a given direction where tile in empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". 2 . . . \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        "2 . . . . \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(1, 1);
            assertEquals(2 + "", e.toString());
            m.moveEntity(e, Entity.Direction.Left);
            assertEquals(e, r.getEntityAt(0, 1));
            assertEquals(endMap, r.toString());
        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_move_LEFT_2() {
        //tests that model moves entities to a given direction where tile is not empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". 4 + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Player p = m.getPlayer();
        assertEquals("+", p.toString());
        try {
            m.moveEntity(p, Entity.Direction.Left);
            fail();
        } catch (Error error) {
            assertEquals(p, r.getEntityAt(2, 2));
            assertEquals(simpleMap, r.toString());
        }
    }

    @Test
    public void test_model_move_LEFT_3() {
        //tests that model moves entities to a given direction where tile is non existent
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". 4 + . . \n" +
                        "* * * . . \n" +
                        "1 . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity e = r.getEntityAt(0, 4);     //enemy 1
        assertEquals("1", e.toString());
        try {
            m.moveEntity(e, Entity.Direction.Left);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(0, 4));
            assertEquals(simpleMap, r.toString());
        }
    }

    @Test
    public void test_model_move_RIGHT_1() {
        //tests that model moves entities to a given direction where tile in empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". 2 . . . \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . 2 . . \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(1, 1);
            assertEquals(2 + "", e.toString());
            m.moveEntity(e, Entity.Direction.Right);
            assertEquals(e, r.getEntityAt(2, 1));
            assertEquals(endMap, r.toString());
        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_move_RIGHT_2() {
        //tests that model moves entities to a given direction where tile is not empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". 4 + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity e = r.getEntityAt(1, 2);
        assertEquals(4+"", e.toString());
        try {
            m.moveEntity(e, Entity.Direction.Right);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(1, 2));
            assertEquals(simpleMap, r.toString());
        }
    }

    @Test
    public void test_model_move_RIGHT_3() {
        //tests that model moves entities to a given direction where tile is non existent
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . 6 \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        "1 . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity e = r.getEntityAt(4, 1);     //enemy 1
        assertEquals("6", e.toString());
        try {
            m.moveEntity(e, Entity.Direction.Right);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(4, 1));
            assertEquals(simpleMap, r.toString());
        }
    }

    @Test
    public void test_model_move_entity_1() {
        //tests that model moves entities that are able to be moved (NOTHING)
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". 4 . . . \n" +
                        "* * . . . \n" +
                        "1 . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity e = r.getEntityAt(3, 2);     //nothing
        assertEquals(".", e.toString());
        try {
            m.moveEntity(e, Entity.Direction.Down);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(3, 2));
            assertEquals(simpleMap, r.toString());
        }
        try {
            m.moveEntity(e, Entity.Direction.Right);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(3, 2));
            assertEquals(simpleMap, r.toString());
        }
        try {
            m.moveEntity(e, Entity.Direction.Left);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(3, 2));
            assertEquals(simpleMap, r.toString());
        }
        try {
            m.moveEntity(e, Entity.Direction.Up);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(3, 2));
            assertEquals(simpleMap, r.toString());
        }
    }

    @Test
    public void test_model_move_entity_2() {
        //tests that model moves entities that are able to be moved (WALL)
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . * . \n" +
                        ". 4 + . . \n" +
                        "* * * . . \n" +
                        "1 . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity e = r.getEntityAt(3, 1);     //wall
        assertEquals("*", e.toString());
        try {
            m.moveEntity(e, Entity.Direction.Down);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(3, 1));
            assertEquals(simpleMap, r.toString());
        }
        try {
            m.moveEntity(e, Entity.Direction.Right);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(3, 1));
            assertEquals(simpleMap, r.toString());
        }
        try {
            m.moveEntity(e, Entity.Direction.Left);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(3, 1));
            assertEquals(simpleMap, r.toString());
        }
        try {
            m.moveEntity(e, Entity.Direction.Up);
            fail();
        } catch (Error error) {
            assertEquals(e, r.getEntityAt(3, 1));
            assertEquals(simpleMap, r.toString());
        }
    }

    @Test
    public void test_model_move_entity_3() {
        //tests that model moves entities that are nonexistent
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . * . \n" +
                        ". 4 + . . \n" +
                        "* * * . . \n" +
                        "1 . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity e = new Enemy(1, 5, 5, 5);
        assertFalse(r.containsEnemy(e));
        try {
            m.moveEntity(e, Entity.Direction.Down);
            fail();
        } catch (Error error) {
            assertFalse(r.containsEnemy(e));}
        try {
            m.moveEntity(e, Entity.Direction.Right);
            fail();
        } catch (Error error) {
            assertFalse(r.containsEnemy(e));}
        try {
            m.moveEntity(e, Entity.Direction.Left);
            fail();
        } catch (Error error) {
            assertFalse(r.containsEnemy(e));}
        try {
            m.moveEntity(e, Entity.Direction.Up);
            fail();
        } catch (Error error) {
            assertFalse(r.containsEnemy(e));}
    }

    @Test
    public void test_model_move_door_1() {
        //tests that when model moves player entity on to door and room is not cleared player is moved however
        //current room is not changed.
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * + . . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(2, 3);
        try {
            r.setRoomClearedTo(false);
            assertFalse(r.isRoomCleared());
            m.moveEntity(player, Entity.Direction.Down);
            fail();
        } catch (Error error) {
            assertEquals(m.getRoom("A"), m.getCurrentRoom());
            assertEquals(r, m.getCurrentRoom());
            assertEquals(player, r.getEntityAt(2, 4));
        }
    }

    @Test
    public void test_model_move_door_2() {
        //tests that when model moves player entity on to door the current room of the model is changed and player is
        //at new room start position
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * + . . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(2, 3);
        try {
            assertEquals(m.getRoom("A"), m.getCurrentRoom());
            m.moveEntity(player, Entity.Direction.Down);
            m.moveEntity(player, Entity.Direction.Down);
            assertEquals(m.getRoom("B"), m.getCurrentRoom());
            assertNotEquals(r, m.getCurrentRoom());
            assertEquals(player, m.getCurrentRoom().getDoorNamed("A").getEntity());
        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_move_door_3() {
        //tests that when model moves player entity on to door the current room of the model is changed and player is
        //at new room start position, testing that player moves normally after transport to new room
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * + . . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(2, 3);
        try {
            r.setRoomClearedTo(true);
            assertEquals(m.getRoom("A"), m.getCurrentRoom());
            m.moveEntity(player, Entity.Direction.Down);
            m.moveEntity(player, Entity.Direction.Down);
            assertEquals(m.getRoom("B"), m.getCurrentRoom());
            assertNotEquals(r, m.getCurrentRoom());
            assertEquals(player, m.getCurrentRoom().getDoorNamed("A").getEntity());
            m.moveEntity(player, Entity.Direction.Right);
        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_move_door_4() {
        //tests that when model moves player entity on to door the current room of the model is changed and player is
        //at new room start position, testing that player moves normally after transport to new room and then returns
        //back to room player originated from
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * + . . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(2, 3);
        try {
            r.setRoomClearedTo(true);
            assertEquals(m.getRoom("A"), m.getCurrentRoom());
            m.moveEntity(player, Entity.Direction.Down);
            m.moveEntity(player, Entity.Direction.Down);
            assertEquals(m.getRoom("B"), m.getCurrentRoom());
            assertNotEquals(r, m.getCurrentRoom());
            assertEquals(player, m.getCurrentRoom().getDoorNamed("A").getEntity());
            m.moveEntity(player, Entity.Direction.Right);//moves to the right and then back to door tile to teleport back
            m.getCurrentRoom().setRoomClearedTo(true);  //must set room to cleared
            m.moveEntity(player, Entity.Direction.Left);
            m.moveEntity(player, Entity.Direction.Up);
            //room is now original room
            assertEquals(r, m.getCurrentRoom());
            //is at current room at door tile B
            assertEquals(player, m.getCurrentRoom().getDoorNamed("B").getEntity());
            assertTrue(m.getCurrentRoom().isRoomCleared());
        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_attack_1() {
        //tests player attack enemy NORTH, EAST, SOUTH and WEST
        //enemy should take damage
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . 3 . . \n" +
                        ". 6 + 9 . \n" +
                        "* * 1 . . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(2, 2);
        try {
            Entity enemy = r.getEntityAt(2, 3);
            m.checkAttack(player, Entity.Direction.Down);
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());     //should have a decrease in health
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
        try {
            Entity enemy = r.getEntityAt(2, 1);
            m.checkAttack(player, Entity.Direction.Up);       //attacks door
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());     //should have a decrease in health
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
        try {
            Entity enemy = r.getEntityAt(1, 2);
            m.checkAttack(player, Entity.Direction.Left);       //attacks door
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());     //should have a decrease in health
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
        try {
            Entity enemy = r.getEntityAt(3, 2);
            m.checkAttack(player, Entity.Direction.Right);       //attacks door
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());     //should have a decrease in health
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_attackAOE_1() {
        //tests player attack aoe
        //enemy should take damage if within block of player
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . 3 . . \n" +
                        ". 6 + 9 . \n" +
                        "* * 1 . . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(2, 2);
        try {
            Entity enemy = r.getEntityAt(2, 3);
            m.checkAttackAOE(player);
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());     //should have a decrease in health
            enemy = r.getEntityAt(2, 1);
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());
            enemy = r.getEntityAt(1, 2);
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());
            enemy = r.getEntityAt(3, 2);
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_attackAOE_2() {
        //tests player attack aoe
        //enemy should take damage if within block of player
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". 1 . 4 . \n" +
                        ". 6 + . . \n" +
                        "* * . 7 . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(2, 2);
        try {
            Entity enemy = r.getEntityAt(1, 1);
            m.checkAttackAOE(player);
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());     //should have a decrease in health
            enemy = r.getEntityAt(3, 1);
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());
            enemy = r.getEntityAt(1, 2);
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());
            enemy = r.getEntityAt(3, 3);
            assertTrue(enemy.getMaxHealth()>enemy.getHealth());
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_collect_1() {
        //checks that enemy does not collect the item(health up potion) when walking over item and item is destroyed
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . ` . \n" +
                        ". . . 4 . \n" +
                        ". . + . . \n" +
                        "* * . 7 . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity enemy = r.getEntityAt(3, 1);
        try {
            int startHP = enemy.getMaxHealth();
            m.moveEntity(enemy, Entity.Direction.Up);
            assertEquals(enemy, r.getEntityAt(3, 0));
            assertEquals(startHP, enemy.getMaxHealth());
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_collect_2() {
        //checks that enemy does not collect the item(attack up potion) when walking over item and item is destroyed
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . ~ . \n" +
                        ". . . 4 . \n" +
                        ". . + . . \n" +
                        "* * . 7 . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity enemy = r.getEntityAt(3, 1);
        try {
            int damage = enemy.getDamage();
            m.moveEntity(enemy, Entity.Direction.Up);
            assertEquals(enemy, r.getEntityAt(3, 0));
            assertEquals(damage, enemy.getDamage());
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_collect_3() {
        //checks that player collect the item(health up potion) when walking over item and stats are increased
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . ` . \n" +
                        ". . . + . \n" +
                        ". . 1 . . \n" +
                        "* * . 7 . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(3, 1);
        try {
            int startMaxHealth = player.getMaxHealth();
            m.moveEntity(player, Entity.Direction.Up);
            assertEquals(player, r.getEntityAt(3, 0));
            assertEquals(startMaxHealth+2, player.getMaxHealth());
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_collect_4() {
        //checks that player collect the item(power up potion) when walking over item and stats are increased
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . ~ . \n" +
                        ". . . + . \n" +
                        ". . 1 . . \n" +
                        "* * . 7 . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(3, 1);
        try {
            int startAttack = player.getDamage();
            m.moveEntity(player, Entity.Direction.Up);
            assertEquals(player, r.getEntityAt(3, 0));
            assertEquals(startAttack+1, player.getDamage());
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_teleport_1() {
        //checks that player moves to teleport exit when stepping on teleport entry
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". . -C . . \n" +
                        ". . + . . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . C \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * \n" +
                        "C 2\n" +
                        "5 5\n" +
                        ". . . . . \n" +
                        "B . = . . \n" +
                        ". . . . . \n" +
                        "* * . . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(2, 3);
        try {
            m.moveEntity(player, Entity.Direction.Up);
            assertEquals(m.getRoom("C"), m.getCurrentRoom());
            assertEquals(player, m.getCurrentRoom().getEntityAt(2, 1));
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_model_teleport_2() {
        //checks that player does not teleport when stepping onto teleport exit
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". . = . . \n" +
                        ". . + . . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . C \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * \n" +
                        "C 2\n" +
                        "5 5\n" +
                        ". . . . . \n" +
                        "B . -A . . \n" +
                        ". . . . . \n" +
                        "* * . . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Entity player = r.getEntityAt(2, 3);
        try {
            m.moveEntity(player, Entity.Direction.Up);
            assertNotEquals(m.getRoom("C"), m.getCurrentRoom());
            assertEquals(m.getRoom("A"), r);
            assertEquals(player, m.getCurrentRoom().getEntityAt(2, 2));
        } catch(GameError error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }
    @Test
    public void test_entity_1() {
        //Testing that player entity is initialised properly
        Model m = new Model();
        String simpleMap =
                "A 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * + . . \n" +
                        ". . B . * \n" +
                        "B 1\n" +
                        "5 5\n" +
                        "* * A . . \n" +
                        ". 2 . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        Scanner sc = new Scanner(simpleMap);
        m.read(sc);
        Room r = m.getCurrentRoom();
        Player player = (Player)r.getEntityAt(2, 3);
        try {
            assertTrue(player.getDamage()==1);
            assertTrue(player.canMove()==true);
            assertTrue(player.getHealth()==10&&player.getMaxHealth()==10);

        } catch (Error error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

}
