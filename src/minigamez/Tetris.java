package minigamez;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.Timer;

import tetris.Shape;
import tetris.Tetrominoe;
/**
 * This Program is used to run the the Tetris Game
 * @author 
 *
 */
public class Tetris extends Panel implements AllGames{
	
	private int columns = 27;
    private final int rows = 30;
    private int period_interval = 300;

    private Timer timer;
    private boolean isFallingFinished = false;
    private int numLinesRemoved = 0;
    private int curX = 0;
    private int curY = 0;
    private Shape curPiece;
    private Tetrominoe[] board;
	
	private Window window;
	private int B_WIDTH;
	private int B_HEIGHT;
	private int offset_row;
	private int offset_left;
	private int offset_right;
	private TAdapter ka;

	/**
	 * This method is used to initialize the variables and run method that starts the game
	 */
	Tetris(){
		super();
    	window = Window.getWindow();

    	window.requestFocus();
		B_WIDTH = 500;
		B_HEIGHT = 500;
	
		if(Window.mode == 0)
		{
			offset_left = 3;
			offset_right = 4;
			offset_row = 3;
		}
		else
		{
			offset_left = 10;
			offset_right = 4;
			B_WIDTH = 1200;
			columns = 62;
			offset_row = 9;
		
		}
		
		window.add(this);
		initBoard();
	}
	
	@Override
	/**
	 * This programs is called whenever the user resizes the screen
	 * Running this program will create a new game
	 * @author Jedidah
	 */
	 
	public AllGames resized(Window window, AllGames old_game) {
		Tetris new_game = new Tetris();
		new_game.timer.stop();
		Tetris game = (Tetris)old_game;
	      
	     
	      new_game.setVisible(false);
	      window.removeKeyListener(new_game.ka);
	      timer.stop();
	      
	      return new_game;
	}
	
	/**
	 * This method initiates the game objects
	 */
	private void initBoard() {

        setFocusable(true);
        ka = new TAdapter();
        window.addKeyListener(ka);
        start();
    }

	/**
	 * this method computes the width of the square of the tetris block
	 * @return returns the computed width
	 */
    private int squareWidth() {

        return (int)  B_WIDTH / columns;
    }

    private int squareHeight() {

        return (int) B_HEIGHT / (rows-offset_row);
    }

    private Tetrominoe shapeAt(int x, int y) {

        return board[(y * columns) + x];
    }

    /**
     * Starts the game
     */
    void start() {

        curPiece = new Shape();
        board = new Tetrominoe[columns * rows];

        clearBoard();
        newPiece();

        timer = new Timer(period_interval, new GameCycle());
        timer.start();
    }

    /**
     * Pauses the game
     */
    private void pause() {

    	this.setVisible(false);
    	window.removeKeyListener(ka);
    	timer.stop();
		new Pause(this, 3);
        

        repaint();
    }
    
    /**
     * resumes the game
     */
    @Override
	public void resume() {
		this.setVisible(true);
        window.addKeyListener(ka);
    	timer.start();
	}

    @Override
    /**
     * This method paints/draws the components/objects on the playable area
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        drawBoundary(g);
        doDrawing(g);
        score(g);
    }
    
    /**
     * This method paints/draws the boundary 
     * @param g
     */
    public void drawBoundary(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	
    	g2d.setPaint(Color.GREEN);
    	int offset_width_left = offset_left*squareWidth();
    	int offset_width_right = offset_right*squareWidth();
    	int offset_height = offset_row*squareHeight();
    	for(int x=offset_width_left-1; x< B_WIDTH-offset_width_right; x=x+25)
    	{
    		if(x+25 >= B_WIDTH-offset_width_right)
    		{
    			if(Window.mode == 0) {
    				g2d.fillRect(x-10, B_HEIGHT+offset_width_left-5, 32, 12);
        			g2d.fillRect(x, 40, 20, 10);
    			}
    			else
    			{
        			g2d.fillRect(x, 40, 0, 10);
    				g2d.fillRect(x-9, B_HEIGHT+offset_height, 10, 12);
    			}
    		}
    		else
    		{
    			g2d.fillRect(x, 40, 20, 10);
    			if(Window.mode == 0)
    				g2d.fillRect(x-10, B_HEIGHT+offset_height-5, 20, 12);
    			else
    				g2d.fillRect(x-10, B_HEIGHT+offset_height, 20, 12);
    		}
    	}
    	
    	for(int y=40; y<B_HEIGHT+offset_height-5; y=y+25)
    	{
    			g2d.fillRect(offset_width_left-11, y, 10, 20);
    			if(Window.mode == 0)
    				g2d.fillRect(B_WIDTH-offset_width_right-13, y, 10, 20);
    			else
    				g2d.fillRect(B_WIDTH-offset_width_right-19, y, 10, 20);
    		
    	}
    	
    }

    /**
     * his method draws/paints the objects
     * @param g
     */
    private void doDrawing(Graphics g) {

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - rows * squareHeight();

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {
                Tetrominoe shape = shapeAt(j, rows - i - 1);
                if (shape != Tetrominoe.NoShape) {

                    drawSquare(g, j * squareWidth(),
                            boardTop + i * squareHeight(), shape);
                }
            }
        }

