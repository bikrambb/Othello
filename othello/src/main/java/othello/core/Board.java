package othello.core;

public class Board {
	public static final int SIZE = 8;
	public static final char DEFAULT_FIELD = '-';
	private char[][] _positions = null;
	public static final char[] COLS = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };

	public Board() {
		_positions = new char[SIZE][SIZE];
		init();
	}

	private void init() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				_positions[x][y] = DEFAULT_FIELD;
			}
		}
		_positions[3][3] = 'O';
		_positions[3][4] = 'X';
		_positions[4][3] = 'X';
		_positions[4][4] = 'O';
	}

	public char getPosition(int row, int col) {
		return _positions[row][col];
	}

	public void setPosition(int row, int col, char color) {
		_positions[row][col] = color;
	}

	public char getPosition(Position pos) {
		return getPosition(pos.getRow(), pos.getCol());
	}

	public void setPosition(Position pos, char color) {
		setPosition(pos.getRow(), pos.getCol(), color);
	}

	public boolean isPositionOnBoard(int row, int col) {
		if (row < 0 || col < 0 || row >= SIZE || col >= SIZE) {
			return false;
		}

		return true;
	}

	public boolean isPositionOnBoard(Position pos) {
		return isPositionOnBoard(pos.getRow(), pos.getCol());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < SIZE; x++) {
			sb.append(x + 1).append(" ");
			for (int y = 0; y < SIZE; y++) {
				sb.append(_positions[x][y]);
			}
			sb.append("\n");
		}
		sb.append("  ");
		for (char c : COLS) {
			sb.append(c);
		}
		sb.append("\n");
		return sb.toString();
	}
}
