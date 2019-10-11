package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Size;

/**
 * Class that represents a LayoutObject
 *
 * @author M.W.Bouwkamp
 */
public abstract class LayoutObject extends GameObject {

    protected Size size;

    /**
     * Constructs a LayoutObject
     *
     * @param position  Top-left position of the Layoutobject
     * @param size      Size of the LayoutObject
     * @param paint     Paint defining the graphical attributes
     */
    public LayoutObject(Point position, Size size, Paint paint) {
        this.position = position;
        this.size = size;
        this.paint = new Paint(paint);
    }


    /**
     * GETTERS
     */
    public Rect getArea() {
        return new Rect(position.x, position.y, position.x + size.getWidth(), position.y + size.getHeight());
    }

    public Paint getPaint() {
        return paint;
    }
}
