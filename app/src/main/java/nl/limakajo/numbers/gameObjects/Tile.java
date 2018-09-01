package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import nl.limakajo.numbers.main.MainThread;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.GameUtils;

import java.util.LinkedList;

/**
 * Tile GameObject Class
 * Represents a Tile in the game
 *
 * @author M.W.Bouwkamp
 */

public class Tile implements GameObject {

	private int number;
	private Tile[] composition;
	private Point originalPosition;
	private Point currentPosition;
	private int color;
	private int colorIndex;
	private Paint paint;
	private boolean animating;
	private long animationStart;
	private int animateXInit;

	/**
	 * Constructs Tile that is not based on a combination of previous tiles
	 * 
	 * @param number the value of the tile
	 * @param position the position of the tile
	 */
	public Tile(int number, int position) {
		this.number = number;
		this.composition = new Tile[2];
		this.originalPosition = new Point(Attributes.TILE_XCOORDS[position], Attributes.TILE_YCOORD);
		this.currentPosition = new Point(Attributes.TILE_XCOORDS[position], Attributes.TILE_YCOORD);
		this.color = Attributes.TILE_COLORS[0];
		this.colorIndex = 0;
		paint = new Paint();
		animating = false;
	}

	/**
	 * Constructs Tile that is based on a combination of previous tiles
	 * 
	 * @param number the value of the tile
	 * @param position the position of the tile
	 * @param composition an array containing the Tiles this one is based on
	 * @param colorIndex the index in the array of tile colors to use
	 */
	public Tile(int number, int position, Tile[] composition, int colorIndex) {
		this.number = number;
		this.composition = composition;
		this.originalPosition = new Point(Attributes.TILE_XCOORDS[position], Attributes.TILE_YCOORD);
		this.currentPosition = new Point(Attributes.TILE_XCOORDS[position], Attributes.TILE_YCOORD);
		this.colorIndex = colorIndex;
		this.color = Attributes.TILE_COLORS[colorIndex];
		paint = new Paint();
		animating = false;
	}

	/**
	 * Moves Tile to shelf
	 * 
	 * @param playHand the Hand to move the Tile to
	 */
	public void toShelf(LinkedList<Tile> playHand) {
		playHand.add(this);
		this.setOriginalPosition(playHand.size());
		this.currentPosition = new Point(Attributes.TILE_XCOORDS[GameUtils.NUMTILES] + Attributes.TILE_WIDTH * 3, Attributes.TILE_YCOORD);
		startAnimation();
	}

	/**
	 * Decomposes the tile in its constituents
	 * 
	 * @param playHand the Hand to move the Tiles to
	 */
	public void crunch(LinkedList<Tile> playHand) {
		if (this.composition[0] != null) {
			this.composition[0].toShelf(playHand);
			this.composition[1].toShelf(playHand);
		} else {
			this.toShelf(playHand);
		}
	}

	@Override
	public void update() {
		if (animating) {
			float timePassed  = (System.nanoTime() - animationStart) / 1000000;
			float factor = timePassed /(float) (Attributes.TILE_ANIMATION_TIME);
			this.currentPosition = new Point((int) (animateXInit * (1-factor) + originalPosition.x * factor), currentPosition.y);
			if (timePassed >= Attributes.TILE_ANIMATION_TIME || currentPosition.x < originalPosition.x) {
				stopAnimation();
				this.currentPosition = originalPosition;
			}
		}

	}

	public void startAnimation() {
		if (!animating) {
			animating = true;
			animationStart = System.nanoTime();
			animateXInit = currentPosition.x;
		}
	}

	public void stopAnimation() {
		animating = false;
	}

	/**
	 * Compares tiles based on value
	 * 
	 */
	public int compareTo(Tile other) {
		return this.getNumber() - other.getNumber();
	}

	
	/**
	 * Checks if a tile is in a given ScreenArea
	 * 
	 * @param screenArea the area to check
	 * @return true if tile is in screenArea
	 */
	public boolean inArea(ScreenArea screenArea) {
		return screenArea.getArea().contains(getBounds(currentPosition));
	}
	
	/**
	 * Calculates the distance of a Tile to the touch position
	 * 
	 * @return true if the distance between the center of the tile and the
	 *         position where the mouse was clicked is smaller than the half of
	 *         the width of a tile
	 */
	public boolean isClicked(Point position) {
		return Math.sqrt((position.x - currentPosition.x) * (position.x - currentPosition.x) + (position.y - currentPosition.y) * (position.y - currentPosition.y)) < Attributes.TILE_WIDTH / 2;
	}

	/**
	 * Draws a tile
	 */
	@Override
	public void draw(Canvas canvas) {

		paint.setStyle(Paint.Style.FILL);
		paint.setColor(getColor());
		canvas.drawCircle(currentPosition.x, currentPosition.y, Attributes.TILE_WIDTH/2, paint);
		String numberText = Integer.toString(getNumber());
		drawCenteredText(canvas, numberText);
	}

	/**
	 * Draws the value in the center of the Tile
	 * @param canvas
	 * @param numberText
	 */
	public void drawCenteredText(Canvas canvas, String numberText) {
		Rect bounds = new Rect();
		paint.setTextAlign(Paint.Align.CENTER);
		paint.getTextBounds(numberText, 0, numberText.length(), bounds);
		paint.setTextSize(30);
		paint.setColor(Attributes.BG_COLOR);
		canvas.drawText(numberText, currentPosition.x, currentPosition.y + bounds.height() / 2, paint);
	}
		
	/**
	 * Converts Tile to String
	 * 
	 * examples: [1+1]*2 when the tile is constructed as follows: first 1 + 1, followed by multiplication with 2
	 */
	public String toString() {
		String toReturn = "";
		if (this.composition[0] != null) {
			if (this.composition[0].getNumber() + this.composition[1].getNumber() == this.getNumber()) {
				toReturn = "[" + this.composition[0].toString() + "+" + this.composition[1].toString() + "]";
			} else if (this.composition[0].getNumber() - this.composition[1].getNumber() == this.getNumber()) {
				toReturn = "[" + this.composition[0].toString() + "-" + this.composition[1].toString() + "]";
			} else if (this.composition[0].getNumber() * this.composition[1].getNumber() == this.getNumber()) {
				toReturn = "[" + this.composition[0].toString() + "*" + this.composition[1].toString() + "]";
			} else if (this.composition[0].getNumber() / this.composition[1].getNumber() == this.getNumber()) {
				toReturn = "[" + this.composition[0].toString() + "/" + this.composition[1].toString() + "]";
			}
		} else {
			toReturn = Integer.toString(this.getNumber());
		}
		return toReturn;
	}

	//Getters and Setters

	public void setCurrentPosition(Point position) {
		this.currentPosition = position;
	}

	public int getNumber() {
		return this.number;
	}

	public Tile[] getComposition() {
		return this.composition;
	}

	public int getColor() {
		return this.color;
	}

	public int getColorIndex() {
		return this.colorIndex;
	}

	public Rect getBounds(Point position) {
		return new Rect(position.x - Attributes.TILE_WIDTH / 2, position.y - Attributes.TILE_WIDTH / 2, position.x + Attributes.TILE_WIDTH / 2, position.y + Attributes.TILE_WIDTH /2);
	}

	public Point getCurrentPosition() {
		return this.currentPosition;
	}
	
	public void setOriginalPosition(int position) {
		this.originalPosition = new Point(Attributes.TILE_XCOORDS[position], Attributes.TILE_YCOORD);
	}
	
	public Point getOriginalPosition() {
		return this.originalPosition;
	}

}
