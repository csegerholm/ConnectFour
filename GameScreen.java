package ImprovedVersion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	 * Listens to player moves and calls make move
	 */
	private Listener mouseListener;
	
	/**
	 * Constructor - creates the game board-> number of players is to be entered in startGame function.
	 */
	public GameScreen(StartMenu sm){
		startMenu = sm;
		board = new char[8][7];
		players = new CFPlayer[2];
		//Set color to purple
		setBackground(Color.magenta);
		//Array layout where you pick coordinates of each component
		setLayout(null);
	}
	
	/**
	 * Starts the game and draws the board.
	 * @param players - number of human players
	 */
	public void startGame(int players){
		currPlayerIndex=0;
		playerAmount = players;
		for(int i=0;i<board.length;i++){
			for(int j=0; j<board[i].length;j++){
				board[i][j] = 'O';
			}
		}
		if(players ==1){
			this.players[0] = new CFHuman('B', this);
			this.players[1] = new CFAI('R', board);
		}
		else if(players ==2){
			this.players[0] = new CFHuman('B', this);
			this.players[1] = new CFHuman('R', this);
		}
		else{
			System.out.println("ERROR Number of players is not 1 or 2.");
			System.exit(0);
		}
		mouseListener = new Listener(this.players, this);
		drawScreen();
	}
	
	/**
	 * Draws the board and the buttons on the screen.
	 */
	private void drawScreen() {
		removeAll();
		drawButtons();
		repaint();
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
		int circleHeight= (int) Math.round(h*7/(8*10.25));
		int spaceWidth = circleWidth/4;
		int spaceHeight = circleHeight/4;
		int[] columns = new int[7];
		
		//Draw the yellow box
		g.setColor(Color.yellow);
		g.fillRect(0, h/16, w, h*7/8);
		
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
				columns[col]= col*(circleWidth+spaceWidth)+spaceWidth;
				g.fillOval(col*(circleWidth+spaceWidth)+spaceWidth , (5-row)*h*(circleHeight+spaceHeight) +spaceHeight, circleWidth, circleHeight);
				
			}
		}
		mouseListener.updateColumns(columns, circleWidth);
		
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
	
	
//END OF CLASS
}