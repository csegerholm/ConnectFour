package ImprovedVersion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Screen that shows the board, updates as moves are pressed and tells when a player has won.
 * @author csegerholm
 */
public class GameScreen extends JPanel{
	
	/**
	 * Reference to start menu so that we can jump back to it when game is over.
	 */
	public StartMenu startMenu; 
	
	/**
	 * Array of char that represents the connect four board
	 */
	public char[][] board;
	
	/**
	 * Array of connect four players (can be human or AI). Always length 2;
	 */
	private CFPlayer[] players;
	
	/**
	 * Keeps track of index of the player who has to go next. Index is the index of player in the players array.
	 */
	int currPlayerIndex;
	
	/**
	 * Amount of human players in current game
	 */
	int playerAmount;
	
	/**
	 * Start of each column in regards to width.
	 */
	int[] columnStart;
	
	/**
	 * Pixel width of each circle.
	 */
	int circleWidth;
	
	MouseAdapter listener;
	
	/**
	 * Constructor - creates the game board-> number of players is to be entered in startGame function.
	 */
	public GameScreen(StartMenu sm){
		startMenu = sm;
		board = new char[6][7];
		players = new CFPlayer[2];
		//Set color to purple
		setBackground(Color.magenta);
		//Array layout where you pick coordinates of each component
		setLayout(null);
		this.columnStart = new int[7];
	}
	
	/**
	 * Starts the game and draws the board.
	 * @param players - number of human players
	 */
	public void startGame(int playerNum){
		currPlayerIndex=0;
		playerAmount = playerNum;
		for(int i=0;i<board.length;i++){
			for(int j=0; j<board[i].length;j++){
				board[i][j] = 'O';
			}
		}
		if(playerNum ==1){
			this.players[0] = new CFHuman('B', this);
			this.players[1] = new CFAI('R', board);
		}
		else if(playerNum ==2){
			this.players[0] = new CFHuman('B', this);
			this.players[1] = new CFHuman('R', this);
		}
		else{
			System.out.println("ERROR Number of players is not 1 or 2.");
			System.exit(0);
		}
		drawScreen();
		
		
		if(listener!= null){
			removeMouseListener(listener);
		}
		listener = new MouseAdapter() { 
	          public void mouseClicked(MouseEvent e) { 
	        	int x =e.getX();
	      		int col =-1;
	      		for(int i=0;i<7;i++){
	      			int start =columnStart[i];
	      			if(x>=start && x<=start+circleWidth){
	      				col = i;
	      				System.out.println("Picked col ="+col);
	      			}
	      		}
	      		char ans =players[currPlayerIndex].makeMove(new Coordinate(0,col));
	      		System.out.println("makemove returned: "+ ans);
	      		if(ans == 't'){
	      			gameOver("Tie Game!");
	      			return;
	      		}
	      		else if(ans == 'w'){
	      			gameOver("Player "+(currPlayerIndex+1) +" wins!");
	      			return;
	      		}
	      		else if(ans =='n'){
	      			//if it is a computer
	      			if(playerAmount==1){
	      				//computer goes
	      				ans = players[1].makeMove(null);
	      				if(ans == 't'){
	      					gameOver("Tie Game!");
	      					return;
	      				}
	      				else if(ans == 'w'){
	      					gameOver("You Lose!");
	      					return;
	      				}
	      				repaint();
	      			}
	      			else{
	      				//if its human then let other player go
	      				currPlayerIndex++;
	      				if(currPlayerIndex==2){
	      					currPlayerIndex = 0;
	      				}
	      				repaint();
	      			}
	      		} 
	          }
		};
		
		addMouseListener(listener); 
	}
	
	/**
	 * Draws the board and the buttons on the screen.
	 */
	private void drawScreen() {
		removeAll();
		repaint();
		drawButtons();
		revalidate();
	}
	
