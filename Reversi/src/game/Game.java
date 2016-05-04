package game;

import java.util.Scanner;

import agent.Agent;
import agent.NegaMaxAgent;
import agent.Human;
//import agent.RandomAgent;

public class Game {
	private Board board;
	private Cell currentTurn;
	private Agent human;
	private Agent computer;

	public Game(int boardSize, Cell humanColor, Cell computerColor,
			Scanner in) {
		this.board = new Board(boardSize);
		this.human = new Human(humanColor, boardSize,in);
		this.computer = new NegaMaxAgent(computerColor, boardSize);
	}

	public void initilizeBoard() {
		currentTurn = Cell.BLACK;
		board.setStartState();
		board.printBoard();
		board.printRecentMove();
		System.out.println("Dark player (human) plays now");
		System.out.flush();
		printCurrentTurn();
		updateBlackPlayer();
		printScore();
	}

	public void blackMove() {
		if (human.getColor() == Cell.BLACK) {
			humanMove();
		} else {
			computerMove();
		}
		setCurrentTurn(Cell.WHITE);
	}

	public void whiteMove() {
		if (human.getColor() == Cell.WHITE) {
			humanMove();
		} else {
			computerMove();
		}
		setCurrentTurn(Cell.BLACK);

	}

	public void updateWhitePlayer() {
		if (human.getColor() == Cell.WHITE) {
			updateHumanMoves();
		} else {
			updateComputerMoves();
		}
	}

	public void updateBlackPlayer() {
		if (human.getColor() == Cell.BLACK) {
			updateHumanMoves();
		} else {
			updateComputerMoves();
		}
	}

	public boolean isGameOver() {
		if (human.numberOfMoves() == 0 && computer.numberOfMoves() == 0) {
			return true;
		}else if(board.calculateScore(Cell.WHITE) == 0 || board.calculateScore(Cell.BLACK) == 0){
			return true;
		}else{
			return false;
		}
	}
	
	public void printWinner(){
		int whiteScore = board.calculateScore(Cell.WHITE);
		int blackScore = board.calculateScore(Cell.BLACK);
		System.out.println("GAME OVER!");
		System.out.flush();
		if(whiteScore > blackScore){
			System.out.println("White is the winner!");
			System.out.flush();
		}else{
			System.out.println("Black is the winner!");
			System.out.flush();
		}
	}

	public void printGame() {
		board.printBoard();
		board.printRecentMove();
		printCurrentTurn();
		printScore();
	}
	
	private void computerMove() {
		if (computer.numberOfMoves() > 0) {
			board.makeMove(computer.getMove(), computer.getColor());
		}else{
			board.setRecentMove("-","-");
		}

	}

	private void humanMove() {
		if (human.numberOfMoves() > 0) {
			board.makeMove(human.getMove(), human.getColor());
		}else{
			board.setRecentMove("-","-");
		}
	}

	private void updateComputerMoves() {
		computer.updateMoves(board.getBoard());

	}

	private void updateHumanMoves() {
		human.updateMoves(board.getBoard());
	}

	private void setCurrentTurn(Cell currentTurn) {
		this.currentTurn = currentTurn;
	}
	
	private void printScore(){
		System.out.println("Score: Light " + board.calculateScore(Cell.WHITE)+" - Dark " + board.calculateScore(Cell.BLACK));
		System.out.flush();
		
	}
	
	private void printCurrentTurn(){
		String player;
		if(currentTurn == Cell.BLACK){
			if(human.getColor() == Cell.BLACK){
				player = "(human)";
			}else{
				player = "(COM)";
			}
			if(!isGameOver()){
				System.out.println("Dark player " + player +" plays now");
				System.out.flush();
			}
		}else{
			if(human.getColor() == Cell.WHITE){
				player = "(human)";
			}else{
				player = "(COM)";
			}
			if(!isGameOver()){
				System.out.println("White player " + player +" plays now");
				System.out.flush();
			}
		}
	}
	

}
