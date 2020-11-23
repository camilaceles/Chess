package model;

import java.util.List;

abstract class Piece {
	private int row, column;
	private PiecesColor color;
	
	Piece (int row, int col, PiecesColor color) {
		this.row = row;
		this.column = col;
		this.color = color;
	}
	
	// Core methods
	
	int getRow() {
		return this.row;
	}
	int getColumn() {
		return this.column;
	}
	PiecesColor getColor() {
		return this.color;
	}
	
	void setPosition(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	// Extandable methods
	
	boolean goTo(int destRow, int destCol) {
		setPosition(destRow, destCol);
		return true;
	}
	
	PiecesEnum getCode() {
		return PiecesEnum.NONE;
	}
	
	List<Integer> getPossibleMoves() {
		return null;
	}
	
	boolean wasLastDouble() {
		return false;
	}

	boolean isFirstMove() {
		return false;
	}
}