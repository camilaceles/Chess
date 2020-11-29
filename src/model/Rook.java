package model;

import java.util.ArrayList;
import java.util.List;

class Rook extends Piece {
	boolean isFirstMove = true;
	
	Rook(int row, int col, PiecesColor color) {
		super(row, col, color);
	}
	
	@Override
	boolean goTo(int destRow, int destCol) {
		Board board = Board.getBoard();
		if (board.hypotheticalMove(this, destRow, destCol) && validateMove(destRow, destCol)) {
			this.isFirstMove = false;
			setPosition(destRow, destCol);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
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
	
	@Override
	List<Integer> getValidMoves() {
		Board board = Board.getBoard();
		List<Integer> moves = new ArrayList<Integer>();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (validateMove(row, col) && board.hypotheticalMove(this, row, col)) {
					moves.add(row);
					moves.add(col);
				}
			}
		}
		return moves;
	}

	@Override
	PiecesEnum getCode() {
		if (getColor() == PiecesColor.WHITE)
			return PiecesEnum.WHITE_ROOK;
		else
			return PiecesEnum.BLACK_ROOK;
	}
	
	@Override
	boolean isFirstMove() {
		return this.isFirstMove;
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
