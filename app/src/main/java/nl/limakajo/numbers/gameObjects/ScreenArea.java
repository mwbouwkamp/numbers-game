package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Size;

import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.PaintComparator;

/**
 * Class that represents a rectangular ScreenArea
 *
 * @author M.W.Bouwkamp
 */

public class ScreenArea extends LayoutObject {

    /**
     * Constructs a LayoutObject
     *
     * @param position  Top-left position of the Layoutobject
     * @param size      Size of the LayoutObject
     * @param paint     Paint defining the graphical attributes
     */
    public ScreenArea(Point position, Size size, Paint paint) {
        super(position, size, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        if (new PaintComparator().compare(paint, Attributes.NO_DRAW) == -1) {
            canvas.drawRect(getArea(), paint);
        }
    }
}
