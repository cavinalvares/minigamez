package tetris;

import java.util.Random;
/**
 * This program set and get the tetris block
 * @author 
 *
 */
public class Shape {
	
	 

	    private Tetrominoe pieceShape;
	    private int[][] coords;

	    /**
	     * Creates a new tetris block with no shape
	     */
	    public Shape() {

	        coords = new int[4][2];
	        setShape(Tetrominoe.NoShape);
	    }

	    /**
	     * Sets the shape of the tetris block to a specified shape
	     * @param shape
	     */
	    public void setShape(Tetrominoe shape) {

	        int[][][] coordsTable = new int[][][]{
	                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
	                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
	                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
	                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
	                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
	                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
	                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
	                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
	        };

	        System.arraycopy(coordsTable[shape.ordinal()], 0, coords, 0, 4);

	        pieceShape = shape;
	    }

	    /**
	     * Sets the x coordinate of a square of the tetris block
	     * @param index
	     * @param x
	     */
	    private void setX(int index, int x) {

	        coords[index][0] = x;
	    }

	    /**
	     * Sets the y coordinate of a square of the tetris block
	     * @param index
	     * @param y
	     */
	    private void setY(int index, int y) {

	        coords[index][1] = y;
	    }

	    /**
	     * Gets the x coordinate of a square of the tetris block
	     * @param index
	     * @return
	     */
	    public int x(int index) {

	        return coords[index][0];
	    }

	    /**
	     * Gets the y coordinate of a square of the tetris block
	     * @param index
	     * @return
	     */
	    public int y(int index) {

	        return coords[index][1];
	    }

	    /**
	     * gets the tetris block
	     * @return
	     */
	    public Tetrominoe getShape() {

	        return pieceShape;
	    }

	    /**
	     * Sets  a random shape to be given to the tetris block
	     */
	    public void setRandomShape() {

	        Random r = new Random();
	        int x = Math.abs(r.nextInt()) % 7 + 1;

	        Tetrominoe[] values = Tetrominoe.values();
	        setShape(values[x]);
	    }

	    /**
	     * Return the least square x coordinate in the tetris block
	     * @return
	     */
	    public int minX() {

	        int m = coords[0][0];

	        for (int i = 0; i < 4; i++) {

	            m = Math.min(m, coords[i][0]);
	        }

	        return m;
	    }

	    /**
	     * Return the least square y coordinate in the tetris block
	     * @return
	     */
	    public int minY() {

	        int m = coords[0][1];

	        for (int i = 0; i < 4; i++) {

	            m = Math.min(m, coords[i][1]);
	        }

	        return m;
	    }

	    /**
	     * Rotate the tetris block to left 
	     * @return
	     */
	    public Shape rotateLeft() {

	        if (pieceShape == Tetrominoe.SquareShape) {

	            return this;
	        }

	        Shape result = new Shape();
	        result.pieceShape = pieceShape;

	        for (int i = 0; i < 4; i++) {

	            result.setX(i, y(i));
	            result.setY(i, -x(i));
	        }

	        return result;
	    }

	    /**
	     * Rotate the tetris block to right 
	     * @return
	     */
	    public Shape rotateRight() {

	        if (pieceShape == Tetrominoe.SquareShape) {

	            return this;
	        }

	        Shape result = new Shape();
	        result.pieceShape = pieceShape;

	        for (int i = 0; i < 4; i++) {

	            result.setX(i, -y(i));
	            result.setY(i, x(i));
	        }

	        return result;
	    }
}
