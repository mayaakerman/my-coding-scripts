package tests;

import static org.junit.jupiter.api.Assertions.*;
import static tests.PathTools.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import finalproject.*;
import finalproject.system.Tile;

public class Level6Tester {
    @Test
    public void pdfFastestMap2Example() {
        int[][] expectedPath = new int[][] {
            { 0, 0 },
            { 1, 0 },
            { 1, 1 },
            { 1, 2 },
            { 1, 3 },
            { 2, 3 },
            { 2, 4 },
            { 3, 4 },
            { 4, 4 }
        };
        Map map = new Map(Map.map2);
        PathFindingService pathfinder = new FastestPath(map.getStart());
        ArrayList<Tile> path = pathfinder.findPath(map.getStart());
        matchPath(map, expectedPath, path, PathfinderType.FASTEST);
    }

    @Test
    public void pdfWaypointExampleB() {
        int[][] wps = new int[][] { { 1, 4 }, { 3, 0 } };
        int[][] expectedPath = new int[][] {
            { 0, 0 },
            { 0, 1 },
            { 0, 2 },
            { 0, 3 },
            { 0, 4 },
            { 1, 4 },
            { 2, 4 },
            { 2, 3 },
            { 2, 2 },
            { 3, 2 },
            { 3, 1 },
            { 3, 0 },
            { 3, 1 },
            { 3, 2 },
            { 3, 3 },
            { 3, 4 },
            { 4, 4 }
        };
        Map map = new Map(Map.map2);
        PathFindingService pathfinder = new FastestPath(map.getStart());
        LinkedList<Tile> waypoints = map.getWaypoints(wps);
        ArrayList<Tile> path = pathfinder.findPath(map.getStart(), waypoints);
        matchPath(map, expectedPath, path, PathfinderType.FASTEST);
    }
}
