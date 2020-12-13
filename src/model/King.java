package model;

import java.util.ArrayList;
import java.util.List;

class King extends Piece {
	boolean isFirstMove = true;
	
	King(int row, int col, PiecesColor color) {
		super(row, col, color);
	}
	
	@Override
	boolean goTo(int destRow, int destCol) {
		Board board = Board.getBoard();
		if (board.hypotheticalMove(this, destRow, destCol) && validateMove(destRow, destCol, true)) {
			this.isFirstMove = false;
			//setPosition(destRow, destCol);
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
				if (validateMove(row, col, false)) {
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
				if (validateMove(row, col, false) && board.hypotheticalMove(this, row, col)) {
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
			return PiecesEnum.WHITE_KING;
		else
			return PiecesEnum.BLACK_KING;
	}

	@Override
	boolean isFirstMove() {
		return this.isFirstMove;
	}
	
	// Private methods
	
	private boolean validateMove(int destRow, int destCol, boolean move) {
		Board board = Board.getBoard();
		int row = getRow();
		int col = getColumn();
		
		if (Math.abs(row - destRow) > 1 || Math.abs(col - destCol) > 1) 
			return checkCastling(destRow, destCol, move);
		
		Piece destPiece = board.getBoardPiece(destRow, destCol);
		if (destPiece != null && destPiece.getColor() == getColor())
			return false;
		
		if (move) {
			board.iMovePiece(getRow(), getColumn(), destRow, destCol);
			setPosition(destRow, destCol);
		}
		return true;
	}
	
	private boolean checkCastling(int destRow, int destCol, boolean move) {
		Board board = Board.getBoard();
		Piece rook = board.getBoardPiece(destRow, destCol);
		if (rook == null || !isFirstMove())
			return false;
		if ((rook.getCode() != PiecesEnum.WHITE_ROOK && rook.getCode() != PiecesEnum.BLACK_ROOK) || !rook.isFirstMove())
			return false;
		if (destRow != getRow())
			return false;
		
		if (destCol == 0) {
			if (board.getBoardPiece(destRow, 1) == null &&
					board.getBoardPiece(destRow, 2) == null &&
					board.getBoardPiece(destRow, 3) == null) {		
				if (move) {					
					board.iMovePiece(destRow, getColumn(), destRow, getColumn()-2);
					board.iMovePiece(destRow, destCol, destRow, destCol+3);
					setPosition(destRow, getColumn()-2);
					rook.setPosition(destRow, destCol+3);
				}
				return true;
			}
		} else if (destCol == 7) {
			if (board.getBoardPiece(destRow, 5) == null &&
					board.getBoardPiece(destRow, 6) == null) {
				if (move) {					
					board.iMovePiece(destRow, getColumn(), destRow, getColumn()+2);
					board.iMovePiece(destRow, destCol, destRow, destCol-2);
					setPosition(destRow, getColumn()+2);
					rook.setPosition(destRow, destCol-2);
				}
				return true;
			}
		}
		
		return false;
	}
}
