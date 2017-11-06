package searchclient;

import java.util.Comparator;

import searchclient.NotImplementedException;

public abstract class Heuristic implements Comparator<Node> {
	public Heuristic(Node initialState) {
		// Here's a chance to pre-process the static parts of the level.
		for (int i = 0; i < initialState.boxes.length; i++){
			for (int j = 0; j < initialState.boxes[0].length; j++){
				
					




			}
		}
	}

	public int h(Node n) {
		throw new NotImplementedException();
		int dx = n.agentRow
		int dy = n.agentCol - 




		//write a recursive method 
		//base case: compute straight line distance from start to box to goal
		//write a recursion to go to next box back to goal 
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
