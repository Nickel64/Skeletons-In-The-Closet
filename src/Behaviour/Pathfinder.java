package Behaviour;

import Map.Room;
import Map.Tile;
import Utils.GameError;
import Utils.Resources;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
    public static Queue<int[]> findPath(int[] pointA, int[] pointB, Room room) {
        //INPUT CHECKING
        if(pointA.equals(pointB)) return new LinkedList<>(); //Already at ending position
        if(pointA[0] < 0 || pointA[1] < 0 || pointB[0] < 0 || pointB[1] < 0) return new LinkedList<>(); //Position outside the left of board (negative)

        //int sizeX = map.length - 1, sizeY = map[0].length - 1;
        int sizeX = room.getWidth(), sizeY = room.getHeight();
        if(pointA[0] > sizeX || pointA[1] > sizeY || pointB[0] > sizeX || pointB[1] > sizeY) return new LinkedList<>(); //position outside right of board

        //OUTPUT STACK
        //Stack<int[]> out = new Stack<>();
        Queue<int[]> out = new LinkedList<>();
        out.add(pointA);

        //FOR EMPTY TILE MAPS:
        if(emptyRoom(room)) {
            /**
            int[] current = new int[]{pointA[0], pointA[1]}; //used to store position as moved
            //System.out.println(current[0] + " " + current[1]);

            while (current[0] != pointB[0]) {
                if (pointA[0] - pointB[0] < 0) {
                    current[0] += 1;
                } else {
                    current[0] -= 1;
                }
                System.out.println(current[0] + " " + current[1]);
                out.add(new int[]{current[0], current[1]});
            }

            while (current[1] != pointB[1]) {
                if (pointA[1] - pointB[1] < 0) {
                    current[1] += 1;
                } else {
                    current[1] -= 1;
                }
                System.out.println(current[0] + " " + current[1]);
                out.add(new int[]{current[0], current[1]});
            }
             **/
        }
        //FOR NON-EMPTY TILE MAPS (gonna have to avoid some stuff):
        else{
            if(Resources.DEBUG) System.out.println("NON-EMPTY");
            int[] current = new int[]{pointA[0], pointA[1]}; //used to store position as moved

            Queue<int[]> path = newPathFind(pointA, pointB, room);
            return path;
        }

        //return out;
        return null;
    }

    public static Queue<int[]> newPathFind(int[] pointA, int[] pointB, Room room){
        Stack<int[]> path = new Stack<>();

        Tile[][] roomTiles = new Tile[room.getWidth()][room.getHeight()];
        boolean[][] roomBoolean = new boolean[room.getWidth()][room.getHeight()];
        for(int i = 0; i < room.getWidth(); i++){
            for(int g = 0; g < room.getHeight(); g++){
                roomTiles[i][g] = room.getTileAtLocation(i,g);
                roomBoolean[i][g] = false;
            }
        }

        boolean found = newPathFind(pointA[0], pointA[1], roomTiles, roomBoolean, pointB, path);

        if(found){
            //System.out.println("PATH:");
            Queue<int[]> out = new LinkedList<>();
            Stack<int[]> out2 = new Stack<>();
            while(!path.isEmpty()){
                int[] point = path.pop();
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

    public static boolean newPathFind(int row, int col, Tile[][] roomTiles, boolean[][] roomBoolean, int[] goal, Stack<int[]> path){
        //if already checked
        if(roomBoolean[row][col]) return false;

        ArrayList<int[]> neighbours = getNeighbours(roomTiles, row, col);

        if(containsTile(goal, neighbours)){
            path.add(new int[] {row,col});
            System.out.println("Goal found!");
            return true;
        }

        roomBoolean[row][col] = true;

        path.add(new int[] {row, col});

        System.out.println("Path added to x: " + row + " y:" + col + " Goal: x:" + goal[0] + " y:" + goal[1]);

        for(int[] neighbour: getNeighbours(roomTiles, row, col)){
            boolean found = newPathFind(neighbour[0], neighbour[1], roomTiles, roomBoolean, goal, path);
            if(found) return true;
        }

        path.pop();
        return false;
    }

    public static ArrayList<int[]> getNeighbours(Tile[][] roomTiles, int row, int col){
        ArrayList<int[]> out = new ArrayList<>();

        if(row-1 > 0 && roomTiles[row-1][col].getEntity().canStepOn()){ //left node row - 1, col
            out.add(new int[]{row-1, col});
        }

        if(row+1 < roomTiles.length && roomTiles[row+1][col].getEntity().canStepOn()){ //right node row + 1, col
            out.add(new int[]{row+1, col});
        }

        if(col-1 > 0 && roomTiles[row][col-1].getEntity().canStepOn()){ //top node row, col-1
            out.add(new int[]{row, col-1});
        }

        if(col+1 < roomTiles[0].length && roomTiles[row][col+1].getEntity().canStepOn()){ //bottom node row, col+1
            out.add(new int[]{row, col+1});
        }

        return out;
    }

    private static boolean containsTile(int[] goal, ArrayList<int[]> neighbours){
        for(int[] neighbour: neighbours){
            if(neighbour[0] == goal[0] && neighbour[1] == goal[1]) return true;
        }
        return false;
    }


    private static boolean emptyRoom(Room room){
        for(int i = 0; i < room.getWidth(); i++){
            for(int g = 0; g < room.getHeight(); g++){
                //if(map[i][g] != null) return false;
                if(room.getTileAtLocation(i,g) != null) return false;
            }
        }
        return true;
    }

}

