package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;

/**
 * Interface for all objects that need to be displayed on the screen.
 * The GameObject interface contains:
 * - a method to draw the GameObject to the canvas
 * - a method to update the GameObject
 *
 * @author M.W.Bouwkamp
 */
public interface GameObject {

    /**
     * Draws the GameObject to the canvas
     *
     * @param canvas    canvas to which to draw
     */
    void draw(Canvas canvas);

    /**
     * Updates the state of the GameObject
     */
    void update();

}
