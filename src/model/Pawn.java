package model;

import java.util.*;

class Pawn extends Piece {
	boolean isFirstMove = true;
	boolean lastMoveDouble = false;
	
	Pawn(int row, int col, Color color) {
		super(row, col, color);
	}
	
	boolean goTo(int destRow, int destCol) {
		if (validateMove(destRow, destCol)) {
			isFirstMove = false;
			setPosition(destRow, destCol);
			return true;
		} else {
			return false;
		}
	}
	
	List<Integer> getPossibleMoves() {
		List<Integer> moves = new ArrayList<Integer>();
		for (int row=0; row<8; row++) {
			for (int col=0; col<8; col++) {
				if (validateMove(row, col)) {
					moves.add(row); moves.add(col);
				}
			}
		}
		return moves;
	}
	
	boolean wasLastDouble() {
		return this.lastMoveDouble;
	}
	
	String getName() {
		if (getColor() == Color.WHITE) 
			return "PAWN";
		else
			return "pawn";
	}
	
	// Private methods
	
	private boolean validateMove(int destRow, int destCol) { // TODO en passant, promotion
		Piece destPiece = Board.getBoard().getBoardPiece(destRow, destCol);
		boolean valid = false;
		
		// Check for en passant
		if (checkEnPassant(destRow, destCol)) {
			return true; // TODO throw 'en passant' exception? idk
		}
	
		// Capture move
		if (destCol != getColumn()) {
			if (destPiece != null && (destCol == getColumn()-1 || destCol == getColumn()+1)) { // diagonal move
				if (((getColor()==Color.WHITE && destRow==getRow()+1) || (getColor()==Color.BLACK && destRow==getRow()-1)) && destPiece.getColor()!=getColor())
					valid = true;
			}
		} else {
			// Regular moves		
			if (getColor() == Color.WHITE) { // moves upwards (row +)
				if (destRow == getRow()+1) { // regular move
					valid = destPiece==null;
				} else if (destRow == getRow()+2) { // double first move
					if (Board.getBoard().getBoardPiece(getRow()+1, getColumn()) == null) {
						if (destPiece==null && isFirstMove) {
							lastMoveDouble = true;
							return true;
						}
					}
				}
				
			} else if (getColor() == Color.BLACK) { // moves downwards (row -)
				if (destRow == getRow()-1) { // regular move
					valid = destPiece==null;
				} else if (destRow == getRow()-2) { // double first move
					if (Board.getBoard().getBoardPiece(getRow()-1, getColumn()) == null) {
						if (destPiece==null && isFirstMove) {
							lastMoveDouble = true;
							return true;
						}
					}
				}
			}
		}
		if (valid) lastMoveDouble = false;
		return valid;
	}
	
	private boolean checkEnPassant(int destRow, int destCol) {
		if (getColor()==Color.WHITE && getRow()==4 && destRow==5 && (destCol==getColumn()-1 || destCol==getColumn()+1)) {
			Piece target = Board.getBoard().getBoardPiece(getRow(), destCol);
			if (target.getColor() == Color.BLACK && target.wasLastDouble()) {
				System.out.println("MOVIMENTO ESPECIAL: en passant");
				return true; // TODO throw exception? to capture target
			}
		} else if (getColor()==Color.BLACK && getRow()==3 && destRow==2 && (destCol==getColumn()-1 || destCol==getColumn()+1)) {
			Piece target = Board.getBoard().getBoardPiece(getRow(), destCol);
			if (target.getColor() == Color.WHITE && target.wasLastDouble()) {
				System.out.println("MOVIMENTO ESPECIAL: en passant");
				return true; // TODO throw exception? to capture target
			}
		}
		return false;
	}
}
