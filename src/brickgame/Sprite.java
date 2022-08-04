package brickgame;

import java.awt.Image;
import java.awt.Rectangle;
/**
 * Initializes the sprite
 * @author 
 *
 */
public class Sprite {
	 	int x;
	    int y;
	    int initialX;
	    int initialY;
	    public static int offset_width;
	    public static int offset_height;
	    int imageWidth;
	    int imageHeight;
	    Image image;
	    public static int b_width;

	    /**
	     * Set the x-coordinate of the sprite
	     * @param x
	     */
	    public void setX(int x) {

	        this.x = x;
	    }

	    /**
	     * Get the x-coordinate of the sprite
	     * @return
	     */
	    public int getX() {

	        return x;
	    }

	    /**
	     * Set the y-coordinate of the sprite
	     * @param y
	     */
	    public void setY(int y) {

	        this.y = y;
	    }

/**
 * Get the y-coordinate of the sprite
 * @return
 */
	    public int getY() {

	        return y;
	    }
	    
	    /**
	     * Get the image width of the sprite
	     * @return
	     */
	    public int getImageWidth() {

	        return imageWidth;
	    }

	    /**
	     * Get the image height of the sprite
	     * @return
	     */
	    public int getImageHeight() {

	        return imageHeight;
	    }

	    /**
	     * Get the image of the sprite
	     * @return
	     */
	    public Image getImage() {

	        return image;
	    }

/**
 * Get the rectangular shape of the sprite
 * @return
 */
	    public Rectangle getRect() {

	        return new Rectangle(x, y,
	                image.getWidth(null), image.getHeight(null));
	    }
	    /**
	     * Get the image dimensions of the sprite
	     */

	    void getImageDimensions() {

	        imageWidth = image.getWidth(null);
	        imageHeight = image.getHeight(null);
	    }
}
