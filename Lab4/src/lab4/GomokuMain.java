package lab4;

import lab4.client.GomokuClient;
import lab4.gui.GomokuGUI;
import lab4.data.GomokuGameState;
/**
 * The main class is used to create 3 instances. 
 * GomokuClient handles the communication and calls on methods in game state.
 * GomokuGameState handles the model part of the game. The model in this case holds all relevant data that describes a game in progress.
 * GomokuGUI handles the view part of the game. The view in this case consists of a window where the game is to be played in! 
 * @author albin
 *
 */
public class GomokuMain {
/**
 * 
 * @param argv - The port number the client should listen to.
 * @throws ArrayIndexOutOfBoundsException - If there is more that one port number, this error is thrown.
 */
	
	public static void main(String[] argv){
		
		GomokuClient client;
		
		if(argv.length == 1) {			
			client = new GomokuClient(Integer.parseInt(argv[0]));
		}else if(argv.length > 1) {
			throw new ArrayIndexOutOfBoundsException("To many portnumbers!");
		}else {
			client = new GomokuClient(4050);
		}
		
		
		
		
		GomokuGameState gamestate = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gamestate, client);

	}

}
