import java.util.Comparator;
import java.util.Iterator;


public class Solver {
	
	private Node solutionNode;
	private Node solutionNodeTwin;
	private MinPQ<Node> thePQ;
	private MinPQ<Node> thePQTwin;
	private boolean initialSolvable = false;
	private boolean initialSolvableTwin = false;
	private final static Comparator<Node> BOARD_ORDER = new BoardOrder();
	private Queue<Board> solutionBoards;
	private class Node {
        private Board theBoard;
        private Node previousNode;
        private int numberOfMovesMade;
        
        Node() {
        	this.numberOfMovesMade = 0;
        }
    }
	
	 private static class BoardOrder implements Comparator<Node>
	 {

		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			int a = o1.theBoard.manhattan()  + o1.numberOfMovesMade;
			int b = o2.theBoard.manhattan()  + o2.numberOfMovesMade;
			
			if (a < b)
				return -1;
			else if (a > b)
				return 1;
			else if (a == b)
				return 0;
			
			return -1;
		}
		 
	 }
	
	public Solver(Board initial) 
	{
		// find a solution to the initial board (using the A* algorithm)
		/*
		1. Create first Node from initial Board
		2. Add initial Node to priority queue
		3. Dequeue Node with least priority
		4. Get back an list of all neighbor boards for dequeued Node
		5. Add each new Node to priority queue with the Nodes previous board set to last dequeued board
		6. Repeat until dequeued board is equal to goal board
		*/
		solutionNode = new Node();
		solutionNodeTwin = new Node();
		
		solutionNode.theBoard = initial;
		solutionNodeTwin.theBoard = initial.twin();
		
		solutionNode.previousNode = null;
		solutionNodeTwin.previousNode = null;
		
		thePQ = new MinPQ<Node>(Solver.BOARD_ORDER);
		thePQTwin = new MinPQ<Node>(Solver.BOARD_ORDER);
		
		thePQ.insert(solutionNode);
		thePQTwin.insert(solutionNodeTwin);
		solutionBoards = new Queue<Board>();
		
		int counter = 0;
		while(!solutionNode.theBoard.isGoal() | !solutionNodeTwin.theBoard.isGoal()) {
			
			solutionNode = thePQ.delMin();
			solutionBoards.enqueue(solutionNode.theBoard);
			if (solutionNode.theBoard.isGoal()) {
				initialSolvable = true;
				break;
			} else {
				solutionNode.numberOfMovesMade++;
				Iterable<Board> neighborBoards = solutionNode.theBoard.neighbors();
				Iterator<Board> itr = neighborBoards.iterator();
				while(itr.hasNext()) {
					Node neighborNode = new Node();
					neighborNode.theBoard = itr.next();
					neighborNode.numberOfMovesMade = solutionNode.numberOfMovesMade;
					neighborNode.previousNode = solutionNode;
					if (counter == 0)
						thePQ.insert(neighborNode);
					else if (!neighborNode.theBoard.equals(solutionNode.previousNode.theBoard))
						thePQ.insert(neighborNode);
				}
			}
			
			solutionNodeTwin = thePQTwin.delMin();
			if (solutionNodeTwin.theBoard.isGoal()) {
				initialSolvableTwin = true;
				break;
			} else {
				solutionNodeTwin.numberOfMovesMade++;
				Iterable<Board> neighborBoardsTwin = solutionNodeTwin.theBoard.neighbors();
				Iterator<Board> itr2 = neighborBoardsTwin.iterator();
				while(itr2.hasNext()) {
					Node neighborNodeTwin = new Node();
					neighborNodeTwin.theBoard = itr2.next();
					neighborNodeTwin.numberOfMovesMade = solutionNodeTwin.numberOfMovesMade;
					neighborNodeTwin.previousNode = solutionNodeTwin;
					if (counter == 0)
						thePQTwin.insert(neighborNodeTwin);
					else if (!neighborNodeTwin.theBoard.equals(solutionNodeTwin.previousNode.theBoard))
							thePQTwin.insert(neighborNodeTwin);
				}
			} 
			counter++;
		}
	}
	
	public boolean isSolvable() 
	{
		// is the initial board solvable?
		if (initialSolvable)
			return true;
		else if (initialSolvableTwin)
			return false;
		else
			return false;
	}
	
	public int moves() 
	{
		// min number of moves to solve initial board; -1 if no solution
		if (initialSolvable)
			return solutionNode.numberOfMovesMade;
		else
			return -1;
	}
	
	public Iterable<Board> solution() 
	{
		// sequence of boards in a shortest solution; null if no solution

		if (initialSolvable) {
			return solutionBoards;
		} else
			return null;
		
	}
	
	public static void main(String[] args) {
		  // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);
	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }

	}

}
