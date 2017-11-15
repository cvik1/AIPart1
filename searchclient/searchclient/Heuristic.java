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




		// Here's a chance to pre-process the static parts of the level.
		for (int i = 0; i < initialState.boxes.length; i++){
			for (int j = 0; j < initialState.boxes[0].length; j++){
				if (initialState.goals[i][j] > 0){
					globalGoalsX.add(i);
					globalGoalsY.add(j);
				}
				if (initialState.boxes[i][j] > 0){
					globalBoxesX.add(i);
					globalBoxesY.add(j);
				}
			}
		}


	}


	public int h(Node n) {

		int total = 0;
		int dex = 0;
		int dist;
		int min;

		ArrayList<Integer> goalsX = new ArrayList<Integer>(globalGoalsX);
		ArrayList<Integer> goalsY = new ArrayList<Integer>(globalGoalsY);
		ArrayList<Integer> boxesX = new ArrayList<Integer>(globalBoxesX);
		ArrayList<Integer> boxesY = new ArrayList<Integer>(globalBoxesY);



		int[] current = new int[2];
		current[0] = n.agentRow;
		current[1] = n.agentCol;

		boolean isBox = true;
		boolean isGoal = false;

		while(goalsX.size() > 0){
			if(isBox == true){
				min = Integer.MAX_VALUE;
				for(int i = 0; i < goalsX.size(); i ++){
					dist = manhattanDist(current[0], current[1], goalsX.get(i), goalsY.get(i));
					if(dist < min){
						min = dist;
						dex = i;
					}
				}
				total += min;
				current[0] = goalsX.remove(dex);
				current[1] = goalsY.remove(dex);
				isBox = false;
				isGoal = true;
			}
			else{
				min = Integer.MAX_VALUE;
				for(int i = 0; i < boxesX.size(); i ++){
					dist = manhattanDist(current[0], current[1], boxesX.get(i), boxesY.get(i));
					if(dist < min){
						min = dist;
						dex = i;
					}
				}
				total += min;
				current[0] = boxesX.remove(dex);
				current[1] = boxesY.remove(dex);
				isBox = true;
				isGoal = false;
			}

		}

		// System.err.print(n.agentCol);
		// System.err.print(" ");
		// System.err.println(n.agentRow);
		// System.err.println(total);




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
