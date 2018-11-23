package nl.limakajo.numbers.layouts;

import android.graphics.Rect;

import nl.limakajo.numbers.gameObjects.LayoutObjectInterface;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.utils.Attributes;

public class NoGamePlayLayout extends BasicLayout {

    NoGamePlayLayout() {
        //Logo
        LayoutObjectInterface logoArea = new ScreenArea (
                new Rect(
                        (int) (0.3 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        (int) (0.3 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        (int) (layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width() - 0.3 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        (int) (0.7 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width())),
                Attributes.NO_DRAW);
        LayoutObjectInterface blueArea = new ScreenArea(
                new Rect(
                        logoArea.getArea().left,
                        logoArea.getArea().top,
                        logoArea.getArea().left + logoArea.getArea().width() / 2 - Attributes.MARGE / 2,
                        logoArea.getArea().top + logoArea.getArea().height() / 2 - Attributes.MARGE / 2),
                Attributes.PLUS_PAINT);
        LayoutObjectInterface redArea = new ScreenArea(
                new Rect(
                        blueArea.getArea().right + Attributes.MARGE,
                        logoArea.getArea().top,
                        logoArea.getArea().right,
                        blueArea.getArea().bottom),
                Attributes.MIN_PAINT);
        LayoutObjectInterface greenArea = new ScreenArea(
                new Rect(
                        blueArea.getArea().left,
                        blueArea.getArea().bottom + Attributes.MARGE,
                        blueArea.getArea().right,
                        logoArea.getArea().bottom),
                Attributes.MULT_PAINT);
        LayoutObjectInterface yellowArea = new ScreenArea(
                new Rect(
                        redArea.getArea().left,
                        greenArea.getArea().top,
                        redArea.getArea().right,
                        greenArea.getArea().bottom),
                Attributes.DIV_PAINT);
        layoutObjects.put(LayoutElementsKeys.LOGO_AREA, logoArea);
        layoutObjects.put(LayoutElementsKeys.BLUE_AREA, blueArea);
        layoutObjects.put(LayoutElementsKeys.RED_AREA, redArea);
        layoutObjects.put(LayoutElementsKeys.GREEN_AREA, greenArea);
        layoutObjects.put(LayoutElementsKeys.YELLOW_AREA, yellowArea);
    }
}
