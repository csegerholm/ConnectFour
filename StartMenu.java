package ImprovedVersion;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Welcome Screen: 
 * 	User selects the amount of players he/she wishes to play with.
 * @author csegerholm
 *
 */
public class StartMenu extends JPanel{
	
	/**
	 * This will be used to switch screens
	 */
	public CFApp home;
	
	/**
	 * Screen to show the current game.
	 */
	public GameScreen gameScreen;
	
	/**
	 * Constructor - Creates player amount selection screen.
	 * @param cfapp - App object used for switching screens.
	 */
	public StartMenu(CFApp cfapp){
		home = cfapp;
		gameScreen = new GameScreen(this);
		//Set color to blue
		setBackground(Color.BLUE);
		//Array layout where you pick coordinates of each component
		setLayout(null);
		updateScreen();
	}
	
	/**
	 * Redraws the screen using current data.
	 */
	public void updateScreen() {
		removeAll();
		makeHeaderText();
		makeOptions();
		revalidate();
	}

	/**
	 * Sets up the text on the start screen
	 */
	private void makeHeaderText(){
		int w =ScreenConfiguration.width;
		int h = ScreenConfiguration.height;
		
		JTextField cfText = new JTextField("Connect Four");
		cfText.setBackground(Color.yellow);
		cfText.setEditable(false);
		cfText.setFont(new Font("Tahoma", Font.BOLD, 64));
		cfText.setHorizontalAlignment(SwingConstants.CENTER);
		cfText.setBounds(w/8, h/16, w*3/4, h/4);
		add(cfText);
		
		JTextField selectPlayerText = new JTextField("Select Amount of Players:");
		selectPlayerText.setEditable(false);
		selectPlayerText.setBackground(Color.yellow);
		selectPlayerText.setFont(new Font("Tahoma", Font.BOLD, 32));
		selectPlayerText.setHorizontalAlignment(SwingConstants.CENTER);
		selectPlayerText.setBounds(w/4, h*3/8, w/2, h*3/16);
		add(selectPlayerText);
		
		
	}
	
	/**
	 * Draws buttons for amount of players
	 */
	private void makeOptions(){
		int w =ScreenConfiguration.width;
		int h = ScreenConfiguration.height;
		
		JButton one = new JButton("One Player");
		one.setBackground(Color.white);
		one.setBounds(w*5/16, h*10/16, w*3/8, h/16);
		one.setFont(new Font("Tahoma", Font.BOLD, 24));
		one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//switch to level screen
				startGame(1);
			}
		});
		add(one);
		
		JButton two = new JButton("Two Player");
		two.setBackground(Color.white);
		two.setFont(new Font("Tahoma", Font.BOLD, 24));
		two.setBounds(w*5/16, h*12/16, w*3/8, h/16);
		two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//switch to level screen
				startGame(2);
			}
		});
		add(two);
	}
	
	/**
	 * Switches to game screen to start the game
	 * @param players
	 */
	private void startGame(int players){
		//set frame
		home.frame.setContentPane(gameScreen);
		gameScreen.startGame(players);
	}
	
	/**
	 * Called to redraw the screen when a game ends.
	 */
	public void restart(){
		//Return here when they quit or win
		home.frame.setContentPane(this);
		this.updateScreen();
	}
	
}