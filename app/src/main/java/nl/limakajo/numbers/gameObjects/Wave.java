package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import nl.limakajo.numbers.animators.PaintAnimator;
import nl.limakajo.numbers.animators.ScaleAnimator;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.PaintComparator;

/**
 * Class representing a Wave
 *
 * @author mwbouwkamp
 */
public class Wave extends GameObject implements CanAnimatePaint, CanAnimateScale {

	private ScaleAnimator scaleAnimator;
	private PaintAnimator paintAnimator;
	
	/**
	 * Constucts a Wave
	 * 
	 * @param position 		Point representing the center of the wave
	 */
	public Wave(Point position) {
		this.position = position;
		this.paint = new Paint(Attributes.WAVE_PAINT_START);
		this.scale = 1;
		this.scaleAnimator = new ScaleAnimator(Attributes.WAVE_ANIMATION_TIME);
		this.paintAnimator = new PaintAnimator(Attributes.WAVE_ANIMATION_TIME);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(position.x, position.y, Attributes.RADIUS * scale, this.paint);
	}

	@Override
	public void update() {
		if (scaleAnimator.isAnimating() || scale != scaleAnimator.getCurrentScale()) {
			scale = scaleAnimator.getCurrentScale();
		}
		if (paintAnimator.isAnimating() || new PaintComparator().compare(paint, paintAnimator.getCurrentPaint()) == -1) {
			paint = paintAnimator.getCurrentPaint();
		}
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

	public void setPaintAnimator(PaintAnimator paintAnimator) {
		this.paintAnimator = paintAnimator;
	}
}
