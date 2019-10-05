package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import nl.limakajo.numbers.animators.PositionAnimator;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numberslib.utils.GameConstants;

/**
 * Class representing a Tile in the game
 *
 * @author M.W.Bouwkamp
 */
public class Tile extends GameObject {

	private final int number;
	private final Tile[] composition;
	private final int color;
	private final int colorIndex;
	private PositionAnimator positionAnimator;

	/**
	 * Constructs Tile that is not based on a combination of previous tiles
	 * 
	 * @param number 	the value of the tile
	 */
	public Tile(int number) {
		this.number = number;
		this.composition = new Tile[2];
		this.color = Attributes.TILE_COLORS[0];
		this.colorIndex = 0;
		this.paint = new Paint();
		this.positionAnimator = new PositionAnimator(Attributes.TILE_ANIMATION_TIME);
	}

	/**
	 * Constructs Tile that is based on a combination of previous tiles
	 * 
	 * @param number 		the value of the tile
	 * @param composition 	an array containing the Tiles this one is based on
	 * @param colorIndex 	the index in the array of tile colors to use
	 */
	public Tile(int number, Tile[] composition, int colorIndex) {
		this.number = number;
		this.composition = composition;
		this.colorIndex = colorIndex;
		this.color = Attributes.TILE_COLORS[colorIndex];
		this.paint = new Paint();
		this.positionAnimator = new PositionAnimator(Attributes.TILE_ANIMATION_TIME);
	}

	/**
	 * Decomposes the tile in its constituents
	 * 
	 * @return 		the tiles after crunching
	 */
	public Tile[] crunch() {
		if (this.composition[0] != null) {
			return composition;
		} else {
			return new Tile[] {this};
		}
	}

	/**
	 * Checks if a tile is in a given ScreenArea
	 * 
	 * @param screenArea the area to check
	 * @return true if tile is in screenArea
	 */
	public boolean inArea(ScreenArea screenArea) {
		return screenArea.getArea().contains(getBounds(position));
	}
	
	/**
	 * Returns true if the Tile was pressed
	 * Calculated using the distance between the coordinates pressed and the center of the tile
	 * 
	 * @return true if the Tile was pressed
	 */
	public boolean isClicked(Point position) {
		return Math.sqrt((position.x - this.position.x) * (position.x - this.position.x) + (position.y - this.position.y) * (position.y - this.position.y)) < Attributes.TILE_WIDTH / 2;
	}

	@Override
	public void draw(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(getColor());
		canvas.drawCircle(position.x, position.y, Attributes.TILE_WIDTH / 2, paint);
		String numberText = Integer.toString(getNumber());
		drawCenteredText(canvas, numberText);
	}

	/**
	 * Draws the value in the center of the Tile
	 * @param canvas		Canvast to draw to
	 * @param numberText	Text to draw
	 */
	private void drawCenteredText(Canvas canvas, String numberText) {
		Rect bounds = new Rect();
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(30);
		paint.getTextBounds(numberText, 0, numberText.length(), bounds);
		paint.setTextSize((float) (0.6 * paint.getTextSize() * Attributes.TILE_WIDTH / Math.sqrt(bounds.width() * bounds.width() + bounds.height() * bounds.height())));
		paint.setTextAlign(Paint.Align.CENTER);
		paint.getTextBounds(numberText, 0, numberText.length(), bounds);
		paint.setColor(Attributes.BG_COLOR);
		canvas.drawText(numberText, position.x, position.y + bounds.height() / 2, paint);
	}

	@Override
	public void update() {
		if (positionAnimator.isAnimating() || position != positionAnimator.getCurrentPosition()) {
			position = positionAnimator.getCurrentPosition();
		}
	}

	@Override
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

	/**
	 * Adds the Tile to a tilePool and return an PositionAnimator that is required to animating the Tile to the correct position
	 *
	 * @param tilePool		the TilePool to add the Tile to
	 * @return			the PositionAnimator required to animating the Tile to the correct position on the tilePool
	 */
	public PositionAnimator addToShelf(TilePool tilePool) {
		int positionOnShelf = tilePool.add(this);
		position = new Point(Attributes.TILE_XCOORDS[GameConstants.NUMTILES] + Attributes.TILE_WIDTH * 3, Attributes.TILE_YCOORD);
		positionAnimator = new PositionAnimator(Attributes.TILE_ANIMATION_TIME);
		positionAnimator.init(
				this,
				new Point(Attributes.TILE_XCOORDS[positionOnShelf], Attributes.TILE_YCOORD));
		positionAnimator.startAnimation();
		return positionAnimator;
	}


	/**
	 * Getters and Setters
	 */

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getNumber() {
		return this.number;
	}

	public int getColor() {
		return this.color;
	}

	public int getColorIndex() {
		return this.colorIndex;
	}

	private Rect getBounds(Point position) {
		return new Rect(position.x - Attributes.TILE_WIDTH / 2, position.y - Attributes.TILE_WIDTH / 2, position.x + Attributes.TILE_WIDTH / 2, position.y + Attributes.TILE_WIDTH /2);
	}

	public Point getPosition() {
		return this.position;
	}

	public PositionAnimator getPositionAnimator() {
		return this.positionAnimator;
	}

	public void setPositionAnimator(PositionAnimator positionAnimator) {
		this.positionAnimator = positionAnimator;
	}
}
