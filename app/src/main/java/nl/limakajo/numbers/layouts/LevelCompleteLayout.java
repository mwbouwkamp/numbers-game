package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.EnumMap;
import java.util.HashMap;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.GameObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

/**
 * @author M.W.Bouwkamp
 */
public class LevelCompleteLayout {

    private EnumMap<LevelCompleteGameObjectKeys, GameObject> gameObjects;

    public LevelCompleteLayout() {
        ScreenArea fullscreen = new ScreenArea (
                new Rect(
                        0,
                        0,
                        MainActivity.getDevice().getWidth(),
                        MainActivity.getDevice().getHeight()),
                Attributes.BG_PAINT);
        ScreenArea logoArea = new ScreenArea (
                new Rect(
                        (int) (0.3 * fullscreen.getArea().width()),
                        (int) (0.3 * fullscreen.getArea().width()),
                        (int) (fullscreen.getArea().width() - 0.3 * fullscreen.getArea().width()),
                        (int) (0.7 * fullscreen.getArea().width())),
                Attributes.BG_PAINT);
        ScreenArea levelCompleteTextArea = new ScreenArea (
                new Rect(
                        fullscreen.getArea().left,
                        logoArea.getArea().bottom + 3 * Attributes.MARGE,
                        fullscreen.getArea().right,
                        logoArea.getArea().bottom + 3 * Attributes.MARGE + 200),
                Attributes.BG_PAINT);
        ScreenArea starsArea = new ScreenArea(
                new Rect(
                        logoArea.getArea().left,
                        levelCompleteTextArea.getArea().bottom + Attributes.MARGE,
                        logoArea.getArea().right,
                        levelCompleteTextArea.getArea().bottom + 6 * Attributes.MARGE),
                Attributes.BG_PAINT);
        ScreenArea starArea1 = new ScreenArea(
                new Rect(
                        starsArea.getArea().left,
                        starsArea.getArea().top,
                        starsArea.getArea().left + starsArea.getArea().width() / 3,
                        starsArea.getArea().bottom),
                Attributes.BG_PAINT);
        ScreenArea starArea2 = new ScreenArea(
                new Rect(
                        starsArea.getArea().left + starsArea.getArea().width() / 3,
                        starsArea.getArea().top,
                        starsArea.getArea().left + 2 * starsArea.getArea().width() / 3,
                        starsArea.getArea().bottom),
                Attributes.BG_PAINT);
        ScreenArea starArea3 = new ScreenArea(
                new Rect(
                        starsArea.getArea().left + 2 * starsArea.getArea().width() / 3,
                        starsArea.getArea().top,
                        starsArea.getArea().right,
                        starsArea.getArea().bottom),
                Attributes.BG_PAINT);
        ScreenArea blueArea = new ScreenArea(
                new Rect(
                        logoArea.getArea().left,
                        logoArea.getArea().top,
                        logoArea.getArea().left + logoArea.getArea().width() / 2 - Attributes.MARGE / 2,
                        logoArea.getArea().top + logoArea.getArea().height() / 2 - Attributes.MARGE / 2),
                Attributes.PLUS_PAINT);
        ScreenArea redArea = new ScreenArea(
                new Rect(
                        blueArea.getArea().right + Attributes.MARGE,
                        logoArea.getArea().top,
                        logoArea.getArea().right,
                        blueArea.getArea().bottom),
                Attributes.MIN_PAINT);
        ScreenArea greenArea = new ScreenArea(
                new Rect(
                        blueArea.getArea().left,
                        blueArea.getArea().bottom + Attributes.MARGE,
                        blueArea.getArea().right,
                        logoArea.getArea().bottom),
                Attributes.MULT_PAINT);
        ScreenArea yellowArea = new ScreenArea(
                new Rect(
                        redArea.getArea().left,
                        greenArea.getArea().top,
                        redArea.getArea().right,
                        greenArea.getArea().bottom),
                Attributes.DIV_PAINT);

        TextBox levelCompleteText = new TextBox(
                MainActivity.getContext().getString(R.string.empty_text),
                Attributes.TextAllignment.XYCENTERED,
                levelCompleteTextArea.getArea(),
                Attributes.TEXTBOX_LARGE_PAINT);
        TextBox star1Text = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                starArea1.getArea(),
                Attributes.TEXT_BOX_STARS_PAINT);
        TextBox star2Text = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                starArea2.getArea(),
                Attributes.TEXT_BOX_STARS_PAINT);
        TextBox star3Text = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                starArea3.getArea(),
                Attributes.TEXT_BOX_STARS_PAINT);

        gameObjects = new EnumMap<>(LevelCompleteGameObjectKeys.class);
        gameObjects.put(LevelCompleteGameObjectKeys.FULLSCREEN_AREA, fullscreen);
        gameObjects.put(LevelCompleteGameObjectKeys.LOGO_AREA, logoArea);
        gameObjects.put(LevelCompleteGameObjectKeys.LEVEL_COMPLETE_TEXT_AREA, levelCompleteTextArea);
        gameObjects.put(LevelCompleteGameObjectKeys.STAR_AREA, starsArea);
        gameObjects.put(LevelCompleteGameObjectKeys.STAR1_AREA, starArea1);
        gameObjects.put(LevelCompleteGameObjectKeys.STAR2_AREA, starArea2);
        gameObjects.put(LevelCompleteGameObjectKeys.STAR3_AREA, starArea3);
        gameObjects.put(LevelCompleteGameObjectKeys.BLUE_AREA, blueArea);
        gameObjects.put(LevelCompleteGameObjectKeys.RED_AREA, redArea);
        gameObjects.put(LevelCompleteGameObjectKeys.GREEN_AREA, greenArea);
        gameObjects.put(LevelCompleteGameObjectKeys.YELLOW_AREA, yellowArea);
        gameObjects.put(LevelCompleteGameObjectKeys.LEVEL_COMPLETE_TEXT, levelCompleteText);
        gameObjects.put(LevelCompleteGameObjectKeys.STAR1_TEXT, star1Text);
        gameObjects.put(LevelCompleteGameObjectKeys.STAR2_TEXT, star2Text);
        gameObjects.put(LevelCompleteGameObjectKeys.STAR3_TEXT, star3Text);
    }

    public ScreenArea getScreenArea(LevelCompleteGameObjectKeys gameOverObjectKey) {
        return (ScreenArea) gameObjects.get(gameOverObjectKey);
    }

    public TextBox getTextBox(LevelCompleteGameObjectKeys gameOverObjectKey) {
        return (TextBox) gameObjects.get(gameOverObjectKey);
    }

    public void draw(Canvas canvas) {
        gameObjects.get(LevelCompleteGameObjectKeys.FULLSCREEN_AREA).draw(canvas);
        gameObjects.get(LevelCompleteGameObjectKeys.BLUE_AREA).draw(canvas);
        gameObjects.get(LevelCompleteGameObjectKeys.RED_AREA).draw(canvas);
        gameObjects.get(LevelCompleteGameObjectKeys.GREEN_AREA).draw(canvas);
        gameObjects.get(LevelCompleteGameObjectKeys.YELLOW_AREA).draw(canvas);
        gameObjects.get(LevelCompleteGameObjectKeys.LEVEL_COMPLETE_TEXT).draw(canvas);
        gameObjects.get(LevelCompleteGameObjectKeys.STAR1_TEXT).draw(canvas);
        gameObjects.get(LevelCompleteGameObjectKeys.STAR2_TEXT).draw(canvas);
        gameObjects.get(LevelCompleteGameObjectKeys.STAR3_TEXT).draw(canvas);
    }
}
