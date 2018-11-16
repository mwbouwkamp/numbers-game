package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.EnumMap;

import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.utils.Attributes;

public class NoGamePlayLayout extends BasicLayout {

    private EnumMap<LayoutElements, LayoutObject> layoutObjects;

    NoGamePlayLayout() {
        layoutObjects = new EnumMap<>(super.getLayoutObjects());

        //Logo
        LayoutObject logoArea = new ScreenArea (
                new Rect(
                        (int) (0.3 * layoutObjects.get(LayoutElements.FULLSCREEN).getArea().width()),
                        (int) (0.3 * layoutObjects.get(LayoutElements.FULLSCREEN).getArea().width()),
                        (int) (layoutObjects.get(LayoutElements.FULLSCREEN).getArea().width() - 0.3 * layoutObjects.get(LayoutElements.FULLSCREEN).getArea().width()),
                        (int) (0.7 * layoutObjects.get(LayoutElements.FULLSCREEN).getArea().width())),
                Attributes.NO_DRAW);
        LayoutObject blueArea = new ScreenArea(
                new Rect(
                        logoArea.getArea().left,
                        logoArea.getArea().top,
                        logoArea.getArea().left + logoArea.getArea().width() / 2 - Attributes.MARGE / 2,
                        logoArea.getArea().top + logoArea.getArea().height() / 2 - Attributes.MARGE / 2),
                Attributes.PLUS_PAINT);
        LayoutObject redArea = new ScreenArea(
                new Rect(
                        blueArea.getArea().right + Attributes.MARGE,
                        logoArea.getArea().top,
                        logoArea.getArea().right,
                        blueArea.getArea().bottom),
                Attributes.MIN_PAINT);
        LayoutObject greenArea = new ScreenArea(
                new Rect(
                        blueArea.getArea().left,
                        blueArea.getArea().bottom + Attributes.MARGE,
                        blueArea.getArea().right,
                        logoArea.getArea().bottom),
                Attributes.MULT_PAINT);
        LayoutObject yellowArea = new ScreenArea(
                new Rect(
                        redArea.getArea().left,
                        greenArea.getArea().top,
                        redArea.getArea().right,
                        greenArea.getArea().bottom),
                Attributes.DIV_PAINT);
        layoutObjects.put(LayoutElements.LOGO_AREA, logoArea);
        layoutObjects.put(LayoutElements.BLUE_AREA, blueArea);
        layoutObjects.put(LayoutElements.RED_AREA, redArea);
        layoutObjects.put(LayoutElements.GREEN_AREA, greenArea);
        layoutObjects.put(LayoutElements.YELLOW_AREA, yellowArea);
    }

    public EnumMap<LayoutElements, LayoutObject> getLayoutObjects() {
        return layoutObjects;
    }

    public TextBox getTextBox(LayoutElements layoutElement) {
        return (TextBox) layoutObjects.get(layoutElement);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (LayoutObject layoutObject : layoutObjects.values()) {
            layoutObject.draw(canvas);
        }
    }
}
