package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.LayoutManager;
import java.awt.event.*;

import javax.swing.*;

public class StartWindow extends JPanel implements  ActionListener {
	static JFrame frame;
	
	public StartWindow() {
	}
	
	public static void main(String[] args) {
		frame = new JFrame("Xadrez");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        
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
        
        panel.add(new_button);
        panel.add(load_button);
		
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.pack();
        frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	static void openGameCanvas() {
		// Closing start window
		frame.setVisible(false);
		frame.dispose();
		
		// Opening game window
		JFrame frame = new JFrame("Xadrez");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas canvas = new GameCanvas();
        canvas.setSize(400, 400);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
	}
	
	static void newGame() {
		// TODO
		openGameCanvas();
	}
	
	static void loadGame() {
		// TODO
		System.out.println("LOAD GAME");
	}

}
