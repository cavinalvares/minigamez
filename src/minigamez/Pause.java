package minigamez;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;
/**
 * This program is used to display the panel after the user has clicked on the ESC button while playing a game
 * This program pauses the game
 * @author Jedidiah
 *
 */
public class Pause extends Panel implements ActionListener, KeyListener{

	Window window;
	JButton resume, settings, menu, help;
	JLabel header;
	AllGames game;
	int gameCode;
	
	/**
	 * This method creates the panel, buttons etc;
	 * @param game
	 */
	public Pause(AllGames game, int gameCode) {
		super();
		this.window = Window.getWindow();
		this.game = game;
		this.gameCode = gameCode;
		window.addKeyListener(this);
		
		header = new JLabel();
		if(Window.mode == 0)
			setLabel(header, "Paused", 175, 50, 250, 50, "Serif", 25, 3);
		
		else
			setLabel(header, "Paused", (window.getWidth()-400)/2, 100, 400, 75, "Serif", 50, 3);
					

		resume = new JButton();
		if(Window.mode == 0)
			setButton(resume, "Resume", 225, 150, 150, 40, 20);
		else
			setButton(resume, "Resume", (window.getWidth()-225)/2, 225, 225, 50, 25);
		resume.addActionListener(this);
		
		settings = new JButton();
		if(Window.mode == 0)
			setButton(settings, "Settings", 225, 200, 150, 40, 20);
		else
			setButton(settings, "Settings", (window.getWidth()-225)/2, 300, 225, 50, 25);
		settings.addActionListener(this);
		
		help = new JButton();
		if(Window.mode == 0)
			setButton(help, "Help", 225, 250, 150, 40, 20);
		else
			setButton(help, "Help", (window.getWidth()-225)/2, 375, 225, 50, 25);
		help.addActionListener(this);
		
		menu = new JButton();
		if(Window.mode == 0)
			setButton(menu, "Main Menu", 225, 300, 150, 40, 20);
		else
			setButton(menu, "Main Menu", (window.getWidth()-225)/2, 450, 225, 50, 25);
		menu.addActionListener(this);
		
		
		this.add(header);
		this.add(resume);
		this.add(settings);
		this.add(menu);
		this.add(help);
		
		window.add(this);
		window.setVisible(true);
	}

	@Override
	/**
	 * This method gives the correct behaviour of each button after they have been clicked
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resume)
		{
			window.remove(resume);
			window.remove(menu);
			window.remove(header);
			window.remove(settings);
			window.remove(help);
			resume = null;
			header = null;
			menu = null;
			settings = null;
			help = null;
			window.removeKeyListener(this);
			
			this.setVisible(false);
			game.resume();
			
		}
		if(e.getSource() == settings)
		{
			
		
			window.removeKeyListener(this);
			
			this.setVisible(false);
			window.add(new Settings(this));
		}
		
		if(e.getSource() == menu)
		{
			window.remove(resume);
			window.remove(menu);
			window.remove(header);
			window.remove(settings);
			window.remove(help);
			resume = null;
			header = null;
			menu = null;
			settings = null;
			help = null;
			window.removeKeyListener(this);
			
			this.setVisible(false);
			window.remove((Component) game);
			game = null; 
			new Menu();
			
		}
		
		if(e.getSource() == help)
		{
			window.removeKeyListener(this);
			
			this.setVisible(false);
			new Help(this, gameCode);
			
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_ESCAPE) {
			window.remove(resume);
			window.remove(menu);
			window.remove(header);
			window.remove(settings);
			window.remove(help);
			resume = null;
			header = null;
			menu = null;
			settings = null;
			help = null;
			window.removeKeyListener(this);
			this.setVisible(false);
			game.resume();
        }
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
