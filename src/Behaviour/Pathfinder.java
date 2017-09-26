package Behaviour;

import Map.Tile;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/** Used to find shortest path from point a to point b
 * Created: 2017/09/21
 * Author: Morgan French-Stagg
 * * * * * * * * * * * * * * * * * * * * * * * * * * * */

public class Pathfinder {

    public static Stack<int[]> findPath(int[] pointA, int[] pointB, Tile[][] map) {
        if(pointA.equals(pointB)) return new Stack<>();

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
