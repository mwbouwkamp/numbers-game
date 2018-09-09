package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import nl.limakajo.numbers.layouts.LevelCompleteLayout;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.GameUtils;

import static nl.limakajo.numbers.utils.GameUtils.GameState.GAME_STATE;

/**
 * @author M.W.Bouwkamp
 */

public class LevelCompleteScene implements SceneInterface {

    private final SceneManager sceneManager;
    private LevelCompleteLayout levelCompleteLayout;

    LevelCompleteScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        levelCompleteLayout = new LevelCompleteLayout();
    }

    @Override
    public void init() {
        int userTime = MainActivity.getGame().getLevel().getUserTime();
        int averageTime = MainActivity.getGame().getLevel().getAverageTime();
        levelCompleteLayout.getTextBox("levelcompletetext").setText(Integer.toString(userTime) + " | " + Integer.toString(averageTime));
        int numStarsToAdd = calculateNumStarsToAdd(userTime, averageTime);
        switch (numStarsToAdd) {
            case 1:
                levelCompleteLayout.getTextBox("star1text").setText("A");
                levelCompleteLayout.getTextBox("star2text").setText("");
                levelCompleteLayout.getTextBox("star3text").setText("");
                System.out.println("*");
                break;
            case 2:
                levelCompleteLayout.getTextBox("star1text").setText("A");
                levelCompleteLayout.getTextBox("star2text").setText("A");
                levelCompleteLayout.getTextBox("star3text").setText("");
                System.out.println("**");
                break;
            case 3:
                levelCompleteLayout.getTextBox("star1text").setText("A");
                levelCompleteLayout.getTextBox("star2text").setText("A");
                levelCompleteLayout.getTextBox("star3text").setText("A");
                System.out.println("***");
                break;
        }
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
        sceneManager.setScene(GAME_STATE.toString());
        GameplayScene gameplayScene = (GameplayScene) sceneManager.getScene(GAME_STATE.toString());
        gameplayScene.init();

    }
}
