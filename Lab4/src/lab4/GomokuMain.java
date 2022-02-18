package lab4;

import lab4.client.GomokuClient;
import lab4.gui.GomokuGUI;
import lab4.data.GomokuGameState;

public class GomokuMain {

	public static void main(String[] argv) throws ArrayIndexOutOfBoundsException {
		if(argv.length == 0) {			
			argv[0] = "4000";
		}else if(argv.length > 1) {
			throw new ArrayIndexOutOfBoundsException("To many portnumbers!");//HEJ
		}
		
		
		
		int portnr = Integer.parseInt(argv[0]);
		GomokuClient client = new GomokuClient(portnr);
		GomokuGameState gamestate = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gamestate, client);

	}

}
