package searchclient;

import java.util.Comparator;
import java.util.*;
import java.lang.Math.*;
import static java.lang.Math.abs;

import searchclient.NotImplementedException;


public abstract class Heuristic implements Comparator<Node> {

	ArrayList<Integer> globalGoalsX;
	ArrayList<Integer> globalGoalsY;
	ArrayList<Integer> globalBoxesX;
	ArrayList<Integer> globalBoxesY;


	public Heuristic(Node initialState) {

		globalGoalsX = new ArrayList<Integer>();
		globalGoalsY = new ArrayList<Integer>();
		globalBoxesX = new ArrayList<Integer>();
		globalBoxesY = new ArrayList<Integer>();

		int dex = 0;
		int dist;
		int min;



		// Here's a chance to pre-process the static parts of the level.
		for (int i = 0; i < initialState.goals.length; i++){
			for (int j = 0; j < initialState.goals[0].length; j++){
				
				if (initialState.goals[i][j] > 0){
					globalGoalsX.add(i);
					globalGoalsY.add(j);
				}
			}
		
	}


	public int h(Node n) {

		int total = 0;
		int dex = 0;
		int dist = 0;
		int min = Integer.MAX_VALUE;
		boolean atGoal = false;

		// ArrayList<Integer> goalsX = new ArrayList<Integer>(globalGoalsX);
		// ArrayList<Integer> goalsY = new ArrayList<Integer>(globalGoalsY);
		ArrayList<Integer> boxesX = new ArrayList<Integer>();
		ArrayList<Integer> boxesY = new ArrayList<Integer>();

		for (int x = 0; x < n.boxes.length; x++){
			for(int y = 0; y < n.boxes[0].length; y++){
				if (n.boxes[x][y] > 0){
					boxesX.add(x);
					boxesY.add(y);	
				}
			}
		}
		
		for(int i = 0; i < boxesX.size(); i++){
			total += manhattanDist(boxesX.get(i), boxesY.get(i), globalGoalsX.get(i), globalGoalsY.get(i));
		}

		return total;
	}




	public int manhattanDist(int x1, int y1, int x2, int y2){
		int dx = abs(x2 - x1);
		int dy = abs(y2 - y1);

		return (dx + dy);

	}

	public abstract int f(Node n);

	@Override
	public int compare(Node n1, Node n2) {
		return this.f(n1) - this.f(n2);
	}

	public static class AStar extends Heuristic {
		public AStar(Node initialState) {
			super(initialState);
		}

		@Override
		public int f(Node n) {
			return n.g() + this.h(n);
		}

		@Override
		public String toString() {
			return "A* evaluation";
		}
	}

	public static class WeightedAStar extends Heuristic {
		private int W;

		public WeightedAStar(Node initialState, int W) {
			super(initialState);
			this.W = W;
		}

		@Override
		public int f(Node n) {
			return n.g() + this.W * this.h(n);
		}

		@Override
		public String toString() {
			return String.format("WA*(%d) evaluation", this.W);
		}
	}

	public static class Greedy extends Heuristic {
		public Greedy(Node initialState) {
			super(initialState);
		}

		@Override
		public int f(Node n) {
			return this.h(n);
		}

		@Override
		public String toString() {
			return "Greedy evaluation";
		}
	}
}
