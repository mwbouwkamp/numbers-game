package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * @author M.W.Bouwkamp
 */

public interface Scene {

    void update();
    void draw(Canvas canvas);
    void terminate();
    void receiveTouch(MotionEvent event);
}
