package agent;

import java.util.ArrayList;
import java.util.Collections;


import game.Cell;
import game.Evaluator;
import game.Move;
import game.Node;
import game.State;

public class NegaMaxAgent extends Agent {
	private Cell player;
	private ArrayList<Move> moveList = new ArrayList<Move>();
	private Node bestMove = new Node();
	private Evaluator eval = new Evaluator(super.boardSize);
	private final int INFINITY = 1000000;

	public NegaMaxAgent(Cell player, int boardSize) {
		super(boardSize);
		this.player = player;
	}

	@Override
	public void updateMoves(Cell[][] board) {
		this.moveList = eval.calculateValidMoves(board, player);
		iterativeDeepening(System.currentTimeMillis(), board);
	}

	public void iterativeDeepening(long systemTime, Cell[][] board) {
		long currentTime = systemTime;
		int depth = 2;
		State startState = new State();
		startState.setCurrentBoard(eval.copyBoard(board));
		int limiter=1000;
		switch (this.boardSize) {
        case 4:  limiter = 10000;
                 break;
        case 6:  limiter = 6000;
                 break;
        case 8:  limiter = 3000;
                 break;
        case 10:  limiter = 1500;
                 break;
        case 12:  limiter = 1300;
                 break;
        case 14:  limiter = 1100;
                 break;
        case 16:  limiter = 900;
                 break;
        case 18:  limiter = 800;;
                 break;
        case 20:  limiter = 700;
                 break;
        case 22: limiter = 600;
                 break;
        case 24: limiter = 500;
                 break;
        case 26: limiter = 500;
                 break;
		}
		
		while (currentTime - systemTime < limiter && depth < 1000) {
			depth++;
			bestMove = (Negamax(startState, player, depth, 1, -INFINITY,
					INFINITY, this.eval));
			currentTime = System.currentTimeMillis();
			System.out.println(depth);
			
		}
	}

	private ArrayList<Move> moveOrder(State state, ArrayList<Move> moveList,
			Cell currentPlayer, int sign) {
		sign = -sign;
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (Move move : moveList) {
			Node tempNode = new Node();
			tempNode.setMove(move);
			State newState = new State();
			newState.setMove(move);
			newState.setCurrentBoard(eval.makeMove(move, eval.copyBoard(state.getCurrentBoard()), player));
			newState.setPreviousBoard(eval.copyBoard(state.getCurrentBoard()));
			tempNode.setMoveScore(eval.evalutationFunction(player, newState.getCurrentBoard(),
					newState.getPreviousBoard(), sign));
			nodes.add(tempNode);
		}
		ArrayList<Move> list = new ArrayList<Move>();
		Collections.sort(nodes);
		for(Node node : nodes){
			list.add(node.getMove());
		}
		return list;
	}

	private Node Negamax(State state, Cell player, int depth, int sign,
			double alpha, double beta, Evaluator eval) {
		ArrayList<Move> validMoves = new ArrayList<Move>();
		validMoves = eval.calculateValidMoves(state.getCurrentBoard(), player);
		validMoves = moveOrder(state, validMoves, player, sign);
		if (depth == 0 || validMoves.size() == 0) {
			Node leafNode = new Node();
			leafNode.setMoveScore(sign
					* eval.evalutationFunction(player, state.getCurrentBoard(),
							state.getPreviousBoard(), sign));
			return leafNode;
		}
		double bestValue = -INFINITY;
		Move bestMove = null;
		for (Move validMove : validMoves) {
			State newState = new State();
			newState.setMove(validMove);
			newState.setCurrentBoard(eval.makeMove(validMove, eval.copyBoard(state.getCurrentBoard()), player));
			newState.setPreviousBoard(eval.copyBoard(state.getCurrentBoard()));
			Node curr = Negamax(newState, eval.nextPlayer(player), depth - 1,
					-sign, -beta, -alpha, eval);
			double currentValue = curr.getMoveScore();
			if (currentValue > bestValue) {
				bestValue = currentValue;
				bestMove = validMove;
			}
			alpha = Math.max(alpha, currentValue);
			if (alpha >= beta) {
				
				break;
			}
		}
		Node bestNode = new Node();
		bestNode.setMove(bestMove);
		bestNode.setMoveScore(bestValue);
		return bestNode;

	}

	@Override
	public Move getMove() {
		return bestMove.getMove();
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
