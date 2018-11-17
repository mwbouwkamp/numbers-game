package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import nl.limakajo.numbers.utils.Attributes;

/**
 * Class that represents a LayoutObject
 *
 * @author M.W.Bouwkamp
 */
public class LayoutObject implements GameObject {
    final Rect rect;
    final Paint paint;

    /**
     * Constructs a LayoutObject
     *
     * @param paint     Paint defining the graphical attributes
     * @param rect      Rect defining the boundaries of the GameObject
     */
    LayoutObject(Paint paint, Rect rect) {
        this.paint = new Paint(paint);
        this.rect = rect;
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public void update() {
    }

    /**
     * GETTERS
     */
    public Rect getArea() {
        return rect;
    }

    public Paint getPaint() {
        return paint;
    }
}
