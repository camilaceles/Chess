package model;

import gui.PawnPromoPopup;

import java.util.ArrayList;
import java.util.List;

public class Board implements Observable {
	private Piece[][] matrix = new Piece[8][8];
	private static Board board = null;
	private Piece lastMoved; // stores last piece moved
	private PiecesColor turn = PiecesColor.WHITE;
	
	private List<Observer> observadores = new ArrayList<Observer>();
	
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
			if ((piece.getCode() == PiecesEnum.WHITE_PAWN && destRow == 7) ||
					(piece.getCode() == PiecesEnum.BLACK_PAWN && destRow == 0)) {
				new PawnPromoPopup(destRow, destCol, piece.getColor());
			}
			lastMoved = piece;
		}
		update();
		return moved;
	}
	
	public Piece getKing(PiecesColor color) {
		PiecesEnum wanted_code = (color == PiecesColor.BLACK) ? PiecesEnum.BLACK_KING : PiecesEnum.WHITE_KING;
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				Piece p = board.matrix[i][j];
				if (p != null && p.getCode() == wanted_code) {
					return p;
				}
			}
		}
		return null;
	}
	
	public boolean checkCheck(PiecesColor color, int row, int col) { // checks if {color} king would be in check at [row, col]
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				Piece p = board.matrix[i][j];
				
				if (p != null && p.getColor() != color) {
					List<Integer> moves = p.getPossibleMoves(); // no need to check if move is valid if you can capture the enemy king
					
					for (int k = 0; k < moves.size(); k+=2) {
						if (moves.get(k) == row && moves.get(k+1) == col) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkCheck(PiecesColor color) {
		Piece king = getKing(color);
		return checkCheck(color, king.getRow(), king.getColumn());
	}
	
	public boolean checkStalemate(PiecesColor color ) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece p = board.matrix[i][j];
				if (p != null && p.getColor() == color) {
					if (p.getValidMoves().size() != 0) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public boolean checkCheckmate(PiecesColor color) {
		if (!checkCheck(color)) {
			return false;
		}
		
//		List<Integer> moves = king.getValidMoves();
//		for (int i = 0; i < moves.size(); i++) {
//			if (!checkCheck(color, moves.get(i), moves.get(++i))) {
//				return false;
//			}
//		}
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece p = board.matrix[i][j];
				if (p != null && p.getColor() == color) {
					if (p.getValidMoves().size() != 0) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public void capturePiece(int row, int col) {
		board.matrix[row][col] = null;
	}
	
	public List<Integer> getValidMoves(int row, int col) {
		Piece piece = this.getBoardPiece(row, col);
		if (piece != null)
			return piece.getValidMoves();
		else
			return new ArrayList<Integer>();
	}
	
	public boolean hypotheticalMove(Piece p, int destRow, int destCol) { // checks if move would leave king in check
		//TODO: en-passant
		int origRow = p.getRow();
		int origCol = p.getColumn();
		
		Piece temp = null;
		Piece[] tempRow = new Piece[8];
		
		boolean isCastle = ((p.getCode() == PiecesEnum.BLACK_KING || p.getCode() == PiecesEnum.WHITE_KING) &&
							origCol == 4 && (destCol == 0 || destCol == 7));
		boolean isEnPassant = ((p.getCode() == PiecesEnum.BLACK_PAWN || p.getCode() == PiecesEnum.WHITE_PAWN) &&
								origCol != destCol && board.matrix[destRow][destCol] == null);
		
		if (isCastle) {
			if (destCol == 0) {
				temp = board.matrix[origRow][0];
				tempRow = board.matrix[origRow].clone();
				board.matrix[origRow][0] = null;
				board.matrix[origRow][2] = p;
				p.setPosition(origRow, 2);
				board.matrix[origRow][3] = temp;
				board.matrix[origRow][4] = null;
			} else {
				temp = board.matrix[origRow][7];
				tempRow = board.matrix[origRow].clone();
				board.matrix[origRow][7] = null;
				board.matrix[origRow][6] = p;
				p.setPosition(origRow, 6);
				board.matrix[origRow][5] = temp;
				board.matrix[origRow][4] = null;
			}
		} else if (isEnPassant) {
			temp = board.matrix[origRow][destCol];
			board.matrix[origRow][destCol] = null;
			board.matrix[destRow][destCol] = p;
			p.setPosition(destRow, destCol);
			board.matrix[origRow][origCol] = null;
		} else {
			Piece destPiece = board.matrix[destRow][destCol]; 
			if (destPiece != null && destPiece.getColor() == p.getColor())
				return false;
			temp = board.matrix[destRow][destCol];
			board.matrix[origRow][origCol] = null;
			board.matrix[destRow][destCol] = p;
			p.setPosition(destRow, destCol);
		}
		
		boolean isValid = !checkCheck(p.getColor());
		
		if (isCastle) {
			board.matrix[origRow] = tempRow;
			p.setPosition(origRow, origCol);
		} else if (isEnPassant) {
			board.matrix[origRow][origCol] = p;
			p.setPosition(origRow, origCol);
			board.matrix[destRow][destCol] = null;
			board.matrix[origRow][destCol] = temp;
		} else {
			board.matrix[origRow][origCol] = p;
			board.matrix[destRow][destCol] = temp;
			p.setPosition(origRow, origCol);
		}
		
		return isValid;
	}
	
	public void promotePawn(int row, int col, PiecesEnum newEnum) {
		Piece newPiece = null;
		if (newEnum == PiecesEnum.WHITE_QUEEN)
			newPiece = new Queen(row, col, PiecesColor.WHITE);
		else if (newEnum == PiecesEnum.BLACK_QUEEN)
			newPiece = new Queen(row, col, PiecesColor.BLACK);
		else if (newEnum == PiecesEnum.WHITE_ROOK)
			newPiece = new Rook(row, col, PiecesColor.WHITE);
		else if (newEnum == PiecesEnum.BLACK_ROOK)
			newPiece = new Rook(row, col, PiecesColor.BLACK);
	    else if (newEnum == PiecesEnum.WHITE_BSHP)
	      newPiece = new Bishop(row, col, PiecesColor.WHITE);
	    else if (newEnum == PiecesEnum.BLACK_BSHP)
	      newPiece = new Bishop(row, col, PiecesColor.BLACK);
	    else if (newEnum == PiecesEnum.WHITE_KNIGHT)
	      newPiece = new Knight(row, col, PiecesColor.WHITE);
	    else if (newEnum == PiecesEnum.BLACK_KNIGHT)
	      newPiece = new Knight(row, col, PiecesColor.BLACK);
		
		board.matrix[row][col] = newPiece;
		
		update();
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
	
	public void setBoardMatrix(PiecesEnum[][] matrix) {
		board = new Board();
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				int pieceType = matrix[i][j].ordinal() % 6;
				PiecesColor color = (matrix[i][j].ordinal()>5) ? PiecesColor.BLACK : PiecesColor.WHITE;
				if (matrix[i][j] == PiecesEnum.NONE) // none
					board.matrix[i][j] = null;
				else if (pieceType == 0) // pawn 
					board.matrix[i][j] = new Pawn(i, j, color);
				else if (pieceType == 1) // rook
					board.matrix[i][j] = new Rook(i, j, color);
				else if (pieceType == 2) // knight
					board.matrix[i][j] = new Knight(i, j, color);
				else if (pieceType == 3) // bishop
					board.matrix[i][j] = new Bishop(i, j, color);
				else if (pieceType == 4) // king
					board.matrix[i][j] = new King(i, j, color);
				else if (pieceType == 5) // queen
					board.matrix[i][j] = new Queen(i, j, color);
			}
		}
		board.update();
	}
	
	// Static method
	
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
	
	Piece getBoardPiece(int row, int col) {
		if (board == null)
			return null;
		return board.matrix[row][col];
	}
	
	void iMovePiece(int origRow, int origCol, int destRow, int destCol) {
		board.matrix[destRow][destCol] = board.matrix[origRow][origCol];
		board.matrix[origRow][origCol] = null;
	}
	
	// Observable methods

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
