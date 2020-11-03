package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private Piece[][] matrix = new Piece[8][8];
	private static Board board = null;
	private Piece lastMoved; // stores last piece moved
	
	public String getSquarePiece(int row, int col) {
		if (board == null) return null;
		Piece p = board.matrix[row][col];
		if (p == null) return null;
		return p.getName();
	}
	
	public String getPieceImage(int row, int col) {
		String pname = getSquarePiece(row, col);
		if (pname == null) return null;
		if (pname.toLowerCase() != pname) { // is white piece
			return "w_"+pname.toLowerCase()+".gif";
		} else {
			return "b_"+pname+".gif";
		}
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
	
	public void capturePiece(int row, int col) {
		board.matrix[row][col] = null;
	}
	
	public List<Integer> getPossibleMoves(int row, int col) {
		Piece piece = this.getBoardPiece(row, col);
		if (piece != null)
			return piece.getPossibleMoves();
		else
			return new ArrayList<Integer>();
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
		// Pawns and empties
		for (int col=0; col<8; col++) {
			board.matrix[1][col] = new Pawn(1, col, Color.WHITE);
			board.matrix[6][col] = new Pawn(6, col, Color.BLACK);
			
			board.matrix[2][col] = null;
			board.matrix[3][col] = null;
			board.matrix[4][col] = null;
			board.matrix[5][col] = null;
		}
		
		// Rooks
		board.matrix[0][0] = new Rook(0, 0, Color.WHITE);
		board.matrix[0][7] = new Rook(0, 7, Color.WHITE);
		board.matrix[7][0] = new Rook(7, 0, Color.BLACK);
		board.matrix[7][7] = new Rook(7, 7, Color.BLACK);
		
		// Knights
		board.matrix[0][1] = new Knight(0, 1, Color.WHITE);
		board.matrix[0][6] = new Knight(0, 6, Color.WHITE);
		board.matrix[7][1] = new Knight(7, 1, Color.BLACK);
		board.matrix[7][6] = new Knight(7, 6, Color.BLACK);
		
		// Bishops
		board.matrix[0][2] = new Bishop(0, 2, Color.WHITE);
		board.matrix[0][5] = new Bishop(0, 5, Color.WHITE);
		board.matrix[7][2] = new Bishop(7, 2, Color.BLACK);
		board.matrix[7][5] = new Bishop(7, 5, Color.BLACK);
		
		// Queens
		board.matrix[0][3] = new Queen(0, 3, Color.WHITE);
		board.matrix[7][3] = new Queen(7, 3, Color.BLACK);
		
		// Kings
		board.matrix[0][4] = new King(0, 4, Color.WHITE);
		board.matrix[7][4] = new King(7, 4, Color.BLACK); 
		
		return board;
	}
	
	// Package methods
	
	Piece getBoardPiece(int row, int col) {
		if (board == null)
			return null;
		return board.matrix[row][col];
	}
}
