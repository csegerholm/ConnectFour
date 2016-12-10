package cms549.connectfour;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class GameScreen extends AppCompatActivity {

    TextView tvturn;
    GridView gvboard;
    int[][] board; //6 rows, 7 cols, 0 means empty, 1 means p1, -1 means p2
    ArrayList<Move> boardAsList;
    int aiDif;//if 0 then there is 2 players
    int turn;
    int p1color,p2color;
    int movesMade;

    private PopupWindow popupWindow;
    private BoardAdapter ba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        tvturn = (TextView) findViewById(R.id.tvturn);
        gvboard = (GridView) findViewById(R.id.gvboard);
        movesMade=0;
        boardAsList = new ArrayList<Move>(42);

        createBlankBoard();

        Intent i =getIntent();
        p1color = i.getIntExtra("chip1",-1);
        p2color = i.getIntExtra("chip2",-1);
        turn = 1;
        aiDif = i.getIntExtra("aiDif",-1);
        if(p1color==-1 || p2color==-1){
            p1color = R.drawable.black_circle;
            p2color=R.drawable.red_circle;
            tvturn.setText("Player 1's Turn:");
        }
        ba = new BoardAdapter(this ,boardAsList );
        gvboard.setAdapter(ba);

        gvboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Move m = boardAsList.get(position);
                if(m.player!=0){
                    return;
                }

                //check if space below you is not blank
                int pos = getPositionBelow(position);
                if(pos>=0 && boardAsList.get(pos).player==0){
                    return;
                }

                if(aiDif>-1) {
                    //there is a computer
                    board[m.row][m.col] = 1;
                    m.player =1;
                    m.picID = p1color;
                    v.setBackground(getDrawable(m.picID));
                    movesMade++;
                    if(didWin(m)){
                        playerWins(1);
                        return;
                    }

                    //computer go -> should pick between hard and easy
                    if(aiDif==1) {
                        Move m1 = aiEasyMakeMove();
                        if(didWin(m1)){
                            playerWins(-1);
                        }
                    }
                    else{
                        Move m1 =aiHardMakeMove();
                        if(didWin(m1)){
                            playerWins(-1);
                        }
                    }
                    movesMade++;

                }else{
                    if(turn==1){
                        //player 1 goes
                        m.player =1;
                        m.picID = p1color;
                        v.setBackground(getDrawable(m.picID));
                        movesMade++;
                        tvturn.setText("Player 2's Turn:");
                        if(didWin(m)){
                            playerWins(1);
                            return;
                        }
                        turn=turn*-1;
                    }
                    else{
                        //player 2 goes
                        m.player =-1;
                        m.picID = p2color;
                        v.setBackground(getDrawable(m.picID));
                        movesMade++;
                        tvturn.setText("Player 1's Turn:");
                        if(didWin(m)){
                            playerWins(2);
                            return;
                        }
                        turn=turn*-1;
                    }
                }
                if(movesMade>=42){
                    playerWins(0);
                    return;
                }

            }
        });
    }

    private Move aiHardMakeMove() {
        int pos = HardAI.makemove(board);
        Move m = boardAsList.get(pos);
        board[m.row][m.col] = -1;
        m.player =-1;
        m.picID = p2color;
        View v=gvboard.getChildAt(pos);
        v.setBackground(getDrawable(m.picID));

        return m;

    }

    private Move aiEasyMakeMove() {
        Random r = new Random();
        int pos =38;
        while(true){
            pos=r.nextInt(42 );
            Move m = boardAsList.get(pos);
            if(m.player==0){
                int p2 = pos +7;
                //check row below it
                if(p2>= 42 || boardAsList.get(p2).player!=0){
                    m.player =-1;
                    m.picID = p2color;
                    View v=gvboard.getChildAt(pos);
                    v.setBackground(getDrawable(m.picID));
                    return m;
                }
            }
        }

    }

    /**
     * Prints out play again
     * @param i -> 0 means tie, 1 means player 1, 2 means player 2, -1 means computer
     */
    private void playerWins(int i) {
        //freeze the board
        //make a pop up

        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.endgame_popup, null);

        popupWindow=new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT,true);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        TextView t = (TextView) popupView.findViewById(R.id.popuptv);

        Button playagain = (Button) popupView.findViewById(R.id.popuppa);
        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                movesMade=0;
                createBlankBoard();
                ba.notifyDataSetChanged();
                gvboard.invalidateViews();

            }
        });

        Button quit = (Button) popupView.findViewById(R.id.popupquit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                quit(null);
            }
        });
        SharedPreferences myPref = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = myPref.edit();
        String ach = myPref.getString("achievements","");

        if(i==-1){
            t.setText("You lost.");
            editor.putInt("games",myPref.getInt("games",0)+1);
            editor.putInt("losses",myPref.getInt("losses",0)+1);
            int sc = myPref.getInt("score",0)+1;
            editor.putInt("score",sc);
            //50points
            if(sc>=50 && !ach.contains("4") ){
                ach= ach+"4";
                editor.putString("achievements", ach);
            }
            //1k points
            else if(sc>=1000 && !ach.contains("5") ){
                ach= ach+"5";
                editor.putString("achievements", ach);
            }
            int st = myPref.getInt("streak",0);
            if(st<0){
                editor.putInt("streak",st-1);
                if(st-1==-10){
                    //Loosing streak
                    if(!ach.contains("6")){
                        ach= ach+"6";
                        editor.putString("achievements", ach);
                    }
                }
            }
            else{
                editor.putInt("streak",0);
            }

            editor.commit();
        }
        else if(i==0){
            t.setText("It's a Tie.");
            editor.putInt("games",myPref.getInt("games",0)+1);
            editor.putInt("ties",myPref.getInt("ties",0)+1);
            int sc = myPref.getInt("score",0)+2;
            editor.putInt("score",sc);
            //50points
            if(sc>=50 && !ach.contains("4") ){
                ach= ach+"4";
                editor.putString("achievements", ach);
            }
            //1k points
            else if(sc>=1000 && !ach.contains("5") ){
                ach= ach+"5";
                editor.putString("achievements", ach);
            }
            editor.putInt("streak",0);


            editor.commit();

        }
        else{
            t.setText("Player "+ i+ " won!");
            if(i==1) {
                //won against pc
                if(aiDif >0){
                    if(!ach.contains("1")){
                        ach= ach+"1";
                        editor.putString("achievements", ach);
                    }
                }
                //won against friend
                else{
                    if(!ach.contains("3")){
                        ach= ach+"3";
                        editor.putString("achievements", ach);
                    }
                }

                editor.putInt("games", myPref.getInt("games", 0) + 1);
                editor.putInt("wins", myPref.getInt("wins", 0) + 1);
                int sc = myPref.getInt("score",0)+5;
                editor.putInt("score",sc);
                //50points
                if(sc>=50 && !ach.contains("4") ){
                    ach= ach+"4";
                    editor.putString("achievements", ach);
                }
                //1k points
                else if(sc>=1000 && !ach.contains("5") ){
                    ach= ach+"5";
                    editor.putString("achievements", ach);
                }

                int st = myPref.getInt("streak",0);
                if(st>0){
                    editor.putInt("streak",st+1);
                    if(st+1==10){
                        //Winning streak
                        if(!ach.contains("7")){
                            ach= ach+"7";
                            editor.putString("achievements", ach);
                        }
                    }
                }
                else{
                    editor.putInt("streak",0);
                }
                editor.commit();
            }
            else{
                //lost against friend
                editor.putInt("games",myPref.getInt("games",0)+1);
                editor.putInt("losses",myPref.getInt("losses",0)+1);
                int sc = myPref.getInt("score",0)+1;
                editor.putInt("score",sc);
                //50points
                if(sc>=50 && !ach.contains("4") ){
                    ach= ach+"4";
                    editor.putString("achievements", ach);
                }
                //1k points
                else if(sc>=1000 && !ach.contains("5") ){
                    ach= ach+"5";
                    editor.putString("achievements", ach);
                }
                int st = myPref.getInt("streak",0);
                if(st<0){
                    editor.putInt("streak",st-1);
                    if(st-1==-10){
                        //Loosing streak
                        if(!ach.contains("6")){
                            ach= ach+"6";
                            editor.putString("achievements", ach);
                        }
                    }
                }
                else{
                    editor.putInt("streak",0);
                }

                editor.commit();
            }
        }

    }

    private int getPositionBelow(int position) {

        int ans= position +7;
        //first row check
        if(ans>= 42){
            return -1;
        }
        return ans;

    }

    private void createBlankBoard() {
        boardAsList.clear();
        board = new int[6][7];
        int cnt =0;
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length;j++){
                board[i][j] =0;
                boardAsList.add(new Move(i,j,0,cnt,R.drawable.white_circle));
                cnt++;
            }
        }
    }

    private boolean didWin(Move m){
        int player = m.player;
        int right=0,left=0, up=0,down=0, ur=0,dl=0,ul=0,dr=0;
        int oldpos = m.position;
        int newpos = oldpos+1;

        //count right -> while you are on the same row
        while(newpos/7 == oldpos/7){
            if(boardAsList.get(newpos).player==player){
                right++;
            }
            else{
                break;
            }
            newpos++;
        }
        //count left-> while you are on the same row
        newpos = oldpos-1;
        while(newpos/7 == oldpos/7){
            if(boardAsList.get(newpos).player==player){
                left++;
            }
            else{
                break;
            }
            newpos--;
        }
        //count up-> until you hit the first row
        newpos = oldpos-7;
        while(newpos>=0){
            if(boardAsList.get(newpos).player==player){
                up++;
            }
            else{
                break;
            }
            newpos = newpos-7;
        }

        //count down -> unitl you hit bottom row
        newpos = oldpos+7;
        while(newpos<42){
            if(boardAsList.get(newpos).player==player){
                down++;
            }
            else{
                break;
            }
            newpos = newpos+7;
        }

        //ul= count up and to the left -> until you hit top row or left most column
        newpos = oldpos-8;
        while(newpos>=0 && newpos%7!=6){
            if(boardAsList.get(newpos).player==player){
                ul++;
            }
            else{
                break;
            }
            newpos = newpos-8;
        }

        //ur= count up and to the right -> until you hit top row or right most column
        newpos = oldpos-6;
        while(newpos>=0 && newpos%7!=0){
            if(boardAsList.get(newpos).player==player){
                ur++;
            }
            else{
                break;
            }
            newpos = newpos-6;
        }

        //dr= count down and to the right -> until you hit last row or right most column
        newpos = oldpos+8;
        while(newpos<42 && newpos%7!=0){
            if(boardAsList.get(newpos).player==player){
                dr++;
            }
            else{
                break;
            }
            newpos = newpos+8;
        }

        //dl
        newpos = oldpos+6;
        while(newpos<42 && newpos%7!=6){
            if(boardAsList.get(newpos).player==player){
                dl++;
            }
            else{
                break;
            }
            newpos = newpos+6;
        }

        //check if we have four in a row
        //horizontal
        int inARow = left+right;
        if(inARow>=3){
            return true;
        }
        //vertical
        inARow = up+down;
        if(inARow>=3){
            return true;
        }
        //diagonal
        inARow = dl+ur;
        if(inARow>=3){
            return true;
        }
        inARow = ul+dr;
        if(inARow>=3){
            return true;
        }

        return false;
    }

    public void quit(View view){
        finish();
    }


}
