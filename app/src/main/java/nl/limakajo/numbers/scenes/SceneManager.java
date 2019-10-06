package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.HashMap;

import nl.limakajo.numbers.animators.AnimatorThread;

import static java.lang.Thread.sleep;
import static nl.limakajo.numbers.utils.GameUtils.GameState.*;

/**
 * @author M.W.Bouwkamp
 */

public class SceneManager {
    private SceneInterface scene;
    private boolean activeSceneInitiating;
    private final AnimatorThread animatorThread;


    public SceneManager(AnimatorThread animatorThread) {
        this.animatorThread = animatorThread;
        scene = new MenuScene(this, animatorThread);
        startScene();
    }

    public void setScene(SceneInterface scene) {
        this.scene = scene;
        startScene();
    }

    public void startScene() {
        activeSceneInitiating = true;
        scene.setInitiating(true);
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
