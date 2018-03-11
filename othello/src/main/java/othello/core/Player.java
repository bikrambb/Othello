package othello.core;

/**
 * Represents players and their color
 * 
 * @author bikram
 *
 */
public enum Player {
	PLAYERX('X'), PLAYERO('O');
	
	private final char _color;
	
	Player(char color){
		_color =color;
	}
	
	public char getColor(){
		return _color;
	}
}
