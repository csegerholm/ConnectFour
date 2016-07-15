package ImprovedVersion;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A ConnectFourWindow is a graphical window designed for the game of connect four.
 * It knows how to display player names, and the board.
 */
public class ConnectFourWindow extends JFrame
{
	private C4Player[] players;
	private char[][] board;
	
	/**
	 * Create a BlackjackWindow for the given set of players.
	 * Entry 0 of the input array should represent the dealer.
	 */
	public ConnectFourWindow(C4Player[] p, char[][] board)
	{
		if (p.length!=2)
		{
			throw new IllegalArgumentException("Player array length " + p.length + ", must be 2.");
		}
		
		if (board.length!=6 || board[0].length!=7)
		{
			throw new IllegalArgumentException("Board array must be 6 rows and 7 columns");
		}
		
		this.players = p;
		
		this.board = board;

		this.setTitle("Connect Four");
		this.setSize(800, 700);

		Container content = getContentPane();
		content.setLayout(null);

		ConnectFourPanel panel = new ConnectFourPanel();
		content.add(panel);
		panel.setBounds(0, 0, 800, 700);
		panel.setBackground(Color.yellow);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	/**
	 * Re-draw the window -
	 * this method should be called whenever there is a change to any player's cards.
	 */
	public void redraw()
	{
		this.repaint();
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			// should never happen
		}
	}
	
	/**
	 * Close the window (remove it from the screen) -
	 * this method should be called when the game ends.
	 */
	public void close()
	{
		this.setVisible(false);
		this.dispose();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// inner classes
	//////////////////////////////////////////////////////////////////////////////////////////////

	private class ConnectFourPanel extends JPanel
	{
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			if (ConnectFourWindow.this.players == null)
			{
				return;
			}
			
			int numplayers = ConnectFourWindow.this.players.length - 1;
			if (numplayers !=1)
			{
				GIO.displayMessage("Error: invalid Player array length " + (numplayers+1));
				System.exit(1);
			}
			
			for (int p = 0 ; p <= numplayers ; p++)
			{
				C4Player player = ConnectFourWindow.this.players[p];
				if (player == null)
				{
					GIO.displayMessage("Error: player[" + p + "] is null");
					System.exit(1);
				}
				/*
				String name = "" + player.getName();
				g.setColor(Color.black);
				if(p==0){
					g.setColor(Color.red);
				}
				g.drawString(name, 50+(p%2*400), 50+(p/2*200));
				*/
			}
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
}
