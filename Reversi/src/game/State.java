package game;

public class State {
	private Move move;
	private Cell[][] currentBoard;
	private Cell[][] previousBoard;

	public State() {
	}

	public Cell[][] getPreviousBoard() {
		return previousBoard;
	}

	public void setPreviousBoard(Cell[][] previousBoard) {
		this.previousBoard = previousBoard;
	}

	public Cell[][] getCurrentBoard() {
		return currentBoard;
	}

	public void setCurrentBoard(Cell[][] currentBoard) {
		this.currentBoard = currentBoard;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

}
