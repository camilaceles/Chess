package model;

import java.util.ArrayList;
import java.util.List;

class King extends Piece {
	King(int row, int col, PiecesColor color) {
		super(row, col, color);
	}
	
	boolean goTo(int destRow, int destCol) {
		if (validateMove(destRow, destCol)) {
			setPosition(destRow, destCol);
			return true;
		} else {
			return false;
		}
	}
	
	List<Integer> getPossibleMoves() {
		List<Integer> moves = new ArrayList<Integer>();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (validateMove(row, col)) {
					moves.add(row);
					moves.add(col);
				}
			}
		}
		return moves;
	}
	
	String getName() {
		if (getColor() == PiecesColor.WHITE)
			return "KING";
		else
			return "king";
	}
	
	PiecesEnum getCode() {
		if (getColor() == PiecesColor.WHITE)
			return PiecesEnum.WHITE_KING;
		else
			return PiecesEnum.BLACK_KING;
	}
	
	// Private methods
	
	private boolean validateMove(int destRow, int destCol) {
		Board board = Board.getBoard();
		int row = getRow();
		int col = getColumn();
		
		if (Math.abs(row - destRow) > 1 || Math.abs(col - destCol) > 1) 
			return false;
		
		Piece destPiece = board.getBoardPiece(destRow, destCol);
		if (destPiece != null)
			return destPiece.getColor() != getColor();
		return true;
	}
}
