package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import nl.limakajo.numbers.animators.AnimatorThread;

import static java.lang.Thread.sleep;

/**
 * @author M.W.Bouwkamp
 */

public class SceneManager {
    private Scene scene;
    private boolean activeSceneInitiating;
    private final AnimatorThread animatorThread;


    public SceneManager(AnimatorThread animatorThread) {
        this.animatorThread = animatorThread;
        scene = new MenuScene(this, animatorThread);
        startScene();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        startScene();
    }

    public void startScene() {
        activeSceneInitiating = true;
        scene.init();
        while (scene.getInitiating()) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        activeSceneInitiating = false;
    }

    public void recieveTouch(MotionEvent event) {
        if (!activeSceneInitiating) {
            scene.receiveTouch(event);
        }
    }

    public void update() {
        if (!activeSceneInitiating) {
            scene.update();
        }
    }

    public void draw(Canvas canvas) {
        if (!activeSceneInitiating) {
            scene.draw(canvas);
        }
    }

}