        if (curPiece.getShape() != Tetrominoe.NoShape) {

            for (int i = 0; i < 4; i++) {

                int x = curX + curPiece.x(i);
                int y = curY - curPiece.y(i);

                drawSquare(g, x * squareWidth(),
                        boardTop + (rows - y - 1) * squareHeight(),
                        curPiece.getShape());
            }
        }
    }
    /**
     * This method draws/paints the score on the screen
     * @param g
     */
    private void score(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	
    	String score = "Score: "+numLinesRemoved;
    	String info = "Press ESC to Pause";
    	
    	g2d.setColor(Color.GREEN);
    	g2d.setFont(new Font("Helvetica", Font.BOLD, 20));
    	g2d.drawString(score, B_WIDTH-50, 250);
    	g2d.setFont(new Font("Helvetica", Font.PLAIN, 10));
    	g2d.drawString(info, B_WIDTH-50, 270);
    }

    /**
     *  this methods drops the tetris block down
     */
    private void dropDown() {

        int newY = curY;

        while (newY > 0) {

            if (!tryMove(curPiece, curX, newY - 1)) {

                break;
            }

            newY--;
        }

        pieceDropped();
    }


    /**
     *  this methods drops the tetris block line by line
     */
    private void oneLineDown() {

        if (!tryMove(curPiece, curX, curY - 1)) {

            pieceDropped();
        }
    }

    /**
     * Clears the board by putting no shape 
     */
    private void clearBoard() {

        for (int i = 0; i < rows * columns; i++) {

            board[i] = Tetrominoe.NoShape;
        }
    }

    /**
     * 
     */
    private void pieceDropped() {

        for (int i = 0; i < 4; i++) {

            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * columns) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished) {

            newPiece();
        }
    }

    /**
     * Creates a new tetris block
     */
    private void newPiece() {

        curPiece.setRandomShape();
        curX = columns / 2 + 1;
        curY = rows - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {

            curPiece.setShape(Tetrominoe.NoShape);
            this.setVisible(false);
        	window.removeKeyListener(ka);
        	timer.stop();

            new GameOver(numLinesRemoved, "Tetris");
        }
    }

    /**
     * moves the tetris block left or right
     * @param newPiece
     * @param newX
     * @param newY
     * @return
     */
    private boolean tryMove(Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 4; i++) {

            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);

            if (x < offset_left || x >= columns-offset_right || y < 3|| y > rows+offset_row) {

                return false;
            }

            if (shapeAt(x, y) != Tetrominoe.NoShape) {

                return false;
            }
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;

        repaint();

        return true;
    }

    /**
     * finds and removes full lines
     */
    private void removeFullLines() {

        int numFullLines = 0;

        for(int i = rows - 1; i >= 0; i--) {

            boolean lineIsFull = true;

            for(int j = offset_left; j < columns-offset_right; j++) {

                if (shapeAt(j, i) == Tetrominoe.NoShape) {

                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {

                numFullLines++;

                for(int k = i; k < rows - 1; k++) {
                    for(int j = 0; j < columns; j++) {
                        board[(k * columns) + j] = shapeAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {

            numLinesRemoved += numFullLines;
           
            isFallingFinished = true;
            curPiece.setShape(Tetrominoe.NoShape);
        }
    }

    /*
     * Draw the square of the tetris block
     */
    private void drawSquare(Graphics g, int x, int y, Tetrominoe shape) {

        Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(204, 204, 102), new Color(204, 102, 204),
                new Color(102, 204, 204), new Color(218, 170, 0)
        };

        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    /*
     * This class contains the action listener and invokes the actionPerformed every time(5 millisecond)
     */
    private class GameCycle implements ActionListener {
    	/**
    	 * The doGameCycle will be called every 5 milliseconds 
    	 */
        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }

		
    }
    /*
     * And repaints the objects/components
     */
    private void doGameCycle() {

        update();
        repaint();
    }

    /**
     * updates the game every time and checks if the tetris block is still dropping or not
     */
    private void update() {


        if (isFallingFinished) {

            isFallingFinished = false;
            newPiece();
        } else {

            oneLineDown();
        }
    }

    /**
     * This program is used to bind the keys to the current panel
     * @author 
     *
     */
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (curPiece.getShape() == Tetrominoe.NoShape) {

                return;
            }

            int keycode = e.getKeyCode();

            // Java 12 switch expressions
            switch (keycode) {

                case KeyEvent.VK_ESCAPE: pause();break;
                case KeyEvent.VK_LEFT: tryMove(curPiece, curX - 1, curY);break;
                case KeyEvent.VK_RIGHT: tryMove(curPiece, curX + 1, curY);break;
                case KeyEvent.VK_DOWN: tryMove(curPiece.rotateRight(), curX, curY);break;
                case KeyEvent.VK_UP: tryMove(curPiece.rotateLeft(), curX, curY);break;
                case KeyEvent.VK_SPACE: dropDown();break;
                case KeyEvent.VK_D: oneLineDown();break;
            }
        }
    }

	
	
}
