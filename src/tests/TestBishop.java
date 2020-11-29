package tests;

import model.*;

import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestBishop {
	Board board = Board.getBoard();
	
	@Test
	public void test1_blockedMove() {
		// Blocked bishop
		int row = 0; int col = 5;
		List<Integer> moves = board.getValidMoves(row, col);
		assert moves.size() == 0;
		
		// Free bishop
		col = 2;
		moves = board.getValidMoves(row, col);
		assert moves.size() == 2*5;
		
		assert board.movePiece(6, 6, 4, 6); // moving pawn to block free bishop
		moves = board.getValidMoves(row, col);
		assert moves.size() == 2*4;
	}
	
	@Test
	public void test2_capture() {
		int row = 0; int col = 2;
		assert board.movePiece(row, col, 4, 6);
		assert board.wasLastMoved(4, 6);
	}
}
