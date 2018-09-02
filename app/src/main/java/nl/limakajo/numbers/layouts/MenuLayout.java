package nl.limakajo.numbers.layouts;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;

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
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setAlpha(100);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Attributes.BG_COLOR);
        paint.setShader(new RadialGradient(MainActivity.getDevice().getWidth() / 2,
                MainActivity.getDevice().getHeight() / 2,
                Math.max(MainActivity.getDevice().getHeight(), MainActivity.getDevice().getWidth()),
                new int[]{Attributes.BG_COLOR, Color.BLACK},
                null,
                Shader.TileMode.MIRROR));
        ScreenArea fullscreen = new ScreenArea (
                new Rect(
                        0,
                        0,
                        MainActivity.getDevice().getWidth(),
                        MainActivity.getDevice().getHeight()),
                paint);
        ScreenArea logoArea = new ScreenArea (
                new Rect(
                        (int) (0.3 * fullscreen.getArea().width()),
                        (int) (0.3 * fullscreen.getArea().width()),
                        (int) (fullscreen.getArea().width() - 0.3 * fullscreen.getArea().width()),
                        (int) (0.7 * fullscreen.getArea().width())),
                paint);
        ScreenArea logotextArea = new ScreenArea (
                new Rect(
                        fullscreen.getArea().left,
                        logoArea.getArea().bottom + 3 * Attributes.MARGE,
                        fullscreen.getArea().right,
                        logoArea.getArea().bottom + 3 * Attributes.MARGE + 200),
                paint);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setAlpha(100);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Attributes.PLUS_COLOR);
        ScreenArea blueArea = new ScreenArea(
                new Rect(
                        logoArea.getArea().left,
                        logoArea.getArea().top,
                        logoArea.getArea().left + logoArea.getArea().width() / 2 - Attributes.MARGE / 2,
                        logoArea.getArea().top + logoArea.getArea().height() / 2 - Attributes.MARGE / 2),
                paint);
        paint.setColor(Attributes.MIN_COLOR);
        ScreenArea redArea = new ScreenArea(
                new Rect(
                        blueArea.getArea().right + Attributes.MARGE,
                        logoArea.getArea().top,
                        logoArea.getArea().right,
                        blueArea.getArea().bottom),
                paint);
        paint.setColor(Attributes.MULT_COLOR);
        ScreenArea greenArea = new ScreenArea(
                new Rect(
                        blueArea.getArea().left,
                        blueArea.getArea().bottom + Attributes.MARGE,
                        blueArea.getArea().right,
                        logoArea.getArea().bottom),
                paint);
        paint.setColor(Attributes.DIV_COLOR);
        ScreenArea yellowArea = new ScreenArea(
                new Rect(
                        redArea.getArea().left,
                        greenArea.getArea().top,
                        redArea.getArea().right,
                        greenArea.getArea().bottom),
                paint);

        screenAreas = new HashMap<>();
        screenAreas.put("fullscreen", fullscreen);
        screenAreas.put("logo", logoArea);
        screenAreas.put("blue", blueArea);
        screenAreas.put("red", redArea);
        screenAreas.put("green", greenArea);
        screenAreas.put("yellow", yellowArea);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(100);
        Typeface typeface = Attributes.TYPEFACE_CALIBRI;
        paint.setTypeface(typeface);
        paint.setColor(Attributes.GOAL_COLOR);
        TextBox logoText = new TextBox(
                MainActivity.getContext().getString(R.string.menu_start_text),
                Attributes.TextAllignment.XYCENTERED,
                logotextArea.getArea(),
                paint);

        textBoxes = new HashMap<>();
        textBoxes.put("logotext", logoText);


    }
}
