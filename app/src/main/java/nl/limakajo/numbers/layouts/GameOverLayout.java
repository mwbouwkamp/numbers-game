package nl.limakajo.numbers.layouts;

import android.graphics.Rect;

import nl.limakajo.numbers.gameObjects.LayoutObjectInterface;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.utils.Attributes;

/**
 * @author M.W.Bouwkamp
 */
public class GameOverLayout extends NoGamePlayLayout {

    public GameOverLayout() {
        //Game over text
        LayoutObjectInterface gameOverText = new TextBox(
                "Out of time",
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().left,
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE,
                        layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().right,
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE + 200),
                Attributes.TEXTBOX_LARGE_PAINT);
        layoutObjects.put(LayoutElementsKeys.GAMEOVER_TEXT, gameOverText);
    }
}
