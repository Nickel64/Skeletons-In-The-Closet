package Behaviour;

import Map.Room;
import Map.Tile;
import Utils.GameError;
import Utils.Resources;
import com.sun.org.apache.regexp.internal.RE;

import java.awt.*;
import java.util.*;

/**
 * Used to find the shortest path from pointA to pointB
 *
 * @author Morgan French-Stagg
 */
public class Pathfinder {

    /**
     * Used to find the shortest path from pointA to pointB
     * @param pointA
     *      A 2D array of ints specifying the X and Y coordinates of the starting position
     *      In format {x,y}
     * @param pointB
     *      A 2D array of ints specifying the X and Y coordinates of the ending position
     *      In format {x,y}
     * @param room
     *      Room of tiles
     * @return
     *      Stack of int 2D arrays.
     *      Each element has format {x,y} which specifies a location on the board that the player
     *          should be at that point.
     *      NOTE: Also includes starting and ending positions
     */
    public static Queue<Point> findPath(Point pointA, Point pointB, Room room) {
        //INPUT CHECKING
        if(pointA.equals(pointB)) return new LinkedList<>(); //Already at ending position
        if(pointA.x < 0 || pointA.y < 0 || pointB.x < 0 || pointB.y < 0) return new LinkedList<>(); //Position outside the left of board (negative)

        int sizeX = room.getWidth(), sizeY = room.getHeight();
        if(pointA.x > sizeX || pointA.y > sizeY || pointB.x > sizeX || pointB.y > sizeY) return new LinkedList<>(); //position outside right of board

        Queue<Point> out = new LinkedList<>();
        out.add(pointA);

        if(Resources.DEBUG) System.out.println("BEGINNING PATH FIND");

        Queue<Point> path = pathFind(pointA, pointB, room);
        return path;
    }

    public static Queue<Point> pathFind(Point pointA, Point pointB, Room room){
        Stack<Point> path = new Stack<>();

        Tile[][] roomTiles = new Tile[room.getWidth()][room.getHeight()];
        boolean[][] roomBoolean = new boolean[room.getWidth()][room.getHeight()];
        for(int i = 0; i < room.getWidth(); i++){
            for(int g = 0; g < room.getHeight(); g++){
                roomTiles[i][g] = room.getTileAtLocation(i,g);
                roomBoolean[i][g] = false;
            }
        }

        boolean found = pathFind(pointA, roomTiles, roomBoolean, pointB, path);

        if(found){
            //System.out.println("PATH:");
            Queue<Point> out = new LinkedList<>();
            Stack<Point> out2 = new Stack<>();
            while(!path.isEmpty()){
                Point point = path.pop();
                out2.add(point);
            }

            while(!out2.isEmpty()){
                out.add(out2.pop());
            }
            out.add(pointB);
          return out;
        }
        else{
            throw new GameError("Unable to find suitable path");
        }
    }

    public static boolean pathFind(Point start, Tile[][] roomTiles, boolean[][] roomBoolean, Point goal, Stack<Point> path){
        //if already checked
        int row = start.x, col = start.y;
        if(roomBoolean[row][col]) return false;

        ArrayList<Point> neighbours = getNeighbours(roomTiles, row, col);

        if(containsTile(goal, neighbours)){
            path.add(new Point(row,col));
            if(Resources.DEBUG) System.out.println("Goal found!");
            return true;
        }

        roomBoolean[row][col] = true;

        path.add(new Point(row,col));

        if(Resources.DEBUG) System.out.println("Path added to x: " + row + " y:" + col + " Goal: x:" + goal.x + " y:" + goal.y);

        for(Point neighbour: bestNeighbours(neighbours, goal)){
            boolean found = pathFind(neighbour, roomTiles, roomBoolean, goal, path);
            if(found) return true;
        }

        path.pop();
        return false;
    }

    public static ArrayList<Point> getNeighbours(Tile[][] roomTiles, int row, int col){
        ArrayList<Point> out = new ArrayList<>();

        if(row-1 > 0 && roomTiles[row-1][col].getEntity().canStepOn()){ //left node row - 1, col
            out.add(new Point(row-1, col));
        }

        if(row+1 < roomTiles.length && roomTiles[row+1][col].getEntity().canStepOn()){ //right node row + 1, col
            out.add(new Point(row+1, col));
        }

        if(col-1 > 0 && roomTiles[row][col-1].getEntity().canStepOn()){ //top node row, col-1
            out.add(new Point(row, col-1));
        }

        if(col+1 < roomTiles[0].length && roomTiles[row][col+1].getEntity().canStepOn()){ //bottom node row, col+1
            out.add(new Point(row, col+1));
        }

        return out;
    }

    private static boolean containsTile(Point goal, ArrayList<Point> neighbours){
        for(Point neighbour: neighbours){
            if(neighbour.x == goal.x && neighbour.y == goal.y) return true;
        }
        return false;
    }

    private static ArrayList<Point> bestNeighbours(ArrayList<Point> neighbours, Point goal){
        if(neighbours == null || goal == null){
            return null;
        }

        ArrayList<Point> newNeighbours = new ArrayList<>();
        newNeighbours.addAll(neighbours);

        for(Point test: newNeighbours){
            if(Resources.DEBUG) System.out.println("Unordered x:" + test.x + " y:" + test.y);
        }

        ArrayList<Point> output = new ArrayList<>();
        for(int i = 0; i < neighbours.size(); i++) {
            Point bestNode = newNeighbours.get(0);
            for (Point neighbour : newNeighbours) {
                double bestNodeDist = Math.sqrt(Math.pow(bestNode.x - goal.x, 2) + Math.pow(bestNode.y - goal.y, 2));
                double thisNodeDist = Math.sqrt(Math.pow(neighbour.x - goal.x, 2) + Math.pow(neighbour.y - goal.y, 2));

                if (thisNodeDist < bestNodeDist) {
                    bestNode = neighbour;
                }
            }
            output.add(bestNode);
            newNeighbours.remove(bestNode);
        }

        for(Point test: output){
            if(Resources.DEBUG) System.out.println("Ordered x:" + test.x + " y:" + test.y);
        }
        return output;
    }
}

