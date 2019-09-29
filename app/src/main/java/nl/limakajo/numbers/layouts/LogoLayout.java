package nl.limakajo.numbers.layouts;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Size;

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
                new Point(
                        (int) (0.3 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        (int) (0.3 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width())),
                new Size(
                        (int) (0.4 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        (int) (0.4 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width())),
                Attributes.NO_DRAW);
        LayoutObject blueArea = new ScreenArea(
                new Point(
                        logoArea.getArea().left,
                        logoArea.getArea().top),
                new Size(
                        (logoArea.getArea().width() - Attributes.MARGE) / 2,
                        (logoArea.getArea().height() - Attributes.MARGE) / 2),
                Attributes.PLUS_PAINT);
        LayoutObject redArea = new ScreenArea(
                new Point(
                        blueArea.getArea().right + Attributes.MARGE,
                        logoArea.getArea().top),
                new Size(
                        (logoArea.getArea().width() - Attributes.MARGE) / 2,
                        (logoArea.getArea().height() - Attributes.MARGE) / 2),
                Attributes.MIN_PAINT);
        LayoutObject greenArea = new ScreenArea(
                new Point(
                        blueArea.getArea().left,
                        blueArea.getArea().bottom + Attributes.MARGE),
                new Size(
                        (logoArea.getArea().width() - Attributes.MARGE) / 2,
                        (logoArea.getArea().height() - Attributes.MARGE) / 2),
                Attributes.MULT_PAINT);
        LayoutObject yellowArea = new ScreenArea(
                new Point(
                        redArea.getArea().left,
                        greenArea.getArea().top),
                new Size(
                        (logoArea.getArea().width() - Attributes.MARGE) / 2,
                        (logoArea.getArea().height() - Attributes.MARGE) / 2),
                Attributes.DIV_PAINT);
        layoutObjects.put(LayoutElementsKeys.LOGO_AREA, logoArea);
        layoutObjects.put(LayoutElementsKeys.BLUE_AREA, blueArea);
        layoutObjects.put(LayoutElementsKeys.RED_AREA, redArea);
        layoutObjects.put(LayoutElementsKeys.GREEN_AREA, greenArea);
        layoutObjects.put(LayoutElementsKeys.YELLOW_AREA, yellowArea);
    }
}
