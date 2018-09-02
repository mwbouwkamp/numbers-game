package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import nl.limakajo.numbers.layouts.MenuLayout;

import static nl.limakajo.numbers.utils.GameUtils.GameState.GAME_STATE;

/**
 * @author M.W.Bouwkamp
 */

public class MenuScene implements SceneInterface {

    private SceneManager sceneManager;
    private MenuLayout menuLayout;

    MenuScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        init();
    }

    @Override
    public void init() {
        menuLayout = new MenuLayout();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
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
}
