package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
		generateGraph();
	}

	
	public void generateGraph() {
		// TODO Auto-generated method stub
		ArrayList<Tile> vertices = GraphTraversal.DFS(source);
		this.costGraph = new Graph(vertices);
		this.damageGraph = new Graph(vertices);
		this.aggregatedGraph = new Graph(vertices);

		for(Tile tile : vertices) {
			for (Tile neighbor : tile.neighbors) {
				if(neighbor.isWalkable()) {

					if (tile instanceof finalproject.tiles.MetroTile && neighbor instanceof finalproject.tiles.MetroTile) {
						((finalproject.tiles.MetroTile) tile).fixMetro(neighbor);
						costGraph.addEdge(tile, neighbor, ((MetroTile) neighbor).metroDistanceCost);
					}
					else {
						costGraph.addEdge(tile, neighbor, neighbor.distanceCost);
						damageGraph.addEdge(tile, neighbor, neighbor.damageCost);
						aggregatedGraph.addEdge(tile, neighbor, neighbor.damageCost);
					}
				}
			}
		}
	}

	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {

		super.g = costGraph;
		ArrayList<Tile> distPath = super.findPath(start, waypoints);
		double damCostDistPath = damageGraph.computePathCost(distPath);
		double distCostDistPath = costGraph.computePathCost(distPath);
		if(damCostDistPath < this.health)
			return distPath;

		super.g = damageGraph;
		ArrayList<Tile> damPath = super.findPath(start, waypoints);
		double damCostDamPath = damageGraph.computePathCost(damPath);
		double distCostDamPath = costGraph.computePathCost(damPath);
		if(damCostDamPath > this.health)
			return null;

		while(true) {
			double lambda = (costGraph.computePathCost(distPath) - costGraph.computePathCost(damPath))/(damageGraph.computePathCost(damPath) - damageGraph.computePathCost(distPath));

			for(Graph.Edge edge : aggregatedGraph.getAllEdges()) {


				if(edge.origin instanceof finalproject.tiles.MetroTile && edge.destination instanceof finalproject.tiles.MetroTile) {
					edge.weight = ((finalproject.tiles.MetroTile) edge.destination).metroDistanceCost + lambda*edge.destination.damageCost;
				}
				else {
					edge.weight = edge.destination.distanceCost + lambda*edge.destination.damageCost;
				}
			}

			super.g = aggregatedGraph;
			ArrayList<Tile> aggPath = super.findPath(start, waypoints);

			double aggCostAggPath = aggregatedGraph.computePathCost(aggPath);
			double damCostAggPath = damageGraph.computePathCost(aggPath);

			double aggCostDistPath = aggregatedGraph.computePathCost(distPath);

			if(aggCostAggPath == aggCostDistPath) {
				return damPath;
			}
			else if(damCostAggPath <= this.health) {
				damPath = aggPath;
			}
			else
				distPath = aggPath;
		}

	}

}
