package tests;

import model.*;

import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestKnight {
	Board board = Board.getBoard();
	
	@Test
	public void test1_jump() {
		int row = 7; int col = 6;
		List<Integer> moves = board.getPossibleMoves(row, col);
		assert moves.size() == 2*3;
		assert board.movePiece(row, col, 5, 5);
	}
	
	@Test
	public void test2_capture() {
		assert board.movePiece(5, 5, 3, 6);
		assert board.movePiece(3, 6, 1, 7);
		assert board.wasLastMoved(1, 7);
	}
}
