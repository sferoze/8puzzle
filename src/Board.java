
public class Board {
	private final int[][] board;
	private final int N;
	
	public Board(int[][] blocks)
	{
		// construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
		this.board = blocks;
		this.N = blocks.length;
	}
	
	public int dimension()
	{
		// board dimension N
		return this.board.length;
	}
	
	public int hamming()
	{
		// number of blocks out of place
		int counter = 0;
		int goalNum = 1;
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				if (i == (this.N - 1) && j == (this.N - 1))
					break;
				if (this.board[i][j] == goalNum)
					counter++;
				goalNum++;
			}
		}
		int outOfPlace = ((this.N * this.N) -1) - counter;
		
		return outOfPlace;
	}
	
	public int manhattan() 
	{
		// sum of Manhattan distances between blocks and goal

		int total = 0;
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				int goalRow;
				int goalCol;
				if (this.board[i][j] != 0) {
					goalRow = (this.board[i][j] - 1) / this.N;
					goalCol = (this.board[i][j] - 1) % this.N;
					total += Math.abs(i - goalRow) + Math.abs(j - goalCol);
				}	
			}
		}
		
		return total;
	}
	
	public boolean isGoal() 
	{
		// is this board the goal board?
		int outOfPlace = this.hamming();
		if (outOfPlace == 0)
			return true;
		else
			return false;
	}
	
	private static Board copy(Board x)
	{
		int[][] twin = new int[x.N][x.N];
		
		for (int i = 0; i < x.N; i++) {
			for (int j = 0; j< x.N; j++) {
				twin[i][j] = x.board[i][j];
			}
		}
		
		Board boardCopy = new Board(twin);
		return boardCopy;
	}
	
	public Board twin() 
	{
		// a board obtained by exchanging two adjacent blocks in the same row
		int block1;
		int block2;
		
		int[][] twin = new int[this.N][this.N];
		int zeroRow = 0;
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j< this.N; j++) {
				if (this.board[i][j] == 0) {
					zeroRow = i;
				}
				twin[i][j] = this.board[i][j];
			}
		}
		if (zeroRow == (this.N - 1)) {
			block1 = this.board[zeroRow -1][0];
			block2 = this.board[zeroRow -1][1];
			twin[zeroRow - 1][0] = block2;
			twin[zeroRow - 1][1] = block1;
		} else {
			block1 = this.board[zeroRow + 1][0];
			block2 = this.board[zeroRow + 1][1];
			twin[zeroRow + 1][0] = block2;
			twin[zeroRow + 1][1] = block1;
		}
		
		Board twinBoard = new Board(twin);
		return twinBoard;
		
		
	}
	
	public boolean equals(Object y) 
	{	
		int counter = 0;
		if (!(y instanceof Board)) return false;
		Board yBoard = (Board)y;
		// does this board equal y?
		if (this.N != yBoard.N)
			return false;
		
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				if (this.board[i][j] == yBoard.board[i][j])
					counter++;
			}
		}
		
		if (counter == (this.N*this.N))
			return true;
		else
			return false;
	}
	
	public String toString()
	{
		// string representation of the board (in the output format specified below)
		String board = this.N + "\n";
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				String addNum = this.board[i][j] + " ";
				board = board.concat(addNum);
			}
			board = board.concat("\n");
		}
		return board;
	}
	
	public Iterable<Board> neighbors()
	{
		// all neighboring boards
		int x = 0;
		int y = 0;
		// Find out where the zero is
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				if (this.board[i][j] == 0) {
					x = i;
					y = j;
					break;
				}
			}
		}
		
		Queue<Board> aque = new Queue<Board>();
		
		try {
			Board newBoard = copy(this);
			newBoard.board[x][y] = this.board[x+1][y]; 
			newBoard.board[x+1][y] = 0;
			aque.enqueue(newBoard);
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		
		try {
			Board newBoard2 = copy(this);;
			newBoard2.board[x][y] = this.board[x-1][y]; 
			newBoard2.board[x-1][y] = 0;
			aque.enqueue(newBoard2);
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		
		try {
			Board newBoard3 = copy(this);
			newBoard3.board[x][y] = this.board[x][y + 1]; 
			newBoard3.board[x][y + 1] = 0;
			aque.enqueue(newBoard3);
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		
		try {
			Board newBoard4 = copy(this);
			newBoard4.board[x][y] = this.board[x][y - 1]; 
			newBoard4.board[x][y - 1] = 0;
			aque.enqueue(newBoard4);
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		
		
		return aque;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		int[][] arr = new int[3][3];
		arr[0][0] = 0;
		arr[0][1] = 1;
		arr[0][2] = 3;
		arr[1][0] = 4;
		arr[1][1] = 2;
		arr[1][2] = 5;
		arr[2][0] = 7;
		arr[2][1] = 8;
		arr[2][2] = 6;
		
		Board newB = new Board(arr);
		StdOut.println(newB.manhattan());
		*/
		//Iterable<Board> neighbors = newB.neighbors();
		//Iterator<Board> itr = neighbors.iterator();
		//StdOut.println(newB);
		//while (itr.hasNext()) {
		//	Board output = itr.next();
		//	if (output.equals(newB))
		//		StdOut.println(output);
		//}
		
		
		//System.out.println(newB2);
		 
	}

}
	

