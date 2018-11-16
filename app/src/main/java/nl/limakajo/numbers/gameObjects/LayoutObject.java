package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import nl.limakajo.numbers.utils.Attributes;

public class LayoutObject implements GameObject {
    final Rect rect;
    final Paint paint;

    LayoutObject(Paint paint, Rect rect) {
        this.paint = new Paint(paint);
        this.rect = rect;
    }

    public Rect getArea() {
        return rect;
    }

    public Paint getPaint() {
        return paint;
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public void update() {
    }
}
