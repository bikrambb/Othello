package othello;

import static org.junit.Assert.*;
import org.junit.Test;

import othello.core.Board;
import othello.core.Othello;
import othello.core.Player;
import othello.core.Position;

public class OthelloTest {
	
	@Test
	public void defaultBoard(){
		Board board = new Board();
		
		assertEquals( 'O', board.getPosition(3, 3));
		assertEquals( 'X', board.getPosition(3, 4));
		assertEquals( 'X', board.getPosition(4, 3));
		assertEquals( 'O', board.getPosition(4, 4));
	}

	@Test
	public void verifyBoardField(){
		Board board = new Board();
		
		Position pos = new Position(1, 2);
		board.setPosition(pos, Player.PLAYERX.getColor());
		
		assertEquals( Player.PLAYERX.getColor(), board.getPosition(1, 2));
	}
	
	@Test
	public void isPositionOnBoard(){
		Board board = new Board();
		
		Position validPosition = new Position(1, 2);
		assertTrue( board.isPositionOnBoard( validPosition ) );
		
		Position invalidPosition = new Position(10, 20);
		assertFalse( board.isPositionOnBoard( invalidPosition ) );
		
		invalidPosition = new Position(-1, 2);
		assertFalse( board.isPositionOnBoard( invalidPosition ) );
		
		invalidPosition = new Position(1, -2);
		assertFalse( board.isPositionOnBoard( invalidPosition ) );
		
		invalidPosition = new Position(-1, -2);
		assertFalse( board.isPositionOnBoard( invalidPosition ) );
		
		invalidPosition = new Position(1, 10);
		assertFalse( board.isPositionOnBoard( invalidPosition ) );
		
		invalidPosition = new Position(10, 1);
		assertFalse( board.isPositionOnBoard( invalidPosition ) );
	}

	@Test
	public void currentPlayerAtGameStart(){
		Othello othelloGame = new Othello();
		
		assertEquals( Player.PLAYERX, othelloGame.getCurrentPlayer() );
	}
	
	@Test
	public void isMovePossible(){
		Othello othelloGame = new Othello();
		
		assertTrue( othelloGame.isMovePossible( Player.PLAYERX ));
		assertTrue( othelloGame.isMovePossible( Player.PLAYERO ));
	}
	
	@Test
	public void isMoveLegal(){
		Othello othelloGame = new Othello();
		
		assertTrue( othelloGame.move( new Position(0,0)) == -1 );
		assertTrue( othelloGame.move( new Position(4,3)) == -1 );		
	}
	
	@Test
	public void checkSwitchPositions(){
		Othello othelloGame = new Othello();
		
		Position pos = new Position(3, 2);
		assertTrue( othelloGame.move(pos) == 0);
		
		assertEquals( Player.PLAYERX.getColor(), othelloGame.getBoardPosition( new Position(3, 3) ));
	}
}
