package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	
	
	private JFrame frame;
	private JPanel panel;
	private JLabel messageLabel;
	private JButton newGameButton;
	private JButton connectButton;
	private JButton disconnectButton;
	
	private GamePanel gamePanelGrid;
	
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setVisible(true);
		
		/*panel = new JPanel();
		panel.add(gamePanelGrid);
		
		frame = new JFrame();
		frame.pack();
		frame.setSize(500,500);
		frame.add(panel); 
		frame.setVisible(true);
		
		gamePanelGrid = new GamePanel(gamestate.getGameGrid());
		*/
		
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}