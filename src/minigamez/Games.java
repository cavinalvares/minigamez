package minigamez;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
/**
 * This program is used to display the panel after the user has clicked on the menu button
 * This program shows all the games
 * @author Jedidah
 *
 */
public class Games extends Panel implements ActionListener{

	Window window;
	JButton back, snakeGame, brickBreaker, tetris, mineSweeper;
	JLabel header;
	
	/*
	 * This method creates the panel, buttons etc;
	 */
	public Games() {
		super();
		this.window = Window.getWindow();
		
		header = new JLabel();
		if(Window.mode == 0)
			setLabel(header, "Games", 175, 50, 250, 50, "Serif", 25, 3);
		else
			setLabel(header, "Games", (window.getWidth()-400)/2, 100, 400, 75, "Serif", 50, 3);
		
		
		back = new JButton();
		if(Window.mode == 0)
			setButton(back, "Back", 5, 5, 50, 20, 15);
		else
			setButton(back, "Back", 10, 10, 100, 35, 25);
		back.addActionListener(this);
		
	
		snakeGame = new JButton();
		if(Window.mode == 0)
			setButton(snakeGame, "Snake Game", 225, 150, 150, 40, 20);
		else
			setButton(snakeGame, "Snake Game", (window.getWidth()-225)/2, 225, 225, 50, 25);
		snakeGame.addActionListener(this);
		
		brickBreaker = new JButton();
		if(Window.mode == 0)
			setButton(brickBreaker, "Brick Breaker", 225, 200, 150, 40, 20);
		else
			setButton(brickBreaker, "Brick Breaker", (window.getWidth()-225)/2, 300, 225, 50, 25);
		brickBreaker.addActionListener(this);
		
		tetris = new JButton();
		if(Window.mode == 0)
			setButton(tetris, "Tetris", 225, 250, 150, 40, 20);
		else
			setButton(tetris, "Tetris", (window.getWidth()-225)/2, 375, 225, 50, 25);
		tetris.addActionListener(this);
		
		mineSweeper = new JButton();
		if(Window.mode == 0)
			setButton(mineSweeper, "Mine Sweeper", 225, 300, 150, 40, 20);
		else
			setButton(mineSweeper, "Mine Sweeper", (window.getWidth()-225)/2, 450, 225, 50, 25);
		mineSweeper.addActionListener(this);
		

		this.add(header);
		this.add(back);
		this.add(snakeGame);
		this.add(brickBreaker);
		this.add(tetris);
		this.add(mineSweeper);
		
		window.add(this);
		window.setVisible(true);
	}
	

	/**
	 * This method gives the correct behaviour of each button after they have been clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == back) {
			this.remove(header);
			this.remove(back);
			this.remove(snakeGame);
			this.remove(brickBreaker);
			this.remove(tetris);
			this.remove(mineSweeper);
			
			back = null;
			header = null;
			snakeGame = null;
			brickBreaker = null;
			tetris = null;
			mineSweeper = null;
			
			this.setVisible(false);
			window.remove(this);
			window.add(new Menu()); 
		}
		
		if(e.getSource() == snakeGame) {
			this.remove(header);
			this.remove(back);
			this.remove(snakeGame);
			this.remove(brickBreaker);
			this.remove(tetris);
			this.remove(mineSweeper);
			
			back = null;
			header = null;
			snakeGame = null;
			brickBreaker = null;
			tetris = null;
			mineSweeper = null;
			
			this.setVisible(false);
			window.remove(this);

			new SnakeGame();
			
		}
		
		if(e.getSource() == brickBreaker) {
			this.remove(header);
			this.remove(back);
			this.remove(snakeGame);
			this.remove(brickBreaker);
			this.remove(tetris);
			this.remove(mineSweeper);
			
			back = null;
			header = null;
			snakeGame = null;
			brickBreaker = null;
			tetris = null;
			mineSweeper = null;
			
			this.setVisible(false);
			window.remove(this);

			new BrickBreaker(1, 0);
			
		}
		
		if(e.getSource() == tetris) {
			this.remove(header);
			this.remove(back);
			this.remove(snakeGame);
			this.remove(brickBreaker);
			this.remove(tetris);
			this.remove(mineSweeper);
			
			back = null;
			header = null;
			snakeGame = null;
			brickBreaker = null;
			tetris = null;
			mineSweeper = null;
			
			this.setVisible(false);
			window.remove(this);

			new Tetris();
			
		}
		
		if(e.getSource() == mineSweeper) {
			this.remove(header);
			this.remove(back);
			this.remove(snakeGame);
			this.remove(brickBreaker);
			this.remove(tetris);
			this.remove(mineSweeper);
			
			back = null;
			header = null;
			snakeGame = null;
			brickBreaker = null;
			tetris = null;
			mineSweeper = null;
			
			this.setVisible(false);
			window.remove(this);
			new MineSweeper();
			
			
		}
		
	}

}
