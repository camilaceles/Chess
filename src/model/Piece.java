package model;

import java.util.List;

abstract class Piece {
	private int row, column;
	private Color color;
	
	Piece (int row, int col, Color color) {
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
	Color getColor() {
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
	
	String getName() {
		return null;
	}
	
	List<Integer> getPossibleMoves() {
		return null;
	}
	
	boolean wasLastDouble() {
		return false;
	}
}