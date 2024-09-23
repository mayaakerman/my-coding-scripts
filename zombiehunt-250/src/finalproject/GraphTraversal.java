package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class GraphTraversal
{


	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) {
		ArrayList<Tile> path = new ArrayList<Tile>();
		LinkedList<Tile> queue = new LinkedList<>();
		ArrayList<Tile> visited = new ArrayList<>();
		Tile cur;

		visited.add(s);
		queue.addLast(s);
		while(!queue.isEmpty()) {
			cur = queue.removeFirst();
			path.add(cur);
			for(Tile neighbor : cur.neighbors) {
				if(!visited.contains(neighbor) && neighbor.isWalkable()) {
					visited.add(neighbor);
					queue.addLast(neighbor);
				}
			}
		}
		return path;
	}


	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {

		ArrayList<Tile> path = new ArrayList<Tile>();
		LinkedList<Tile> stack = new LinkedList<>();
		ArrayList<Tile> visited = new ArrayList<>();
		Tile cur;

		visited.add(s);
		stack.addLast(s);

		while(!stack.isEmpty()) {
			cur = stack.removeLast();
			path.add(cur);
			for(Tile neighbor : cur.neighbors) {
				if(!visited.contains(neighbor) && neighbor.isWalkable()) {
					visited.add(neighbor);
					stack.addLast(neighbor);
				}
			}
		}
		return path;
	}
}  