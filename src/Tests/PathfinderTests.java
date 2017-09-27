package Tests;

import Behaviour.Pathfinder;
import org.junit.*;
import static org.junit.Assert.*;

import Map.*;

import java.util.Stack;

/**
 * Used to test the pathfinder algorithm and
 * ensure that it can find a short path between two points on a Board
 *
 * @author Morgan French-Stagg
 */
public class PathfinderTests {

    /**
     * Used to test that a path between a point and itself just returns itself (a.k.a no movement)
     */
    @Test
    public void testEmptyGridSuccess1(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{0,0}, grid);
        Stack<int[]> expected = createExpected(new int[][]{{0,0}});
        checkEmptyGrid(expected, path);
    }

    /**
     * Tests a path between 0,0 and 2,2 on an empty board
     */
    @Test
    public void testEmptyGridSuccess2(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{2,2}, grid);
        Stack<int[]> expected = createExpected(new int[][]{{0,0}, {1,0}, {2,0}, {2,1}, {2,2}});
        checkEmptyGrid(expected, path);
    }

    /**
     * Tests a path between 0,0 and 3,3 on an empty board
     */
    @Test
    public void testEmptyGridSuccess3(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{3,3}, grid);
        Stack<int[]> expected = createExpected(new int[][]{{0,0}, {1,0}, {2,0}, {3,0}, {3,1}, {3,2}, {3,3}});
        checkEmptyGrid(expected, path);
    }

    /**
     * Used to test that the pathfinder will not attempt to move into negative positions
     */
    @Test
    public void testEmptyGridFail1(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{-1,-1}, grid);
        //Stack<int[]> expected = createExpected(new int[][]{{0,0}, {1,0}, {2,0}, {3,0}, {3,1}, {3,2}, {3,3}});
        Stack<int[]> expected = new Stack<>();
        checkEmptyGrid(expected, path);
    }

    /**
     * Used to check that the pathfinder will not move into positions outside the right edge of the board
     */
    @Test
    public void testEmptyGridFail2(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{11,11}, grid);
        //Stack<int[]> expected = createExpected(new int[][]{{0,0}, {1,0}, {2,0}, {3,0}, {3,1}, {3,2}, {3,3}});
        Stack<int[]> expected = new Stack<>();
        checkEmptyGrid(expected, path);
    }

    /**
     * Used to check that the pathfinder will not move into positions outside the right edge of the board
     */
    @Test
    public void testEmptyGridFail3(){
        Tile[][] grid = new Tile[10][10];
        Stack<int[]> path = Pathfinder.findPath(new int[]{0,0}, new int[]{10,10}, grid);
        Stack<int[]> expected = new Stack<>();
        checkEmptyGrid(expected, path);
    }

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


}
