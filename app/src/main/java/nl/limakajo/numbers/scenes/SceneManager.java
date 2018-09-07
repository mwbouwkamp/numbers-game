package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.HashMap;

import static nl.limakajo.numbers.utils.GameUtils.GameState.*;

/**
 * @author M.W.Bouwkamp
 */

public class SceneManager {
    private final HashMap<String, SceneInterface> scenes = new HashMap<>();
    private String activeScene;

    public SceneManager() {
        scenes.put(MENU_STATE.toString(), new MenuScene(this));
        scenes.put(GAME_STATE.toString(), new GameplayScene(this));
        scenes.put(GAME_OVER_STATE.toString(), new GameOverScene(this));
        scenes.put(LEVEL_COMPLETE_STATE.toString(), new LevelCompleteScene(this));
        activeScene = MENU_STATE.toString();
    }

    public SceneInterface getScene(String sceneType) {
        return scenes.get(sceneType);
    }

    public void setScene(String sceneName) {
        this.activeScene = sceneName;
        scenes.get(sceneName).init();

    }
    public void recieveTouch(MotionEvent event) {
        scenes.get(activeScene).receiveTouch(event);
    }

    public void update() {
        scenes.get(activeScene).update();
    }

    public void draw(Canvas canvas) {
        scenes.get(activeScene).draw(canvas);
    }

}
