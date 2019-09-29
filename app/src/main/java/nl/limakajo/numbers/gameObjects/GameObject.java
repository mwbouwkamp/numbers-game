package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class GameObject implements GameObjectInterface {
    protected Point position;
    protected Paint paint;
    protected double scale;
}
