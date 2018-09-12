package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import nl.limakajo.numbers.layouts.LevelCompleteLayout;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;

import static nl.limakajo.numbers.utils.GameUtils.GameState.GAME_STATE;

/**
 * @author M.W.Bouwkamp
 */

public class LevelCompleteScene implements SceneInterface {

    private final SceneManager sceneManager;
    private LevelCompleteLayout levelCompleteLayout;
    private long animationStartTime;
    private int numStarsToAdd;
    private boolean animating;

    LevelCompleteScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.animating = false;
        levelCompleteLayout = new LevelCompleteLayout();
    }

    @Override
    public void init() {
        int userTime = MainActivity.getGame().getLevel().getUserTime();
        animating = true;
        int averageTime = MainActivity.getGame().getLevel().getAverageTime();
        DatabaseUtils.updateTableLevelsUserTime(MainActivity.getContext(), MainActivity.getGame().getLevel(), userTime);
        DatabaseUtils.updateTableCompletedLevelsUserTime(MainActivity.getContext(), MainActivity.getGame().getLevel(), userTime);
        MainActivity.launchDownloadService();
        MainActivity.launchUploadService();

        levelCompleteLayout.getTextBox("levelcompletetext").setText(Integer.toString(userTime) + " | " + Integer.toString(averageTime));
        numStarsToAdd = calculateNumStarsToAdd(userTime, averageTime);
        MainActivity.getPlayer().increaseNumLives(numStarsToAdd);
        MainActivity.getPlayer().increaseNumStars(numStarsToAdd);
        resetStar("star1text");
        resetStar("star2text");
        resetStar("star3text");
        animationStartTime = System.currentTimeMillis();
    }

    private void resetStar(String star1text) {
        levelCompleteLayout.getTextBox(star1text).getPaint().setAlpha(Attributes.STARS_DARK_ALPHA);
        levelCompleteLayout.getTextBox(star1text).getPaint().setStyle(Paint.Style.STROKE);
    }

    /**
     * Calculates the number of stars to add, based on the userTime
     * *: averageTime
     * @param userTime
     * @return
     */
    private int calculateNumStarsToAdd(int userTime, int averageTime) {
        int numStarsToAdd = 1;
        if (userTime < averageTime + (GameUtils.TIMER - averageTime) / 3) {
            numStarsToAdd++;
        }
        if (userTime < averageTime / 2) {
            numStarsToAdd++;
        }
        return numStarsToAdd;
    }

    @Override
    public void update() {
        double relativeTime = (System.currentTimeMillis() - animationStartTime) / (1.0 * Attributes.LEVELCOMPLETE_ANIMATION_TIME);
        double delay = 1.0 * Attributes.LEVELCOMPLETE_TIME_BETWEEN_ANIMATIONS / Attributes.LEVELCOMPLETE_ANIMATION_TIME;
        if (relativeTime <= 1) {
            levelCompleteLayout.getTextBox("star1text").getPaint().setAlpha((int) (Attributes.STARS_DARK_ALPHA + (255 - Attributes.STARS_DARK_ALPHA) * relativeTime));
        }
        if (relativeTime > 1) {
            levelCompleteLayout.getTextBox("star1text").getPaint().setStyle(Paint.Style.FILL);
        }
        if (relativeTime > 1 + delay && relativeTime <= 2 + delay && numStarsToAdd > 1) {
            levelCompleteLayout.getTextBox("star2text").getPaint().setAlpha((int) (Attributes.STARS_DARK_ALPHA + (255 - Attributes.STARS_DARK_ALPHA) * (relativeTime - 1 - delay)));
        }
        if (relativeTime > 2 + delay && numStarsToAdd > 1) {
            levelCompleteLayout.getTextBox("star2text").getPaint().setStyle(Paint.Style.FILL);
        }
        if (relativeTime > 2 + 2 * delay && relativeTime <= 3 + 2 * delay && numStarsToAdd > 2) {
            levelCompleteLayout.getTextBox("star3text").getPaint().setAlpha((int) (Attributes.STARS_DARK_ALPHA + (255 - Attributes.STARS_DARK_ALPHA) * (relativeTime - 2 - 2 * delay)));
        }
        if (relativeTime > 3 + 2 * delay && numStarsToAdd > 2) {
            levelCompleteLayout.getTextBox("star3text").getPaint().setStyle(Paint.Style.FILL);
        }
        if (System.currentTimeMillis() - animationStartTime > Attributes.LEVELCOMPLETE_ANIMATION_TIME * 3 + Attributes.LEVELCOMPLETE_TIME_BETWEEN_ANIMATIONS * 2){
            animating = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        levelCompleteLayout.getScreenAreas().get("fullscreen").draw(canvas);
        levelCompleteLayout.getScreenAreas().get("blue").draw(canvas);
        levelCompleteLayout.getScreenAreas().get("red").draw(canvas);
        levelCompleteLayout.getScreenAreas().get("green").draw(canvas);
        levelCompleteLayout.getScreenAreas().get("yellow").draw(canvas);
        levelCompleteLayout.getTextBox("levelcompletetext").draw(canvas);
        levelCompleteLayout.getTextBox("star1text").draw(canvas);
        levelCompleteLayout.getTextBox("star2text").draw(canvas);
        levelCompleteLayout.getTextBox("star3text").draw(canvas);
    }

    @Override
    public void terminate() {

    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (!animating) {
            sceneManager.setScene(GAME_STATE.toString());
            GameplayScene gameplayScene = (GameplayScene) sceneManager.getScene(GAME_STATE.toString());
            gameplayScene.init();
        }
    }
}
