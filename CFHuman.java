package ImprovedVersion;
//NOT DONE

/**
 * Player object for human user. Will listen for mouse clicks and decide if player has won, tied, quit, or wishes to undo.
 * @author csegerholm
 *
 */
public class CFHuman implements CFPlayer{
	
	private char color;
	private GameScreen gameScreen;
	private Coordinate[] prevMoves;
	private int movesCnt;
	
	public CFHuman(char color, GameScreen gameScreen){
		this.color=color;
		this.prevMoves = new Coordinate[3*7];
		this.movesCnt=0;
		this.gameScreen=gameScreen;
	}


	
	//Helper methods
	/**
	 * Returns true if valid row selected
	 * @param ans
	 * @return
	 */
	public boolean checkValidRow(Coordinate ans){
		char[][] board =gameScreen.board;
		//col is not valid col number
		if(ans.col<0 || ans.col>6){
			return false;
		}
		//col is full
		if(board[5][ans.col]=='O'){
			for(int i=0;i<6;i++){
				if(board[i][ans.col]=='O'){
					ans.row=i;
					return true;
				}
			}
		}
		return false;
	}

	public char getColor() {
		return color;
	}
	
	public char makeMove(Coordinate ans) {
		//tie
		if(color=='R'&&movesCnt>=3*7-1){
			return 't';
		}
		//bad move
		if(!checkValidRow(ans)){
			return 'b';
		}
		
		
		prevMoves[movesCnt]=ans;
		movesCnt++;
		gameScreen.board[ans.row][ans.col]=color;
		if(didWin()){
			return 'w';
		}
		return 'n';
	}

	//only gets this if undoing move
	public void undoMove() {
		if(movesCnt==0){
			return;
		}
		movesCnt--;
		Coordinate last = prevMoves[movesCnt];
		gameScreen.board[last.row][last.col]='O';
	}
	
	public boolean didWin() {
		Coordinate last = prevMoves[movesCnt-1];
		int lr=0;
		int ud=0;
		//check left
		for(int i=last.col-1; i>=0; i-- ){
			if(gameScreen.board[last.row][i]==color){
				lr++;
			}
			else{
				break;
			}
		}
		//check right
		for(int i=last.col+1; i< 7; i++ ){
			if(gameScreen.board[last.row][i]==color){
				lr++;
			}
			else{
				break;
			}
		}
		if(lr>=3){
			return true;
		}
		//check up
		for(int i=last.row+1; i< 6; i++ ){
			if(gameScreen.board[i][last.col]==color){
				ud++;
			}
			else{
				break;
			}
		}
		//check down
		for(int i=last.row-1; i>=0; i-- ){
			if(gameScreen.board[i][last.col]==color){
				ud++;
			}
			else{
				break;
			}
		}
		if(ud>=3){
			return true;
		}
		int ur=0;
		int ul=0;
		//check up right 
		for(int i=1;last.row+i<6 && last.col+i<7 ; i++ ){
			if(gameScreen.board[last.row+i][last.col+i]==color){
				ur++;
			}
			else{
				break;
			}
		}
		//check down left
		for(int i=1;last.row-i>=0 && last.col-i>=0 ; i++ ){
			if(gameScreen.board[last.row-i][last.col-i]==color){
				ur++;
			}
			else{
				break;
			}
		}
		if(ur>=3){
			return true;
		}
		//check up left
		for(int i=1;last.row+i<6 && last.col-i>=0 ; i++ ){
			if(gameScreen.board[last.row+i][last.col-i]==color){
				ul++;
			}
			else{
				break;
			}
		}
		//check down right
		for(int i=1;last.row-i>=0 && last.col+i<7 ; i++ ){
			if(gameScreen.board[last.row-i][last.col+i]==color){
				ul++;
			}
			else{
				break;
			}
		}
		if(ul>=3){
			return true;
		}
		
		return false;
	}



	/**
	 * Erases list of previous moves and clears gameScreen's board
	 */
	public void playAgain() {
		this.movesCnt=0;
		
		for(int i=0; i<gameScreen.board.length;i++){
			for(int j=0; j<gameScreen.board[i].length; j++){
				gameScreen.board[i][j] = 'O';
			}
		}
		
	}
	
}