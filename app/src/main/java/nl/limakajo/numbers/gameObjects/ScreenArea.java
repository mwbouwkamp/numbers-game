package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.PaintComparator;

/**
 * Class that represents a rectangular ScreenArea
 *
 * @author M.W.Bouwkamp
 */

public class ScreenArea extends LayoutObject {

    /**
     * Constructs a ScreenArea
     *
     * @param paint     Paint defining the graphical attributes
     * @param rect      Rect defining the boundaries of the GameObjectInterface
     */
    public ScreenArea(Rect rect, Paint paint) {
        super(rect, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        if (new PaintComparator().compare(paint, Attributes.NO_DRAW) == -1) {
            canvas.drawRect(rect, paint);
        }
    }
}
