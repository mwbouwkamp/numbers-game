package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * @author M.W.Bouwkamp
 */

public interface SceneInterface {

    void init();
    void update();
    void draw(Canvas canvas);
    void terminate();
    void receiveTouch(MotionEvent event);
    boolean getInitiating();
    void setInitiating(boolean initiating);
}
