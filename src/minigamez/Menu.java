package minigamez;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
/**
 * This program is used to display the Main menu
 * @author Jedidiah
 *
 */
public class Menu extends Panel implements ActionListener{

	Window window;
	JButton games, settings, exit, scores;
	JLabel header;
	Image img;
	
	/**
	 * This method creates the panel, buttons etc;
	 */
	Menu(){
		super();
		this.window = Window.getWindow();
		
		header = new JLabel();
		if(Window.mode == 0)
			setLabel(header, "MiniGamez", 175, 50, 250, 50, "Serif", 25, 3);
		else
			setLabel(header, "MiniGamez", (window.getWidth()-400)/2, 100, 400, 75, "Serif", 50, 3);
			
		
		games = new JButton();
		if(Window.mode == 0)
			setButton(games, "Games", 225, 150, 150, 40, 20);
		else
			setButton(games, "Games", (window.getWidth()-225)/2, 225, 225, 50, 25);
		games.addActionListener(this);
		
		
		settings = new JButton();
		if(Window.mode == 0)
			setButton(settings, "Settings", 225, 200, 150, 40, 20);
		else
			setButton(settings, "Settings", (window.getWidth()-225)/2, 300, 225, 50, 25);
		settings.addActionListener(this);
		
		
		scores = new JButton();
		if(Window.mode == 0)
			setButton(scores, "Scores", 225, 250, 150, 40, 20);
		else
			setButton(scores, "Scores", (window.getWidth()-225)/2, 375, 225, 50, 25);
		scores.addActionListener(this);
		
		exit = new JButton();
		if(Window.mode == 0)
			setButton(exit, "Exit", 225, 300, 150, 40, 20);
		else
			setButton(exit, "Exit", (window.getWidth()-225)/2, 450, 225, 50, 25);
		exit.addActionListener(e -> System.exit(0));
		
		
		this.add(header);
		this.add(games);
		this.add(settings);
		this.add(scores);
		this.add(exit);
		
		img = new ImageIcon("src/resources/OIP3.jpg").getImage();
		
		window.add(this);
		window.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, window.getWidth(), window.getHeight(), this);
	}

	/**
	 * This method gives the correct behaviour of each button after they have been clicked
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == games)
		{
			this.remove(games);
			this.remove(exit);
			this.remove(header);
			this.remove(settings);
			this.setVisible(false);
			window.remove(this);
			//window.add(new Games());
			new Games();
		}
		if(e.getSource() == settings)
		{
			window.remove(games);
			window.remove(exit);
			window.remove(header);
			window.remove(settings);
			games = null;
			header = null;
			exit = null;
			settings = null;
			
			this.setVisible(false);
			window.add(new Settings());
		}
		if(e.getSource() == scores)
		{
			window.remove(games);
			window.remove(exit);
			window.remove(header);
			window.remove(settings);
			games = null;
			header = null;
			exit = null;
			settings = null;
			
			this.setVisible(false);
			//window.add(new scores());
			new GameScore();
		}
	}
}
