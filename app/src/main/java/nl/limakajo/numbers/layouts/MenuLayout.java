package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.EnumMap;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.GameObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

/**
 * @author M.W.Bouwkamp
 */
public class MenuLayout {

    public enum ObjectKeys {
        FULLSCREEN_AREA,

        LOGO_AREA,
        BLUE_AREA,
        RED_AREA,
        GREEN_AREA,
        YELLOW_AREA,

        LOGO_TEXT
    }

    private EnumMap<ObjectKeys, GameObject> gameObjects;

    public MenuLayout() {
        gameObjects = new EnumMap<>(ObjectKeys.class);

        //Fullscreen
        ScreenArea fullscreen = new ScreenArea (
                new Rect(
                        0,
                        0,
                        MainActivity.getDevice().getWidth(),
                        MainActivity.getDevice().getHeight()),
                Attributes.BG_PAINT);
        gameObjects.put(ObjectKeys.FULLSCREEN_AREA, fullscreen);

        //Logo
        ScreenArea logoArea = new ScreenArea (
                new Rect(
                        (int) (0.3 * fullscreen.getArea().width()),
                        (int) (0.3 * fullscreen.getArea().width()),
                        (int) (fullscreen.getArea().width() - 0.3 * fullscreen.getArea().width()),
                        (int) (0.7 * fullscreen.getArea().width())),
                Attributes.NO_DRAW);
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
        gameObjects.put(ObjectKeys.LOGO_AREA, logoArea);
        gameObjects.put(ObjectKeys.BLUE_AREA, blueArea);
        gameObjects.put(ObjectKeys.RED_AREA, redArea);
        gameObjects.put(ObjectKeys.GREEN_AREA, greenArea);
        gameObjects.put(ObjectKeys.YELLOW_AREA, yellowArea);

        //Text
        TextBox logoText = new TextBox(
                MainActivity.getContext().getString(R.string.menu_start_text),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        fullscreen.getArea().left,
                        logoArea.getArea().bottom + 3 * Attributes.MARGE,
                        fullscreen.getArea().right,
                        logoArea.getArea().bottom + 3 * Attributes.MARGE + 200),
                Attributes.TEXTBOX_LARGE_PAINT);
        gameObjects.put(ObjectKeys.LOGO_TEXT, logoText);
    }

    public ScreenArea getScreenArea(ObjectKeys gameOverObjectKey) {
        return (ScreenArea) gameObjects.get(gameOverObjectKey);
    }

    public TextBox getTextBox(ObjectKeys gameOverObjectKey) {
        return (TextBox) gameObjects.get(gameOverObjectKey);
    }

    public void draw(Canvas canvas) {
        for (GameObject gameObject: gameObjects.values()) {
            gameObject.draw(canvas);
        }
    }
}
