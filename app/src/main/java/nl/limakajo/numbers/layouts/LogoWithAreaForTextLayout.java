package nl.limakajo.numbers.layouts;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Size;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.utils.Attributes;

/**
 * Class that represents a layout with logo and screenarea for text
 *
 * @author M.W.Bouwkamp
 */
public class LogoWithAreaForTextLayout extends LogoLayout {

    /**
     * Constructs a LogoWithAreaForTextLayout
     */
    public LogoWithAreaForTextLayout() {
        //ScreenAreay for main text of screen
        LayoutObject mainTextArea = new ScreenArea(
                new Point(
                        (int) (layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().left),
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE),
                new Size(
                        (int) (layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        //TODO: height is now hardcoded
                        100),
                Attributes.NO_DRAW);
        layoutObjects.put(LayoutElementsKeys.MAINTEXT_AREA, mainTextArea);
    }
}
