package nl.limakajo.numbers.numbersgame;

import java.util.Date;

import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameConstants;

/**
 * Player represents a mutable representation of the player 
 *
 * @author M.W.Bouwkamp
 */
public class Player {

	//	rep:
	private int numLives;
	private int numStars;
	private int userAverageTime;
	private Date lastCheckNumLives;

	//	rep invariant:
	//		0 <= numLives <= MainActivity.MAX_NUMLIVES
	// 		0 <= numStars
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
		this.numStars = 0;
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
		if (numLives < 0 || numLives > GameConstants.MAX_NUMLIVES) {
			throw new RuntimeException("number of lives out of range");
		}
		if (numStars < 0) {
			throw new RuntimeException("number of stars out of range");
		}
		if (userAverageTime < 0 || userAverageTime> GameConstants.TIMEPENALTY) {
			throw new RuntimeException("averageTime out of range");
		}
	}

	/**
	 * Calculates the average time
	 * @return int representing the average time it takes a user to finish a level
	 */
	public int calcUserAverageTime() {
		return DatabaseUtils.getAverageTimeFromCompletedLevels(MainActivity.getContext());
	}

	/**
	 * Increases the number of Lives based on numStarsToAdd. If resulting numLives > MainActivity.MAX_NUMLIVES sets
	 * numLives to MainActivity.MAX_NUMLIVES
	 */
	public synchronized void increaseNumLives(int numLivesToAdd) {
		numLives += numLivesToAdd;
		if (numLives > GameConstants.MAX_NUMLIVES) {
			numLives = GameConstants.MAX_NUMLIVES;
		}
	}

	/**
	 * Increases the number of Stars based on numStarsToAdd
	 */
	public synchronized void increaseNumStars(int numStarsToAdd) {
		numStars += numStarsToAdd;
	}

	/**
	 * Decreases the number of Lives with one if numLives > 0. Otherwise sets numLives to 0
	 */
	public void decreaseNumLives() {
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

	public int getNumStars() {
		return numStars;
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

	public synchronized void setNumStars(int numStars) {
		this.numStars = numStars;
	}

	public void setLastCheckNumLives(Date date) {
		this.lastCheckNumLives = new Date(date.getTime());
	}
	
}
