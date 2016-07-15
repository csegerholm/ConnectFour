package ImprovedVersion;
public interface CFPlayer{
	
	/**
	 * Gets color of player ('R' or 'B')
	 * @return Player's color
	 */
	public char getColor();
	
	/**
	 * Asks and plays the next move
	 * @return w = win, u = undo, q = quit, n = nothing = good to go
	 */
	public char makeMove();
	
	/**
	 * Removes move from board as well as array of prev moves
	 */
	public void undoMove();
	
	
}