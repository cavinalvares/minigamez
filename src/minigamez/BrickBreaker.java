package minigamez;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import brickgame.Ball;
import brickgame.Brick;
import brickgame.Commons;
import brickgame.Paddle;
import brickgame.Sprite;

/**
 * This Program is used to run the the Brick Breaker Game
 * @author 
 *
 */
public class BrickBreaker extends Panel implements KeyListener, AllGames{
	
	private Window window;
	private Timer timer;
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean inGame = true;
	private int OFFSET_WIDTH;
	private int OFFSET_HEIGHT;
    int INIT_PADDLE_X = 150;
    int INIT_PADDLE_Y = 460;
    int INIT_BALL_X = 180;
    int INIT_BALL_Y = 455;
	private int Score;
	int N_OF_BRICKS;
	private int defaultScore;
	int lvl;
	private int B_WIDTH;
	
	/**
	 * This method is used to initialize the variables and run method that starts the game
	 * @param lvl
	 * @param score
	 */
	BrickBreaker(int lvl,int score){
		super();
		window = Window.getWindow();
		this.lvl=lvl;
		defaultScore=score;
		//window.setSize(300, 500);
		window.requestFocus();
		B_WIDTH = 300;
		//B_HEIGHT = 500;
    	
		if(Window.mode == 0)
			OFFSET_WIDTH = 150;
		else
		{
			B_WIDTH = 900;
			OFFSET_WIDTH = (int)(this.getWidth()-900)/2;
		}
		
		
		
		OFFSET_HEIGHT = 50;
		Sprite.offset_width = OFFSET_WIDTH;
		Sprite.offset_height = OFFSET_HEIGHT;
		Sprite.b_width = B_WIDTH;
		
		window.addKeyListener(this);
		
		window.add(this);
        gameInit();
	}
	
	/**
	 * This method select the current level layout 
	 * @param lvl
	 */
	private void levelSelector(int lvl) {
		
		int row = 0;
		int column = 0;
		if(lvl==1)
			if(Window.mode == 0)
			{
				row = 5;
				column = 6;
				N_OF_BRICKS=30;
			}
			else {
				row = 10;
				column = 17;
				N_OF_BRICKS=170;
			}
		else
			if(Window.mode == 0)
			{
				row = 8;
				column = 6;
				N_OF_BRICKS=48;
			}
			else {
				row = 10;
				column = 17;
				N_OF_BRICKS=170;
			}
		
		bricks = new Brick[N_OF_BRICKS];
	        
	    ball = new Ball(OFFSET_WIDTH+INIT_BALL_X, OFFSET_HEIGHT+INIT_BALL_Y);
	    paddle = new Paddle(OFFSET_WIDTH+INIT_PADDLE_X, OFFSET_HEIGHT+INIT_PADDLE_Y);
	       
	    int imageWidth;
	    int imageHeight;
	    
	    if(Window.mode == 0) {
	    	imageWidth = 40;
	    	imageHeight = 10;
	    }
	    else
	    {
	    	imageWidth = 50;
	    	imageHeight = 20;
	    	
	    }
	    	
	    
	    int k = 0;
       //Level 1
	    if(lvl==1)
        {
        	for (int i = 0; i < row; i++) {

                for (int j = 0; j < column; j++) {

                    bricks[k] = new Brick(j * imageWidth + OFFSET_WIDTH+30, i * imageHeight + OFFSET_HEIGHT+60);
                    k++;
                }
        	}
        }
        //level 2
       if(lvl==2) { 
        for(int i=0;i<row;i++)
		{
			if(i<=row-3) {
				for(int j=0;j<column;j++)
				{	
					bricks[k] = new Brick(j * imageWidth + OFFSET_WIDTH+30, i * imageHeight + OFFSET_HEIGHT+60);
                	k++;
				}
			}
			else
			{
				for(int j=0;j<column;j++)
				{
					if((j>=column/2-1)&&(j<column/2+1))
					{
						bricks[k] = new Brick(j * imageWidth + OFFSET_WIDTH+30, i * imageHeight + OFFSET_HEIGHT+60);
						bricks[k].setDestroyed(true);
						
						k++;
					}	
					else
					{
						bricks[k] = new Brick(j * imageWidth + OFFSET_WIDTH+30, i * imageHeight + OFFSET_HEIGHT+60);
	                	k++;
					}
				}
			}
		} //for closing
	 }//if closing
   

       //level 3
      if(lvl==3) { 
       for(int i=0;i<row;i++)
		{
			if((i<row/2+2)&&(i>=row/2-2)) {
				for(int j=0;j<column;j++)
				{	
					bricks[k] = new Brick(j * imageWidth + OFFSET_WIDTH+30, i * imageHeight + OFFSET_HEIGHT+60);
					k++;
				}
			}
			else
			{
				for(int j=0;j<column;j++)
				{
					if((j>=column/2-1)&&(j<column/2+1))
					{
						bricks[k] = new Brick(j * imageWidth + OFFSET_WIDTH+30, i * imageHeight + OFFSET_HEIGHT+60);
						bricks[k].setDestroyed(true);
						
						k++;
					}	
					else
					{
						bricks[k] = new Brick(j * imageWidth + OFFSET_WIDTH+30, i * imageHeight + OFFSET_HEIGHT+60);
	                	k++;
					}
				}
			}
		} //for closing
	 }//if closing 
    }
	
