package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Test;

import finalproject.GraphTraversal;
import finalproject.system.Tile;

public class Level1Tester {
    //#region Size of graph traversals
    //#region Breadth first search
    @Test
    public void BFSNumberMap1() {
        testGraphTraversalNumberBFS(Map.map1);
    }

    @Test
    public void BFSNumberMap2() {
        testGraphTraversalNumberBFS(Map.map2);
    }

    @Test
    public void BFSNumberMap3() {
        testGraphTraversalNumberBFS(Map.map3);
    }

    @Test
    public void BFSNumberMap4() {
        testGraphTraversalNumberBFS(Map.map4);
    }
    //#endregion

    //#region Depth first search
    @Test
    public void DFSNumberMap1() {
        testGraphTraversalNumberDFS(Map.map1);
    }

    @Test
    public void DFSNumberMap2() {
        testGraphTraversalNumberDFS(Map.map2);
    }

    @Test
    public void DFSNumberMap3() {
        testGraphTraversalNumberDFS(Map.map3);
    }

    @Test
    public void DFSNumberMap4() {
        testGraphTraversalNumberDFS(Map.map4);
    }
    //#endregion

    //#region Graph traversal amount methods
    private void testGraphTraversalNumberBFS(char[][] map) {
        Map tileMap = new Map(map);
        assertEquals(tileMap.getNumberOfWalkableTiles(), GraphTraversal.BFS(tileMap.getStart()).size(), "Number of walkable tiles should match");
    }

    private void testGraphTraversalNumberDFS(char[][] map) {
        Map tileMap = new Map(map);
        assertEquals(tileMap.getNumberOfWalkableTiles(), GraphTraversal.DFS(tileMap.getStart()).size(), "Number of walkable tiles should match");
    }
    //#endregion
    //#endregion

    //#region Repeition of graph traversals
    //#region Breadth first search
    @Test
    public void BFSRepeatedMap1() {
        testForRepeatedTiles(GraphTraversal.BFS(new Map(Map.map1).getStart()));
    }

    @Test
    public void BFSRepeatedMap2() {
        testForRepeatedTiles(GraphTraversal.BFS(new Map(Map.map2).getStart()));
    }

    @Test
    public void BFSRepeatedMap3() {
        testForRepeatedTiles(GraphTraversal.BFS(new Map(Map.map3).getStart()));
    }

    @Test
    public void BFSRepeatedMap4() {
        testForRepeatedTiles(GraphTraversal.BFS(new Map(Map.map4).getStart()));
    }
    //#endregion

    //#region Depth first search
    @Test
    public void DFSRepeatedMap1() {
        testForRepeatedTiles(GraphTraversal.DFS(new Map(Map.map1).getStart()));
    }

    @Test
    public void DFSRepeatedMap2() {
        testForRepeatedTiles(GraphTraversal.DFS(new Map(Map.map2).getStart()));
    }

    @Test
    public void DFSRepeatedMap3() {
        testForRepeatedTiles(GraphTraversal.DFS(new Map(Map.map3).getStart()));
    }

    @Test
    public void DFSRepeatedMap4() {
        testForRepeatedTiles(GraphTraversal.DFS(new Map(Map.map4).getStart()));
    }
    //#endregion
    
    private void testForRepeatedTiles(ArrayList<Tile> tiles) {
        int repetitions = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = 0; j < tiles.size(); j++) {
                if (i != j && tiles.get(i).equals(tiles.get(j))) {
                    repetitions++;
                }
            }
        }
        assertEquals(0, repetitions, "There should be no repeated tiles");
    }
    //#endregion
}
