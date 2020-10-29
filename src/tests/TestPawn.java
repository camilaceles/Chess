package tests;

import model.*;

import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPawn {
	Board board = Board.getBoard();
	
	@Test
	public void test1_firstMove() {
		int row = 1; int col = 3;
		List<Integer> moves = board.getPossibleMoves(row, col);
		int[] expected = {2, 3, 3, 3};
		
		assert expected.length == moves.size() ;
		for (int i=0; i<expected.length; i++) {
			assert (int)expected[i] == (int)moves.get(i) ;
		}
		
		assert board.movePiece(row, col, row+2, col);
		assert board.getBoardPiece(row+2, col)!=null;
	}
	
	@Test
	public void test2_capture() {
		assert board.movePiece(6, 4, 4, 4);
		assert board.movePiece(3, 3, 4, 4);
		assert board.wasLastMoved(4, 4);
	}
	
	@Test
	public void test3_enPassant() {
		assert board.movePiece(6, 3, 4, 3);
		assert board.movePiece(4, 4, 5, 3);
		assert board.getBoardPiece(4, 3)==null;
	}
}
