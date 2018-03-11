package othello.core;

/**
 * This is any position on the board
 * 
 * @author bikram
 */
public class Position {
	private final int _row;
	private final int _col;

	public Position(int row, int col) {
		_row = row;
		_col = col;
	}

	public int getRow() {
		return _row;
	}

	public int getCol() {
		return _col;
	}
}
