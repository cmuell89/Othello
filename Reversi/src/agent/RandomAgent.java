package agent;

import game.Cell;
import game.Evaluator;
import game.Move;

import java.util.ArrayList;
import java.util.Random;

public class RandomAgent extends Agent {
	private Cell player;
	private ArrayList<Move> moveList = new ArrayList<Move>();
	private Evaluator eval = new Evaluator(super.boardSize);

	public RandomAgent(Cell player, int boardSize) {
		super(boardSize);
		this.player = player;
	}

	@Override
	public void updateMoves(Cell[][] board) {
		this.moveList = eval.calculateValidMoves(board, player);	
	}

	@Override
	public Move getMove() {
		Random rand = new Random();
		int moveIndex = rand.nextInt(moveList.size());
		return moveList.get(moveIndex);
	}

	@Override
	public Cell getColor() {
		return player;
	}

	public Move getMoveWithSameCoordinates(Move testMove) {
		for (Move move : moveList) {
			if (move.equalCoordinates(testMove)) {
				return move;
			}
		}
		return null;
	}

	@Override
	public int numberOfMoves() {
		return moveList.size();
	}
}
