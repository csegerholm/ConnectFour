package ImprovedVersion;

//NOT DONE
/**
 * Player object for computer player. 
 * @author csegerholm
 *
 */
public class CFAI implements CFPlayer{
	
	private char color;
	private char[][] board;
	private Coordinate[] prevMoves;
	private int movesCnt;
	private int[] possibleRow = new int[7];
	
	
	public CFAI(char color, char[][] board){
		this.color=color;
		this.prevMoves = new Coordinate[3*7];
		this.movesCnt=0;
		this.board=board;
	}

	//helper
	/**
	 * Fills in possibleRow moves. If column is full then fills in -1
	 */
	private void checkCol(){ 
		for(int x=0; x<7; x++){
			possibleRow[x]=-1;
			for(int y=0; y<6;y++){
				if(board[y][x]=='O'){
					possibleRow[x]= y;
						break;
				}
			}
		}
	}
	/**
	 * Lets you know if opp has two in a row at the bottom
	 * @return
	 */
	private int twoBott(){
		if(board[0][3]=='R' && (board[0][2]=='R' || board[0][4]=='R') ){
			if(board[0][2]=='O')
				return 2;
			if(board[0][4]=='O')
				return 4;
		}
		return -1;
	}
	
	private int three(){
		int down, right, dr, dl, y;
		for(int x=0;x<7;x++){ 
				down=0;
				right=0;
				dr=0;
				dl=0;
				if(possibleRow[x]==-1){
					continue;
				}
				y=possibleRow[x];
				//down
				m:for(int i=0; i<3; i++){
					if(y+i<=5){
						if(board[y+i][x]==color)
							down=down+1;
						else
							break m;	
					}
					else
						break m;
				}
				//right
				n:for(int i=0; i<3; i++){
					if(x+i<=6){
						if(board[y][x+i]==color)
							right=right+1;
						else
							break n;	
					}
					else
						break n;
				}

				//left down
				a: for(int i=0; i<3; i++){
					if(x-i>=0){ //left
							if(y+i<6){ //down
								if(board[y+i][x-i]==color)
									dl=dl+1;
								else
									break a;	
							}
							else
								break a;
					}	
					else
						break;
				}
		//right down
				d: for(int i=0; i<3; i++){
					if(x+i<7){
							if(y+i<6){
								if(board[y+i][x+i]==color)
									dr=dr+1;
								else
									break d;	
							}
							else
								break d;
					}	
					else
						break;
				}
		
				if(right>2){
					return x;
				}
				if(down>2){
					return x;
				}
			
				if(dr>2){
					return x;
				}
				if(dl>2){
					return x;
				}
			}
		return -1;
	}
	
	private int two(){
		int down, right, dr, dl, y;
		for(int x=0;x<7;x++){ 
				down=0;
				right=0;
				dr=0;
				dl=0;
				if(possibleRow[x]==-1){
					continue;
				}
				y=possibleRow[x];
				//down
				m:for(int i=0; i<2; i++){
					if(y+i<=5){
						if(board[y+i][x]==color)
							down=down+1;
						else
							break m;	
					}
					else
						break m;
				}
				//right
				n:for(int i=0; i<2; i++){
					if(x+i<=6){
						if(board[y][x+i]==color)
							right=right+1;
						else
							break n;	
					}
					else
						break n;
				}

				//left down
				a: for(int i=0; i<2; i++){
					if(x-i>=0){ //left
							if(y+i<6){ //down
								if(board[y+i][x-i]==color)
									dl=dl+1;
								else
									break a;	
							}
							else
								break a;
					}	
					else
						break;
				}
		//right down
				d: for(int i=0; i<2; i++){
					if(x+i<7){
							if(y+i<6){
								if(board[y+i][x+i]==color)
									dr=dr+1;
								else
									break d;	
							}
							else
								break d;
					}	
					else
						break;
				}
		
				if(right>1){
					return x;
				}
				if(down>1){
					return x;
				}
			
				if(dr>1){
					return x;
				}
				if(dl>1){
					return x;
				}
			}
		return -1;
	}
	
	//
	public char getColor() {
		return color;
	}
	
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
		return winOneMove(last, color);
	}
	
	public char makeMove() {
		if(movesCnt>=3*7){
			//CFGame.printBoard();
			String again= GIO.readString("Out of moves. It's a Tie.\nPlay again?");
			char a = again.charAt(0);
			if(a=='y'|| a=='Y'){
				restart();
				return -1;//restart
			}
			return -2;
		}
		checkCol();
		Coordinate ans = new Coordinate(-1,-1);
		//check if you can win
		ans.col = canWin('B');
		if(ans.col!=-1){
			ans.row = possibleRow[ans.col];
		}
		else{
			//check if they can win
			ans.col = canWin('R');
			if(ans.col!=-1){
				ans.row = possibleRow[ans.col];
			}
			else{
				//check double bottom
				ans.col = twoBott();
				if(ans.col!=-1){
					ans.row = possibleRow[ans.col];
				}
				else{
					//go middle
					if(possibleRow[3]!=-1){
						ans.col=3;
						ans.row=possibleRow[3];
					}
					else{
						//go three
						ans.col = three();
						if(ans.col!=-1){
							ans.row = possibleRow[ans.col];
						}
						else{
							ans.col = two();
							if(ans.col!=-1){
								ans.row = possibleRow[ans.col];
							}
							else{
								int ij=0;
								while(ij<7){
									if(possibleRow[ij]!=-1){
										ans.col=ij;
										ans.row = possibleRow[ans.col];
										break;
									}
									ij++;
								}
							}
						}
					}	
				}
			}
		}
		prevMoves[movesCnt]=ans;
		movesCnt++;
		board[ans.row][ans.col]=color;
		
		if(didWin()){
			CFGame.printBoard();
			String again=GIO.readString(name+" WON!\n Play again? y/n");
			char a = again.charAt(0);
			if(a=='y'|| a=='Y'){
				restart();
				return -1;
			}
			return -2;
		}
		return 0;
	}

	private int canWin(char color){ // returns col number to win or block a win -1 if cant
		Coordinate last = new Coordinate(-1,-1);
		for(int x=0; x<7; x++){
			if(possibleRow[x]==-1){
				continue;
			}
			board[possibleRow[x]][x]=color;
			last.col =x;
			last.row = possibleRow[x];
			if( winOneMove(last, color) ){
				return x;
			}
			board[possibleRow[x]][x]='O';
		}
		return -1;
		
	}

	private boolean winOneMove(Coordinate last, char color){
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