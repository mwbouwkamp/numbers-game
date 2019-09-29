package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;

/**
 * Interface for all objects that need to be displayed on the screen.
 * The GameObjectInterface interface contains:
 * - a method to draw the GameObjectInterface to the canvas
 * - a method to update the GameObjectInterface
 *
 * @author M.W.Bouwkamp
 */
public interface GameObjectInterface {

    /**
     * Draws the GameObjectInterface to the canvas
     *
     * @param canvas    canvas to which to draw
     */
    void draw(Canvas canvas);

    /**
     * Updates the state of the GameObjectInterface
     */
    void update();


}
