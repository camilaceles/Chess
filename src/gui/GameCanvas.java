package gui;

import model.Board;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.*;

import javax.swing.JFrame;

public class GameCanvas extends Canvas implements MouseListener {
	final double squareSize = 50.0;
	int selectedRow = -1, selectedCol = -1;
	List<Integer> highlight = new ArrayList<Integer>();
	
	public GameCanvas() {
		this.addMouseListener(this);
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
				if (i== selectedRow && j==selectedCol) {
					g2d.setPaint(new Color(176, 252, 88, 127));
					rt = new Rectangle2D.Double(x,y,squareSize,squareSize);
					g2d.fill(rt);
				}
				
				// Drawing pieces
				Image img = getImage(i, j);
				if (img != null) {
					g.drawImage(img, (int)x+9, (int)y+9, null);
				}
				
				x += squareSize;
			}
			y -= squareSize;
		}
		// Highlighting possible moves, if any
		System.out.println(highlight);
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
		selectedRow = i; selectedCol = j;
		
		highlight = board.getPossibleMoves(i, j);
		
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
	
	private Image getImage(int i, int j) {
		Board board = Board.getBoard();
		String fn = board.getPieceImage(i, j);
		Image img = null;
		
		if (fn != null) {
			try {
				img = ImageIO.read(new File("images/"+fn));
			}
			catch(IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
			
		}
		return img;
	}
}
