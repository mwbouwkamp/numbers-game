package nl.limakajo.numbers.numbersgame;

import java.util.Date;

import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;

/**
 * Player represents a mutable representation of the player 
 *
 * @author M.W.Bouwkamp
 */
public class Player {

	//	rep:
	private int numLives;
	private int userAverageTime;
	private Date lastCheckNumLives;

	//	rep invariant:
	//		0 <= numLives <= MainActivity.MAX_NUMLIVES
	//		0 <= userAverageTime <= NumberGames.TIMEPENALTY
	//
	//	abstraction function:
	//		represents the characteristics of the player 
	//
	//	safety from rep exposure argument:
	//		all variables are private and immutable

	/**
	 * Constructor
	 */
	public Player() {
		this.numLives = 0;
		this.lastCheckNumLives = new Date();
		userAverageTime = calcUserAverageTime();
		checkRep();
	}
	
	/**
	 * Checks the rep invariants
	 * 
	 * @effects Nothing if the rep is satisfied; otherwise throws exception 
	 */
	private void checkRep() {
		if (numLives < 0 || numLives > GameUtils.MAX_NUMLIVES) {
			throw new RuntimeException("number of lives out of range");
		}
		if (userAverageTime < 0 || userAverageTime> GameUtils.TIMEPENALTY) {
			throw new RuntimeException("averageTime out of range");
		}
	}

	/**
	 * Calculates the average time
	 * @return int representing the average time it takes a user to finish a level
	 */
	public int calcUserAverageTime() {
		return DatabaseUtils.getAverageTime(MainActivity.getContext());
	}

	/**
	 * Increases the number of Lives with one if numLives < MainActivity.MAX_NUMLIVES. Otherwise sets 
	 * numLives to MainActivity.MAX_NUMLIVES
	 */
	public synchronized void increaseNumLives() {
		if (numLives < GameUtils.MAX_NUMLIVES) {
			numLives++;
		}
		else {
			numLives = GameUtils.MAX_NUMLIVES;
		}
	}
	
	/**
	 * Decreases the number of Lives with one if numLives > 0. Otherwise sets numLives to 0
	 */
	public synchronized void decreaseNumLives() {
		if (numLives > 0) {
			numLives--;
		}
		else {
			numLives = 0;
		}
	}

	/**
	 * GETTERS
	 */
	public int getNumLives() {
		return numLives;
	}

	public int getUserAverageTime() {
		return userAverageTime;
	}

	public Date getLastCheckNumLives() {
		return lastCheckNumLives;
	}

	/**
	 * SETTERS
	 */
	public synchronized void setNumLives(int numLives) {
		this.numLives = numLives;
	}

	public void setLastCheckNumLives(Date date) {
		this.lastCheckNumLives = new Date(date.getTime());
	}
	
}
