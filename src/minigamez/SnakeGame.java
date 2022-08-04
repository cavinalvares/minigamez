package minigamez;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/**
 * 	This program runs the Snake Game
 * @author
 *
 */
public class SnakeGame extends Panel implements ActionListener, AllGames, KeyListener{

	private int B_WIDTH;
    private int B_HEIGHT;
    private int OFFSET_WIDTH;
    private int OFFSET_HEIGHT;
    private int SCORE = 0;
    private int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private int RAND_POS_x = 29;
    private int RAND_POS_y = 49;
    private int delay = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    public Window window; 
    
    /**
     * This method is used to initialize the variables and run method that starts the game
     */
    public SnakeGame() {
    	
    	super();
    	window = Window.getWindow();
    	window.requestFocus();
		B_WIDTH = 300;
		B_HEIGHT = 500;
	
		if(Window.mode == 0)
		{
			OFFSET_WIDTH = 150;
			OFFSET_HEIGHT = 50;
		}
		else
		{
		    RAND_POS_x = 59;
		    RAND_POS_y = 39;
    		B_WIDTH = 900;
    		B_HEIGHT = 600;
			OFFSET_HEIGHT = 60;
			OFFSET_WIDTH = (int)(this.getWidth()-900)/2-(((int)this.getWidth()-900)/2)%15;
		}

		window.addKeyListener(this);
		
		window.add(this);
		initBoard();
    
    }
 
    /**
	 * This programs is called whenever the user resizes the screen
	 * Running this program will create a new game
	 * @author Jedidah
	 * 
	 * 
	 */
    @Override
  	public AllGames resized(Window window, AllGames old_game) {
      SnakeGame new_game = new SnakeGame();
      new_game.timer.stop();
      SnakeGame game = (SnakeGame)old_game;
     
      new_game.setVisible(false);
      window.removeKeyListener(new_game);
      
      
      return new_game;
      
  	}
 
    /**
	 * This method initiates the playable area of the game
	 */
    private void initBoard() {
        loadImages();
        initGame();
    }

    /**
     * This program loads all the images
     */
    private void loadImages() {
    	
    	if(Window.mode == 0)
    	{
        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    	}
    	else
    	{
    		DOT_SIZE = 15;
    		ImageIcon iid = new ImageIcon("src/resources/dotb.png");
            ball = iid.getImage();

            ImageIcon iia = new ImageIcon("src/resources/appleb.png");
            apple = iia.getImage();

            ImageIcon iih = new ImageIcon("src/resources/headb.png");
            head = iih.getImage();
    	}
    }

    /**
	 * This method initiates the game objects
	 */
    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = (OFFSET_WIDTH+60) - z * DOT_SIZE;
            y[z] = (OFFSET_HEIGHT+60);
        }
        
        locateApple();

        timer = new Timer(delay, this);
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
     * @param g2d
     */
    public void drawBoundary(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	
    	g2d.setPaint(Color.GREEN);
    	for(int x=OFFSET_WIDTH; x<B_WIDTH+OFFSET_WIDTH; x=x+25)
    	{
    		if(x+25 >= B_WIDTH+OFFSET_WIDTH)
    		{
    			g2d.fillRect(x, 40, 25, 10);
        		g2d.fillRect(x, B_HEIGHT+OFFSET_HEIGHT, 25, 10);
    		}
    		else
    		{
    			g2d.fillRect(x, 40, 20, 10);
    			g2d.fillRect(x, B_HEIGHT+OFFSET_HEIGHT, 20, 10);
    		}
    	}
    	
    	for(int y=40; y<B_HEIGHT+OFFSET_HEIGHT; y=y+25)
    	{
    			g2d.fillRect(OFFSET_WIDTH-10, y, 10, 20);
        		g2d.fillRect(B_WIDTH+OFFSET_WIDTH, y, 10, 20);
    	}
    }
    
    /**
     * This method draws/paints the objects(ball, paddle, bricks)
     * @param g2d
     */
    private void doDrawing(Graphics g) {
        
        if (inGame) {
        
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver();
        }        
    }

    /**
     * This method is used to stop the game after winning all level or losing the current level
     * And calls the gameOver class
     */
    private void gameOver() {
        
    	this.setVisible(false);
    	window.remove(this);
    	window.removeKeyListener(this);
    	timer.stop();
    	new GameOver(SCORE, "Snake Game");
    }

    /**
     * This method checks if the snake has eaten the apple
     * Then increases the score, body dot and calls the method to create a new apple
     */
    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            SCORE++;
            if(delay > 100)
            	delay -= 5;
            locateApple();
            timer.setDelay(delay);
        }
    }
    
    /**
     * This method draws/paints the score on the screen
     * @param g
     */
    private void score(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	
    	String score = "Score: "+SCORE;
    	String info = "Press ESC to Pause";
    	
    	g2d.setColor(Color.GREEN);
    	g2d.setFont(new Font("Helvetica", Font.BOLD, 20));
    	g2d.drawString(score, OFFSET_WIDTH+B_WIDTH+50, B_HEIGHT/2);
    	g2d.setFont(new Font("Helvetica", Font.PLAIN, 10));
    	g2d.drawString(info, OFFSET_WIDTH+B_WIDTH+50, B_HEIGHT/2+20);
    }

    /**
     * This method handles the movement of the snake
     */
    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    /**
     * This method checks for collision of the snake to itself or the boundary 
     */
    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT+OFFSET_HEIGHT) {
            inGame = false;
        }

        if (y[0] < OFFSET_HEIGHT) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH+OFFSET_WIDTH) {
            inGame = false;
        }

        if (x[0] < OFFSET_WIDTH) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop();
        }
    }

    /**
     * This method creates a apple at a random position
     */
    private void locateApple() {

    	int r = (int) (Math.random() * RAND_POS_x);
        apple_x = (r * DOT_SIZE)+OFFSET_WIDTH; 
        
    	r = (int) (Math.random() * RAND_POS_y);
        apple_y = (r * DOT_SIZE)+OFFSET_HEIGHT;
    }

    /**
     * Runs the game in a loop
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
  
    }
  
    /**
     * Resumes the game
     */
    public void resume() {
    	this.setVisible(true);
    	window.addKeyListener(this);
    	timer.start();
    }
    
 
	
    /**
     * It handles the binding of the key of up, right, down, left and ESC to the panel
     */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
            leftDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
            rightDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if ((key == KeyEvent.VK_UP) && (!downDirection)) {
            upDirection = true;
            rightDirection = false;
            leftDirection = false;
        }

        if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
            downDirection = true;
            rightDirection = false;
            leftDirection = false;
        }
        
        if (key == KeyEvent.VK_ESCAPE) {
        	this.setVisible(false);
        	window.removeKeyListener(this);
        	timer.stop();
			window.add(new Pause(this, 1));
        }
    }

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
