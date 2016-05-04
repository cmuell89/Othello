package game;


import java.util.ArrayList;

public class Evaluator {
	char[][] stabilityScore;
	int boardSize;

	public Evaluator(int boardSize) {
		this.boardSize = boardSize;
	}

	// factor represent game stage
	private int[][] getBoardWeight(){
		int[][] b = new int[boardSize][boardSize];
		// Corners
		b[0][0] = 1000;
		b[0][boardSize - 1] = 1000;
		b[boardSize - 1][0] = 1000;
		b[boardSize - 1][boardSize - 1] = 1000;
		// x-squares
		b[0][1] = -80;
		b[1][0] = -80;
		b[0][boardSize - 2] = -80;
		b[1][boardSize - 1] = -80;
		b[boardSize - 2][0] = -80;
		b[boardSize - 1][1] = -80;
		b[boardSize - 2][boardSize - 1] = -80;
		b[boardSize - 1][boardSize - 2] = -80;
		// C-squares
		b[1][1] = -100;
		b[boardSize - 2][1] = -100;
		b[1][boardSize - 2] = -100;
		b[boardSize - 2][boardSize - 2] = -100;
		// edges
		for (int i = 2; i < boardSize - 2; i++) {
			b[i][0] = 50;
			b[i][boardSize - 1] = 50;
		}
		for (int j = 2; j < boardSize - 2; j++) {
			b[1][j] = 50;
			b[boardSize-2][j] = 50;
		}
		//inside edges
		for (int i = 2; i < boardSize - 3; i++) {
			b[i][2] = -20;
			b[i][boardSize - 3] = -20;
		}
		for (int j = 2; j < boardSize - 3; j++) {
			b[2][j] = -20;
			b[boardSize-3][j] = -20;
		}
		
		return b;

	}

	public double evalutationFunction(Cell currentPlayer,
			Cell[][] currentBoard, Cell[][] previousBoard, int sign) {
		double mobility = mobilityHeuristic(currentPlayer, currentBoard);
		double claimedCorners = cornerHeurisitc(currentPlayer,
				currentBoard);
		double cornerProximity = cornerProximity(currentBoard, currentPlayer);
		
		return mobility + claimedCorners;
		
	}
	
	private double cornerProximity(Cell[][] currentBoard, Cell currentPlayer){
		int current = 0;
		int opponent = 0;
		Cell opponentPlayer = nextPlayer(currentPlayer);
		if(currentBoard[0][0] == Cell.EMPTY)   {
			if(currentBoard[0][1] == currentPlayer) current++;
			else if(currentBoard[0][1] == opponentPlayer) opponent++;
			if(currentBoard[1][1] == currentPlayer) current++;
			else if(currentBoard[1][1] == opponentPlayer) opponent++;
			if(currentBoard[1][0] == currentPlayer) current++;
			else if(currentBoard[1][0] == opponentPlayer) opponent++;
		}
		if(currentBoard[0][boardSize-1] == Cell.EMPTY)   {
			if(currentBoard[0][boardSize - 2] == currentPlayer) current++;
			else if(currentBoard[0][boardSize - 2] == opponentPlayer) opponent++;
			if(currentBoard[1][boardSize - 2] == currentPlayer) current++;
			else if(currentBoard[1][boardSize - 2] == opponentPlayer) opponent++;
			if(currentBoard[1][boardSize - 1] == currentPlayer) current++;
			else if(currentBoard[1][boardSize - 1] == opponentPlayer) opponent++;
		}
		if(currentBoard[boardSize-1][0] == Cell.EMPTY)   {
			if(currentBoard[boardSize-1][1] == currentPlayer) current++;
			else if(currentBoard[boardSize-1][1] == opponentPlayer) opponent++;
			if(currentBoard[boardSize-2][1] == currentPlayer) current++;
			else if(currentBoard[boardSize-2][1] == opponentPlayer) opponent++;
			if(currentBoard[boardSize-2][0] == currentPlayer) current++;
			else if(currentBoard[boardSize-2][0] == opponentPlayer) opponent++;
		}
		if(currentBoard[boardSize-1][boardSize-1] == Cell.EMPTY)   {
			if(currentBoard[boardSize-2][boardSize-1] == currentPlayer) current++;
			else if(currentBoard[boardSize-2][boardSize-1] == opponentPlayer) opponent++;
			if(currentBoard[boardSize-2][boardSize-2] == currentPlayer) current++;
			else if(currentBoard[boardSize-2][boardSize-2] == opponentPlayer) opponent++;
			if(currentBoard[boardSize-1][boardSize-2] == currentPlayer) current++;
			else if(currentBoard[boardSize-1][boardSize-2] == opponentPlayer) opponent++;
		}
		
		return -12.5 * (current - opponent);
	}
	
