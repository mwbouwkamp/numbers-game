package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class GameObject {
    protected Point position;
    protected Paint paint;
    protected float scale;

    public Point getPosition() {
        return position;
    }

    public Paint getPaint() {
        return paint;
    }

    public float getScale() {
        return scale;
    }

    public abstract void update();

    public abstract void draw(Canvas canvas);
}
