package brickgame;

import javax.swing.ImageIcon;

import minigamez.Window;
/**
 * The program is used to initialize the brick and its coordinates 
 * @author 
 *
 */
public class Brick extends Sprite{
	private boolean destroyed;

	/** 
	 * initializes the brick
	 * 
	 * @param x
	 * @param y
	 */
    public Brick(int x, int y) {

        initBrick(x, y);
    }

    /**
     * initializes the brick coordinates
     * @param x
     * @param y
     */
    private void initBrick(int x, int y) {

        this.x = x;
        this.y = y;

        destroyed = false;

        loadImage();
        getImageDimensions();
    }

    /**
     * loads the brick image
     */
    private void loadImage() {
    	ImageIcon ii = null;
    	if(Window.mode == 0)
    		ii = new ImageIcon("src/resources/brick.png");
    	else
    		ii = new ImageIcon("src/resources/brickb.png");
    	image = ii.getImage();
    }

    /**
     * checks if the brick has been destroyed
     * @return
     */
    public boolean isDestroyed() {

        return destroyed;
    }

    /**
     * Set the destroyed variable to true to indicate that the brick is destroyed
     * @param val
     */
    public void setDestroyed(boolean val) {

        destroyed = val;
    }
}
