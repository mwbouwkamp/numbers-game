package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;

/**
 * GameObject Interface
 * Interface for all objects that need to be displayed on the screen
 *
 * @author M.W.Bouwkamp
 */

public interface GameObject {
    void draw(Canvas canvas);
    void update();
}
