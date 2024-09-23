package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public abstract class PathFindingService {
	Tile source;
	Graph g;
	
	public PathFindingService(Tile start) {
    	this.source = start;
    }

	public abstract void generateGraph();
    
    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {
        initSingleSource(this.g.vertices, startNode);
        ArrayList<Tile> s = new ArrayList<>(); // tiles visited
        TilePriorityQ q = new TilePriorityQ(g.vertices);
        Tile u;

        while(!q.priorityQueue.isEmpty()) {
            u = q.removeMin();
            s.add(u);
            for(Tile vertex : g.getNeighbors(u)) {
                this.relax(u, vertex, q);
            }
            if(u.isDestination)
                break;
        }

        ArrayList<Tile> path = new ArrayList<>(s.size());
        Tile destination = s.get(s.size() - 1);
        path.add(0, destination);

        for(int i = 0; i < s.size()-1; i++) {
            if(destination.equals(startNode))
                break;
            path.add(0, destination.predecessor);
            destination = destination.predecessor;
        }
    	return path;
    }

    /*
    private ArrayList<Tile> dijkstra(Graph graph, Tile source) {
        initSingleSource(graph.vertices, source);
        ArrayList<Tile> s = new ArrayList<>(); // tiles visited
        TilePriorityQ q = new TilePriorityQ(graph.vertices);
        Tile u;

        while(!q.priorityQueue.isEmpty()) {
            u = q.removeMin();
            s.add(u);
            for(Tile vertex : graph.getNeighbors(u)) {
                this.relax(u, vertex, q);
            }
            if(u.isDestination)
                break;
        }
        return s;
    }

     */

    private void initSingleSource(ArrayList<Tile> vertices, Tile source) {
        for(Tile tile : vertices) {
            tile.costEstimate = Double.POSITIVE_INFINITY;
            tile.predecessor = null;
        }
        source.costEstimate = 0;
    }

    private void relax(Tile u, Tile v, TilePriorityQ queue) {
        double weight = 0;
        for(Graph.Edge edge : this.g.edges) {
            if(edge.origin.equals(u) && edge.destination.equals(v))
                weight = edge.weight;
        }
        if(v.costEstimate > u.costEstimate + weight) {
            queue.updateKeys(v, u, (u.costEstimate + weight));
        }
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        initSingleSource(this.g.vertices, start);
        ArrayList<Tile> s = new ArrayList<>(); // tiles visited
        TilePriorityQ q = new TilePriorityQ(g.vertices);
        Tile u;

        while(!q.priorityQueue.isEmpty()) {
            u = q.removeMin();
            s.add(u);
            if(u.equals(end))
                break;
            for(Tile vertex : g.getNeighbors(u)) {
                this.relax(u, vertex, q);
            }
        }

        ArrayList<Tile> path = new ArrayList<>(s.size());
        Tile destination = end;
        path.add(0, destination);

        for(int i = 0; i < s.size()-1; i++) {
            if(destination.equals(start))
                break;
            path.add(0, destination.predecessor);
            destination = destination.predecessor;
        }
        return path;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
        if(waypoints.isEmpty())
            return findPath(start);

        ArrayList<Tile> path = new ArrayList<>();
        path.add(start);

        // break up path into smaller paths separated by waypoints
        for(Tile waypoint : waypoints) {
            ArrayList<Tile> pathSeg = findPath(start, waypoint);
            //add tiles in pathSeg to main path, leave out the start tile
            for(Tile tile : pathSeg) {
                if(!tile.equals(start))
                    path.add(tile);
            }
            start = waypoint; //waypoint is now the new start for the next iteration
        }

        //path is almost complete - missing segment from last waypoint to destination
        Tile destination = null;
        for(Tile t : g.vertices) {
            if(t.isDestination)
                destination = t;
        }
        ArrayList<Tile> finalSeg = findPath(start, destination);
        for(Tile tile : finalSeg) {
            if(!tile.equals(start))
                path.add(tile);
        }
        return path;
    }
        
}

