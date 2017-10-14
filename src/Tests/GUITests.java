package Tests;

import Entities.Entity;
import Map.Room;
import View.*;
import Model.Model;
import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.Stack;

import static org.junit.Assert.*;


public class GUITests {
    /**
    @Test
    public void test_GUIKeyboard_move_UP_1(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . + . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            SwingUtilities.invokeLater(() -> new View(m));

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(2, 2);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(3000);
            robot.keyPress(KeyEvent.VK_W);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_W);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_W);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_W);

            assertEquals(e, r.getEntityAt(2, 1));
            assertEquals(endMap, r.toString());

        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_GUIKeyboard_move_UP_2(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". . + . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * + . . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            SwingUtilities.invokeLater(() -> new View(m));

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(2, 2);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(3000);
            robot.keyPress(KeyEvent.VK_W);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_W);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_W);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_W);

            assertEquals(e, r.getEntityAt(2, 1));
            assertNotEquals(e, r.getEntityAt(2,2));
            //assertEquals(endMap, r.toString());

            robot.keyPress(KeyEvent.VK_UP);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_UP);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_UP);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_UP);

            assertEquals(e, r.getEntityAt(2,0));
            assertEquals(endMap, r.toString());



        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    /**
    @Test
    public void test_GUIKeyboard_move_DOWN_1(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . + . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". . . + . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            SwingUtilities.invokeLater(() -> new View(m));

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 0);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(3000);
            robot.keyPress(KeyEvent.VK_S);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_S);

            assertEquals(e, r.getEntityAt(3, 1));
            assertNotEquals(e, r.getEntityAt(3,0));

            robot.keyPress(KeyEvent.VK_DOWN);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_DOWN);

            assertEquals(e, r.getEntityAt(3,2));

            assertEquals(endMap, r.toString());

        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_GUIKeyboard_move_DOWN_2(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . + . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . . \n" +
                        ". . . + . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            //NOTE: CURRENTLY POINTLESS
            Scanner sc = new Scanner(simpleMap);
            m.read(sc);
            SwingUtilities.invokeLater(() -> new View(m));

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 0);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(3000);
            robot.keyPress(KeyEvent.VK_S);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_S);

            assertEquals(e, r.getEntityAt(3, 1));
            assertNotEquals(e, r.getEntityAt(3,0));

            robot.keyPress(KeyEvent.VK_DOWN);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_DOWN);

            assertEquals(e, r.getEntityAt(3,2));

            //robot.keyPress(KeyEvent.VK_KP_DOWN);
            //robot.delay(500);
            //robot.keyRelease(KeyEvent.VK_KP_DOWN);

            //assertEquals(e, r.getEntityAt(3,3));

            assertEquals(endMap, r.toString());

        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }
    */
}
