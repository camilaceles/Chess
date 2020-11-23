package model;

import java.util.ArrayList;
import java.util.List;

public class Board implements Observable {
	private Piece[][] matrix = new Piece[8][8];
	private static Board board = null;
	private Piece lastMoved; // stores last piece moved
	
	private List<Observer> observadores = new ArrayList<Observer>();
	
	public PiecesEnum[][] getBoardMatrix() {
		PiecesEnum[][] out = new PiecesEnum[8][8];
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				Piece p = board.matrix[i][j];
				if (p != null) {
					out[i][j] = p.getCode();
				} else {
					out[i][j] = PiecesEnum.NONE;
				}
			}
		}
		return out;
	}
	
	public boolean movePiece(int origRow, int origCol, int destRow, int destCol) {
		Piece piece = this.getBoardPiece(origRow, origCol);
		if (piece == null) return false;
		boolean moved = piece.goTo(destRow, destCol);
		if (moved) {
			if (piece.getCode() != PiecesEnum.WHITE_KING && piece.getCode() != PiecesEnum.BLACK_KING) {
				// Because of castling, piece moving logic for king is different				
				board.matrix[destRow][destCol] = board.matrix[origRow][origCol];
				board.matrix[origRow][origCol] = null;
			}
			lastMoved = piece;
		}
		update();
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
	
	public PiecesColor getPieceColor(int row, int col) {
		Piece piece = this.getBoardPiece(row, col);
		if (piece != null)
			return piece.getColor();
		else
			return null;
	}
	
	public static Board getBoard() {
		if (board != null)
			return board;
		
		board = new Board();
		// Pawns and empties
		for (int col=0; col<8; col++) {
			board.matrix[1][col] = new Pawn(1, col, PiecesColor.WHITE);
			board.matrix[6][col] = new Pawn(6, col, PiecesColor.BLACK);
			
			board.matrix[2][col] = null;
			board.matrix[3][col] = null;
			board.matrix[4][col] = null;
			board.matrix[5][col] = null;
		}
		
		// Rooks
		board.matrix[0][0] = new Rook(0, 0, PiecesColor.WHITE);
		board.matrix[0][7] = new Rook(0, 7, PiecesColor.WHITE);
		board.matrix[7][0] = new Rook(7, 0, PiecesColor.BLACK);
		board.matrix[7][7] = new Rook(7, 7, PiecesColor.BLACK);
		
		// Knights
		board.matrix[0][1] = new Knight(0, 1, PiecesColor.WHITE);
		board.matrix[0][6] = new Knight(0, 6, PiecesColor.WHITE);
		board.matrix[7][1] = new Knight(7, 1, PiecesColor.BLACK);
		board.matrix[7][6] = new Knight(7, 6, PiecesColor.BLACK);
		
		// Bishops
		board.matrix[0][2] = new Bishop(0, 2, PiecesColor.WHITE);
		board.matrix[0][5] = new Bishop(0, 5, PiecesColor.WHITE);
		board.matrix[7][2] = new Bishop(7, 2, PiecesColor.BLACK);
		board.matrix[7][5] = new Bishop(7, 5, PiecesColor.BLACK);
		
		// Queens
		board.matrix[0][3] = new Queen(0, 3, PiecesColor.WHITE);
		board.matrix[7][3] = new Queen(7, 3, PiecesColor.BLACK);
		
		// Kings
		board.matrix[0][4] = new King(0, 4, PiecesColor.WHITE);
		board.matrix[7][4] = new King(7, 4, PiecesColor.BLACK);
		
		board.update();
		
		return board;
	}
	
	// Package methods
	
	Piece getBoardPiece(int row, int col) {
		if (board == null)
			return null;
		return board.matrix[row][col];
	}
	
	void iMovePiece(int origRow, int origCol, int destRow, int destCol) {
		board.matrix[destRow][destCol] = board.matrix[origRow][origCol];
		board.matrix[origRow][origCol] = null;
	}

	@Override
	public void add(Observer o) {
		observadores.add(o);
	}

	@Override
	public void remove(Observer o) {
		observadores.remove(o);
	}

	@Override
	public PiecesEnum[][] get() {
		PiecesEnum[][] out = new PiecesEnum[8][8];
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				Piece p = board.matrix[i][j];
				if (p != null) {
					out[i][j] = p.getCode();
				} else {
					out[i][j] = PiecesEnum.NONE;
				}
			}
		}
		return out;
	}

	@Override
	public void update() {
		for (Observer o : observadores) {
			o.notify(this);
		}
	}
}
