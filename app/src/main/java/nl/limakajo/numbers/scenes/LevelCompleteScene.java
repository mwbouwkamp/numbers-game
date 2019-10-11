package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Arrays;
import java.util.List;

import nl.limakajo.numbers.animators.AnimatorThread;
import nl.limakajo.numbers.animators.PaintAnimator;
import nl.limakajo.numbers.gameObjects.Animates;
import nl.limakajo.numbers.gameObjects.AnimatesPaint;
import nl.limakajo.numbers.gameObjects.TextBox;
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
    private LevelCompleteLayout levelCompleteLayout;
    private int numStarsToAdd;

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

        levelCompleteLayout.getTextBox(LayoutElementsKeys.LEVELCOMPLETE_TEXT).setText(Integer.toString(userTime) + " | " + Integer.toString(averageTime));
        numStarsToAdd = calculateNumStarsToAdd(userTime, averageTime);
        MainActivity.getPlayer().increaseNumLives(numStarsToAdd);
        MainActivity.getPlayer().increaseNumStars(numStarsToAdd);

        initStarAnimation(levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR1_TEXT), animatorThread);
        initStarAnimation(levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR2_TEXT), animatorThread);
        initStarAnimation(levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR3_TEXT), animatorThread);

        setInitiating(false);
    }


    public void initStarAnimation(AnimatesPaint startTextBox, AnimatorThread animatorThread) {
        PaintAnimator paintAnimator = new PaintAnimator(Attributes.STAR_ANIMATION_TIME);
        paintAnimator.init(startTextBox, new Paint(Attributes.STARS_PAINT_STROKE_END));
        List<PaintAnimator.PaintAnimatorParams> paintAnimatorParams = Arrays.asList(
                PaintAnimator.PaintAnimatorParams.ALPHA
        );
        paintAnimator.setPaintAnimatorParams(paintAnimatorParams);
        startTextBox.setPaintAnimator(paintAnimator);
        paintAnimator.startAnimation();
        animatorThread.add(paintAnimator);

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
        levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR1_TEXT).update();
        levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR2_TEXT).update();
        levelCompleteLayout.getTextBox(LayoutElementsKeys.STAR3_TEXT).update();
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
        sceneManager.setScene(new GameplayScene(sceneManager, animatorThread));
    }

}
