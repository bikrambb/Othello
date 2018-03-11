package othello.core;

import static othello.core.Board.*;
import static othello.core.Player.PLAYERO;
import static othello.core.Player.PLAYERX;

import java.util.ArrayList;
import java.util.List;

public class Othello {

	private Player _currentPlayer = Player.PLAYERX;
	private boolean _isGameInProgress = true;
	private final Board _board;
	private static final int[][] ADJ_POSITIONS = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 },
			{ 1, -1 }, { 1, 0 }, { 1, 1 } };

	public Othello() {
		_board = new Board();
		//checkBoardState();
	}

	public void checkBoardState() {
		if (!isMovePossible(PLAYERX)) {
			if (!isMovePossible(PLAYERO)) {
				_isGameInProgress = false;
			} else {
				_currentPlayer = PLAYERO;
			}
		}
	}

	public boolean isMovePossible(Player player) {
		return getPossibleMoves(player).size() > 0;
	}

	public List<Position> getPossibleMoves(Player player) {
		if (!_isGameInProgress) {
			throw new IllegalStateException("Game is not in progress");
		}

		List<Position> possibleMoves = new ArrayList<Position>();
		Position pos = null;
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				pos = new Position(x, y);
				if (isMovePositionValid(pos) && hasSwitchOptions(pos, player) ) {
					possibleMoves.add(pos);
				}
			}
		}

		return possibleMoves;
	}

	private boolean hasSwitchOptions(Position pos, Player player) {		
		return getSwitchOptions(pos, player) > 0;
	}

	public int move(Position pos) {
		if (!_isGameInProgress) {
			throw new IllegalStateException("Game is not in progress");
		}

		int returnCode = -1;

		if (isMovePositionValid(pos) && getSwitchOptions(pos, _currentPlayer) > 0) {
			int switchCount = 0;
			_board.setPosition(pos, _currentPlayer.getColor());
			for (int[] adjPosition : ADJ_POSITIONS) {
				switchCount = noOfSwitches(pos, _currentPlayer, adjPosition[0], adjPosition[1]);

				if (switchCount > 0) { // count and switch
					switchPositions(pos, _currentPlayer, adjPosition[0], adjPosition[1]);
				}
			}
			nextPlayer();

			if (!isMovePossible(_currentPlayer)) {
				if (isMovePossible(otherPlayer())) {// PASS
					nextPlayer();
					returnCode = 1;
				} else {
					setGameOver();
					returnCode = 2;
				}
			} else {
				returnCode = 0;
			}
		}
		return returnCode;
	}

	private boolean isMovePositionValid(Position pos) {
		boolean isMovePositionValid = false;

		if (isBoardSetAtPosition(pos)) {
			return isMovePositionValid;
		}

		Position adjPos = null;
		for (int[] adjPosition : ADJ_POSITIONS) {
			adjPos = new Position(pos.getRow() + adjPosition[0], pos.getCol() + adjPosition[1]);
			if (isBoardSetAtPosition(adjPos)) {
				isMovePositionValid = true;
				break;
			}
		}
		return isMovePositionValid;
	}

	private boolean isBoardSetAtPosition(Position pos) {
		if (_board.isPositionOnBoard(pos) && _board.getPosition(pos) != DEFAULT_FIELD)
			return true;

		return false;
	}

	public char getBoardPosition(Position pos) {
		if (_board.isPositionOnBoard(pos))
			return _board.getPosition(pos);

		throw new IndexOutOfBoundsException("Position not on board");
	}

	private int noOfSwitches(Position pos, Player player, int adjRow, int adjCol) {
		int switches = 0;
		int opponentCount = 0;

		for (int count = 1; pos.getRow() + count * adjRow >= 0 && 
							pos.getRow() + count * adjRow < SIZE && 
							pos.getCol() + count * adjCol >= 0 && 
							pos.getCol() + count * adjCol < SIZE; 
										count++) {
			char curColor = _board.getPosition(pos.getRow() + count * adjRow, pos.getCol() + count * adjCol);
			if (curColor == _currentPlayer.getColor()) {
				switches += opponentCount;
				break;
			} else if (curColor == DEFAULT_FIELD) {
				return 0;
			} else {
				opponentCount++;
			}
		}

		return switches;
	}

	private int getSwitchOptions(Position pos, Player player) {
		int switches = 0;

		for (int[] adjPosition : ADJ_POSITIONS) {
			switches += noOfSwitches(pos, player, adjPosition[0], adjPosition[1]);
		}

		return switches;
	}

	private void switchPositions(Position pos, Player player, int adjRow, int adjCol) {
		for (int count = 1;; count++) {
			if (_board.getPosition(pos.getRow() + count * adjRow, pos.getCol() + count * adjCol) == player.getColor()) {
				break;
			}
			_board.setPosition(pos.getRow() + count * adjRow, pos.getCol() + count * adjCol, player.getColor());
		}
	}

	public void setGameOver() {
		if (!_isGameInProgress) {
			throw new IllegalStateException("No active game is in progress");
		}

		_isGameInProgress = false;
	}

	public void nextPlayer() {
		_currentPlayer = _currentPlayer == PLAYERX ? PLAYERO : PLAYERX;
	}

	public Player otherPlayer() {
		return _currentPlayer == PLAYERX ? PLAYERO : PLAYERX;
	}

	public Player getCurrentPlayer() {
		return _currentPlayer;
	}

	public boolean isGameInProgress() {
		return _isGameInProgress;
	}

	public int[] getResult() {
		int xCount = 0;
		int oCount = 0;
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (_board.getPosition(x, y) == PLAYERX.getColor()) {
					xCount++;
				} else if (_board.getPosition(x, y) == PLAYERO.getColor()) {
					oCount++;
				}
			}
		}

		return new int[] { xCount, oCount };
	}

	public void printBoardState() {
		System.out.println(_board.toString());
	}
}
