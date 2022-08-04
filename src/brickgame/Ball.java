package brickgame;

import javax.swing.ImageIcon;
/**
 * The program is used to initialize the ball, its coordinates and the directions 
 * @author 
 *
 */

public class Ball extends Sprite{
	private int xdir;
    private int ydir;

    /**
     * Initializing the coordinates
     * @param x
     * @param y
     */
    public Ball(int x, int y) {
    	initialX = x;
    	initialY = y;
        initBall();
    }

    /**
     * sets the direction of the ball
     * 
     */
    private void initBall() {

        xdir = -1;
        ydir = 1;

        loadImage();
        getImageDimensions();
        resetState();
    }

    /**
     * loads the image of the ball
     */
    private void loadImage() {
    	ImageIcon ii = null;
    	//if(Window.mode == 0)
    	ii = new ImageIcon("src/resources/ball.png");
    	
        image = ii.getImage();
    }

    /**
     * this method handles the movement of the ball
     */
    public void move() {

        x += xdir;
        y += ydir;

        if (x == offset_width) {

            setXDir(1);
        }

        if (x == b_width+offset_width - imageWidth) {

            setXDir(-1);
        }

        if (y == offset_height) {

            setYDir(1);
        }
    }

    /**
     * this method resets the ball
     */
    private void resetState() {

        x = initialX;
        y = initialY;

    }
    
    /**
     * Sets the x dir of the ball
     * @param x
     */
    public void setXDir(int x) {

        xdir = x;
    }

    /**
     * Sets the y dir of the ball
     * @param y
     */
    public void setYDir(int y) {

        ydir = y;
    }

    /**
     * Gets the x dir of the ball
     * @return
     */
    public int getXDir() {

        return xdir;
    }
    
    /**
     * Gets the y dir of the ball
     * @return
     */
    public int getYDir() {

        return ydir;
    }
}
