package model;

import java.util.ArrayList;
import java.util.List;

class Bishop extends Piece {
	Bishop(int row, int col, PiecesColor color) {
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
			return PiecesEnum.WHITE_BSHP;
		else
			return PiecesEnum.BLACK_BSHP;
	}
	
	// Private methods
	
	private boolean validateMove(int destRow, int destCol) {
		Board board = Board.getBoard();
		int row = getRow();
		int col = getColumn();
		
		if (Math.abs(row - destRow) != Math.abs(col - destCol)) // must move diagonally
			return false;
		
		int incRow; if (destRow > row) incRow = 1; else incRow = -1;
		int incCol; if (destCol > col) incCol = 1; else incCol = -1;
		
		row += incRow; col += incCol;
		while (row != destRow) {
			if (!(row>=0 && row<8 && col>=0 && col<8)) // square out of bounds
				break;
			if (board.getBoardPiece(row, col) != null) {
				return false;
			}
			row += incRow; col += incCol;
		}
		Piece destPiece = board.getBoardPiece(destRow, destCol);
		if (destPiece != null)
			return destPiece.getColor() != getColor();
		return true;
	}
}
