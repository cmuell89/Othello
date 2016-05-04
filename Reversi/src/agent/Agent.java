package agent;

import game.Cell;
import game.Evaluator;
import game.Move;

public abstract class Agent {
	protected int boardSize;
	protected Evaluator eval;
	
	public Agent(int boardSize){
		this.boardSize = boardSize;
		eval = new Evaluator(boardSize);
	}
	
	public abstract void updateMoves(Cell[][] board);
	
	public abstract Move getMove();
	
	public abstract Cell getColor();

	public abstract int numberOfMoves();
	

	

}
