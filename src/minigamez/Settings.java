package minigamez;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
/**
 * This program is used to display the panel after the user has clicked on the settings button
 * This program pauses the game
 * @author Jedidiah
 *
 */
public class Settings extends Panel implements ActionListener{

	Window window;
	JButton back, leftButton, rightButton, apply;
	JLabel header, size;
	int mode;
	Pause pause;
	
	/**
	 * This method is invoked when the user accesses the settings from the pause menu
	 * @param pause
	 */
	public Settings(Pause pause) {
		this();
		this.pause = pause;
	}
	
	/**
	 * This method creates the panel, buttons etc;
	 */
	public Settings() {
		super();
		this.window = Window.getWindow();
		mode = Window.mode;
		
		
		header = new JLabel();
		if(Window.mode == 0)
			setLabel(header, "Settings", 175, 50, 250, 50, "Serif", 25, 3);
		else
			setLabel(header, "Settings", (window.getWidth()-400)/2, 100, 400, 75, "Serif", 50, 3);
		
		
		back = new JButton();
		if(Window.mode == 0)
			setButton(back, "Back", 5, 5, 50, 20, 15);
		else
			setButton(back, "Back", 10, 10, 100, 35, 25);
		back.addActionListener(this);
		
		
		leftButton = new JButton();
		if(Window.mode == 0)
			setButton(leftButton, "<=", 200, 150, 20, 20, 13);
		else
			setButton(leftButton, "<=", ((window.getWidth()-400)/2)+25, 200, 40, 40, 25);
		leftButton.addActionListener(this);
		
		
		size = new JLabel();
		String text;
		if(Window.mode == 0)
			text = "600 X 600";
		else
		{
			if(Window.mode == 1)
				text = "Windowed";
			else
				text = "Fullscreen";
		}
		
		if(Window.mode == 0)
			setLabel(size, text, 220, 150, 150, 20, null, 15, 1);
		else
			setLabel(size, text, ((window.getWidth()-400)/2)+65, 200, 265, 40, null, 25, 1);
		
		
		rightButton = new JButton();
		if(Window.mode == 0)
			setButton(rightButton, "=>", 370, 150, 20, 20, 13);
		else
			setButton(rightButton, "=>", ((window.getWidth()-400)/2)+330, 200, 40, 40, 25);
		rightButton.addActionListener(this);
		
		
		apply = new JButton();
		if(Window.mode == 0)
			setButton(apply, "Apply", 225, 200, 150, 40, 20);
		else
			setButton(apply, "Apply", (window.getWidth()-225)/2, 275, 225, 50, 25);
		apply.addActionListener(this);
		
		
		window.add(header);
		window.add(back);
		window.add(leftButton);
		window.add(size);
		window.add(rightButton);
		window.add(apply);
		window.setVisible(true);
	}
	
	/**
	 * This method gives the correct behaviour of each button after they have been clicked
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == back) {
			window.remove(header);
			window.remove(back);
			window.remove(leftButton);
			window.remove(size);
			window.remove(rightButton);
			window.remove(apply);
			back = null;
			header = null;
			leftButton = null;
			size = null;
			rightButton = null;
			apply = null;
			
			this.setVisible(false);
			
			if(pause != null)
			{
				pause.setVisible(true);
				window.addKeyListener(pause);
			}
			else
				window.add(new Menu()); 
		}
		
		if(e.getSource() == leftButton) {
			
			if(mode == 0)
				size.setText("Fullscreen");
			else
			{
				if(mode == 1)
					size.setText("600 x 600");
				else
					size.setText("Windowed");
			}
			
			if(--mode < 0)
				mode = 2;
			
		}
		
		if(e.getSource() ==rightButton) {
			
			if(mode == 0)
				size.setText("Windowed");
			else
			{
				if(mode == 1)
					size.setText("Fullscreen");
				else
					size.setText("600 x 600");
			}
			
			mode = (mode + 1) % 3;
		}
		
		if(e.getSource() == apply && Window.mode != mode) {
		
			Window.mode = mode;
			window.dispose();
			window.setWindow();
			window = Window.getWindow();
			if(pause != null)
			{
				pause = new Pause(pause.game, pause.gameCode);
				window.removeKeyListener(pause);
				pause.setVisible(false);
				AllGames new_game = pause.game.resized(window, pause.game);
				pause.game = new_game;
				window.add((Panel)new_game);
				window.add(pause);
				window.add(new Settings(pause));
			}
			else
				window.add(new Settings());
		
		}
		
		
	}
	
}
