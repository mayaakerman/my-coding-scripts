package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Test;

import finalproject.*;
import finalproject.system.Tile;
import finalproject.tiles.*;

public class Level3Tester {
    private ArrayList<Tile> getTiles(int n, double estimate) {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (int i = 0; i < n; i++) {
            Tile tile = new FacilityTile();
            tile.costEstimate = estimate == 0 ? (int) (Math.random() * 1000) / 100.0 : estimate;
            tiles.add(tile);
        }
        return tiles;
    }

    private ArrayList<Tile> getRandomTiles(int n) {
        return getTiles(n, 0);
    }

    @Test
    public void queueRemovesInCorrectOrder() {
        int n = 30;
        ArrayList<Tile> tiles = getRandomTiles(n);
        TilePriorityQ queue = new TilePriorityQ(tiles);
        double previous = queue.removeMin().costEstimate;
        for (int i = 0; i < n - 1; i++) {
            double estimate = queue.removeMin().costEstimate;
            assertTrue(previous <= estimate, "Remove min should remove in the correct order");
            previous = estimate;
        }
    }

    @Test
    public void updateKeysUpdatesTile() {
        FacilityTile tile = new FacilityTile();
        DesertTile predecessor = new DesertTile();
        double estimate = 10;
        ArrayList<Tile> list = new ArrayList<Tile>();
        list.add(tile);
        list.add(predecessor);
        TilePriorityQ queue = new TilePriorityQ(list);
        queue.updateKeys(tile, predecessor, estimate);
        assertEquals(predecessor, tile.predecessor, "Tile should now have the correct predecessor");
        assertEquals(tile.costEstimate, estimate, "Tile should now have the correct cost estimate");
    }

    @Test 
    public void updateKeysNotInQueue() {
        TilePriorityQ queue = new TilePriorityQ(getRandomTiles(30));
        FacilityTile tile = new FacilityTile();
        DesertTile predecessor = new DesertTile();
        double estimate = 10;
        queue.updateKeys(tile, predecessor, estimate);
        assertNotEquals(predecessor, tile.predecessor, "Predecessor should not be updated since it is not in the queue");
        assertNotEquals(tile.costEstimate, estimate, "Estimate should not be updated since it is not in the queue");
    }

    @Test 
    public void updateKeysMaintainsPriority() {
        int estimate = 10;
        ArrayList<Tile> tiles = getTiles(estimate * 2, estimate);
        TilePriorityQ queue = new TilePriorityQ(tiles);
        for (int i = 0; i < estimate; i++) {
            queue.updateKeys(tiles.get(i), tiles.get(i + 1), i);
        }
        for (int i = 0; i < estimate; i++) {
            assertEquals(queue.removeMin().costEstimate, (double) i, "Updating keys should maintain priority");
        }
    }
}
