package finalproject;


import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class ShortestPath extends PathFindingService {
    //TODO level 4: find distance prioritized path
    public ShortestPath(Tile start) {
        super(start);
        generateGraph();
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
        this.g = new Graph(GraphTraversal.DFS(source));

        for(Tile tile : g.vertices) {
            for(Tile neighbor : tile.neighbors) {
                if(neighbor.isWalkable()) {
                    if(tile instanceof finalproject.tiles.MetroTile && neighbor instanceof finalproject.tiles.MetroTile) {
                        ((finalproject.tiles.MetroTile) tile).fixMetro(neighbor);
                        g.addEdge(tile, neighbor, ((MetroTile) neighbor).metroDistanceCost);
                    }
                    else
                        g.addEdge(tile, neighbor, neighbor.distanceCost);
                }
            }
        }
	}
    
}