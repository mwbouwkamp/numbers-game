package nl.limakajo.numbers.numbersgame;

import nl.limakajo.numbers.utils.GameUtils;

/**
 * Level represents a mutable representation of a level in the gameplay
 * TODO: Make sure levels can be completed without making hugh numbers. Also make sure that player cannot make hugh numbers. In that case display error message in the footer area
 * @author M.W.Bouwkamp
 */
public class Level {

	//	rep:
	private int[] hand;
	private int goal;
	private int averageTime;
	private int userTime;

	//	rep invariant:
	//		size of hand is NumbersGame.NUMTILES
	//		100 <= goal <= 999
	//		0 <= averageTime <= NumbersGame.TIMEPENALTY
	//		0 <= userTime <= NumbersGame.TIMEPENALTY
	//
	//	abstraction function:
	//		represents the characteristics of the device that is running the App
	//
	//	safety from rep exposure argument:
	//		all variables are private

	/**
	 * Constructor
	 *  
	 * @param hand the hand to play 
	 * @param goal the goal to reach
	 * @param averageTime the average time it has taken people to complete the level
	 */
	public Level(int[] hand, int goal, int averageTime) {
		this.hand = new int[GameUtils.NUMTILES];
		for (int i = 0; i < GameUtils.NUMTILES; i++) {
			this.hand[i] = hand[i];
		}
		this.goal = goal;
		this.averageTime = averageTime;
		this.userTime = 0;
		checkRep();
	}

	/**
	 * Constructs Level
	 *  
	 * @param averageTime the average time it has taken people to complete the level
	 */
	public Level(String numbersString, int averageTime, int userTime) {
		this.hand = toArray(numbersString);
		this.goal = Integer.parseInt(numbersString.substring(GameUtils.NUMTILES * 3, GameUtils.NUMTILES * 3 + 3));
		this.averageTime = averageTime;
		this.userTime = userTime;
		checkRep();
	}
	
	/**
	 * Checks the rep invariants
	 * 
	 * @effects Nothing if the rep is satisfied; otherwise throws exception 
	 */
	private void checkRep() {
		if (hand.length != GameUtils.NUMTILES) {
			throw new RuntimeException("number of tiles in hand out of range");
		}
		if (goal < 0 || goal > 999) {
			throw new RuntimeException("goal out of range");
		}
		if (averageTime < 0 || averageTime> GameUtils.TIMEPENALTY) {
			throw new RuntimeException("averageTime out of range");
		}
		if (userTime < 0 || userTime> GameUtils.TIMEPENALTY) {
			throw new RuntimeException("userTime out of range");
		}
	}

	/**
	 * Converts an integer into a string using three characters
	 * 
	 * @param i the integer to convert
	 * @return String representation of the integer
	 */
	private String intToString(int i) {
		String returnString;
		if (i < 10){
			returnString = "00";
		}
		else if (i < 100) {
			returnString = "0";
		}
		else {
			returnString = "";
		}
		returnString += Integer.toString(i);
		return returnString;
	}

	/**
	 * Converts a string representation of a hand to an array representation
	 * 
	 * @param numbers the string representing of the hand. numbers.length() = 3 * NumbersGame.NUMTILES
	 * @return an array of type int (tile values). array.size() = NumbersGame.NUMTILES
	 */
	private int[] toArray(String numbers) {
		int[] returnArray = new int[GameUtils.NUMTILES];
		for (int i = 0; i < GameUtils.NUMTILES; i++) {
			returnArray[i] = Integer.parseInt(numbers.substring(i * 3, i * 3 + 3));
		}
		return returnArray;
	}

	/**
	 * Represents the hand in String format
	 * 
	 * @return a string representing the tiles and the goal. Each value is represented by three digits
	 * @example: 001002003004005006007008 for seven tiles with values 1 - 7 and a goal of 8
	 */
	public String toString() {
		String returnString = "";
		for (int i = 0; i < GameUtils.NUMTILES; i++) {
			returnString += intToString(this.getHand()[i]);
		}
		returnString += intToString(this.getGoal());
		return returnString;
	}

	/**
	 * GETTERS
	 */
	public int[] getHand() {
		return hand;
	}

	public int getGoal() {
		return goal;
	}

	public int getAverageTime() {
		return averageTime;
	}

	public int getUserTime() {
		return userTime;
	}

}
