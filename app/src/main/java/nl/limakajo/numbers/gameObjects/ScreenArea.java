package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.PaintComparator;

/**
 * ScreenArea GameObject Class
 * Creates rectangular screen area
 *
 * @author M.W.Bouwkamp
 */

public class ScreenArea extends LayoutObject {

    public ScreenArea(Rect rect, Paint paint) {
        super(paint, rect);
    }

    @Override
    public void draw(Canvas canvas) {
        if (new PaintComparator().compare(paint, Attributes.NO_DRAW) == -1) {
            canvas.drawRect(rect, paint);
        }
    }
}
