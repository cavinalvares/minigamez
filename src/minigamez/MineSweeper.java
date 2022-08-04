package minigamez;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
/**
 * This Program is used to run the the Mine sweeper Game
 * @author 
 *
 */
public class MineSweeper extends Panel implements ActionListener, KeyListener, AllGames{
	
	private Window window;
	private int DECO_OFFSET = 25;
	
	private final int NUM_IMAGES = 13;
    private int CELL_SIZE = 20;

    
    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    private final int N_MINES = 40;
    private final int N_ROWS = 16;
    private final int N_COLS = 16;

    private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
    private final int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    private int allCells;
	private int OFFSET_WIDTH;
	private int B_WIDTH;
	private int B_HEIGHT;
	private int OFFSET_HEIGHT;
	private Timer timer;
	private int sec = 0;
	private int dumsec = 0;
	private int min = 0;
	private JLabel counterLabel;
	private DecimalFormat dformat;
	private MinesAdapter ma;
	boolean win = false;
	
	/**
	 * This method is used to initialize the variables and run method that starts the game
	 */
	MineSweeper(){
		super();
		window = Window.getWindow();

		window.requestFocus();

		if(Window.mode != 0)
			CELL_SIZE = 30;
		if(Window.mode == 2)
			DECO_OFFSET = 0;
		
		
		B_WIDTH = N_COLS*CELL_SIZE;
		B_HEIGHT = N_ROWS*CELL_SIZE;

		OFFSET_WIDTH = (window.getWidth()-B_WIDTH)/2;
		OFFSET_HEIGHT = (window.getHeight()-B_WIDTH)/2;
		
		
		dformat = new DecimalFormat("00");
		
		counterLabel = new JLabel();
		this.setLabel(counterLabel, dformat.format(min)+":"+dformat.format(sec), OFFSET_WIDTH+B_WIDTH/2-40, OFFSET_HEIGHT-80, 80, 100, "Arial", 20, 0);
		this.add(counterLabel);

		window.addKeyListener(this);
		window.add(this);
		initBoard();
	}
	
	/**
	 * This method initiates the game objects
	 */
	 private void initBoard() {


	        img = new Image[NUM_IMAGES];

	        for (int i = 0; i < NUM_IMAGES; i++) {

	        	String path;
	            if(Window.mode == 0)
	            	path = "src/resources/" + i + ".png";
	            else
	            	path = "src/resources/" + i + "b.png";
	            
	            img[i] = (new ImageIcon(path)).getImage();
	        }

	        ma = new MinesAdapter();
	        window.addMouseListener(ma);
	        newGame();
	        timer = new Timer(1000, this);
	        timer.start();
	    }
	 
