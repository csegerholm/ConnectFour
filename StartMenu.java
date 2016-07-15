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
		gameScreen = new GameScreen();
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
		
		JTextField cfText = new JTextField("Connect Four");
		cfText.setEditable(false);
		cfText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cfText.setHorizontalAlignment(SwingConstants.CENTER);
		cfText.setBounds(200, 10, 200, 30);
		add(cfText);
		
		JTextField selectPlayerText = new JTextField("Please Select Amount of Players:");
		selectPlayerText.setEditable(false);
		selectPlayerText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		selectPlayerText.setHorizontalAlignment(SwingConstants.CENTER);
		selectPlayerText.setBounds(500, 50, 200, 30);
		add(selectPlayerText);
		
		
	}
	
	/**
	 * Draws buttons for amount of players
	 */
	private void makeOptions(){
		JButton one = new JButton("One Player");
		one.setBounds(500, 150, 200, 100);
		one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//switch to level screen
				startGame(1);
			}
		});
		add(one);
		
		JButton two = new JButton("Two Player");
		two.setBounds(500, 350, 200, 100);
		two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//switch to level screen
				startGame(2);
			}
		});
		add(two);
	}
	
	private void startGame(int players){
		//set frame
		home.frame.setContentPane(gameScreen);
		gameScreen.startGame(players);
		//Return here when they quit or win
		home.frame.setContentPane(this);
		this.updateScreen();
	}
	
}