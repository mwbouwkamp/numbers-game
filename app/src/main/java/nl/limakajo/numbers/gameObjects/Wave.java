package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import nl.limakajo.numbers.utils.Attributes;

/**
 * Class representing a Wave
 *
 * @author mwbouwkamp
 */
public class Wave implements GameObjectInterface {

	private final Point position;
	private float radius;
	private final Paint paint;
	private final long animationStart;
	private final int animateTime;
	private volatile boolean animating;
	
	/**
	 * Constucts a Wave
	 * 
	 * @param position 		Point representing the center of the wave
	 */
	public Wave(Point position) {
		this.position = position;
		radius = Attributes.TILE_WIDTH / 2;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setARGB(Attributes.WAVE_ALPHA_START, Attributes.WAVE_RED, Attributes.WAVE_GREEN, Attributes.WAVE_BLUE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(Attributes.WAVE_STROKE_START);
		animateTime = Attributes.WAVE_ANIMATION_TIME;
		animationStart = System.nanoTime();
		animating = true;
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(position.x, position.y, radius, this.paint);
	}

	@Override
	public void update() {
		float timePassed  = (System.nanoTime() - animationStart) / 1000000;
		float factor = timePassed /(float) (animateTime);
		radius = (int) (500 * factor + Attributes.TILE_WIDTH * (1-factor) / 2);
		int alpha = (int) (Attributes.WAVE_ALPHA_START * (1 - factor));
		int stroke = (int) (Attributes.WAVE_STROKE_START + (factor) * (Attributes.WAVE_STROKE_END - Attributes.WAVE_STROKE_START));
		paint.setStrokeWidth(stroke);
		paint.setARGB(alpha, Attributes.WAVE_RED, Attributes.WAVE_GREEN, Attributes.WAVE_BLUE);
		if (timePassed >= animateTime) {
			animating = false;
		}
	}

	/**
	 * GETTERS AND SETTERS
	 */

	public boolean animates() {
		return animating;
	}
}