	 /**
	  * Creates a new game
	  */
	    private void newGame() {

	        int cell;

	        Random random = new Random();
	        inGame = true;
	        minesLeft = N_MINES;

	        allCells = N_ROWS * N_COLS;
	        field = new int[allCells];

	        for (int i = 0; i < allCells; i++) {

	            field[i] = COVER_FOR_CELL;
	        }


	        int i = 0;

	        while (i < N_MINES) {

	            int position = (int) (allCells * random.nextDouble());

	        	
	            if ((position < allCells) && (field[position] != COVERED_MINE_CELL)) {
	                int current_col = position % N_COLS;
	                field[position] = COVERED_MINE_CELL;
	                i++;

	                if (current_col > 0) {
	                    cell = position - 1 - N_COLS;
	                    if (cell >= 0) {
	                        if (field[cell] != COVERED_MINE_CELL) {
	                            field[cell] += 1;
	                        }
	                    }
	                    cell = position - 1;
	                    if (cell >= 0) {
	                        if (field[cell] != COVERED_MINE_CELL) {
	                            field[cell] += 1;
	                        }
	                    }

	                    cell = position + N_COLS - 1;
	                    if (cell < allCells) {
	                        if (field[cell] != COVERED_MINE_CELL) {
	                            field[cell] += 1;
	                        }
	                    }
	                }

	                cell = position - N_COLS;
	                if (cell >= 0) {
	                    if (field[cell] != COVERED_MINE_CELL) {
	                        field[cell] += 1;
	                    }
	                }

	                cell = position + N_COLS;
	                if (cell < allCells) {
	                    if (field[cell] != COVERED_MINE_CELL) {
	                        field[cell] += 1;
	                    }
	                }

	                if (current_col < (N_COLS - 1)) {
	                    cell = position - N_COLS + 1;
	                    if (cell >= 0) {
	                        if (field[cell] != COVERED_MINE_CELL) {
	                            field[cell] += 1;
	                        }
	                    }
	                    cell = position + N_COLS + 1;
	                    if (cell < allCells) {
	                        if (field[cell] != COVERED_MINE_CELL) {
	                            field[cell] += 1;
	                        }
	                    }
	                    cell = position + 1;
	                    if (cell < allCells) {
	                        if (field[cell] != COVERED_MINE_CELL) {
	                            field[cell] += 1;
	                        }
	                    }
	                }
	            }
	        }
	        
	    }
/**
 * Finds an empty cell(cell without number, mine or flag)
 * @param j
 */
	    private void find_empty_cells(int j) {

	        int current_col = j % N_COLS;
	        int cell;

	        if (current_col > 0) {
	            cell = j - N_COLS - 1;
	            if (cell >= 0) {
	                if (field[cell] > MINE_CELL && field[cell] < COVERED_MINE_CELL) {
	                    field[cell] -= COVER_FOR_CELL;
	                    if (field[cell] == EMPTY_CELL) {
	                        find_empty_cells(cell);
	                    }
	                }
	            }

	            cell = j - 1;
	            if (cell >= 0) {
	                if (field[cell] > MINE_CELL && field[cell] < COVERED_MINE_CELL) {
	                    field[cell] -= COVER_FOR_CELL;
	                    if (field[cell] == EMPTY_CELL) {
	                        find_empty_cells(cell);
	                    }
	                }
	            }

	            cell = j + N_COLS - 1;
	            if (cell < allCells) {
	                if (field[cell] > MINE_CELL && field[cell] < COVERED_MINE_CELL) {
	                    field[cell] -= COVER_FOR_CELL;
	                    if (field[cell] == EMPTY_CELL) {
	                        find_empty_cells(cell);
	                    }
	                }
	            }
	        }

	        cell = j - N_COLS;
	        if (cell >= 0) {
	            if (field[cell] > MINE_CELL && field[cell] < COVERED_MINE_CELL) {
	                field[cell] -= COVER_FOR_CELL;
	                if (field[cell] == EMPTY_CELL) {
	                    find_empty_cells(cell);
	                }
	            }
	        }

	        cell = j + N_COLS;
	        if (cell < allCells) {
	            if (field[cell] > MINE_CELL  && field[cell] < COVERED_MINE_CELL) {
	                field[cell] -= COVER_FOR_CELL;
	                if (field[cell] == EMPTY_CELL) {
	                    find_empty_cells(cell);
	                }
	            }
	        }

	        if (current_col < (N_COLS - 1)) {
	            cell = j - N_COLS + 1;
	            if (cell >= 0) {
	                if (field[cell] > MINE_CELL && field[cell] < COVERED_MINE_CELL) {
	                    field[cell] -= COVER_FOR_CELL;
	                    if (field[cell] == EMPTY_CELL) {
	                        find_empty_cells(cell);
	                    }
	                }
	            }

	            cell = j + N_COLS + 1;
	            if (cell < allCells) {
	                if (field[cell] > MINE_CELL && field[cell] < COVERED_MINE_CELL) {
	                    field[cell] -= COVER_FOR_CELL;
	                    if (field[cell] == EMPTY_CELL) {
	                        find_empty_cells(cell);
	                    }
	                }
	            }

	            cell = j + 1;
	            if (cell < allCells) {
	                if (field[cell] > MINE_CELL && field[cell] < COVERED_MINE_CELL) {
	                    field[cell] -= COVER_FOR_CELL;
	                    if (field[cell] == EMPTY_CELL) {
	                        find_empty_cells(cell);
	                    }
	                }
	            }
	        }

	    }

	    /**
	     * This method paints/draws the components/objects on the playable area
	     */
	    @Override
	    public void paintComponent(Graphics g) {
	    	super.paintComponent(g);
	    	score(g);
	    	
	        int uncover = 0;

	        for (int i = 0; i < N_ROWS; i++) {

	            for (int j = 0; j < N_COLS; j++) {

	                int cell = field[(i * N_COLS) + j];
	                if (inGame && cell == MINE_CELL) {

	                    inGame = false;
	                }

	                if (!inGame) {

	                    if (cell == COVERED_MINE_CELL) {
	                        cell = DRAW_MINE;
	                    } else if (cell == MARKED_MINE_CELL) {
	                        cell = DRAW_MARK;
	                    } else if (cell > COVERED_MINE_CELL) {
	                        cell = DRAW_WRONG_MARK;
	                    } else if (cell > MINE_CELL) {
	                        cell = DRAW_COVER;
	                    }

	                } else {

	                    if (cell > COVERED_MINE_CELL) {
	                        cell = DRAW_MARK;
	                    } else if (cell > MINE_CELL) {
	                        cell = DRAW_COVER;
	                        uncover++;
	                    }
	                }

	                
	                g.drawImage(img[cell], OFFSET_WIDTH+(j * CELL_SIZE),
	                		OFFSET_HEIGHT+(i * CELL_SIZE), this);
	                		
	            }
	        }

	        if (uncover == 0 && inGame) {

		    	timer.stop();
	            inGame = false;
	            win = true;

	        } 
	    }
	    
