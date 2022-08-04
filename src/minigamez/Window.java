package minigamez;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
/**
 * This Program creates the window/screen of the application
 * @author Jedidiah
 *
 */
public class Window extends JFrame{

	public static int mode = 0;
	private static Window window;
	
	/**
	 * This method initializes the window
	 */
	Window(){
		
		this.setTitle("MiniGamez");
		this.setMode();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//ImageIcon image = new ImageIcon("");
		//this.setIconImage(image.getImage());
		//this.getContentPane().setBackground(Color.BLACK);
		
		this.setLayout(null);

		this.setVisible(true);
		
		if(mode == 1)
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	}
	
	/**
	 * This method sets the mode(dimensions of the screen)
	 */
	public void setMode(){
		if(mode == 0 || mode == 1)
		{
			setSize(600, 600);
			setUndecorated(false);
		}
		else
		{
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setUndecorated(true);
			
		}
	}
	
	/**
	 * This method gets the window objets 
	 */
	
	public static Window getWindow(){
		if(window == null)
			window = new Window();
		
		return window;
	}

	/**
	 * This method sets the window objets 
	 */
	public void setWindow(){
		window = null;
		
	}
}
