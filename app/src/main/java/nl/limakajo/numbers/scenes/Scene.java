package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * @author M.W.Bouwkamp
 */

public abstract class Scene {
    private boolean initiating;

    public abstract void init();
    public abstract void update();
    public abstract void draw(Canvas canvas);
    public abstract void terminate();
    public abstract void receiveTouch(MotionEvent event);

    public boolean getInitiating() {
        return initiating;
    }
    public void setInitiating(boolean initiating) {
        this.initiating = initiating;
    }
}