	    /**
	     * This method draws/paints the score on the screen
	     * @param g
	     */
	    private void score(Graphics g) {
	    	Graphics2D g2d = (Graphics2D) g;
	    	
	    	String score = "Mine Left: "+minesLeft;
	    	String info = "Press ESC to Pause";
	    	
	    	g2d.setColor(Color.GREEN);
	    	g2d.setFont(new Font("Helvetica", Font.BOLD, 20));
	    	g2d.drawString(score, OFFSET_WIDTH+B_WIDTH, 50);
	    	g2d.setFont(new Font("Helvetica", Font.PLAIN, 10));
	    	g2d.drawString(info, OFFSET_WIDTH+B_WIDTH, 70);
	    }
	    
	    /**
	     * This method is used to stop the game after winning all level or losing the current level
	     * And calls the gameOver class
	     */
	    private void gameOver() {

	    	timer.stop();
	    	int score = 0;
	    	
	    	if(win) 
	    		score = min*100+sec;
	    	
        	this.setVisible(false);
	    	window.removeKeyListener(this);
	    	window.remove(this);
	    	window.removeMouseListener(ma);
	    	new GameOver(score, "Mine Sweeper");
		}

	    
	    @Override
	    /**
	     * This method is used to make the counter
	     */
		public void actionPerformed(ActionEvent arg0) {
	    	
	    		sec++;
			
	    		if(sec == 60)
	    		{
	    			min++;
	    			sec = 0;
	    		}
			
	    		counterLabel.setText(dformat.format(min)+":"+dformat.format(sec));
	    		
	    		
		}
	    
	    /**
	     * This method is used to bind the keys to the current panel
	     * It is used to bring the pause window after clicking the ESC button
	     */
	    public void keyPressed(KeyEvent e) {

	        int key = e.getKeyCode();
	    	
	    	if (key == KeyEvent.VK_ESCAPE) {
            	this.setVisible(false);
            	window.removeKeyListener(this);
            	window.removeMouseListener(ma);
            	timer.stop();
    			window.add(new Pause(this, 4));
            }
	    }
	    
	    /**
	     * Handles the clicking events
	     * @author 
	     *
	     */
	    private class MinesAdapter implements MouseListener{
	    
			@Override
	        public void mousePressed(MouseEvent e) {
				
	            int x = e.getX();
	            int y = e.getY()-DECO_OFFSET ;

	            int cCol = (x-OFFSET_WIDTH) / CELL_SIZE;
	            int cRow = (y-OFFSET_HEIGHT) / CELL_SIZE;
	            int cell = (cRow * N_COLS) + cCol;
	            boolean doRepaint = false;

	            if (!inGame) {
	                gameOver();
	            }

	            if ((x>OFFSET_WIDTH)&&(x < N_COLS * CELL_SIZE+OFFSET_WIDTH) && (y>OFFSET_HEIGHT) && (y < N_ROWS * CELL_SIZE+OFFSET_HEIGHT)) {
	                if (e.getButton() == MouseEvent.BUTTON3) {

	                    if (field[cell] > MINE_CELL) {

	                        doRepaint = true;

	                        if (field[cell] <= COVERED_MINE_CELL) {

	                            if (minesLeft > 0) {
	                                field[cell] += MARK_FOR_CELL;
	                                minesLeft--;
	                            }
	                        } else {

	                            field[cell] -= MARK_FOR_CELL;
	                            minesLeft++;
	                        }
	                    }

	                } else {

	                    if (field[cell] > COVERED_MINE_CELL) {

	                        return;
	                    }

	                    if ((field[cell] > MINE_CELL)
	                            && (field[cell] < MARKED_MINE_CELL)) {

	                        field[cell] -= COVER_FOR_CELL;
	                        doRepaint = true;

	                        if (field[cell] == MINE_CELL) {
	                            inGame = false;
	                        }

	                        if (field[cell] == EMPTY_CELL) {
	                            find_empty_cells(cell);
	                        }
	                    }
	                }

	                if (doRepaint) {
	                    repaint();
	                }
	            }
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
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

		/**
		 * 
		 */
		@Override
		public void resume() {
			this.setVisible(true);
        	window.addMouseListener(ma);
	    	window.addKeyListener(this);
	    	timer.start();
		}

		/**
		 * 
		 */
		@Override
		public AllGames resized(Window window, AllGames old_game) {
			MineSweeper new_game = new MineSweeper();
		    new_game.timer.stop();
			
			MineSweeper game = (MineSweeper)old_game;
		    
			window.remove(game);
		
			new_game.setVisible(false);
			window.removeMouseListener(new_game.ma);
	    	window.removeKeyListener(new_game);
			window.removeKeyListener(new_game);
		      
		    return new_game;
		      
			
		}

			
			


			
}
