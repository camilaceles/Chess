package model;

import java.util.ArrayList;
import java.util.List;

class Rook extends Piece {
	Rook(int row, int col, Color color) {
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
		if (getColor() == Color.WHITE)
			return "ROOK";
		else
			return "rook";
	}
	
	// Private methods
	
	private boolean validateMove(int destRow, int destCol) {
		Board board = Board.getBoard();
		int row = getRow();
		int col = getColumn();
		
		if (row != destRow && col != destCol)
				return false; // rook cannot move diagonally
		
		if (col != destCol) { // moving horizontally
			int inc;
			if (destCol > col) inc = 1; else inc = -1;
			
			col += inc;
			while (col != destCol) {
				if (!(col>=0 && col<8)) // square out of bounds
					break;
				if (board.getBoardPiece(row, col) != null) {
					return false;
				}
				col += inc;
			}
			Piece destPiece = board.getBoardPiece(destRow, destCol);
			if (destPiece != null)
				return destPiece.getColor() != getColor();
			
		} else { // row != destRow, moving vertically
			int inc;
			if (destRow > row) inc = 1; else inc = -1;
			
			row += inc;
			while (row != destRow) {
				if (!(row>=0 && row<8)) // square out of bounds
					break;
				if (board.getBoardPiece(row, col) != null) {
					return false;
				}
				row += inc;
			}
			Piece destPiece = board.getBoardPiece(destRow, destCol);
			if (destPiece != null)
				return destPiece.getColor() != getColor();
		}
		return true;
	}
}
