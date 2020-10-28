package tests;

import model.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPawn {
	Board board = Board.getBoard();
	
	@Test
	@Order(1)
	public void testFirstMove() {
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
	@Order(2)
	public void testCapture() {
		assert board.movePiece(6, 4, 4, 4);
		assert board.movePiece(3, 3, 4, 4);
		assert board.wasLastMoved(4, 4);
	}
	
	@Test
	@Order(3)
	public void testEnPassant() {
		assert board.movePiece(6, 3, 4, 3);
		assert board.movePiece(4, 4, 5, 3);
		assert board.getBoardPiece(4, 3)==null;
	}
}
