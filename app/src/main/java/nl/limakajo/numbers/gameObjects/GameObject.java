package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;

/**
 * GameObject Interface
 * Interface for all objects that need to be displayed on the screen
 *
 * Created by M.W.Bouwkamp on 9-9-2017.
 */

public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
