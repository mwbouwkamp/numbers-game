package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import nl.limakajo.numbers.animators.Animator;
import nl.limakajo.numbers.animators.PaintAnimationStarter;
import nl.limakajo.numbers.main.AnimatorThread;
import nl.limakajo.numbers.layouts.LayoutElementsKeys;
import nl.limakajo.numbers.layouts.LevelCompleteLayout;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;
import nl.limakajo.numberslib.utils.GameConstants;

/**
 * @author M.W.Bouwkamp
 */

public class LevelCompleteScene extends Scene {

    private final SceneManager sceneManager;
    private final AnimatorThread animatorThread;
    private final LevelCompleteLayout levelCompleteLayout;

    LevelCompleteScene(SceneManager sceneManager, AnimatorThread animatorThread) {
        this.sceneManager = sceneManager;
        this.levelCompleteLayout = new LevelCompleteLayout();
        this.animatorThread = animatorThread;
    }

    @Override
    public void init() {
        this.animatorThread.removeAll();

        int userTime = MainActivity.getGame().getLevel().getUserTime();
        int averageTime = MainActivity.getGame().getLevel().getAverageTime();

        //Update userTime, insert completed level to table completed levels and delete active levels
        DatabaseUtils.updateTableLevelsUserTimeForSpecificLevel(MainActivity.getContext(), MainActivity.getGame().getLevel());
        DatabaseUtils.updateTableLevelsLevelStatusForSpecificLevel(MainActivity.getContext(), MainActivity.getGame().getLevel(), GameUtils.LevelState.UPLOAD);

        MainActivity.launchDownloadService();
        MainActivity.launchUploadService();

        levelCompleteLayout.getTextBox(LayoutElementsKeys.LEVELCOMPLETE_TEXT).setText(userTime + " | " + averageTime);
        int numStarsToAdd = calculateNumStarsToAdd(userTime, averageTime);
        MainActivity.getPlayer().increaseNumLives(numStarsToAdd);
        MainActivity.getPlayer().increaseNumStars(numStarsToAdd);

        if (numStarsToAdd > 0) {
            new PaintAnimationStarter().startAnimation(
                    levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR1_STROKE_TEXT),
                    animatorThread,
                    Attributes.STARS_PAINT_STROKE_START,
                    Attributes.STARS_PAINT_STROKE_END,
                    Attributes.STAR_ANIMATION_TIME,
                    0);
            new PaintAnimationStarter().startAnimation(
                    levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR1_FILL_TEXT),
                    animatorThread,
                    Attributes.STARS_PAINT_FILL_START,
                    Attributes.STARS_PAINT_FILL_END,
                    Attributes.STAR_ANIMATION_TIME,
                    0);
        }
        if (numStarsToAdd > 1) {
            new PaintAnimationStarter().startAnimation(
                    levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR2_STROKE_TEXT),
                    animatorThread,
                    Attributes.STARS_PAINT_STROKE_START,
                    Attributes.STARS_PAINT_STROKE_END,
                    Attributes.STAR_ANIMATION_TIME,
                    Attributes.STAR_ANIMATION_TIME + Attributes.STAR_ANIMATION_DELAY);
            new PaintAnimationStarter().startAnimation(
                    levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR2_FILL_TEXT),
                    animatorThread,
                    Attributes.STARS_PAINT_FILL_START,
                    Attributes.STARS_PAINT_FILL_END,
                    Attributes.STAR_ANIMATION_TIME,
                    Attributes.STAR_ANIMATION_TIME + Attributes.STAR_ANIMATION_DELAY);
        }
        if (numStarsToAdd > 2) {
            new PaintAnimationStarter().startAnimation(
                    levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR3_STROKE_TEXT),
                    animatorThread,
                    Attributes.STARS_PAINT_STROKE_START,
                    Attributes.STARS_PAINT_STROKE_END,
                    Attributes.STAR_ANIMATION_TIME,
                    2 * (Attributes.STAR_ANIMATION_TIME + Attributes.STAR_ANIMATION_DELAY));
            new PaintAnimationStarter().startAnimation(
                    levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR3_FILL_TEXT),
                    animatorThread,
                    Attributes.STARS_PAINT_FILL_START,
                    Attributes.STARS_PAINT_FILL_END,
                    Attributes.STAR_ANIMATION_TIME,
                    2 * (Attributes.STAR_ANIMATION_TIME + Attributes.STAR_ANIMATION_DELAY));
        }
        boolean animating = true;
        setInitiating(false);
    }


    /**
     * Calculates the number of stars to add, based on the userTime
     * *: averageTime
     * @param userTime userTime
     * @return numstars to add
     */
    private int calculateNumStarsToAdd(int userTime, int averageTime) {
        int numStarsToAdd = 1;
        if (userTime < averageTime + (GameConstants.TIMER - averageTime) / 3) {
            numStarsToAdd++;
        }
        if (userTime < averageTime / 2) {
            numStarsToAdd++;
        }
        return numStarsToAdd;
    }

    @Override
    public void update() {
        levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR1_STROKE_TEXT).update();
        levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR2_STROKE_TEXT).update();
        levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR3_STROKE_TEXT).update();
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
        if (!animatorThread.isAnimating()) {
            sceneManager.setScene(new GameplayScene(sceneManager, animatorThread));
        }
    }

}
