package finalproject;

import finalproject.system.Tile;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
        super(start);
        generateGraph();
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
        this.g = new Graph(GraphTraversal.DFS(this.source));

        for(Tile tile : g.vertices) {
            for(Tile neighbor : tile.neighbors) {
                if(neighbor.isWalkable()) {
                    if(tile instanceof finalproject.tiles.MetroTile && neighbor instanceof finalproject.tiles.MetroTile) {
                        ((finalproject.tiles.MetroTile) tile).fixMetro(neighbor);
                        g.addEdge(tile, neighbor, ((finalproject.tiles.MetroTile) neighbor).metroTimeCost);
                    }
                    else
                        g.addEdge(tile, neighbor, neighbor.timeCost);
                }
            }
        }
    }


}
