package minigamez;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
/**
 * Program to display the help menu
 * @author Jedidiah
 *
 */
public class Help extends Panel implements ActionListener{

	private JLabel header;
	Window window;
	private JButton back;
	private Pause pause;
	String help[] = {"", "", "", ""};
	int gameCode;
	
	/**
	 * Initialize the help panel
	 * @param pause
	 * @param gameCode
	 */
	Help(Pause pause, int gameCode){
		super();
		this.window = Window.getWindow();
		this.pause = pause;
		this.gameCode = gameCode;
		
		header = new JLabel();
		if(Window.mode == 0)
			setLabel(header, "Help", 175, 50, 250, 50, "Serif", 25, 3);
		else
			setLabel(header, "Help", (window.getWidth()-400)/2, 100, 400, 75, "Serif", 50, 3);
		
		back = new JButton();
		if(Window.mode == 0)
			setButton(back, "Back", 5, 5, 50, 20, 15);
		else
			setButton(back, "Back", 10, 10, 100, 35, 25);
		back.addActionListener(this);
		
		this.add(header);
		this.add(back);
		
		window.add(this);
	}
	
	
	/**
	 * Paint or draw the images
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image con = new ImageIcon("src/resources/control"+gameCode+".png").getImage();
        if(Window.mode == 0)
        	g.drawImage(con, 0, 100, 600, 450,  this);
        else
        	g.drawImage(con, 0, 200, 1200, 500,  this);
        	
	}

	/**
	 * action for backbutton to go back to the pause menu
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		window.remove(header);
		window.remove(back);
		back = null;
		header = null;
		
		this.setVisible(false);
		
		pause.setVisible(true);
		window.addKeyListener(pause);
		 
		
	}
}
