/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;
import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer {

	// Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;

	// Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED = 3;
	private int currentState;

	private GomokuClient client;

	private String message;

	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc) {
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}

	public void setChangedNotify() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString() {
		return this.message;
	}

	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid() {
		return this.gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y) {
		/*
		 * 1: Make sure a game is going on! 2: Make sure it's me's turn 3: the square is
		 * "free"
		 */
		if (currentState == NOT_STARTED) {
			message = "The game has not started";
			setChangedNotify();
		} else {

			if (currentState == MY_TURN) {
				if (gameGrid.move(x, y, GameGrid.ME) == false) {
					message = "The square was not clear, please try again!";
					setChangedNotify();
				} else {
					gameGrid.move(x, y, GameGrid.ME);
					message = "Move made!";
					client.sendMoveMessage(x, y);
					// Checks if the move is a game ending move.
					if (gameGrid.isWinner(GameGrid.ME)) {
						message = "You are the winner!!";
						currentState = FINISHED;
						setChangedNotify();
						// Other players turn!
					} else {
						message = "Your turn, make a move!";
						currentState = OTHER_TURN;
						setChangedNotify();
					}
				}

			}
		}

	}

	/**
	 * Starts a new game with the current client
	 */
	public void newGame() {
		gameGrid.clearGrid();
		currentState = OTHER_TURN;
		message = "Another game wants to be played!";
		client.sendNewGameMessage();
		setChangedNotify();
	}

	/**
	 * Other player has requested a new game, so the game state is changed
	 * accordingly
	 */
	public void receivedNewGame() {
		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "a new game wants to be made, make the first move!";
		setChangedNotify();

	}

	/**
	 * The connection to the other player is lost, so the game is interrupted
	 */
	public void otherGuyLeft() {
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "The other player left the game.";
		setChangedNotify();

	}

	/**
	 * The player disconnects from the client
	 */
	public void disconnect() {
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "You left the game.";
		client.disconnect();
		setChangedNotify();

	}

	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {

		gameGrid.move(x, y, GameGrid.OTHER);
		
		if (gameGrid.isWinner(GameGrid.OTHER)) {
			currentState = FINISHED;
			message = "You lost :(";			
		}else {
			currentState = MY_TURN;
			message = "Your turn!";			
		}
		setChangedNotify();
	}

	public void update(Observable o, Object arg) {

		switch (client.getConnectionStatus()) {
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();

	}

}