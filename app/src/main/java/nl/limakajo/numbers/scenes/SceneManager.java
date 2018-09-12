package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.HashMap;

import static java.lang.Thread.sleep;
import static nl.limakajo.numbers.utils.GameUtils.GameState.*;

/**
 * @author M.W.Bouwkamp
 */

public class SceneManager {
    private final HashMap<String, SceneInterface> scenes = new HashMap<>();
    private String activeScene;
    private boolean activeSceneInitiating;

    public SceneManager() {
        scenes.put(MENU_STATE.toString(), new MenuScene(this));
        scenes.put(GAME_STATE.toString(), new GameplayScene(this));
        scenes.put(GAME_OVER_STATE.toString(), new GameOverScene(this));
        scenes.put(LEVEL_COMPLETE_STATE.toString(), new LevelCompleteScene(this));
        activeScene = MENU_STATE.toString();
        setScene(activeScene);
    }

    public SceneInterface getScene(String sceneType) {
        return scenes.get(sceneType);
    }

    public void setScene(String sceneName) {
        this.activeScene = sceneName;
        activeSceneInitiating = true;
        scenes.get(sceneName).setInitiating(true);
        scenes.get(sceneName).init();
        while (scenes.get(sceneName).getInitiating()) {
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
            scenes.get(activeScene).receiveTouch(event);
        }
    }

    public void update() {
        if (!activeSceneInitiating) {
            scenes.get(activeScene).update();
        }
    }

    public void draw(Canvas canvas) {
        if (!activeSceneInitiating) {
            scenes.get(activeScene).draw(canvas);
        }
    }

}
