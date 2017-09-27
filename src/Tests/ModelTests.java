package Tests;

import Map.Room;
import org.junit.Test;

import java.util.Scanner;
import Model.*;
import Entities.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
            } catch (Error e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        }

    @Test
    public void test_move_1() {
        //tests that model moves entities to a given direction where tile in empty
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "* * . . .\n" +
                        ". . . . 2\n" +
                        ". . + . .\n" +
                        "* * * . .\n" +
                        ". . . . *";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(4, 1);
            assertEquals(2 + "", e.toString());
            m.moveEntity(e, Entity.Direction.Down);
            assertEquals(e, r.getEntityAt(4, 2));
        } catch (Error e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