	/**
	 * This programs is called whenever the user resizes the screen
	 * Running this program will create a new game
	 * @author Jedidah
	 * 
	 * 
	 */
	public AllGames resized(Window window, AllGames old_game) {
		BrickBreaker new_game = new BrickBreaker(1,0);
		new_game.timer.stop();
		BrickBreaker game = (BrickBreaker)old_game;
	     
	      new_game.setVisible(false);
	      window.removeKeyListener(new_game);
	      
	      
	      return new_game;
	}
	
	/**
	 * This method initiates the playable area of the game
	 */


	/**
	 * This method initiates the game objects
	 */
    private void gameInit() {

    	
        levelSelector(lvl);
        
        timer = new Timer(Commons.PERIOD, new GameCycle());
        timer.start();
    }

    /**
     * This method paints/draws the components/objects on the playable area
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (inGame) {
        	drawBoundary(g2d);
            drawObjects(g2d);
            score(g2d);
        } else {

            gameFinished();
        }

        Toolkit.getDefaultToolkit().sync();
    }
    
    /**
     * This method paints/draws the boundary 
     * @param g2d
     */
    public void drawBoundary(Graphics2D g2d) {
    	
    	g2d.setPaint(Color.GREEN);
    	for(int x=OFFSET_WIDTH; x<B_WIDTH+OFFSET_WIDTH; x=x+25)
    	{
    		if(x+25 >= B_WIDTH+OFFSET_WIDTH)
    		{
    			g2d.fillRect(x, 40, 25, 10);
        		g2d.fillRect(x, Commons.HEIGHT+OFFSET_HEIGHT, 25, 10);
    		}
    		else
    		{
    			g2d.fillRect(x, 40, 20, 10);
    			g2d.fillRect(x, Commons.HEIGHT+OFFSET_HEIGHT, 20, 10);
    		}
    	}
    	
    	for(int y=40; y<Commons.HEIGHT+OFFSET_HEIGHT; y=y+25)
    	{
    			g2d.fillRect(OFFSET_WIDTH-10, y, 10, 20);
        		g2d.fillRect(B_WIDTH+OFFSET_WIDTH, y, 10, 20);
    	}
    }

    /**
     * This method draws/paints the objects(ball, paddle, bricks)
     * @param g2d
     */
    private void drawObjects(Graphics2D g2d) {

        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                ball.getImageWidth(), ball.getImageHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                paddle.getImageWidth(), paddle.getImageHeight(), this);

