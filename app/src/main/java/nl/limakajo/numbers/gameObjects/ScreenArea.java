package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * ScreenArea GameObject Class
 * Creates rectangular screen area
 *
 * Created by M.W.Bouwkamp on 9-9-2017.
 */

public class ScreenArea implements GameObject {

    private Rect rect;
    private Paint paint;

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
