package lab4.data;

import java.util.Observable;
import java.util.Arrays;

/**
 * Represents the 2-d game grid
 */
/**
 * 
 * @author albin
 *
 * Here we create the gamingboard it self.
 *
 */

public class GameGrid extends Observable {

	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	public static final int INROW = 5;
	public int[][] gridArr;

	public GameGrid(int size) {
		this.gridArr = new int[size][size];
	}

	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y) {
		return gridArr[x][y];
	}

	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize() {
		return gridArr.length;
	}

	/**
	 * Enters a move in the game grid
	 * 
	 * @param x      the x position
	 * @param y      the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player) {
		if(gridArr[x][y] == EMPTY) {
			gridArr[x][y] = player;
			setChanged();
			notifyObservers();
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid() {
		this.gridArr = new int[getSize()][getSize()];
		setChanged();
		notifyObservers();
	}

	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player) {
		if(vertical(player) || horizontal(player) || diagonal(player)) {
			return true;
		}else {
			return false;
		}
	}

	//Checks if any column is full of the same player.
	private boolean vertical(int player) {
		for (int j = 0; j < getSize(); j++) {
			int inARow = 0;
			for (int i = 0; i < getSize(); i++) {
				if (gridArr[i][j] == player) {
					inARow++;
				} else {
					inARow = 0;
				}
				if (inARow == INROW) {
					return true;
				}
			}
		}

		return false;
	}

	// Checks if any row is full of the same player.
	private boolean horizontal(int player) {
		for (int i = 0; i < getSize(); i++) {
			int inARow = 0;
			for (int j = 0; j < getSize(); j++) {
				if (gridArr[i][j] == player) {
					inARow++;
				} else {
					inARow = 0;
				}
				if (inARow == INROW) {
					return true;
				}
			}
		}

		return false;
	}

	

	// Checks for five in a row in the diagonal direction!
	private boolean diagonal(int player) {
		//First checks which column is the last to be checked for a diagonal win.
		int lastPosible = getSize() - INROW;
		for(int j = 0; j <= lastPosible ; j++) {
			int i = 0;
			int inARow = 0;
			while(i < INROW) {
				if(gridArr[i][j] == player) {
					inARow++;
				}else {
					inARow = 0;
				}
				if(inARow == INROW) {
					return true;
				}
			}
			
		}

		return false;
	}

}