package Behaviour;

import Entities.Player;
import Map.Room;
import Utils.Resources;

import java.awt.*;
import java.util.*;

/**
 * Used to find the shortest path from pointA to pointB
 *
 * Uses many ideas from https://stackoverflow.com/a/43851271
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
        int sizeX = room.getWidth(), sizeY = room.getHeight();
        if(pointA.equals(pointB)
                || pointA.x < 0 || pointA.y < 0 || pointB.x < 0 || pointB.y < 0
                || pointA.x > sizeX || pointA.y > sizeY || pointB.x > sizeX || pointB.y > sizeY)
            return new LinkedList<>(); //Already at ending position

        if(Resources.DEBUG) System.out.println("BEGINNING PATH FIND");

        Queue<Point> out = new LinkedList<>();
        Stack<Point> path = new Stack<>();
        boolean[][] roomBoolean = new boolean[room.getWidth()][room.getHeight()];

        if(pathFindHelper(pointA, pointB, path, roomBoolean, room)){
            out.addAll(path);
            out.add(pointB);
            return out;
        }
        else{
            return new LinkedList<>();
        }
    }

    /**
     * Used to help perform the shortest path algorithm
     *
     * @param start
     * @param goal
     * @param path
     * @param roomBoolean
     * @param room
     * @return
     */
    public static boolean pathFindHelper(Point start, Point goal, Stack<Point> path, boolean[][] roomBoolean, Room room){
        if(roomBoolean[start.x][start.y]) return false;

        ArrayList<Point> neighbours = getNeighbours(room, start, goal);

        //if the goal is a neighbour - then we're there!
        boolean neighbourContainsGoal = false;
        for(Point neighbour: neighbours) if(neighbour.x == goal.x && neighbour.y == goal.y) neighbourContainsGoal = true;
        if(neighbourContainsGoal){
            path.add(new Point(start.x,start.y));
            if(Resources.DEBUG) System.out.println("Goal found!");
            return true;
        }

        roomBoolean[start.x][start.y] = true;
        path.add(new Point(start.x,start.y));

        if(Resources.DEBUG) System.out.println("Path added to x: " + start.x + " y:" + start.y + " Goal: x:" + goal.x + " y:" + goal.y);

        for(Point neighbour: neighbours){
            boolean found = pathFindHelper(neighbour, goal, path, roomBoolean, room);
            if(found) return true;
        }

        path.pop();
        return false;
    }

    /**
     * Gets the neighbours for a node,
     * ordered in order of their priority (closeness to the goal node)
     * @param room
     * @param node
     * @param goal
     * @return
     */
    public static ArrayList<Point> getNeighbours(Room room, Point node, Point goal){
        ArrayList<Point> neighbours = new ArrayList<>();

        if(node.x-1 >= 0){ //left node row - 1, col
            if(room.getTileAtLocation(node.x-1, node.y).getEntity().canStepOn()|| room.getTileAtLocation(node.x-1, node.y).getEntity() instanceof Player) {
                neighbours.add(new Point(node.x - 1, node.y));
            }
        }

        if(node.x+1 < room.getWidth()){ //right node row + 1, col
            if(room.getTileAtLocation(node.x+1, node.y).getEntity().canStepOn() || room.getTileAtLocation(node.x+1, node.y).getEntity() instanceof Player){
                neighbours.add(new Point(node.x + 1, node.y));
            }
        }

        if(node.y-1 >= 0){ //top node row, col-1
            if(room.getTileAtLocation(node.x, node.y-1).getEntity().canStepOn() || room.getTileAtLocation(node.x, node.y-1).getEntity() instanceof Player){
                neighbours.add(new Point(node.x, node.y - 1));
            }
        }

        if(node.y+1 < room.getHeight()){ //bottom node row, col+1
            if(room.getTileAtLocation(node.x, node.y+1).getEntity().canStepOn() || room.getTileAtLocation(node.x, node.y+1).getEntity() instanceof Player){
                neighbours.add(new Point(node.x, node.y + 1));
            }
        }


        ArrayList<Point> output = new ArrayList<>();
        for(int i = 0; i < neighbours.size(); i++) {
            Point bestNode = neighbours.get(0);
            double bestNodeDist = Math.sqrt(Math.pow(bestNode.x - goal.x, 2) + Math.pow(bestNode.y - goal.y, 2));

            for (Point neighbour : neighbours) {
                double thisNodeDist = Math.sqrt(Math.pow(neighbour.x - goal.x, 2) + Math.pow(neighbour.y - goal.y, 2));
                if (thisNodeDist <= bestNodeDist) {bestNode = neighbour; bestNodeDist = thisNodeDist;}
            }

            output.add(bestNode);
            neighbours.remove(bestNode);
        }

        for(Point point: output){
            if(Resources.DEBUG) System.out.println(" x:" + point.x + " y:" + point.y);
        }

        //if(Resources.DEBUG) System.out.println("Best Node: x:" + output.get(0).x + " Y:" + output.get(0).y);

        return output;
    }

    public static Point findNextClosestPointToGoal(Room room, Point node, Point goal){
        Queue<Point> path = findPath(node, goal, room);
        while(!path.isEmpty() && path.peek().x == node.x && path.peek().y == node.y){
            Point p = path.poll();
        }
        Point nextStep = path.poll();
        return nextStep;
    }
}

