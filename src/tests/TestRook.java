package tests;

import model.*;

import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRook {
	Board board = Board.getBoard();
	
	@Test
	public void test1_blockedMove() {
		// Blocked rook
		int row = 7; int col = 0;
		List<Integer> moves = board.getPossibleMoves(row, col);
		assert moves.size() == 0;

		// Free rook
		col = 7;
		moves = board.getPossibleMoves(row, col);
		assert moves.size() == 2*1;
		
		assert board.movePiece(row, col, 7, 6);
		moves = board.getPossibleMoves(7, 6);
		assert moves.size() == 2*4;
	}
}
