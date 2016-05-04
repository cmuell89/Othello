


import java.util.ArrayList;
import java.util.Scanner;

import game.*;
public class Reversi {
	public static void main(String args[]) {
		boolean l = true;
		int boardSize = 8;
		
		
		ArrayList<String> CLI = new ArrayList<String>();
		for(int i=0;i<args.length;i++){
			CLI.add(args[i]);
		}
		
		if(CLI.contains("-l")){
			l = true;
		}else{
			l = false;
		}
		
		if(CLI.contains("-n")){
			int argument = Integer.parseInt(CLI.get( CLI.indexOf("-n")+1));
			if(argument%2 == 0){
				boardSize = argument;
			}
		}
		
		Cell humanColor = Cell.BLACK;
		Cell computerColor = Cell.WHITE;
		Scanner in = new Scanner(System.in);
		if(l){
			humanColor = Cell.WHITE;
			computerColor = Cell.BLACK;
		}
		Game reversi = new Game(boardSize, humanColor, computerColor, in);
		System.out.println("Size: " + boardSize);
		System.out.flush();
		reversi.initilizeBoard();
		do{
			reversi.blackMove();
			reversi.printGame();
			System.out.flush();
			reversi.updateWhitePlayer();
			if(!reversi.isGameOver()){
				reversi.whiteMove();
				reversi.printGame();
				System.out.flush();
				reversi.updateBlackPlayer();
			}
		}while(!reversi.isGameOver());
		reversi.printWinner();
		System.out.flush();
		in.close();
		
		
	}
}
