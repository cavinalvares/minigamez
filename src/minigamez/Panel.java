package minigamez;


import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
/**
 * This program is used to give a jpanel/panel to a jframe/window
 * @author Jedidiah
 *
 */
public class Panel extends JPanel{
	
	/**
	 * Sets the panel to give its dimensions and background color
	 */
	public Panel() {

		this.setBackground(Color.BLACK);
		this.setSize((Window.getWindow()).getWidth(), (Window.getWindow()).getHeight());
		this.setLayout(null);
	}
	
	/**
	 * initializes the jlabel/label
	 * @param label The label
	 * @param text The text that the label should have
	 * @param left The x coordinate of the label
	 * @param down The y coordinate of the label
	 * @param width The width of the label
	 * @param heigth The height of the label
	 * @param fontStyle The font style 
	 * @param fontSize The font size
	 * @param borderWidth The border size
	 */
	public void setLabel(JLabel label, String text, int left, int down, int width, int heigth, String fontStyle, int fontSize, int borderWidth) {
		Border border = BorderFactory.createLineBorder(Color.GREEN, borderWidth);
		
		label.setText(text);
		label.setForeground(Color.GREEN);
		if(borderWidth == 1)
			label.setFont(new Font(fontStyle, Font.PLAIN, fontSize));
		else
			label.setFont(new Font(fontStyle, Font.BOLD, fontSize));
		label.setBounds(left, down, width, heigth);	
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(border);
		
	}
	
	/**
	 * initializes the jbutton/button
	 * @param button The Button
	 * @param text The text that the label should have
	 * @param left The x coordinate of the label
	 * @param down The y coordinate of the label
	 * @param width The width of the label
	 * @param heigth The height of the label
	 * @param fontSize  The font size
	 */
	public void setButton(JButton button, String text, int left, int down, int width, int heigth, int fontSize) {
		
		button.setText(text);
		button.setBounds(left, down, width, heigth);
		button.setFont(new Font(null, Font.PLAIN, fontSize));
		button.setFocusable(false);
		button.setBackground(Color.BLACK);
		button.setForeground(Color.GREEN);
		button.setBorder(BorderFactory.createLineBorder(Color.GREEN));
	}
	
	
	
}
