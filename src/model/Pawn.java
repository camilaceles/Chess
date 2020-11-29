package model;

import java.util.*;

class Pawn extends Piece { // TODO promotion
	boolean isFirstMove = true;
	boolean lastMoveDouble = false;

	Pawn(int row, int col, PiecesColor color) {
		super(row, col, color);
	}
	
	boolean goTo(int destRow, int destCol) {
		Board board = Board.getBoard();
		if (board.hypotheticalMove(this, destRow, destCol) && validateMove(destRow, destCol, true)) {
			isFirstMove = false;
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
				if (validateMove(row, col, false)) {
					moves.add(row);
					moves.add(col);
				}
			}
		}
		return moves;
	}

	List<Integer> getValidMoves() {
		Board board = Board.getBoard();
		List<Integer> moves = new ArrayList<Integer>();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (validateMove(row, col, false) && board.hypotheticalMove(this, row, col)) {
					moves.add(row);
					moves.add(col);
				}
			}
		}
		return moves;
	}
	
	boolean wasLastDouble() {
		return this.lastMoveDouble;
	}
	
	PiecesEnum getCode() {
		if (getColor() == PiecesColor.WHITE)
			return PiecesEnum.WHITE_PAWN;
		else
			return PiecesEnum.BLACK_PAWN;
	}

	// Private methods

	private boolean validateMove(int destRow, int destCol, boolean move) {
		Piece destPiece = Board.getBoard().getBoardPiece(destRow, destCol);
		boolean valid = false;

		// Check for en passant
		if (checkEnPassant(destRow, destCol)) {
			if (move) Board.getBoard().capturePiece(getRow(), destCol); // actual move, captures in en passant
			return true;
		}

		// Capture move
		if (destCol != getColumn()) {
			if (destPiece != null && (destCol == getColumn() - 1 || destCol == getColumn() + 1)) { // diagonal move
				if (((getColor() == PiecesColor.WHITE && destRow == getRow() + 1)
						|| (getColor() == PiecesColor.BLACK && destRow == getRow() - 1))
						&& destPiece.getColor() != getColor())
					valid = true;
			}
		} else {
			// Regular moves
			if (getColor() == PiecesColor.WHITE) { // moves upwards (row +)
				if (destRow == getRow() + 1) { // regular move
					valid = destPiece == null;
				} else if (destRow == getRow() + 2) { // double first move
					if (Board.getBoard().getBoardPiece(getRow() + 1, getColumn()) == null) {
						if (destPiece == null && isFirstMove) {
							lastMoveDouble = true;
							return true;
						}
					}
				}

			} else if (getColor() == PiecesColor.BLACK) { // moves downwards (row -)
				if (destRow == getRow() - 1) { // regular move
					valid = destPiece == null;
				} else if (destRow == getRow() - 2) { // double first move
					if (Board.getBoard().getBoardPiece(getRow() - 1, getColumn()) == null) {
						if (destPiece == null && isFirstMove) {
							lastMoveDouble = true;
							return true;
						}
					}
				}
			}
		}
		if (valid)
			lastMoveDouble = false;
		return valid;
	}

	private boolean checkEnPassant(int destRow, int destCol) {
		Board board = Board.getBoard();
		if (getColor() == PiecesColor.WHITE && getRow() == 4 && destRow == 5
				&& (destCol == getColumn() - 1 || destCol == getColumn() + 1)) {
			Piece target = board.getBoardPiece(getRow(), destCol);
			if (target!= null && target.getColor() == PiecesColor.BLACK && target.wasLastDouble()) {
				return true;
			}
		} else if (getColor() == PiecesColor.BLACK && getRow() == 3 && destRow == 2 
				&& (destCol == getColumn() - 1 || destCol == getColumn() + 1)) {
			Piece target = board.getBoardPiece(getRow(), destCol);
			if (target!= null &&target.getColor() == PiecesColor.WHITE && target.wasLastDouble()) {
				return true;
			}
		}
		return false;
	}
}