	/**
	 * Draws quit and undo buttons on screen.
	 */
	private void drawButtons() {
		int w =ScreenConfiguration.width;
		int h = ScreenConfiguration.height;	
		
		//MAKE QUIT BUTTON
		JButton quit = new JButton("Quit");
		quit.setBounds(w*7/8, 0, w/8, h/16);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//switch to level screen
				startMenu.restart();
			}
		});
		add(quit);
		//MAKE UNDO BUTTON
		JButton undo = new JButton("Undo");
		undo.setBounds(0, 0, w/8, h/16);
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//player undoes the computer's move and their own move
				if(playerAmount ==1){
					//undo ai move
					players[1].undoMove();
					//undo human move
					players[0].undoMove();
					//still players turn
					//update screen
					drawScreen();
				}
				else{
					//undo other players move and make it their turn
					if(currPlayerIndex==0){
						currPlayerIndex=1;
					}
					else{
						currPlayerIndex=0;
					}
					players[currPlayerIndex].undoMove();
					//update screen
					drawScreen();
				}
				
			}
		});
		add(undo);
	}

	/**
	 * Draw pop up to play again or quit
	 * @param message
	 */
	public void gameOver(String message){
		int w =ScreenConfiguration.width;
		int h = ScreenConfiguration.height;
		int popw = (3*w)/4;
		int poph = h/2;
		//draw pop up
		JTextField popup = new JTextField(message);
		popup.setEditable(false);
		popup.setBackground(Color.gray);
		popup.setBounds(w/8, h/4, popw, poph);
		add(popup);
		//draw 2 buttons - play again or main menu
		JButton pa = new JButton("Play Again");
		pa.setBounds(popw/4, poph/2, popw/4, poph/4);
		pa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				players[0].playAgain();
				players[1].playAgain();
				drawScreen();
			}
		});
		popup.add(pa);
		
		JButton quit = new JButton("Quit");
		quit.setBounds(3*popw/4, poph/2, popw/4, poph/4);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//switch to level screen
				startMenu.restart();
			}
		});
		popup.add(quit);
		
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int w =ScreenConfiguration.width;
		int h = ScreenConfiguration.height;	
		int circleWidth = w/9;
		int circleHeight= (int) Math.round(h*7/(8*7.75));
		int spaceWidth = circleWidth/4;
		int spaceHeight = circleHeight/4;
		
		//Draw the yellow box
		g.setColor(Color.yellow);
		g.fillRect(0, h/16, w, h*7/8);
		
		printBoard();
		for(int row = board.length-1; row>=0; row--){
			for(int col = 0; col<board[row].length; col++){
				if(board[row][col]=='R'){
					g.setColor(Color.red);
				}
				else if(board[row][col]=='B'){
					g.setColor(Color.black);
				}
				else{
					g.setColor(Color.white);
				}
				this.columnStart[col]= col*(circleWidth+spaceWidth)+spaceWidth;
				int heightStart = (5-row)*(circleHeight+spaceHeight) +spaceHeight+h/16;
				g.fillOval(columnStart[col] , heightStart, circleWidth, circleHeight);
			}
		}
		this.circleWidth = circleWidth;
		
		//Draw Curr Player Box
		JTextField currP = new JTextField("Player "+(currPlayerIndex+1)+"'s Turn");
		currP.setEditable(false);
		//cfText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		currP.setHorizontalAlignment(SwingConstants.CENTER);
		currP.setBounds(w*3/8, 0, w/4, h/16);
		add(currP);
		
		//Draw P1 Box
		JTextField p1 = new JTextField("Player 1");
		p1.setEditable(false);
		p1.setBackground(Color.black);
		p1.setForeground(Color.white);
		//cfText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		p1.setHorizontalAlignment(SwingConstants.CENTER);
		p1.setBounds(0, h*15/16, w/8, h/16);
		add(p1);
		//Draw P2 Box
		JTextField p2 = new JTextField("Player 2");
		p2.setEditable(false);
		p2.setBackground(Color.red);
		p2.setForeground(Color.white);
		//cfText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		p2.setHorizontalAlignment(SwingConstants.CENTER);
		p2.setBounds(w*7/8, h*15/16, w/8, h/16);
		add(p2);
	}
	
	public void printBoard(){
		for(int row=0; row<board.length; row++){
			for(int col=0; col<board[row].length; col++){
				System.out.print(board[row][col]+"  ");
			}
			System.out.println();
		}
	}
	
//END OF CLASS
}