package gui;

import model.*;

import javax.swing.*;  
import java.awt.event.*;

public class PawnPromoPopup {
	final double squareSize = 50.0;
	int row, col;
	private PiecesEnum selected;
	Board board = Board.getBoard();
	
	public PawnPromoPopup(int row, int col, PiecesColor color) {
		this.row = row; this.col = col;
		
		JPopupMenu popup = new JPopupMenu("Seleção");
		
		JMenuItem queen =  new JMenuItem("Rainha");
		JMenuItem rook =   new JMenuItem("Torre");
		JMenuItem bishop = new JMenuItem("Bispo");
		JMenuItem knight = new JMenuItem("Cavalo");
		
		popup.add(queen); popup.add(rook); popup.add(bishop); popup.add(knight); 
		
		queen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (color == PiecesColor.WHITE)
					selected = PiecesEnum.WHITE_QUEEN;
				else
					selected = PiecesEnum.BLACK_QUEEN;
				promote();
				popup.setVisible(false);
			}
		});
		
		rook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (color == PiecesColor.WHITE)
					selected = PiecesEnum.WHITE_ROOK;
				else
					selected = PiecesEnum.BLACK_ROOK;
				promote();
				popup.setVisible(false);
			}
		});
		
		bishop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (color == PiecesColor.WHITE)
					selected = PiecesEnum.WHITE_BSHP;
				else
					selected = PiecesEnum.BLACK_BSHP;
				promote();
				popup.setVisible(false);
			}
		});
		
		knight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (color == PiecesColor.WHITE)
					selected = PiecesEnum.WHITE_KNIGHT;
				else
					selected = PiecesEnum.BLACK_KNIGHT;
				promote();
				popup.setVisible(false);
			}
		});
		
		int x = (int)(col * squareSize + squareSize/2);
		int y = (int)((7 - row) * squareSize + 3*squareSize/2);
		popup.setLocation(x, y);
		popup.setVisible(true);
	}
	
	private void promote() {
		board.promotePawn(row, col, selected);
	}
}