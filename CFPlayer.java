package JavaApp;
public interface CFPlayer{
	
	/**
	 * Gets color of player ('R' or 'B')
	 * @return Player's color
	 */
	public char getColor();
	
	/**
	 * Asks and plays the next move
	 * @param move - coordinates of the move user wishes to make (not used for AI)
	 * @return w = win, n = nothing = good to go, t = tie
	 */
	public char makeMove(Coordinate move);
	
	/**
	 * Removes move from board as well as array of prev moves
	 */
	public void undoMove();

	/**
	 * Restarts player's boards and list of last moves
	 */
	public void playAgain();
	
	/**
	 * Gets number of moves this player made
	 * @return number of moves
	 */
	public int getNumberOfMoves(); 
	
	
}