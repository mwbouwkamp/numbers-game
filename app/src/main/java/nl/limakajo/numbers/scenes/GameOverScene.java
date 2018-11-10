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
        //Update levels usertime and transfer active levels
        DatabaseUtils.updateTableLevelsUserTime(MainActivity.getContext(), MainActivity.getGame().getLevel(), GameUtils.TIMEPENALTY);
        DatabaseUtils.transferActiveLevelToCompletedLevelIfExists(MainActivity.getContext());
        MainActivity.launchDownloadService();
        MainActivity.launchUploadService();
        initiating = false;
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
