package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;

import org.w3c.dom.Text;

import java.util.EnumMap;

import nl.limakajo.numbers.gameObjects.GameObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

/**
 * @author M.W.Bouwkamp
 */
public class GameOverLayout {

    private EnumMap<GameOverGameObjectKeys, GameObject> gameObjects;

    public GameOverLayout() {
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
        ScreenArea gameOverTextArea = new ScreenArea (
                new Rect(
                        fullscreen.getArea().left,
                        logoArea.getArea().bottom + 3 * Attributes.MARGE,
                        fullscreen.getArea().right,
                        logoArea.getArea().bottom + 3 * Attributes.MARGE + 200),
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

        TextBox GameOverText = new TextBox(
                "Out of time",
                Attributes.TextAllignment.XYCENTERED,
                gameOverTextArea.getArea(),
                Attributes.TEXTBOX_LARGE_PAINT);

        gameObjects = new EnumMap<>(GameOverGameObjectKeys.class);
        gameObjects.put(GameOverGameObjectKeys.FULLSCREEN_AREA, fullscreen);
        gameObjects.put(GameOverGameObjectKeys.LOGO_AREA, logoArea);
        gameObjects.put(GameOverGameObjectKeys.GAME_OVER_TEXT_AREA, gameOverTextArea);
        gameObjects.put(GameOverGameObjectKeys.BLUE_AREA, blueArea);
        gameObjects.put(GameOverGameObjectKeys.RED_AREA, redArea);
        gameObjects.put(GameOverGameObjectKeys.GREEN_AREA, greenArea);
        gameObjects.put(GameOverGameObjectKeys.YELLOW_AREA, yellowArea);
        gameObjects.put(GameOverGameObjectKeys.GAME_OVER_TEXT, GameOverText);
    }

    public ScreenArea getScreenArea(GameOverGameObjectKeys gameOverObjectKey) {
        return (ScreenArea) gameObjects.get(gameOverObjectKey);
    }

    public TextBox getTextBox(GameOverGameObjectKeys gameOverObjectKey) {
        return (TextBox) gameObjects.get(gameOverObjectKey);
    }

    public void draw(Canvas canvas) {
        gameObjects.get(GameOverGameObjectKeys.FULLSCREEN_AREA).draw(canvas);
        gameObjects.get(GameOverGameObjectKeys.BLUE_AREA).draw(canvas);
        gameObjects.get(GameOverGameObjectKeys.RED_AREA).draw(canvas);
        gameObjects.get(GameOverGameObjectKeys.GREEN_AREA).draw(canvas);
        gameObjects.get(GameOverGameObjectKeys.YELLOW_AREA).draw(canvas);
        gameObjects.get(GameOverGameObjectKeys.GAME_OVER_TEXT).draw(canvas);
    }

}
