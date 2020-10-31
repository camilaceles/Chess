package tests;

import model.*;

import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestQueen {
	Board board = Board.getBoard();
	
	@Test
	public void test1_move() {
		int row = 7, col = 3;
		List<Integer> moves = board.getPossibleMoves(row, col);
		
//		System.out.println(moves);
//		assert moves.size() == 2*5; // TODO (7,7) is an impossible move
		
		assert board.movePiece(1, 2, 2, 2); // Moving white pawn to free queen
		row = 0;
		moves = board.getPossibleMoves(row, col);
		assert moves.size() == 2*8;
		assert board.movePiece(row, col, 2, 1);
	}
}
