package agent;

import game.Cell;
import game.Move;

import java.util.ArrayList;
import java.util.Scanner;


public class Human extends Agent {
	private Cell color;
	private ArrayList<Move> moveList = new ArrayList<Move>();
	private Scanner in;
	
	public Human(Cell color, int boardSize, Scanner in){
		super(boardSize);
		this.color = color;
		this.in = in;
	}

	
	@Override
	public Move getMove(){
		Move move = null;
		boolean valid = false;
		System.out.print("> ");
		System.out.flush();
		String inputString = in.nextLine();
		char[] input = inputString.toCharArray();
		while(!valid){
			if(move!=null && validMove(move)){
				valid=true;
				continue;
			}else{
				if(move!=null){
					move = null;
					System.out.println("You tried an invalid move \"" + inputString + "\", please try another move.");
					System.out.flush();
					System.out.print("> ");
					System.out.flush();
					inputString = in.nextLine();
					input = inputString.toCharArray();
					continue;
				}
			}
			if(input.length<2 || input.length>3){
				System.out.println("Error: Invalid format.");
				System.out.flush();
				System.out.print("> ");
				System.out.flush();
				inputString = in.nextLine();
				input = inputString.toCharArray();
				continue;
			}
			if(input.length==2 && Character.isLetter(input[0]) && Character.isDigit(input[1])){
				int x = (input[0]) - 97;
				int y = Character.getNumericValue(input[1]) - 1;
				move = new Move(x,y);
				continue;
			}else if(input.length==3 && Character.isLetter(input[0]) && Character.isDigit(input[1]) && Character.isDigit(input[2])){
				int x = (input[0]) - 97;
				int y = (Integer.parseInt(inputString.substring(1))-1);
				move = new Move(x,y);
				continue;
			}
			else{
				System.out.println("Error: You tried an invalid move \"" + inputString + "\", please try again.");
				System.out.flush();
				System.out.print("> ");
				System.out.flush();
				inputString = in.nextLine();
				input = inputString.toCharArray();
				continue;
			}
			
		}
		return this.getMoveWithSameCoordinates(move);
	}


	@Override
	public void updateMoves(Cell[][] board) {
		this.moveList = eval.calculateValidMoves(board, this.color);
		
	}

	@Override
	public Cell getColor() {
		return this.color;
	}


	public boolean validMove(Move testMove) {
		boolean valid = false;
		for(Move move : moveList){
			if(move.equalCoordinates(testMove)){
				valid = true;
			}
		}
		return valid;
	}
	
	public Move getMoveWithSameCoordinates(Move testMove) {
		for(Move move : moveList){
			if(move.equalCoordinates(testMove)){
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
