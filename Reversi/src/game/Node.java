package game;

public class Node implements Comparable<Node> {

	private double moveScore;
	private Move move;

	public Node() {
	}

	public Move getMove() {
		return move;
	}
	
	public void setMove(Move move){
		this.move = move;
	}
	
	public void setMoveScore(double moveScore){
		this.moveScore = moveScore;
	}

	public double getMoveScore() {
		return moveScore;
	}
	
	@Override
	public int compareTo(Node moveNode) {
		if(this.moveScore > moveNode.getMoveScore()){
			return -1;
		}else if(this.moveScore < moveNode.getMoveScore()){
			return 1;
		}else{
		return 0;
		}
	}

}
