package ImprovedVersion;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Screen that shows the board, updates as moves are pressed and tells when a player has won.
 * @author csegerholm
 *
 */
public class GameScreen extends JPanel{
		
	/**
	 * Array of char that represents the connect four board
	 */
	public char[][] board;
	
	/**
	 * Array of connect four players (can be human or AI). Always length 2;
	 */
	private CFPlayer[] players;
	
	/**
	 * Constructor - creates the game board-> number of players is to be entered in startGame function.
	 */
	public GameScreen(){
		board = new char[7][8];
		players = new CFPlayer[2];
	}
	
	public void startGame(int players){
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
		
		while(true){
			char output = this.players[0].makeMove();
			if(output=='u'){
				//undo last move
			}
			else if(output!='n'){
				if(output == 'w'){
					//p1 wins
				}
				if(output =='t'){
					//tie out of moves
				}
				//if q then quit
				break;
				//returns to start menu
			}
			output = this.players[1].makeMove();
			if(output=='u'){
				//undo last move
			}
			else if(output!='n'){
				if(output == 'w'){
					//p2 wins
				}
				if(output =='t'){
					//tie out of moves
				}
				//if q then quit
				break;
				//returns to start menu
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
			super.paintComponent(g);
			for(int row = board.length-1; row>=0; row--){
				for(int col = 0; col<board[0].length; col++){
					if(board[row][col]=='R'){
						g.setColor(Color.red);
					}
					else if(board[row][col]=='B'){
						g.setColor(Color.black);
					}
					else{
						g.setColor(Color.white);
					}
					g.fillOval(col*100 +25, (5-row)*100 +25, 90, 90);
				}
			}
			
		}
	}