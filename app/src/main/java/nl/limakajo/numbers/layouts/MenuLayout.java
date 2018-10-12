package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;

import org.w3c.dom.Attr;

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
public class MenuLayout {

    private EnumMap<MenuGameObjectKeys, GameObject> gameObjects;

    public MenuLayout() {
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
        ScreenArea logotextArea = new ScreenArea (
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

        TextBox logoText = new TextBox(
                MainActivity.getContext().getString(R.string.menu_start_text),
                Attributes.TextAllignment.XYCENTERED,
                logotextArea.getArea(),
                Attributes.TEXTBOX_LARGE_PAINT);

        gameObjects = new EnumMap<>(MenuGameObjectKeys.class);
        gameObjects.put(MenuGameObjectKeys.FULLSCREEN_AREA, fullscreen);
        gameObjects.put(MenuGameObjectKeys.LOGO_AREA, logoArea);
        gameObjects.put(MenuGameObjectKeys.LOGO_TEXT_AREA, logotextArea);
        gameObjects.put(MenuGameObjectKeys.BLUE_AREA, blueArea);
        gameObjects.put(MenuGameObjectKeys.RED_AREA, redArea);
        gameObjects.put(MenuGameObjectKeys.GREEN_AREA, greenArea);
        gameObjects.put(MenuGameObjectKeys.YELLOW_AREA, yellowArea);
        gameObjects.put(MenuGameObjectKeys.LOGO_TEXT, logoText);
    }

    public ScreenArea getScreenArea(MenuGameObjectKeys gameOverObjectKey) {
        return (ScreenArea) gameObjects.get(gameOverObjectKey);
    }

    public TextBox getTextBox(MenuGameObjectKeys gameOverObjectKey) {
        return (TextBox) gameObjects.get(gameOverObjectKey);
    }

    public void draw(Canvas canvas) {
        gameObjects.get(MenuGameObjectKeys.FULLSCREEN_AREA).draw(canvas);
        gameObjects.get(MenuGameObjectKeys.BLUE_AREA).draw(canvas);
        gameObjects.get(MenuGameObjectKeys.RED_AREA).draw(canvas);
        gameObjects.get(MenuGameObjectKeys.GREEN_AREA).draw(canvas);
        gameObjects.get(MenuGameObjectKeys.YELLOW_AREA).draw(canvas);
        gameObjects.get(MenuGameObjectKeys.LOGO_TEXT).draw(canvas);
    }
}
