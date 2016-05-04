package game;

import java.util.HashMap;
import java.util.Map;

public class Move{
	private final int x;
	private final int y;

	private Map <Direction,Integer> flips = new HashMap<Direction,Integer>();
	
	public Move(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void pushFlip(Direction d, int numberOfFlips){
		flips.put(d, numberOfFlips);
	}
	
	public int getDirectionFlips(Direction d){
		if(flips.containsKey(d))
			return flips.get(d);
		else 
			return 0;
	}
	
	//Brittle
	public boolean validMove(){
		if(flips.isEmpty()){
			return false;
		}
		return true;
	}
	
	
	public boolean equalCoordinates(Move move){
		if(move.getX() == this.getX() && move.getY() == this.getY()){
			return true;
		}
		else{
			return false;
		}
	}

}
