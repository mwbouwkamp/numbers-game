package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import nl.limakajo.numbers.main.AnimatorThread;
import nl.limakajo.numbers.layouts.GameOverLayout;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;

/**
 * @author M.W.Bouwkamp
 */

public class GameOverScene extends Scene {

    private final SceneManager sceneManager;
    private final AnimatorThread animatorThread;
    private final GameOverLayout gameOverLayout;

    GameOverScene(SceneManager sceneManager, AnimatorThread animatorThread) {
        this.sceneManager = sceneManager;
        this.gameOverLayout = new GameOverLayout();
        this.animatorThread = animatorThread;
    }

    @Override
    public void init() {
        animatorThread.removeAll();
        //Transfer ACTIVE level to UPLOAD
        DatabaseUtils.updateTableLevelsLevelStatusForSpecificLevel(MainActivity.getContext(), MainActivity.getGame().getLevel(), GameUtils.LevelState.UPLOAD);
        MainActivity.launchDownloadService();
        MainActivity.launchUploadService();
        setInitiating(false);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        gameOverLayout.draw(canvas);
    }

    @Override
    public void terminate() {

    }

    @Override
    public void receiveTouch(MotionEvent event) {
        sceneManager.setScene(new GameplayScene(sceneManager, animatorThread));
    }

}
