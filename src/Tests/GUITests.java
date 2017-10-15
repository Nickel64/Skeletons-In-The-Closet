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
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(2, 2);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_W);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_W);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_W);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_W);

            assertEquals(e, r.getEntityAt(2, 1));
            assertEquals(endMap, r.toString());

            v.dispose();

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
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(2, 2);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(5000);
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

            v.dispose();

            assertEquals(e, r.getEntityAt(2,0));
            assertEquals(endMap, r.toString());

        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }


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
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 0);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_S);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_S);
            robot.delay(500);
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

            v.dispose();

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
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 0);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_S);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_S);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_S);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_S);

            assertEquals(e, r.getEntityAt(3, 1));
            assertNotEquals(e, r.getEntityAt(3,0));

            robot.keyPress(KeyEvent.VK_DOWN);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_DOWN);

            assertEquals(e, r.getEntityAt(3,2));

            v.dispose();

            assertEquals(endMap, r.toString());

        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_GUIKeyboard_move_LEFT_1(){
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
                        "* * + . . \n" +
                        ". . . . . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 0);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_A);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_A);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_A);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_A);

            assertEquals(e, r.getEntityAt(2, 0));
            assertNotEquals(e, r.getEntityAt(3,0));
            assertEquals(endMap, r.toString());

            v.dispose();
        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_GUIKeyboard_move_LEFT_2(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . + . \n" +
                        ". . . . . \n" +
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
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 1);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_A);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_A);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_A);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_A);

            assertEquals(e, r.getEntityAt(2, 1));
            assertNotEquals(e, r.getEntityAt(3,1));
            assertEquals(endMap, r.toString());

            v.dispose();
        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_GUIKeyboard_move_RIGHT_1(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . + . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        String endMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . . + \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 1);
            assertEquals("+", e.toString());

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_D);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_D);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_D);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_D);

            assertEquals(e, r.getEntityAt(4, 1));
            assertNotEquals(e, r.getEntityAt(3,1));
            assertEquals(endMap, r.toString());

            v.dispose();
        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_GUIKeyboard_attack_1(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . + 5 \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 1);
            assertEquals("+", e.toString());

            assertEquals(r.getEnemies().size(), 1);

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(5000);

            for(int i = 0; i < 10; i++){
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.delay(500);
                robot.keyRelease(KeyEvent.VK_SPACE);
                robot.delay(500);
            }

            assertEquals(e, r.getEntityAt(3, 1));
            assertEquals(r.getEnemies().size(), 0);

            v.dispose();
        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_GUIKeyboard_attack_2(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . + 9 \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        "9 . . . * ";
        try {
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 1);
            assertEquals("+", e.toString());

            assertEquals(r.getEnemies().size(), 2);

            //create robot to attempt keyboard press
            Robot robot = new Robot();
            robot.delay(1000);

            for(int i = 0; i < 100; i++){
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.delay(5);
                robot.keyRelease(KeyEvent.VK_SPACE);
                robot.delay(50);
            }

            assertEquals(r.getEnemies().size(), 1);

            robot.keyPress(KeyEvent.VK_S);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_S);

            for(int i = 0; i < 100; i++){
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.delay(5);
                robot.keyRelease(KeyEvent.VK_SPACE);
                robot.delay(50);
            }

            assertEquals(e, r.getEntityAt(3, 1));
            assertEquals(r.getEnemies().size(), 0);

            v.dispose();
        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_GUIKeyboard_pauseMenu(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . + . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 1);
            assertEquals("+", e.toString());

            Robot robot = new Robot();
            robot.delay(1000);

            assertFalse(v.pauseMenuVisible);

            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.delay(50);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            robot.delay(100);

            assertTrue(v.pauseMenuVisible);

            v.dispose();
        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_GUIKeyboard_shieldOn(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . + . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 1);
            assertEquals("+", e.toString());

            Robot robot = new Robot();
            robot.delay(1000);

            assertFalse(m.getPlayer().isDefending());

            robot.keyPress(KeyEvent.VK_E);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_E);
            robot.delay(500);

            assertTrue(m.getPlayer().isDefending());

            robot.keyPress(KeyEvent.VK_E);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_E);
            robot.delay(500);

            assertFalse(m.getPlayer().isDefending());

            v.dispose();
        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

    @Test
    public void test_GUIKeyboard_AOEAttack(){
        Model m = new Model();
        String simpleMap =
                "RoomA 1\n" +
                        "5 5\n" +
                        "* * . . . \n" +
                        ". . . + . \n" +
                        ". . . . . \n" +
                        "* * * . . \n" +
                        ". . . . * ";
        try {
            m.initialise(simpleMap);
            View v = new View(m);

            Room r = m.getCurrentRoom();
            Entity e = r.getEntityAt(3, 1);
            assertEquals("+", e.toString());

            Robot robot = new Robot();
            robot.delay(1000);

            assertFalse(m.getPlayer().isPlayerAttackAoE());

            robot.keyPress(KeyEvent.VK_Q);
            robot.delay(10);
            robot.keyRelease(KeyEvent.VK_Q);
            robot.delay(50);
            assertTrue(m.getPlayer().isPlayerAttackAoE());


            v.dispose();
        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }
}
