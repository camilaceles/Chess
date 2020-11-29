package tests;

import model.*;

import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestKing {
	Board board = Board.getBoard();
	
	@Test
	public void test1_move() {
		int row = 7, col = 4;
		List<Integer> moves = board.getValidMoves(row, col);
		assert moves.size() == 2*2;
		
		row = 0;
		moves = board.getValidMoves(row, col);
		assert moves.size() == 2*2;
		
		assert board.movePiece(row, col, 0, 3);
		moves = board.getValidMoves(0, 3);
		assert moves.size() == 2*4;
	}
}
