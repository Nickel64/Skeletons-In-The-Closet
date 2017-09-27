package Behaviour;

import Map.Tile;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Used to find the shortest path from pointA to pointB
 *
 * @author Morgan French-Stagg
 */
public class Pathfinder {

    public static Stack<int[]> findPath(int[] pointA, int[] pointB, Tile[][] map) {
        if(pointA.equals(pointB)) return new Stack<>();
        if(pointA[0] < 0 || pointA[1] < 0 || pointB[0] < 0 || pointB[1] < 0) return new Stack<>();
        int sizeX = map.length - 1, sizeY = map[0].length - 1;
        if(pointA[0] > sizeX || pointA[1] > sizeY || pointB[0] > sizeX || pointB[1] > sizeY) return new Stack<>();

        Stack<int[]> out = new Stack<>();
        out.push(pointA);

        int[] current = new int[] {pointA[0], pointA[1]};
        System.out.println(current[0] + " " + current[1]);

        while(current[0] != pointB[0]){
            if(pointA[0] - pointB[0] < 0){
                current[0] += 1;
            }
            else{
                current[0] -= 1;
            }
            System.out.println(current[0] + " " + current[1]);
            out.push(new int[]{current[0], current[1]});
        }

        while(current[1] != pointB[1]){
            if(pointA[1] - pointB[1] < 0){
                current[1] += 1;
            }
            else{
                current[1] -= 1;
            }
            System.out.println(current[0] + " " + current[1]);
            out.push(new int[]{current[0], current[1]});
        }

        return out;
    }

}
