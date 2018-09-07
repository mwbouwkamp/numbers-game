package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import nl.limakajo.numbers.layouts.LevelCompleteLayout;

import static nl.limakajo.numbers.utils.GameUtils.GameState.GAME_STATE;

/**
 * @author M.W.Bouwkamp
 */

public class LevelCompleteScene implements SceneInterface {

    private final SceneManager sceneManager;
    private LevelCompleteLayout levelCompleteLayout;

    LevelCompleteScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        init();
    }

    @Override
    public void init() {
        levelCompleteLayout = new LevelCompleteLayout();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        levelCompleteLayout.getScreenAreas().get("blue").draw(canvas);
        levelCompleteLayout.getScreenAreas().get("red").draw(canvas);
        levelCompleteLayout.getScreenAreas().get("green").draw(canvas);
        levelCompleteLayout.getScreenAreas().get("yellow").draw(canvas);
        levelCompleteLayout.getTextBoxes().get("logotext").draw(canvas);
    }

    @Override
    public void terminate() {

    }

    @Override
    public void receiveTouch(MotionEvent event) {
        sceneManager.setScene(GAME_STATE.toString());
        GameplayScene gameplayScene = (GameplayScene) sceneManager.getScene(GAME_STATE.toString());
        gameplayScene.init();

    }
}
