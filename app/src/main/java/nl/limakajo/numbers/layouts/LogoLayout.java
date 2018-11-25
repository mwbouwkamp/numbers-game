package nl.limakajo.numbers.layouts;

import android.graphics.Rect;

import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.utils.Attributes;
/**
 * Class that represents the basic layout for layouts other then the GamePlayLayout
 *
 * @author M.W.Bouwkamp
 */
public class LogoLayout extends BasicLayout {

    /**
     * Constructs a LogoLayout
     */
    LogoLayout() {
        //Logo
        LayoutObject logoArea = new ScreenArea (
                new Rect(
                        (int) (0.3 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        (int) (0.3 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        (int) (layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width() - 0.3 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        (int) (0.7 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width())),
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
        layoutObjects.put(LayoutElementsKeys.LOGO_AREA, logoArea);
        layoutObjects.put(LayoutElementsKeys.BLUE_AREA, blueArea);
        layoutObjects.put(LayoutElementsKeys.RED_AREA, redArea);
        layoutObjects.put(LayoutElementsKeys.GREEN_AREA, greenArea);
        layoutObjects.put(LayoutElementsKeys.YELLOW_AREA, yellowArea);
    }
}
