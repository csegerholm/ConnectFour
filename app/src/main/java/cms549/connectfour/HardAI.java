package cms549.connectfour;

import android.util.Log;

public class HardAI {

    public static int makemove(int[][] board) {
        int pos = 0;
        int[] possibleRow = new int[7];
        findPossibleMoves(possibleRow, board);

        //check if you can win
        pos= canWin(board, possibleRow, -1);
        if(pos!=-1){
            return pos;
        }
        //check if other person can win
        pos = canWin(board, possibleRow, 1);
        if(pos!=-1){
            return pos;
        }

        //check for two at the bottom
        if(board[5][3]==0){
            return 38;
        }
        if(board[5][2]==0){
            return 37;
        }
        if(board[5][4]==0){
            return 39;
        }
        //go middle
        if(possibleRow[3]!=-1){
            return 3+possibleRow[3]*7;
        }

        //check if you can get three in a row
        pos = canThree(board, possibleRow, -1);
        if(pos!=-1){
            return pos;
        }
        //check if you block 3 in a row
        pos = canThree(board, possibleRow, 1);
        if(pos!=-1){
            return pos;
        }

        //do whatever
        for(int i=0; i< possibleRow.length;i++){
            if(possibleRow[i]!=-1){
                return i+possibleRow[i]*7;
            }
        }

        return pos;

    }

    /**
     * Returns the index of place you should go to get 3 in a row or block 3 in a row or -1 if no such place
     */
    private static int canThree(int[][] board, int[] possibleRow, int color) {
        for(int col=0; col<7; col++){
            //if column is full skip it
            if(possibleRow[col]<0){
                continue;
            }
            int row = possibleRow[col];
            board[row][col]=color;
            //if you can win go here
            if( inARow(board, 3, row, col ) ){
                board[row][col]=0;
                return row*7+col;
            }
            board[row][col]=0;
        }
        return -1;
    }

    /**
     * Returns the index of place you should go to get 4 in a row or block 4 in a row or -1 if no such place
     */
    private static int canWin(int[][] board, int[] possibleRow, int color){
        for(int col=0; col<7; col++){
            //if column is full skip it
            if(possibleRow[col]<0){
                continue;
            }
            int row = possibleRow[col];
            board[row][col]=color;
            //if you can win go here
            if( inARow(board, 4, row, col ) ){
                board[row][col]=0;
                return row*7+col;
            }
            board[row][col]=0;
        }
        return -1;

    }

    /**
     * returns if the specified row and col is part of a in a row sequence of length amt
     */
    private static boolean inARow(int[][] board, int amt, int row, int col) {
        int color = board[row][col];

        int inARow=1;
        //count down then up
        int currRow = row+1;
        while(currRow<board.length && board[currRow][col]==color ){
            inARow++;
            currRow++;
        }
        currRow = row-1;
        while(currRow>-1 && board[currRow][col]==color ){
            inARow++;
            currRow--;
        }
        if(inARow>=amt){
            return true;
        }

        //count right then left
        inARow=1;
        int currCol = col+1;
        while(currCol<board[0].length && board[row][currCol]==color ){
            inARow++;
            currCol++;
        }
        currCol = col-1;
        while(currCol>-1 && board[row][currCol]==color ){
            inARow++;
            currCol--;
        }
        if(inARow>=amt){
            return true;
        }

        //count down and right then up and left (diagonal)
        inARow=1;
        currCol = col+1;
        currRow = row+1;
        while(currCol<board[0].length && currRow<board.length &&board[currRow][currCol]==color ) {
            inARow++;
            currCol++;
            currRow++;
        }
        currCol = col-1;
        currRow = row-1;
        while(currCol>-1 && currRow>-1 &&board[currRow][currCol]==color ){
            inARow++;
            currCol--;
            currRow--;
        }
        if(inARow>=amt){
            return true;
        }


        inARow=0;
        //count down and left then up and right (diagonal)
        currCol = col+1;
        currRow = row-1;
        while(currCol<board[0].length && currRow>=0 && board[currRow][currCol]==color ){
            inARow++;
            currCol++;
            currRow--;
        }
        currCol = col-1;
        currRow = row+1;
        while(currCol>-1 && currRow<board.length && board[currRow][currCol]==color ){
            inARow++;
            currCol--;
            currRow++;
        }
        if(inARow>=amt){
            return true;
        }

        return false;

    }


    private static void findPossibleMoves(int[] possibleRow, int[][] board){
        for(int col=0; col<7; col++){
            possibleRow[col]=-1;
            for(int row=5; row>=0;row--){
                if(board[row][col]==0){
                    possibleRow[col]= row;
                    break;
                }
            }
        }
    }



}
