package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import nl.limakajo.numbers.utils.Animator;

public abstract class GameObject implements GameObjectInterface {
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
}
