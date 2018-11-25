package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Class that represents a LayoutObject
 *
 * @author M.W.Bouwkamp
 */
public class LayoutObject implements GameObjectInterface {
    final Rect rect;
    final Paint paint;

    /**
     * Constructs a LayoutObject
     *
     * @param paint     Paint defining the graphical attributes
     * @param rect      Rect defining the boundaries of the GameObjectInterface
     */
    LayoutObject(Paint paint, Rect rect) {
        this.paint = paint;
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
