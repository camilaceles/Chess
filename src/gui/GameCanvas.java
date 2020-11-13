package gui;

import model.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.imageio.*;

import javax.swing.JFrame;

public class GameCanvas extends Canvas implements MouseListener, Observer {
	final double squareSize = 50.0;
	int selectedRow = -1, selectedCol = -1;
	boolean isMoving = false;
	PiecesColor turn = PiecesColor.WHITE;
	List<Integer> highlight = new ArrayList<Integer>();
	EnumMap<PiecesEnum,Image> imageMap = new EnumMap<>(PiecesEnum.class);
	PiecesEnum[][] boardMatrix;
	
	public GameCanvas() {
		// Building image enum map
		imageMap.put(PiecesEnum.WHITE_PAWN, getImage(PiecesEnum.WHITE_PAWN));
		imageMap.put(PiecesEnum.WHITE_ROOK, getImage(PiecesEnum.WHITE_ROOK));
		imageMap.put(PiecesEnum.WHITE_KNIGHT,  getImage(PiecesEnum.WHITE_KNIGHT));
		imageMap.put(PiecesEnum.WHITE_BSHP, getImage(PiecesEnum.WHITE_BSHP));
		imageMap.put(PiecesEnum.WHITE_KING, getImage(PiecesEnum.WHITE_KING));
		imageMap.put(PiecesEnum.WHITE_QUEEN, getImage(PiecesEnum.WHITE_QUEEN));
		imageMap.put(PiecesEnum.BLACK_PAWN, getImage(PiecesEnum.BLACK_PAWN));
		imageMap.put(PiecesEnum.BLACK_ROOK, getImage(PiecesEnum.BLACK_ROOK));
		imageMap.put(PiecesEnum.BLACK_KNIGHT, getImage(PiecesEnum.BLACK_KNIGHT));
		imageMap.put(PiecesEnum.BLACK_BSHP, getImage(PiecesEnum.BLACK_BSHP));
		imageMap.put(PiecesEnum.BLACK_KING, getImage(PiecesEnum.BLACK_KING));
		imageMap.put(PiecesEnum.BLACK_QUEEN, getImage(PiecesEnum.BLACK_QUEEN));
		
		this.addMouseListener(this);
		Board board = Board.getBoard();
		board.add(this);
		notify(board);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Xadrez");
        Canvas canvas = new GameCanvas();
        canvas.setSize(400, 400);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
	}
	
	public void paint(Graphics g) {
		double x;
		double y = 7.0 * squareSize;
		//PiecesEnum[][] boardMatrix = Board.getBoard().getBoardMatrix();
		
		Graphics2D g2d = (Graphics2D) g;
		for (int i=0; i<8; i++) {
			x = 0.0;
			for (int j=0; j<8; j++) {
				// Drawing board squares
				if ((i+j)%2 == 0) g2d.setPaint(new Color(138, 92, 37));
				else g2d.setPaint(new Color(237, 208, 171));
				Rectangle2D rt = new Rectangle2D.Double(x,y,squareSize,squareSize);
				g2d.fill(rt);
				
				// Drawing selection indicators
				if (i==selectedRow && j==selectedCol) {
					g2d.setPaint(new Color(176, 252, 88, 127));
					rt = new Rectangle2D.Double(x,y,squareSize,squareSize);
					g2d.fill(rt);
				}
				
				// Drawing pieces
				Image img = imageMap.get(boardMatrix[i][j]);
				if (img != null) {
					g.drawImage(img, (int)x+9, (int)y+9, null);
				}
				
				x += squareSize;
			}
			y -= squareSize;
		}
		// Highlighting possible moves, if any
		for (int k=0; k<highlight.size(); k++) {
			y = (7-highlight.get(k)) * squareSize;
			x = (highlight.get(++k)) * squareSize;
			g2d.setPaint(new Color(244, 252, 88, 127));
			Rectangle2D rt = new Rectangle2D.Double(x,y,squareSize,squareSize);
			g2d.fill(rt);
		}
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		Board board = Board.getBoard();
		double x = e.getX();
		double y = e.getY();
		int i = 7 - ((int)y / (int)(squareSize));
		int j = (int)x / (int)(squareSize);
		
		List<Integer> moves = board.getPossibleMoves(i, j);
		highlight = new ArrayList<Integer>();
		
		PiecesColor selectedColor;
		if (selectedRow > 0)
			selectedColor = board.getPieceColor(selectedRow, selectedCol);
		else
			selectedColor = turn; // in first round, selected color is white
		
		if (isMoving) { // selecting destination square
			if (board.movePiece(selectedRow, selectedCol, i, j)) { // if piece was moved, next player
				int colorInt = selectedColor.ordinal();
				turn = PiecesColor.values()[(colorInt+1)%2]; // alternate turn
			}
			isMoving = false; // go to next round
		} 
		else { // selecting piece to be moved
			if (selectedColor!=null && selectedColor==turn) {
				System.out.println(turn);
				highlight = moves;
				isMoving = true;
			}
		}
		selectedRow = i; selectedCol = j;
		
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	
	// Private methods
	
	private Image getImage(PiecesEnum p) {
		String fn = p.name();
		Image img = null;
		
		if (fn != null) {
			try {
				img = ImageIO.read(new File("images/"+fn+".gif"));
			}
			catch(IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
			
		}
		return img;
	}

	@Override
	public void notify(Observable o) {
		boardMatrix = (PiecesEnum[][]) o.get();
	}
}
