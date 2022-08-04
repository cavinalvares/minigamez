package minigamez;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * 
 * This program is used to display the panel after the user has lost/won the game
 * This program shows the users score and allow you to type a user name and save or directly goto the main menu
 * @author Jedidah
 *
 */
public class GameOver extends Panel implements ActionListener{

	Window window;
	JLabel header, score, enterName;
	JTextField name;
	JButton save, menu;
	String gm;
	int scored;
	String scorestr;
	
	/**
	 * This method creates the panel, buttons etc;
	 * @param scored
	 * @param gm
	 */
	public GameOver(int scored, String gm) {
		super();
		
		this.window = Window.getWindow();
		this.scored = scored;
		this.scorestr = ""+scored;
		if(gm == "Mine Sweeper") {
			int sec = scored%100;
			int min = scored/100;
			
			DecimalFormat dformat = new DecimalFormat("00");
			scorestr = ""+dformat.format(min)+":"+dformat.format(sec);
		}
		this.gm = gm;
		
		header = new JLabel();
		if(Window.mode == 0)
			setLabel(header, "Game Over", 175, 50, 250, 50, "Serif", 25, 3);
		else
			setLabel(header, "Game Over", (window.getWidth()-400)/2, 100, 400, 75, "Serif", 50, 3);
	
		score = new JLabel();
		if(Window.mode == 0)
			setLabel(score, "Score: "+scorestr, 225, 150, 150, 40, "Serif", 25, 3);
		else
			setLabel(score, "Score: "+scorestr, (window.getWidth()-225)/2, 225, 225, 50, "Serif", 30, 1);
		
		enterName = new JLabel();
		if(Window.mode == 0)
			setLabel(enterName, "Enter your Name", 150, 200, 300, 40, "Serif", 20, 0);
		else
			setLabel(enterName, "Enter your Name", (window.getWidth()-300)/2, 300, 300, 50, "Serif", 25, 0);
	
		name = new JTextField("Player");
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		name.setBackground(Color.GREEN);
		name.setForeground(Color.BLACK);
		name.setHorizontalAlignment(JLabel.CENTER);
		name.setBorder(border);
		if(Window.mode == 0)
		{
			name.setBounds(200, 250, 200, 40);
			name.setFont(new Font(null, Font.BOLD, 15));
		}
		else
		{
			name.setBounds((window.getWidth()-300)/2, 375, 300, 50);
			name.setFont(new Font(null, Font.BOLD, 25));
		}
		
		save = new JButton();
		if(Window.mode == 0)
			setButton(save, "Save", 150, 325, 100, 40, 20);
		else
			setButton(save, "Save", (window.getWidth()-225)/2-100, 475, 150, 50, 25);
		save.addActionListener(this);
		
		menu = new JButton();
		if(Window.mode == 0)
			setButton(menu, "Main Menu", 350, 325, 150, 40, 20);
		else
			setButton(menu, "Main Menu", (window.getWidth()-225)/2+200, 475, 200, 50, 25);
		menu.addActionListener(this);
		
		this.add(header);
		this.add(score);
		this.add(enterName);
		this.add(name);
		this.add(save);
		this.add(menu);
		
		if(scored == 0 && gm == "Mine Sweeper") {
			name.setEditable(false);
			save.setEnabled(false);
			score.setText("You Lose");
		}
		
		window.add(this);
		window.setVisible(true);

		
	}
	
	
	/**
	 * This method gives the correct behaviour of each button after they have been clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource() == save)
		{
			String text = gm+";"+name.getText()+";"+scored+"\n";
			
			try {
				FileWriter fw = new FileWriter("src/resources/Save_file.txt", true);
				
				for(int i=0; i<text.length(); i++) {
					fw.write(text.charAt(i));
				}
				
				
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			this.remove(menu);
			this.remove(name);
			this.remove(header);
			this.remove(enterName);
			this.remove(score);
			this.remove(save);
			header = null;
			score = null;
			enterName = null;
			name = null;
			save = null;
			menu = null;
			
			this.setVisible(false);
			window.add(new Menu());
			
		}
		
		if(e.getSource() == menu)
		{	
			
			this.remove(menu);
			this.remove(name);
			this.remove(header);
			this.remove(enterName);
			this.remove(score);
			this.remove(save);
			header = null;
			score = null;
			enterName = null;
			name = null;
			save = null;
			menu = null;
			
			this.setVisible(false);
			window.remove(this);
			new Menu();
			
		}
		
		if(e.getSource() == name)
		{
			System.out.println("here");
			
		}
		
	}

}
