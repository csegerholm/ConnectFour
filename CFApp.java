package ImprovedVersion;

import java.awt.Dimension;
import javax.swing.JFrame;


/**
 * This will be the process that starts the game.
 * @author csegerholm
 */
public class CFApp {
	
	/**
	 * Will be in use the whole time
	 */
	JFrame frame;
	
	/**
	 * Holds the panel for selecting player amount
	 */
	StartMenu startMenu;
	
	public CFApp(){
		frame= new JFrame("Connect Four");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startMenu = new StartMenu(this);
		frame.setContentPane(startMenu);
		frame.pack();
		frame.setSize(new Dimension(ScreenConfiguration.actualWidth,ScreenConfiguration.actualHeight));
	}
	
	/**
	 * Starts application at the select amount of players screen
	 * @param args
	 */
	public static void main(String[] args) {
		new CFApp();
	}
}
