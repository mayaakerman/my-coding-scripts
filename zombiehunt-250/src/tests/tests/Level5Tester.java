package tests;

import static org.junit.jupiter.api.Assertions.*;
import static tests.PathTools.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import finalproject.*;
import finalproject.system.Tile;

public class Level5Tester {
    @Test
    public void noWaypoints() {
        Map map = new Map(Map.map1);
        PathFindingService pathfinder = new ShortestPath(map.getStart());
        try {
            pathfinder.findPath(map.getStart(), new LinkedList<Tile>());
        } catch (Exception e) {
            fail("findPath(Tile start, LinkedList<Tile> waypoints) should be able to handle an empty waypoint list");
        }
    }

    //#region Consecutive waypoints
    @Test
    public void consecutiveWaypointsMap1() {
        testConsecutiveWaypoints(Map.map1, new int[][] { { 0, 1 }, { 2, 1 } });
    }

    @Test
    public void consecutiveWaypointsMap2() {
        testConsecutiveWaypoints(Map.map2, new int[][] { { 3, 0 } });
    }

    @Test
    public void consecutiveWaypointsMap4() {
        testConsecutiveWaypoints(Map.map4, new int[][] { { 8, 2 }, { 1, 3 }, { 3, 0 }, { 5, 4 } });
    }

    private void testConsecutiveWaypoints(char[][] charMap, int[][] wps) {
        Map map = new Map(charMap);
        LinkedList<Tile> waypoints = map.getWaypoints(wps);
        PathFindingService pathfinder = new ShortestPath(map.getStart());
        ArrayList<Tile> path = pathfinder.findPath(map.getStart(), waypoints);
        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(i).equals(path.get(i + 1))) {
                fail("Tile is the same as the previous");
            }
            assertTrue(path.get(i).neighbors.contains(path.get(i + 1)), "Each tile in the path should be consecutive");
        }
    }
    //#endregion

    @Test
    public void pdfWaypointExampleA() {
        int[][] wps = new int[][] { { 1, 4 }, { 3, 0 } };
        int[][] expectedPath = new int[][] {
            { 0, 0 },
            { 1, 0 },
            { 1, 1 },
            { 1, 2 },
            { 1, 3 },
            { 1, 4 },
            { 1, 3 },
            { 1, 2 },
            { 1, 1 },
            { 1, 0 },
            { 2, 0 },
            { 3, 0 },
            { 3, 1 },
            { 3, 2 },
            { 3, 3 },
            { 3, 4 },
            { 4, 4 }
        };
        Map map = new Map(Map.map2);
        PathFindingService pathfinder = new ShortestPath(map.getStart());
        LinkedList<Tile> waypoints = map.getWaypoints(wps);
        ArrayList<Tile> path = pathfinder.findPath(map.getStart(), waypoints);
        matchPath(map, expectedPath, path, PathfinderType.SHORTEST);
        assertEquals(expectedPath.length, path.size(), "Length of path should match length of shortest path");
    }
}
