package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import nl.limakajo.numbers.layouts.LayoutElementsKeys;
import nl.limakajo.numbers.layouts.LevelCompleteLayout;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;

/**
 * @author M.W.Bouwkamp
 */

public class LevelCompleteScene implements SceneInterface {

    private final SceneManager sceneManager;
    private LevelCompleteLayout levelCompleteLayout;
    private long animationStartTime;
    private int numStarsToAdd;
    private boolean animating;
    private boolean initiating;

    LevelCompleteScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.animating = false;
        this.levelCompleteLayout = new LevelCompleteLayout();
    }

    @Override
    public void init() {
        animating = true;
        int userTime = MainActivity.getGame().getLevel().getUserTime();
        int averageTime = MainActivity.getGame().getLevel().getAverageTime();

        //Update userTime, insert completed level to table completed levels and delete active levels
        DatabaseUtils.updateTableLevelsUserTimeForSpecificLevel(MainActivity.getContext(), MainActivity.getGame().getLevel());
        DatabaseUtils.updateTableLevelsLevelStatusForSpecificLevel(MainActivity.getContext(), MainActivity.getGame().getLevel(), GameUtils.LevelState.UPLOAD);

        MainActivity.launchDownloadService();
        MainActivity.launchUploadService();

        levelCompleteLayout.getTextBox(LayoutElementsKeys.LEVELCOMPLETE_TEXT).setText(Integer.toString(userTime) + " | " + Integer.toString(averageTime));
        numStarsToAdd = calculateNumStarsToAdd(userTime, averageTime);
        MainActivity.getPlayer().increaseNumLives(numStarsToAdd);
        MainActivity.getPlayer().increaseNumStars(numStarsToAdd);
        resetStar(LayoutElementsKeys.STAR1_TEXT);
        resetStar(LayoutElementsKeys.STAR2_TEXT);
        resetStar(LayoutElementsKeys.STAR3_TEXT);
        animationStartTime = System.currentTimeMillis();
        initiating = false;
    }

    private void resetStar(LayoutElementsKeys starKey) {
        levelCompleteLayout.getTextBox(starKey).getPaint().setAlpha(Attributes.STARS_DARK_ALPHA);
        levelCompleteLayout.getTextBox(starKey).getPaint().setStyle(Paint.Style.STROKE);
    }

    /**
     * Calculates the number of stars to add, based on the userTime
     * *: averageTime
     * @param userTime userTime
     * @return numstars to add
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
            levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR1_TEXT).getPaint().setAlpha((int) (Attributes.STARS_DARK_ALPHA + (255 - Attributes.STARS_DARK_ALPHA) * relativeTime));
        }
        if (relativeTime > 1) {
            levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR1_TEXT).getPaint().setStyle(Paint.Style.FILL);
        }
        if (relativeTime > 1 + delay && relativeTime <= 2 + delay && numStarsToAdd > 1) {
            levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR2_TEXT).getPaint().setAlpha((int) (Attributes.STARS_DARK_ALPHA + (255 - Attributes.STARS_DARK_ALPHA) * (relativeTime - 1 - delay)));
        }
        if (relativeTime > 2 + delay && numStarsToAdd > 1) {
            levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR2_TEXT).getPaint().setStyle(Paint.Style.FILL);
        }
        if (relativeTime > 2 + 2 * delay && relativeTime <= 3 + 2 * delay && numStarsToAdd > 2) {
            levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR3_TEXT).getPaint().setAlpha((int) (Attributes.STARS_DARK_ALPHA + (255 - Attributes.STARS_DARK_ALPHA) * (relativeTime - 2 - 2 * delay)));
        }
        if (relativeTime > 3 + 2 * delay && numStarsToAdd > 2) {
            levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR3_TEXT).getPaint().setStyle(Paint.Style.FILL);
        }
        if (System.currentTimeMillis() - animationStartTime > Attributes.LEVELCOMPLETE_ANIMATION_TIME * 3 + Attributes.LEVELCOMPLETE_TIME_BETWEEN_ANIMATIONS * 2){
            animating = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        levelCompleteLayout.draw(canvas);
    }

    @Override
    public void terminate() {

    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (!animating) {
            sceneManager.setScene(new GameplayScene(sceneManager));
        }
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
