package nl.limakajo.numbers.numbersgame;

import android.graphics.Point;

/**
 * Device represents an immutable representation of the actual device that is running the App
 * 
 * @author mwbou
 */

public class Device {

	//	rep:
	private int width;
	private int height;
	
	//	rep invariant:
	//		N.A.
	//
	//	abstraction function:
	//		represents the characteristics of the device that is running the App
	//
	//	safety from rep exposure argument:
	//		all variables are private and immutable

	/**
	 * Constructor
	 * 
	 * @param size Size of the display
	 */
	public Device(Point size) {
		this.width = size.x;
		this.height = size.y;
	}

	/**
	 * GETTERS
	 */
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
