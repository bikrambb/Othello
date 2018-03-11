package othello.game;

import java.util.Scanner;

import othello.core.Othello;
import othello.core.Position;

public class PlayOthello {
	private static Othello _othelloGame;
	
	public static void main(String... args){
		_othelloGame = new Othello();
		
		if( _othelloGame.isGameInProgress() ) {
			System.out.println( "Staring position" );
			_othelloGame.printBoardState();
			
			Scanner scanner = new Scanner( System.in );
			while( _othelloGame.isGameInProgress() ){
				System.out.print( "Player '" + _othelloGame.getCurrentPlayer().getColor() + "' move: ");
				move( scanner.nextLine() );
			}
		}
	}

	public static void move(String move) {
		if( move == null || move.length() != 2 )
			throw new IllegalArgumentException( "Moves are specified as coordinates. Column+row or row+column (e.g. 3d or d3)" );
		
		boolean isFirstCharDigit = Character.isDigit( move.charAt(0));
		int row = 0;
		int col = 0;
		if( isFirstCharDigit ){
			row = Character.getNumericValue( move.charAt(0)) - 1;
			col = move.charAt(1) -97;
		} else {
			row = Character.getNumericValue( move.charAt(1)) - 1;
			col = move.charAt(0) -97;
		}
		
		int status = _othelloGame.move( new Position(row, col));
		switch( status ){
		case -1:
			System.out.println( "Invalid move. Please try again.\n" );
			break;
		case 0:
			_othelloGame.printBoardState();
			break;
		case 1:
			System.out.println( "PASS" );
			break;
		case 2:
			System.out.println( "No further moves available" );
			int[] result = _othelloGame.getResult();
			if( result[0] > result[1] ){
				System.out.printf( "Player 'X' wins ( %d vs %d )", result[0] , result[1]);
			} else if( result[0] < result[1] ){
				System.out.printf( "Player 'O' wins ( %d vs %d )", result[1] , result[0]);
			} else {
				System.out.printf( "Tie game ( %d vs %d )", result[1] , result[0] );
			}
			break;
		}
	}

}
