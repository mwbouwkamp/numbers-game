package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import nl.limakajo.numbers.utils.Attributes;

/**
 * Wave class
 * 
 * This mutable data type represents a wave on the board for visual effect
 * 
 * @author mwbouwkamp
 */
public class Wave implements GameObject {

	private Point position;
	private float radius;
	private Paint paint;
	private int alpha;
	private int stroke;
	private long animationStart;
	private int animateTime;
	private volatile boolean animating;
	
	/**
	 * Constucts Wave
	 * 
	 * @param position of the wave
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
	
	/**
	 * Draws the wave
	 * 
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		canvas.drawCircle(position.x, position.y, radius, this.paint);
	}

	@Override
	public void update() {
		float timePassed  = (System.nanoTime() - animationStart) / 1000000;
		float factor = timePassed /(float) (animateTime);
		radius = (int) (500 * factor + Attributes.TILE_WIDTH * (1-factor) / 2);
		alpha = (int) (Attributes.WAVE_ALPHA_START * (1-factor));
		stroke = (int) (Attributes.WAVE_STROKE_START + (factor) * (Attributes.WAVE_STROKE_END - Attributes.WAVE_STROKE_START));
		paint.setStrokeWidth(stroke);
		paint.setARGB(alpha, Attributes.WAVE_RED, Attributes.WAVE_GREEN, Attributes.WAVE_BLUE);
		if (timePassed >= animateTime) {
			animating = false;
		}
	}

	public boolean animates() {
		return animating;
	}
}
