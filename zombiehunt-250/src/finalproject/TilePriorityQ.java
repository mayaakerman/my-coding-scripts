package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;

public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	ArrayList<Tile> priorityQueue;
	int queueSize;



	// TODO level 3: implement the constructor for the priority queue
	public TilePriorityQ (ArrayList<Tile> vertices) {
		this.priorityQueue = new ArrayList<>();
		this.priorityQueue.add(null);
		this.queueSize = 0;

		for(Tile tile : vertices) {
			this.addToQueue(tile);
		}
	}

	private void addToQueue(Tile t) {
		this.queueSize ++;
		this.priorityQueue.add(t); // tile added to end of queue

		int index = this.priorityQueue.size() -1;
		int parent = index/2;
		while(index > 1 && this.priorityQueue.get(index).costEstimate < this.priorityQueue.get(parent).costEstimate) {
			swapElements(index, parent);
			index = parent;
			parent = index/2;
		}
	}
	/*

	private void upHeap(int index) {
		int parent;
		while(index > 1) {
			parent = index/2;
			if(this.priorityQueue.get(index).costEstimate < this.priorityQueue.get(parent).costEstimate) {
				swapElements(index, parent);
				index = parent;
			}
			else
				break;
		}
	}

	 */

	private void swapElements(int i1, int i2) {
		Tile tmp = this.priorityQueue.get(i1);
		this.priorityQueue.set(i1, this.priorityQueue.get(i2));
		this.priorityQueue.set(i2, tmp);
	}
	
	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {
		if(this.queueSize == 0)
			return null;
		Tile tmpTile = this.priorityQueue.get(1);
		this.priorityQueue.set(1, priorityQueue.get(this.priorityQueue.size() -1));
		this.priorityQueue.remove(this.priorityQueue.size()-1);
		this.queueSize--;
		downHeap(1, this.priorityQueue.size()-1);
		return tmpTile;
	}

	private void downHeap(int startIndex, int maxIndex) {
		int i = startIndex;

		while(2*i <= maxIndex) {
			int child = 2*i;

			if(child < maxIndex) {
				if(this.priorityQueue.get(child+1).costEstimate < this.priorityQueue.get(child).costEstimate) {
					child++;
				}
			}
			if(this.priorityQueue.get(child).costEstimate < this.priorityQueue.get(i).costEstimate) {
				swapElements(i, child);
				i = child;
			}
			else
				break;
		}
	}
	/*

	private void heapify() {
		for(int i = this.queueSize/2; i > 0; i++) {
			downHeap(i, this.queueSize);
		}

		for(int j = (this.queueSize/2)+1; j <= this.queueSize; j++) {
			upHeap(j);
		}
	}

	 */

	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		if(this.priorityQueue.contains(t)) {
			t.predecessor = newPred;
			t.costEstimate = newEstimate;

			this.priorityQueue.remove(t);
			this.addToQueue(t);
			/*
			int index = this.priorityQueue.indexOf(t);
			if(index > 1 && t.costEstimate < this.priorityQueue.get(index/2).costEstimate) {
				upHeap(index);
            }
			else if(2*index <= this.queueSize) {
				downHeap(index, this.queueSize);
			}
			 */
		}
	}
	
}
