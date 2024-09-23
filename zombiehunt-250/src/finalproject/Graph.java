package finalproject;

import java.util.ArrayList;
import java.util.HashSet;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {
	// TODO level 2: Add fields that can help you implement this data type
    ArrayList<Tile> vertices;
    ArrayList<Edge> edges;

    // TODO level 2: initialize and assign all variables inside the constructor
	public Graph(ArrayList<Tile> vertices) {
		this.vertices = new ArrayList<>(vertices);
        this.edges = new ArrayList<Edge>();
	}
	
    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight){
    	if(destination.isWalkable()) {
            Edge newEdge = new Edge(origin, destination, weight);
            this.edges.add(newEdge);
            origin.addNeighbor(destination);
            destination.addNeighbor(origin);
        }
    }
    
    // TODO level 2: return a list of all edges in the graph
	public ArrayList<Edge> getAllEdges() {
		return this.edges;
	}
  
	// TODO level 2: return list of tiles adjacent to t
	public ArrayList<Tile> getNeighbors(Tile t) {
    	ArrayList<Tile> neighbors = new ArrayList<>();

        for(Tile neighbor : t.neighbors) {
            for(Edge edge : this.edges) {
                if (edge.getStart().equals(t) && edge.getEnd().equals(neighbor) && !neighbors.contains(neighbor)) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }
	
	// TODO level 2: return total cost for the input path
	public double computePathCost(ArrayList<Tile> path) {
        double cost = 0;

        for(int i = 1; i < path.size(); i++) {
            for(Edge edge : this.edges) {
                if(path.get(i-1) == edge.origin && path.get(i) == edge.destination)
                    cost += edge.weight;
            }
        }
        return cost;
	}
	
   
    public static class Edge{
    	Tile origin;
    	Tile destination;
    	double weight;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d, double cost){
            this.origin = s;
            this.destination = d;
            this.weight = cost;
        }
        
        // TODO level 2: getter function 1
        public Tile getStart(){
            return this.origin;
        }

        
        // TODO level 2: getter function 2
        public Tile getEnd() {
            return this.destination;
        }
        
    }
    
}