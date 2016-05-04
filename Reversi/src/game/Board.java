package game;

public class Board {
	private int boardSize;
	private Cell[][] board;
	private Evaluator eval;
	private String[] recentMove = { "-", "-" };

	public Board(int boardSize) {
		this.boardSize = boardSize;
		this.eval = new Evaluator(boardSize);
		this.board = new Cell[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				this.board[i][j] = Cell.EMPTY;
			}
		}
	}

	public void setStartState() {
		int x = boardSize / 2 - 1;
		int y = boardSize / 2 - 1;
		this.board[x][y] = Cell.WHITE;
		this.board[x + 1][y] = Cell.BLACK;
		this.board[x][y + 1] = Cell.BLACK;
		this.board[x + 1][y + 1] = Cell.WHITE;

	}

	public Cell[][] getBoard() {
		return eval.copyBoard(board);
	}

	public void makeMove(Move move, Cell currentPlayer) {
		this.board = eval.makeMove(move, this.board, currentPlayer);
		setRecentMove(Character.toString(((char) (move.getX() + 97))),
				Integer.toString(move.getY() + 1));
	}

	public int calculateScore(Cell color) {
		int score = 0;
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (board[i][j] == color) {
					score++;
				}
			}
		}
		return score;
	}

	public void printBoard() {
		System.out.printf("%-3s", " ");
		System.out.flush();
		for (int i = 0; i < boardSize; i++) {
			char x = 'A';
			x += i;
			System.out.print("  " + x + " ");
			System.out.flush();
			if (i == boardSize - 1){
				System.out.println();
				System.out.flush();
			}
		}
		for (int i = 0; i < boardSize; i++) {
			if (i == 0){
				System.out.print("   ");
				System.out.flush();
			}
			System.out.print("+---");
			System.out.flush();
			if (i == boardSize - 1) {
				System.out.println('+');
				System.out.flush();
			}
		}
		for (int j = 0; j < boardSize; j++) {
			System.out.printf("%-2s", j + 1);
			System.out.flush();
			for (int i = 0; i < boardSize; i++) {
				if (i == 0){
					System.out.print(" ");
					System.out.flush();
				}
				System.out.print("| " + cellToASCII(board[i][j]) + " ");
				System.out.flush();
				if (i == boardSize - 1) {
					System.out.print('|');
					System.out.flush();
					System.out.println();
					System.out.flush();
				}
			}
			for (int i = 0; i < boardSize; i++) {
				if (i == 0){
					System.out.print("   ");
					System.out.flush();
				}
				System.out.print("+---");
				System.out.flush();
				if (i == boardSize - 1) {
					System.out.print('+');
					System.out.flush();
					System.out.println();
					System.out.flush();
				}
			}
		}
	}

	public void setRecentMove(String x, String y) {
		this.recentMove[0] = x;
		this.recentMove[1] = y;
	}

	public void printRecentMove() {
		System.out.println("Move played: " + recentMove[0] + recentMove[1]);
		System.out.flush();

	}

	private char cellToASCII(Cell cell) {
		char r = ' ';
		switch (cell) {
		case BLACK: {
			r = 'D';
			break;
		}
		case WHITE: {
			r = 'L';
			break;
		}
		case EMPTY: {
			r = ' ';
			break;
		}
		}
		return r;
	}
}
