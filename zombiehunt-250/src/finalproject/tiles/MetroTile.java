package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class MetroTile extends Tile {
	public double metroTimeCost = 100;
	public double metroDistanceCost = 100;
	public double metroCommuteFactor = 0.2;
	
    //TODO level 0: finish constructor
    public MetroTile() {
        super(1,1,2);
        this.type = TileType.Metro;
    }
    
    // TODO level 7: updates the distance and time cost differently between metro tiles
    public void fixMetro(Tile node) {
        if(node instanceof MetroTile) {
            double manhattanDist = Math.abs(this.xCoord - node.xCoord) + Math.abs(this.yCoord - node.yCoord);
            this.metroTimeCost = manhattanDist*metroCommuteFactor;
            ((MetroTile) node).metroTimeCost = manhattanDist*metroCommuteFactor;

            this.metroDistanceCost = manhattanDist/metroCommuteFactor;
            ((MetroTile) node).metroDistanceCost = manhattanDist/metroCommuteFactor;
        }
    }
}
