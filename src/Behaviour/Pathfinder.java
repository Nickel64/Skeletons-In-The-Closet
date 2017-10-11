package Behaviour;

import Map.Tile;

import java.util.LinkedList;
import java.util.Queue;

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
     * @param map
     *      2D array of Tile objects. Used to determine if the player needs to avoid an obsticle
     * @return
     *      Stack of int 2D arrays.
     *      Each element has format {x,y} which specifies a location on the board that the player
     *          should be at that point.
     *      NOTE: Also includes starting and ending positions
     */
    public static Queue<int[]> findPath(int[] pointA, int[] pointB, Tile[][] map) {
        //INPUT CHECKING
        if(pointA.equals(pointB)) return new LinkedList<>(); //Already at ending position
        if(pointA[0] < 0 || pointA[1] < 0 || pointB[0] < 0 || pointB[1] < 0) return new LinkedList<>(); //Position outside the left of board (negative)

        int sizeX = map.length - 1, sizeY = map[0].length - 1;
        if(pointA[0] > sizeX || pointA[1] > sizeY || pointB[0] > sizeX || pointB[1] > sizeY) return new LinkedList<>(); //position outside right of board

        //OUTPUT STACK
        //Stack<int[]> out = new Stack<>();
        Queue<int[]> out = new LinkedList<>();
        out.add(pointA);

        //FOR EMPTY TILE MAPS:
        if(emptyMap(map)) {
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
        }
        //FOR NON-EMPTY TILE MAPS (gonna have to avoid some stuff):
        else{
            int[] current = new int[]{pointA[0], pointA[1]}; //used to store position as moved
            System.out.println(current[0] + " " + current[1]);

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
        }

        return out;
    }

    private static boolean emptyMap(Tile[][] map){
        for(int i = 0; i < map.length; i++){
            for(int g = 0; g < map[0].length; g++){
                if(map[i][g] != null) return false;
            }
        }
        return true;
    }

}
