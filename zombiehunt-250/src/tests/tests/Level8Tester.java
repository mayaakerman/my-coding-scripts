package tests;

import static tests.PathTools.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import finalproject.*;
import finalproject.system.Tile;

public class Level8Tester {
    @Test
    public void pdfExample() {
        Map map = new Map(Map.map4);
        int health = 27;
        PathFindingService pathfinder = new SafestShortestPath(map.getStart(), health);
        int[][] expected = new int[][] {
            {1, 1},
            {2, 1},
            {2, 2},
            {2, 3},
            {3, 3},
            {4, 3},
            {5, 3},
            {6, 3},
            {7, 3},
            {7, 4},
            {8, 4}
        };
        ArrayList<Tile> actual = pathfinder.findPath(map.getStart(), new LinkedList<Tile>());
        matchPath(map, expected, actual, PathfinderType.SAFEST_SHORTEST);
    }
}
