package brickgame;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import minigamez.Pause;
/**
 * The program is used to initialize the ball, its coordinates and the directions 
 * @author 
 *
 */
public class Paddle extends Sprite{
	private int dx;

	/**
    * Initializing the coordinates
    * @param x
    * @param y
    */
    public Paddle(int x, int y) {
    	initialX = x;
    	initialY = y;
        initPaddle();
    }

    /**
     * initializes the paddle
     */
    private void initPaddle() {

        loadImage();
        getImageDimensions();

        resetState();
    }

    /**
     * loads the image of the paddle
     */
    private void loadImage() {

    	ImageIcon ii = new ImageIcon("src/resources/paddle.png");
        image = ii.getImage();
    }

    /**
     *  handles the movement of the paddle
     */
    public void move() {

        x += dx;

        if (x <= offset_width) {

            x = offset_width;
        }

        if (x >= b_width+offset_width - imageWidth) {

            x = b_width+offset_width - imageWidth;
        }
    }
    
    /**
     * Sets the x dir of the paddle
     * @param dx
     */
    public void setXDir(int dx) {
    	this.dx = dx;
    }
    
    /**
     * Gets the x dir of the ball
     * @return
     */
    public int getXDir() {
    	return dx;
    }

    /**
     * Key bindings for the pressed buttons(left and right)
     * @param e
     */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 1;
        }
        
        
    }
    /**
     * Key bindings for the released buttons(left and right)
     * @param e
     */
     public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }

     /**
      * Resets the state(coordinates) of the paddle
      */
    private void resetState() {

    	  x = initialX;
          y = initialY;
    }
}
