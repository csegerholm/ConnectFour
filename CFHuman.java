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
	public void checkValidRow(Coordinate ans){
		char[][] board =gameScreen.board;
		while(ans.col<0 || ans.col>6){
			ans.col=GIO.readInt("Error: Column out of bounds.\n Please Enter a valid column number:");
		}
		while(true){
			if(board[5][ans.col]=='O'){
				for(int i=0;i<6;i++){
					if(board[i][ans.col]=='O'){
						ans.row=i;
						break;
					}
				}
				break;
			}
			else{
				ans.col=GIO.readInt("Error: Column fill.\n Please Enter a valid column number:");
				while(ans.col<0 || ans.col>6){
					ans.col=GIO.readInt("Error: Column out of bounds.\n Please Enter a valid column number:");
				}
			}
		}
	}
	//

	public char getColor() {
		return color;
	}
	
	//Non GUI 
	public char makeMove() {
		if(movesCnt>=3*7){
			CFGame.printBoard();
			String again=GIO.readString("Out of moves. It's a Tie.\nPlay again?");
			char a = again.charAt(0);
			if(a=='y'|| a=='Y'){
				restart();
				return -1;//restart
			}
			return -2;
		}
		Coordinate ans = new Coordinate(-1,-1);
		ans.col=GIO.readInt(name+", please enter the column number you would like to drop your chip in.");
		checkValidRow(ans);
		
		prevMoves[movesCnt]=ans;
		movesCnt++;
		board[ans.row][ans.col]=color;
		if(didWin()){
			CFGame.printBoard();
			String again=GIO.readString(name+", YOU WON!\n Play again? y/n");
			char a = again.charAt(0);
			if(a=='y'|| a=='Y'){
				restart();
				return -1;
			}
			return -2;
		}
		return 0;
	}

	//only gets this if undoing move
	public void undoMove() {
		Coordinate last = prevMoves[movesCnt-1];
		board[last.row][last.col]='O';
		movesCnt--;
	}

	public void restart() {
		this.prevMoves = new Coordinate[3*7];
		this.movesCnt=0;
	}
	
	public boolean didWin() {
		Coordinate last = prevMoves[movesCnt-1];
		int lr=0;
		int ud=0;
		//check left
		for(int i=last.col-1; i>=0; i-- ){
			if(board[last.row][i]==color){
				lr++;
			}
			else{
				break;
			}
		}
		//check right
		for(int i=last.col+1; i< 7; i++ ){
			if(board[last.row][i]==color){
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
			if(board[i][last.col]==color){
				ud++;
			}
			else{
				break;
			}
		}
		//check down
		for(int i=last.row-1; i>=0; i-- ){
			if(board[i][last.col]==color){
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
			if(board[last.row+i][last.col+i]==color){
				ur++;
			}
			else{
				break;
			}
		}
		//check down left
		for(int i=1;last.row-i>=0 && last.col-i>=0 ; i++ ){
			if(board[last.row-i][last.col-i]==color){
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
			if(board[last.row+i][last.col-i]==color){
				ul++;
			}
			else{
				break;
			}
		}
		//check down right
		for(int i=1;last.row-i>=0 && last.col+i<7 ; i++ ){
			if(board[last.row-i][last.col+i]==color){
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
	
}