	private double cornerHeurisitc(Cell currentPlayer,
			Cell[][] currentBoard) {
		int current = 0, opponent = 0;
	
			if (currentBoard != null) {
				if (currentBoard[0][0] == currentPlayer) {
					current++;
				}else{
					opponent++;
				}
				if (currentBoard[0][boardSize - 1] == currentPlayer) {
					current++;
				}else{
					opponent++;
				}
				if (currentBoard[boardSize - 1][0] == currentPlayer) {
					current++;
				}else{
					opponent++;
				}
				if (currentBoard[boardSize - 1][boardSize - 1] == currentPlayer) {
					current++;
				}else{
					opponent++;
				}
			}
			return 25 * (current - opponent);
	}

	private double mobilityHeuristic(Cell currentPlayer, Cell[][] currentBoard) {

		return calculateValidMoves(currentBoard, currentPlayer).size() - calculateValidMoves(currentBoard, nextPlayer(currentPlayer)).size();
	}

	public Cell[][] copyBoard(Cell[][] currentBoard) {
		Cell[][] boardCopy = new Cell[boardSize][];
		for (int i = 0; i < boardSize; i++) {
			Cell[] boardColumn = currentBoard[i];
			boardCopy[i] = new Cell[boardSize];
			System.arraycopy(boardColumn, 0, boardCopy[i], 0, boardSize);
		}
		return boardCopy;
	}

	public Cell[][] makeMove(Move move, Cell[][] board, Cell currentPlayer) {
		board[move.getX()][move.getY()] = currentPlayer;
		for (Direction d : Direction.values()) {
			int[] shift = getArrayShift(d);
			int x = move.getX();
			int y = move.getY();
			int flips = move.getDirectionFlips(d);
			if (flips > 0) {
				for (int i = 0; i < flips; i++) {
					x = x + shift[0];
					y = y + shift[1];
					board[x][y] = currentPlayer;
				}
			}
		}
		return board;
	}

	public ArrayList<Move> calculateValidMoves(Cell[][] board, Cell curr) {
		ArrayList<Move> moveList = new ArrayList<Move>();
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				Move move = new Move(i, j);
				for (Direction direction : Direction.values()) {
					if (board[i][j] == Cell.EMPTY) {
						int[] shift = getArrayShift(direction);
						int flipCount = directionCount(shift, i, j, curr,
								board, 0);
						if (flipCount > 0)
							move.pushFlip(direction, flipCount);
					}
				}
				if (move.validMove()) {
					moveList.add(move);
				}
			}
		}
		return moveList;
	}

	public int[] getArrayShift(Direction d) {
		int[] shift = new int[2];
		switch (d) {
		case NORTH: {
			shift[0] = 0;
			shift[1] = -1;
			break;
		}
		case SOUTH: {
			shift[0] = 0;
			shift[1] = 1;
			break;
		}
		case EAST: {
			shift[0] = 1;
			shift[1] = 0;
			break;
		}
		case WEST: {
			shift[0] = -1;
			shift[1] = 0;
			break;
		}
		case NORTHWEST: {
			shift[0] = -1;
			shift[1] = -1;
			break;
		}
		case SOUTHWEST: {
			shift[0] = -1;
			shift[1] = 1;
			break;
		}
		case NORTHEAST: {
			shift[0] = 1;
			shift[1] = -1;
			break;
		}
		case SOUTHEAST: {
			shift[0] = 1;
			shift[1] = 1;
			break;
		}
		}
		return shift;
	}

	// Private methods
	private int directionCount(int[] shift, int xCoordinate, int yCoordinate,
			Cell currentPlayer, Cell[][] board, int stepCount) {
		int curr = stepCount;
		int x = xCoordinate + shift[0];
		int y = yCoordinate + shift[1];
		if (inboundsCheck(x, y) && board[x][y] != currentPlayer
				&& board[x][y] != Cell.EMPTY) {
			curr++;
			curr = directionCount(shift, x, y, currentPlayer, board, curr);
		}
		if (inboundsCheck(x, y) && board[x][y] == Cell.EMPTY) {
			curr = -100;
		}
		if (!inboundsCheck(x, y)) {
			curr = -100;
		}
		return curr;
	}

	private boolean inboundsCheck(int x, int y) {
		if ((x >= 0) && (x < boardSize) && (y >= 0) && (y < boardSize)) {
			return true;
		} else {
			return false;
		}
	}

	public Cell nextPlayer(Cell player) {
		if (player == Cell.WHITE) {
			return Cell.BLACK;
		} else {
			return Cell.WHITE;
		}
	}
}
