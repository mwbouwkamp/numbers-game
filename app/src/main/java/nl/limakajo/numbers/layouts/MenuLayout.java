package nl.limakajo.numbers.layouts;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;

import org.w3c.dom.Attr;

import java.util.HashMap;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

/**
 * @author M.W.Bouwkamp
 */
public class MenuLayout extends Layout {

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

        screenAreas = new HashMap<>();
        screenAreas.put("fullscreen", fullscreen);
        screenAreas.put("logo", logoArea);
        screenAreas.put("blue", blueArea);
        screenAreas.put("red", redArea);
        screenAreas.put("green", greenArea);
        screenAreas.put("yellow", yellowArea);

        TextBox logoText = new TextBox(
                MainActivity.getContext().getString(R.string.menu_start_text),
                Attributes.TextAllignment.XYCENTERED,
                logotextArea.getArea(),
                Attributes.TEXTBOX_LARGE_PAINT);

        textBoxes = new HashMap<>();
        textBoxes.put("logotext", logoText);


    }
}