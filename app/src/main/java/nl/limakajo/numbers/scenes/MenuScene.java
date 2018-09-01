package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import static nl.limakajo.numbers.utils.GameUtils.GameState.GAME_STATE;
import static nl.limakajo.numbers.utils.GameUtils.GameState.MENU_STATE;

/**
 * Created by mwbou on 20-1-2018.
 */

public class MenuScene implements Scene {

    private SceneManager sceneManager;

    public MenuScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = new Rect(100,100,300,300);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.DKGRAY);
        canvas.drawRect(rect, paint);
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
