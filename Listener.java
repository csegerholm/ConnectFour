package ImprovedVersion;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Listener implements MouseListener{

	private CFPlayer[] players;
	private int[] columnStart;
	private int circleWidth;
	
	private GameScreen gs;
		
	public Listener(CFPlayer[] players, GameScreen gs) {
		this.players = players;
		this.gs = gs;
	}
	
	public void updateColumns(int[] cols, int cw){
		columnStart = cols;
		circleWidth = cw;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x =e.getX();
		System.out.println("x="+x);
		int row =-1;
		for(int i=0;i<7;i++){
			int start =columnStart[i];
			if(x>=start && x<=start+circleWidth){
				row = i;
			}
		}
		char ans =players[gs.currPlayerIndex].makeMove(new Coordinate(row,0));
		if(ans == 't'){
			gs.gameOver("Tie Game!");
			return;
		}
		else if(ans == 'w'){
			gs.gameOver("Player "+(gs.currPlayerIndex+1) +" wins!");
			return;
		}
		else if(ans =='n'){
			//if it is a computer
			if(gs.playerAmount==1){
				//computer goes
				ans = players[1].makeMove(null);
				if(ans == 't'){
					gs.gameOver("Tie Game!");
					return;
				}
				else if(ans == 'w'){
					gs.gameOver("You Lose!");
					return;
				}
			}
			else{
				//if its human then let other player go
				gs.currPlayerIndex++;
				if(gs.currPlayerIndex==2){
					gs.currPlayerIndex = 0;
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
