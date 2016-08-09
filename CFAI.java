package JavaApp;

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
	
	/**
	 * Gives row coordinate for corresponding column. -1 means column is full. 
	 * any other -# means if you go here the other player can win 
	 */
	private int[] possibleRow = new int[7];
	
	/**
	 * Computer player
	 * @param color -> char of color this player is assigned (B or R)
	 * @param board -> char[6][7] of the board game is played on
	 */
	public CFAI(char color, char[][] board){
		this.color=color;
		this.prevMoves = new Coordinate[3*7];
		this.movesCnt=0;
		this.board=board;
	}

	public char getColor() {
		return color;
	}
	
	public void undoMove() {
		//If no moves were made don't do anything
		if(movesCnt==0){
			return;
		}	
		movesCnt--;
		Coordinate last = prevMoves[movesCnt];
		board[last.row][last.col]='O';
	}

	public void playAgain() {
		this.movesCnt=0;
		for(int i=0; i<board.length;i++){
			for(int j=0; j<board[i].length; j++){
				board[i][j] = 'O';
			}
		}
	}
	
	public char makeMove(Coordinate move) {
		//tie
		if(movesCnt>=(3*7-1)){
			return 't';
		}
		//Find all possible moves
		findPossibleMoves();
		move = new Coordinate(-1,-1);
		char ans = 'n';
		
		//check if you can win
		move.col = canWin(color);
		if(move.col!=-1){
			ans = 'w';
		}
		else{
			//check if other person can win
			move.col = canWin('B');
			//if they can't win
			if(move.col==-1){
				//X out all of the col I can't go bc they would win
				move.col = markBadCol();
				//if I still have col to choose from
				if(move.col==-1){
					//check for two at the bottom
					move.col = twoBott();
					if(move.col==-1){
						//Get rid of moves where I could let them block me
						markNotBestCol();
						//go middle
						if(possibleRow[3]>-1){
							move.col=3;
						}
						else{
							//block a three in a row
							move.col = three('B');
							if(move.col==-1){
								//go for three in a row
								move.col = three(color);
								if(move.col==-1){
									move.col = two('B');
									if(move.col==-1){
										//go for two in a row
										move.col = two(color);
										//other wise just go anywhere
										if(move.col==-1){
											int ij=0;
											while(ij<7){
												if(possibleRow[ij]>-1){
													move.col=ij;
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
				}
			}
		}
	
		move.row = possibleRow[move.col];
		prevMoves[movesCnt]=move;
		movesCnt++;
		board[move.row][move.col]=color;
		return ans;
	}

	
	public int getNumberOfMoves(){
		return movesCnt;
	}
	
	//helper
	/**
	 * Fills in possibleRow moves. If column is full then fills in -1
	 */
	private void findPossibleMoves(){ 
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
	 * Lets you know if opponent has two in a row at the bottom
	 * @return
	 */
	private int twoBott(){
		if(board[0][3]=='B' && (board[0][2]=='B' || board[0][4]=='B') ){
			if(board[0][2]=='O')
				return 2;
			if(board[0][4]=='O')
				return 4;
		}
		return -1;
	}
	
	private int three(char color){
		int down, right, dr, dl, y;
		for(int x=0;x<7;x++){ 
				down=0;
				right=0;
				dr=0;
				dl=0;
				if(possibleRow[x]<0){
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
	
	private int two( char color){
		int down, right, dr, dl, y;
		for(int x=0;x<7;x++){ 
				down=0;
				right=0;
				dr=0;
				dl=0;
				if(possibleRow[x]<0){
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

	/**
	 * returns column number to win in one move -1 if can't
	 * @param color
	 * @return column number to win
	 */
	private int canWin(char color){ 
		Coordinate last = new Coordinate(-1,-1);
		for(int x=0; x<7; x++){
			//if column is full skip it
			if(possibleRow[x]<0){
				continue;
			}
			last.col =x;
			last.row = possibleRow[x];
			board[last.row][last.col]=color;
			//if you can win go here
			if( winOneMove(last, color) ){
				System.out.println("Blocking a win on col:"+ last.row);
				board[last.row][last.col]='O';
				return last.col;
			}
			board[last.row][last.col]='O';
		}
		return -1;
		
	}

	/**
	 * Returns true if this color can win in one move by making move last
	 * @param last -> coordinate of move to be made
	 * @param color -> char of color (R or B)
	 * @return
	 */
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

	/**
	 * Marks all the columns where if AI goes could allow other player to win
	 * @return -1 if there is still columns to choose from after marking or returns col number if all col are bad
	 */
	private int markBadCol(){
		int avaiableCol = 7;
		//for each column
		for(int col=0; col<possibleRow.length; col++){
			int openRow = possibleRow[col];
			//if row is full or has only one left skip analysis
			if(openRow==-1){
				avaiableCol--;
				continue;
			}
			if(openRow==5){
				continue;
			}
			//if you go here
			board[openRow][col]= color;
			possibleRow[col]++;
			//check if other player can win if I went there
			if(canWin('B')!=-1){
				//mark col as bad
				possibleRow[col]=possibleRow[col]-105;
				avaiableCol--;
			}
			possibleRow[col]--;
			board[openRow][col]= 'O';
		}
		if(avaiableCol<1){
			for(int col=0; col<possibleRow.length; col++){
				int openRow = possibleRow[col];
				//if all moves cause a loss then just choose first open col
				if(openRow!=-1){
					possibleRow[col]= openRow+105;
					return col;
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * Marks cols where the space above the current play would allow AI to win.
	 * If marking these cols means that no col are left to play on then it does not mark them
	 */
	private void markNotBestCol(){
		int avaiableCol = 7;
		int[] newPossibleRow = new int[7];
		//for each column
		for(int col=0; col<possibleRow.length; col++){
			int openRow = possibleRow[col];
			newPossibleRow[col]= openRow;
			//if row is full or marked as bad or has only one left skip analysis
			if(openRow<0){
				avaiableCol--;
				continue;
			}
			if(openRow==5){
				continue;
			}
			//check if I can win if I went in space above
			if(winOneMove(new Coordinate(openRow+1,col),color)){
				//mark col as not best
				newPossibleRow[col]=possibleRow[col]-205;
				avaiableCol--;
			}
		}
		//if we did not mark off every col then show the new marks
		if(avaiableCol>=1){
			possibleRow = newPossibleRow;
		}
		//otherwise leave the possible Rows
	}
	
}