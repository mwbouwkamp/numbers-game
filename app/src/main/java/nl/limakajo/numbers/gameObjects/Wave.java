package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import nl.limakajo.numbers.animators.ScaleAnimator;
import nl.limakajo.numbers.utils.Attributes;

/**
 * Class representing a Wave
 *
 * @author mwbouwkamp
 */
public class Wave extends GameObject {

	private float radius;
	private final Paint paint;
	private ScaleAnimator scaleAnimator;
	
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
		this.scaleAnimator = new ScaleAnimator(Attributes.WAVE_ANIMATION_TIME);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(position.x, position.y, radius * scale, this.paint);
	}

	@Override
	public void update() {
		scale = scaleAnimator.getCurrentScale();
//		int alpha = (int) (Attributes.WAVE_ALPHA_START * (1 - factor));
//		int stroke = (int) (Attributes.WAVE_STROKE_START + (factor) * (Attributes.WAVE_STROKE_END - Attributes.WAVE_STROKE_START));
//		paint.setStrokeWidth(scaleAnimator.getCurrentStrokeWidth());
//		paint.setARGB(alpha, Attributes.WAVE_RED, Attributes.WAVE_GREEN, Attributes.WAVE_BLUE);
	}

	/**
	 * GETTERS AND SETTERS
	 */

	public ScaleAnimator getScaleAnimator() {
		return scaleAnimator;
	}

	public void setScaleAnimator(ScaleAnimator scaleAnimator) {
		this.scaleAnimator = scaleAnimator;
	}
}
