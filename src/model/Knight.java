package model;

import java.util.ArrayList;
import java.util.List;

class Knight extends Piece {
	Knight(int row, int col, PiecesColor color) {
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
	
	PiecesEnum getCode() {
		if (getColor() == PiecesColor.WHITE)
			return PiecesEnum.WHITE_KNIGHT;
		else
			return PiecesEnum.BLACK_KNIGHT;
	}
	
	// Private methods
	
	private boolean validateMove(int destRow, int destCol) {
		Board board = Board.getBoard();
		Piece destPiece = board.getBoardPiece(destRow, destCol);
		int row = getRow();
		int col = getColumn();
		
		if (Math.abs(row - destRow) == 2 && Math.abs(col - destCol) == 1) {
			if (destPiece != null)
				return destPiece.getColor() != getColor();
			else
				return true;
		}
		if (Math.abs(row - destRow) == 1 && Math.abs(col - destCol) == 2) {
			if (destPiece != null)
				return destPiece.getColor() != getColor();
			else
				return true;
		}
		return false;
	}
}
