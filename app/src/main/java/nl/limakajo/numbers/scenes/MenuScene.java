package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import nl.limakajo.numbers.layouts.MenuLayout;

import static nl.limakajo.numbers.utils.GameUtils.GameState.GAME_STATE;

/**
 * @author M.W.Bouwkamp
 */

public class MenuScene implements SceneInterface {

    private final SceneManager sceneManager;
    private MenuLayout menuLayout;
    private boolean initiating;

    MenuScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        menuLayout = new MenuLayout();
    }

    @Override
    public void init() {
        initiating = false;

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        menuLayout.getScreenAreas().get("fullscreen").draw(canvas);
        menuLayout.getScreenAreas().get("blue").draw(canvas);
        menuLayout.getScreenAreas().get("red").draw(canvas);
        menuLayout.getScreenAreas().get("green").draw(canvas);
        menuLayout.getScreenAreas().get("yellow").draw(canvas);
        menuLayout.getTextBoxes().get("logotext").draw(canvas);
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

    @Override
    public boolean getInitiating() {
        return initiating;
    }

    @Override
    public void setInitiating(boolean initiating) {
        this.initiating = initiating;
    }
}
