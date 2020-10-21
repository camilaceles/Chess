package model;

import java.util.*;

class Pawn extends Piece {
	boolean isFirstMove = true;
	
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
	
	String getName() {
		if (getColor() == Color.WHITE) 
			return "PAWN";
		else
			return "pawn";
	}
	
	// Private methods
	
	private boolean validateMove(int destRow, int destCol) { // TODO en passant, promotion
		Piece destPiece = Board.getBoard().getBoardPiece(destRow, destCol);
	
		// Capture move
		if (destCol != getColumn()) {
			if (destPiece != null && (destCol == getColumn()-1 || destCol == getColumn()+1)) { // diagonal move
				if (((getColor()==Color.WHITE && destRow==getRow()+1) || (getColor()==Color.BLACK && destRow==getRow()-1)) && destPiece.getColor()!=getColor())
					return true;
			}
		} else {
			// Regular moves		
			if (getColor() == Color.WHITE) { // moves upwards (row +)
				if (destRow == getRow()+1) { // regular move
					return destPiece==null;
				} else if (destRow == getRow()+2) { // double first move
					if (Board.getBoard().getBoardPiece(getRow()+1, getColumn()) == null)
						return destPiece==null && isFirstMove;
				}
				
			} else if (getColor() == Color.BLACK) { // moves downwards (row -)
				if (destRow == getRow()-1) { // regular move
					return destPiece==null;
				} else if (destRow == getRow()-2) { // double first move
					if (Board.getBoard().getBoardPiece(getRow()-1, getColumn()) == null)
						return destPiece==null && isFirstMove;
				}
			}
		}
		return false;
	}
}
