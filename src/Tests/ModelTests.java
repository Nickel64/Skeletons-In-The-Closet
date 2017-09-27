package Tests;

import Map.Room;
import org.junit.Test;

import java.util.Scanner;
import Model.*;
import Entities.*;

import static org.junit.Assert.*;

/**
 * Created by nambalg on 27/09/17.
 */
public class ModelTests {

        @Test
        public void test_initialise_1() {
            //tests that model initialises rooms and entities properly
            //correct number of enemy entities made, room layout is the same to simpleMap
            Model m = new Model();
            String simpleMap =
                    "RoomA 1\n" +
                            "* * . . . \n" +
                            ". . . . 2 \n" +
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
    public void test_move_DOWN_1() {
        //tests that model moves entities to a given direction where tile in empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "* * . . . \n" +
                        ". . . . 2 \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
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
    public void test_move_DOWN_2() {
        //tests that model moves entities to a given direction where tile is not empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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
    public void test_move_DOWN_3() {
        //tests that model moves entities to a given direction where tile is non existent
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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
    public void test_move_UP_1() {
        //tests that model moves entities to a given direction where tile in empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "* * . . . \n" +
                        ". . . . 2 \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
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
    public void test_move_UP_2() {
        //tests that model moves entities to a given direction where tile is not empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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
    public void test_move_UP_3() {
        //tests that model moves entities to a given direction where tile is non existent
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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
    public void test_move_LEFT_1() {
        //tests that model moves entities to a given direction where tile in empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "* * . . . \n" +
                        ". 2 . . . \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
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
    public void test_move_LEFT_2() {
        //tests that model moves entities to a given direction where tile is not empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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
    public void test_move_LEFT_3() {
        //tests that model moves entities to a given direction where tile is non existent
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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
    public void test_move_RIGHT_1() {
        //tests that model moves entities to a given direction where tile in empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "* * . . . \n" +
                        ". 2 . . . \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
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
    public void test_move_RIGHT_2() {
        //tests that model moves entities to a given direction where tile is not empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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
    public void test_move_RIGHT_3() {
        //tests that model moves entities to a given direction where tile is non existent
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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
    public void test_move_entity_1() {
        //tests that model moves entities that are able to be moved (NOTHING)
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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
    public void test_move_entity_2() {
        //tests that model moves entities that are able to be moved (WALL)
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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
    public void test_move_entity_3() {
        //tests that model moves entities that are nonexistent
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
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

}
