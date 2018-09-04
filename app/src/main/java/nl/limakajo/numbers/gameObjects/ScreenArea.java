package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * ScreenArea GameObject Class
 * Creates rectangular screen area
 *
 * @author M.W.Bouwkamp
 */

public class ScreenArea implements GameObject {

    private final Rect rect;
    private final Paint paint;

    public ScreenArea(Rect rect, Paint paint) {
        this.rect = rect;
        this.paint = new Paint(paint);
    }

    public Rect getArea() {
        return rect;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update() {

    }
}
