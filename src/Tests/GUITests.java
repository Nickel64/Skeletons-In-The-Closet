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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class GUITests {
    @Test
    public void test_GUIKeyboard_move_up1(){
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
                        ". . + . 2 \n" +
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

            assertEquals(e, r.getEntityAt(2, 1));
            assertEquals(endMap, r.toString());

        } catch (Exception error) {
            error.printStackTrace();
            fail(error.getMessage());
        }
    }

}
