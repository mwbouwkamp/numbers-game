package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import nl.limakajo.numbers.layouts.GameOverLayout;
import nl.limakajo.numbers.layouts.LevelCompleteLayout;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;

/**
 * @author M.W.Bouwkamp
 */

public class GameOverScene implements SceneInterface {

    private final SceneManager sceneManager;
    private GameOverLayout gameOverLayout;
    private boolean initiating;

    GameOverScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.gameOverLayout = new GameOverLayout();
    }

    @Override
    public void init() {
        DatabaseUtils.updateTableLevelsUserTime(MainActivity.getContext(), MainActivity.getGame().getLevel(), GameUtils.TIMEPENALTY);
        DatabaseUtils.updateTableCompletedLevelsUserTime(MainActivity.getContext(), MainActivity.getGame().getLevel(), GameUtils.TIMEPENALTY);
        MainActivity.launchDownloadService();
        MainActivity.launchUploadService();
        initiating = false;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        gameOverLayout.getScreenAreas().get("fullscreen").draw(canvas);
        gameOverLayout.getScreenAreas().get("blue").draw(canvas);
        gameOverLayout.getScreenAreas().get("red").draw(canvas);
        gameOverLayout.getScreenAreas().get("green").draw(canvas);
        gameOverLayout.getScreenAreas().get("yellow").draw(canvas);
        gameOverLayout.getTextBox("gameovertext").draw(canvas);
    }

    @Override
    public void terminate() {

    }

    @Override
    public void receiveTouch(MotionEvent event) {
        sceneManager.setScene(new GameplayScene(sceneManager));
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
