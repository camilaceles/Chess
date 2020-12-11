package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;

import model.Board;
import model.PiecesColor;
import model.PiecesEnum;

import java.io.*;
import java.util.Scanner;

public class StartWindow extends JPanel implements  ActionListener {
	static JFrame frame;
	static PiecesColor turn;
	
	public StartWindow() {
	}
	
	public static void main(String[] args) {
		frame = new JFrame("Xadrez");
		frame.setPreferredSize(new Dimension(400, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.setOpaque(true);
        
        // Placing game logo
        ImageIcon logo_icon = new ImageIcon("images/logo.png");
        JLabel logo = new JLabel(logo_icon);
        logo.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        panel.add(logo, gbc);
        
        // Creating 'new game' button
        JButton new_button = new JButton("Novo jogo");
        new_button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        new_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	newGame();
            }
        });
        
        // Creating 'load game' button
        JButton load_button = new JButton("Carregar jogo");
        load_button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        load_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	loadGame();
            }
        });
        
        panel.add(new_button, gbc);
        panel.add(load_button, gbc);
		
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	static void openGameCanvas() {
		// Closing start window
		frame.setVisible(false);
		frame.dispose();
		
		// Opening game window
		JFrame frame = new JFrame("Xadrez");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas canvas = new GameCanvas();
        ((GameCanvas) canvas).setTurn(turn);
        canvas.setSize(400, 400);
        frame.add(canvas);
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
	}
	
	static void newGame() {
		// Opens game canvas with default board config
		turn = PiecesColor.WHITE;
		openGameCanvas();
	}
	
	static void loadGame() {
		// Opens file chooser to select loaded game file
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue != JFileChooser.APPROVE_OPTION) {
			return;
        }
		String filename = jfc.getSelectedFile().getAbsolutePath();
		
		PiecesEnum[][] boardMatrix = new PiecesEnum[8][8];
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			// Reads file into PiecesEnum[8][8] matrix
			for (int i=0; i<8; i++) {
				String line = reader.readLine();
				Scanner scanner = new Scanner(line);
				for (int j=0; j<8; j++) {
					boardMatrix[i][j] = (PiecesEnum.values())[scanner.nextInt()];
				}
				scanner.close();
			}
			// Reads turn (last line of file)
			String line = reader.readLine();
			Scanner scanner = new Scanner(line);
			turn = (PiecesColor.values())[scanner.nextInt()];
			scanner.close();
			
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Opens game canvas with file board config
		Board board = Board.getBoard();
		board.setBoardMatrix(boardMatrix);
		openGameCanvas();
	}

}
