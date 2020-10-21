package model;

import java.util.List;

public class Board {
	private Piece[][] matrix = new Piece[8][8];
	private static Board board = null;
	private Piece lastMoved; // stores last piece moved
	
	public Piece getBoardPiece(int row, int col) {
		if (board == null)
			return null;
		return board.matrix[row][col];
	}
	
	public void print() { // white pieces are UPPERCASE, black are lowercase
		System.out.printf("  |   0   |   1   |   2   |   3   |   4   |   5   |   6   |   7   | ");
		System.out.printf("%n--------------------------------------------------------------------%n");
		for (int i=7; i>=0; i--) {
			System.out.printf("%d | ", i);
			for (int j=0; j<8; j++) {
				if (board.matrix[i][j] != null) {
					System.out.printf("%5s | ", board.matrix[i][j].getName());
				} else {
					System.out.printf("      | ");
				}
			}
			System.out.printf("%n--------------------------------------------------------------------%n");
		}
	}
	
	public boolean movePiece(int origRow, int origCol, int destRow, int destCol) {
		Piece piece = this.getBoardPiece(origRow, origCol);
		boolean moved = piece.goTo(destRow, destCol);
		if (moved) {
			board.matrix[destRow][destCol] = board.matrix[origRow][origCol];
			board.matrix[origRow][origCol] = null;
			lastMoved = piece;
		}
		return moved;
	}
	
	public List<Integer> getPossibleMoves(int row, int col) {
		Piece piece = this.getBoardPiece(row, col);
		return piece.getPossibleMoves();
	}
	
	public boolean wasLastMoved(int row, int col) {
		Piece piece = this.getBoardPiece(row, col);
		return piece == this.lastMoved;
	}
	
	public static Board getBoard() {
		if (board != null)
			return board;
		
		board = new Board();
		// TODO fill board with right pieces
		for (int col=0; col<8; col++) {
			board.matrix[1][col] = new Pawn(1, col, Color.WHITE);
			board.matrix[3][col] = new Pawn(3, col, Color.BLACK);
			
			board.matrix[0][col] = null; // TODO
			board.matrix[2][col] = null;
			board.matrix[6][col] = null;
			board.matrix[4][col] = null;
			board.matrix[5][col] = null;
			board.matrix[7][col] = null; // TODO
		}
		return board;
	}
}
