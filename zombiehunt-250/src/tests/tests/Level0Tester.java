package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import finalproject.tiles.*;

public class Level0Tester {
    @Test
    public void plainTile() {
        PlainTile tile = new PlainTile();
        assertEquals(3.0, tile.distanceCost, "Distance cost should be corect");
        assertEquals(1.0, tile.timeCost, "Distance cost should be corect");
        assertEquals(0.0, tile.damageCost, "Distance cost should be corect");
    }

    @Test
    public void desertTile() {
        DesertTile tile = new DesertTile();
        assertEquals(2.0, tile.distanceCost, "Distance cost should be corect");
        assertEquals(6.0, tile.timeCost, "Distance cost should be corect");
        assertEquals(3.0, tile.damageCost, "Distance cost should be corect");
    }
    
    @Test
    public void mountainTile() {
        MountainTile tile = new MountainTile();
        assertEquals(100.0, tile.distanceCost, "Distance cost should be corect");
        assertEquals(100.0, tile.timeCost, "Distance cost should be corect");
        assertEquals(100.0, tile.damageCost, "Distance cost should be corect");
    }
    
    @Test
    public void facilityTile() {
        FacilityTile tile = new FacilityTile();
        assertEquals(1.0, tile.distanceCost, "Distance cost should be corect");
        assertEquals(2.0, tile.timeCost, "Distance cost should be corect");
        assertEquals(0.0, tile.damageCost, "Distance cost should be corect");
    }
    
    @Test
    public void metroTile() {
        MetroTile tile = new MetroTile();
        assertEquals(1.0, tile.distanceCost, "Distance cost should be corect");
        assertEquals(1.0, tile.timeCost, "Distance cost should be corect");
        assertEquals(2.0, tile.damageCost, "Distance cost should be corect");
    }
    
    @Test
    public void zombieRuinsTile() {
        ZombieInfectedRuinTile tile = new ZombieInfectedRuinTile();
        assertEquals(1.0, tile.distanceCost, "Distance cost should be corect");
        assertEquals(3.0, tile.timeCost, "Distance cost should be corect");
        assertEquals(5.0, tile.damageCost, "Distance cost should be corect");
    }
}
