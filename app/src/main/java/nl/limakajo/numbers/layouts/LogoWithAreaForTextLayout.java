package nl.limakajo.numbers.layouts;

import android.graphics.Rect;

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
                new Rect(
                        (int) (layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().left),
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE,
                        (int) (layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().right),
                        //TODO: height is now hardcoded
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE + 100),
                Attributes.NO_DRAW);
        layoutObjects.put(LayoutElementsKeys.MAINTEXT_AREA, mainTextArea);
    }
}
