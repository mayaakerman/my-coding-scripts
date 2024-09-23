package assignment2;

import java.awt.Color;
import java.util.Random;
import java.util.Stack;

import assignment2.food.*;


public class Caterpillar {
	// All the fields have been declared public for testing purposes
	public Segment head;
	public Segment tail;
	public int length;
	public EvolutionStage stage;

	public Stack<Position> positionsPreviouslyOccupied;
	public int goal;
	public int turnsNeededToDigest;


	public static Random randNumGenerator = new Random(1);


	// Creates a Caterpillar with one Segment. It is up to students to decide how to implement this. 
	public Caterpillar(Position p, Color c, int goal) {

		Segment first = new Segment(p, c);
		this.head = first;
		this.tail = first;
		this.length = 1;

		this.positionsPreviouslyOccupied = new Stack<>();
		this.goal = goal;
		this.stage = EvolutionStage.FEEDING_STAGE;
	}

	public EvolutionStage getEvolutionStage() {
		return this.stage;
	}

	public Position getHeadPosition() {
		return this.head.position;
	}

	public int getLength() {
		return this.length;
	}


	// returns the color of the segment in position p. Returns null if such segment does not exist
	public Color getSegmentColor(Position p) {
		Segment tmp = this.head;

		for(int i = 0; i < this.length; i++) {
			if(tmp.position.equals(p)) {
				return tmp.color;
			}
			else
				tmp = tmp.next;
		}
		return null;
	}


	// shift all Segments to the previous Position while maintaining the old color
	public void move(Position p) {

		if(Position.getDistance(this.getHeadPosition(), p) != 1)
			throw new IllegalArgumentException();

		if(this.length > 2 ) {
			Segment tmp = this.head.next;
			for (int i = 0; i < this.length - 2; i++) {
				if (tmp.position.equals(p)) {
					this.stage = EvolutionStage.ENTANGLED;
					return;
				} else
					tmp = tmp.next;
			}
		}
		Segment tmp = head;
		Position newPos = p;
		Position tailPos = this.tail.position;

		for(int j = 0; j < this.length; j++) {
			Position tmpPos = tmp.position;
			tmp.position = newPos;
			newPos = tmpPos;
			tmp = tmp.next;
		}

		if(this.turnsNeededToDigest > 0) {
			int k = randNumGenerator.nextInt(5);
			Segment newSeg = new Segment(tailPos, GameColors.SEGMENT_COLORS[k]);
			this.tail.next = newSeg;
			this.tail = newSeg;
			this.length ++;
			this.turnsNeededToDigest--;
			if(this.length == this.goal) {
				this.stage = EvolutionStage.BUTTERFLY;
				return;
			}
			if(this.turnsNeededToDigest == 0)
				this.stage = EvolutionStage.FEEDING_STAGE;
		}
		else
			positionsPreviouslyOccupied.push(tailPos);
	}



	// a segment of the fruit's color is added at the end
	public void eat(Fruit f) {

		Color newColor = f.getColor();
		Position newPos = positionsPreviouslyOccupied.pop();
		Segment newSeg = new Segment(newPos, newColor);

		this.tail.next = newSeg;
		this.tail = newSeg;
		this.length++;

		// goal reached?
	}

	// the caterpillar moves one step backwards because of sourness
	public void eat(Pickle p) {
		Position lastPos = positionsPreviouslyOccupied.pop();

		Segment tmp = this.head;
		while(tmp != tail) {
			tmp.position = tmp.next.position;
			tmp = tmp.next;
		}
		tmp.position = lastPos;
	}


	// all the caterpillar's colors shuffles around
	public void eat(Lollipop lolly) {
		Color[] colors = new Color[this.length];
		Segment tmp = head;

		for(int i = 0; i < this.length; i++) {
			colors[i] = tmp.color;
			tmp = tmp.next;
		}

		for(int j = this.length-1; j > 0; j--) {
			int k = randNumGenerator.nextInt(j+1);
			Color swap = colors[j];
			colors[j] = colors[k];
			colors[k] = swap;
		}
		tmp = this.head;
		for(int i = 0; i < this.length; i++) {
			tmp.color = colors[i];
			tmp = tmp.next;
		}

	}

	// brain freeze!!
	// It reverses and its (new) head turns blue
	public void eat(IceCream gelato) {

		this.positionsPreviouslyOccupied.clear();

		if(this.length == 1) {
			this.head.color = GameColors.BLUE;
			return;
		}

		this.tail = this.head;
		Segment prev = null;
		Segment curr = this.head;
		Segment next = null;

		while(curr != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		this.head = prev;
		this.head.color = GameColors.BLUE;


	}

	// the caterpillar embodies a slide of Swiss cheese loosing half of its segments. 
	public void eat(SwissCheese cheese) {

		if(this.length == 1)
			return;

		Stack<Segment> tmpStack = new Stack<>();
		Segment currSeg = this.head;
		Segment nextSeg = this.head;
		int newLength = this.length/2 + this.length%2;

		for(int i = 0; i < newLength-1; i++) {
			currSeg = currSeg.next;
			nextSeg = nextSeg.next.next;
			currSeg.color = nextSeg.color;


		}

		currSeg = this.head;

		while(currSeg != null) {
			tmpStack.push(currSeg);
			currSeg = currSeg.next;
		}

		for(int i = 0; i < this.length - newLength; i++) {
			positionsPreviouslyOccupied.push(tmpStack.pop().position);
		}

		this.tail = tmpStack.pop();
		this.tail.next = null;
		this.length = newLength;
		//update length, tail, tail.next, positions occupied
	}


	public void eat(Cake cake) {

		this.stage = EvolutionStage.GROWING_STAGE;
		int leftOver = cake.getEnergyProvided();

		for(int i = 0; i < cake.getEnergyProvided() && !positionsPreviouslyOccupied.empty(); i++) {

			Position newPos = positionsPreviouslyOccupied.peek();
			if(this.getSegmentColor(newPos) == null) {

				positionsPreviouslyOccupied.pop();
				int k = randNumGenerator.nextInt(5);
				Segment newSeg = new Segment(newPos, GameColors.SEGMENT_COLORS[k]);
				this.tail.next = newSeg;
				this.tail = newSeg;
				this.length ++;
				leftOver --;

				if(this.length == this.goal) {
					this.stage = EvolutionStage.BUTTERFLY;
					return;
				}
			}
			else {	// there is a segment at this position
				break;
			}
		}
		if(leftOver != 0)
			this.turnsNeededToDigest = leftOver;
		else
			this.stage = EvolutionStage.FEEDING_STAGE;
	}



	// This nested class was declared public for testing purposes
	public class Segment {
		private Position position;
		private Color color;
		private Segment next;

		public Segment(Position p, Color c) {
			this.position = p;
			this.color = c;
		}

	}


	public String toString() {
		Segment s = this.head;
		String gus = "";
		while (s!=null) {
			String coloredPosition = GameColors.colorToANSIColor(s.color) + 
					s.position.toString() + GameColors.colorToANSIColor(Color.WHITE);
			gus = coloredPosition + " " + gus;
			s = s.next;
		}
		return gus;
	}

}