        for (int i = 0; i < N_OF_BRICKS; i++) {

            if (!bricks[i].isDestroyed()) {

                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), this);
            }
        }
        
    }

    
    /**
     * This method draws/paints the score on the screen
     * @param g2d
     */
    private void score(Graphics2D g2d) {
    	
    	String score = "Score: "+Score;
    	String info = "Press ESC to Pause";
    	
    	g2d.setColor(Color.GREEN);
    	g2d.setFont(new Font("Helvetica", Font.PLAIN, 20));
    	g2d.drawString("Lvl:"+lvl, OFFSET_WIDTH+B_WIDTH+50, 230);
    	g2d.setFont(new Font("Helvetica", Font.BOLD, 20));
    	g2d.drawString(score, OFFSET_WIDTH+B_WIDTH+50, 250);
    	g2d.setFont(new Font("Helvetica", Font.PLAIN, 10));
    	g2d.drawString(info, OFFSET_WIDTH+B_WIDTH+50, 270);
    }
    
    /**
     * This method either takes the user to a new level or ends the game after winning/losing the current level
     * @param g2d
     */
    private void gameFinished() {

    	if(lvl==3)
    		gameOver();
    	else
    		new BrickBreaker(lvl+1,Score);
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
     * This method moves the ball and paddle
     * It also checks for collisions
     * And repaints the objects/components
     */
    private void doGameCycle() {

        ball.move();
        paddle.move();
        checkCollision();
        repaint();
    }

    /**
     * This method is used to stop the game after winning all level or losing the current level
     * And calls the gameOver class
     */
 private void gameOver() {
	 	
	 	timer.stop();
    	this.setVisible(false);
    	window.remove(this);
    	window.removeKeyListener(this);
    	timer.stop();
    	new GameOver(Score, "Brick Break");
    }

 	/**
 	 * This program is used to check the collisions of the ball with the paddle, boundary, bricks and after the paddle
 	 */
    private void checkCollision() {

        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE+OFFSET_HEIGHT) {

        	gameOver();
        }

        for (int i = 0, j = 0; i < N_OF_BRICKS; i++) {

            if (bricks[i].isDestroyed()) {

                j++;
            }
            Score = j+defaultScore;
            if(lvl==2)
            {
            	Score=Score-4;
            }
            
            if(lvl==3)
            {
            	Score=Score-8;
            }
            
            if (j == N_OF_BRICKS) {

            	gameOver();
            }
        }

        if ((ball.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;
            if (ballLPos < first) {

                ball.setXDir(-1);
                ball.setYDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {

                //ball.setXDir(-1);
                ball.setYDir(-1);
            }

            if (ballLPos >= second && ballLPos < third) {

                ball.setXDir(0);
                ball.setYDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {

                //ball.setXDir(1);
                ball.setYDir(-1);
            }

            if (ballLPos > fourth) {

                ball.setXDir(1);
                ball.setYDir(-1);
            }
        }

        for (int i = 0; i < N_OF_BRICKS; i++) {

            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed()) {

                    if (bricks[i].getRect().contains(pointRight)) {

                        ball.setXDir(-1);
                    } else if (bricks[i].getRect().contains(pointLeft)) {

                        ball.setXDir(1);
                    }

                    if (bricks[i].getRect().contains(pointTop)) {

                        ball.setYDir(1);
                    } else if (bricks[i].getRect().contains(pointBottom)) {

                        ball.setYDir(-1);
                    }

                    bricks[i].setDestroyed(true);
                }
            }
        }
    }

    /**
     * This method is used to bind the keys to the current panel
     * It is used to bring the pause window after clicking the ESC button
     */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		paddle.keyPressed(e);
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
        	this.setVisible(false);
        	window.removeKeyListener(this);
        	timer.stop();
			new Pause(this, 2);
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		paddle.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This program is used to resume the paused game
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		this.setVisible(true);
    	window.addKeyListener(this);
    	timer.start();
	}

	

}
