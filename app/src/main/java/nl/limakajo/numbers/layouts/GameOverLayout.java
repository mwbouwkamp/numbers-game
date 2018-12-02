package nl.limakajo.numbers.layouts;

import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.utils.Attributes;

/**
 * Class that represents the BasicLayout
 *
 * @author M.W.Bouwkamp
 */
public class GameOverLayout extends LogoWithAreaForTextLayout {

    /**
     * Constructs GameOverLayout
     */
    public GameOverLayout() {
        //Game over text
        LayoutObject gameOverText = new TextBox(
                "Out of time",
                Attributes.TextAllignment.XYCENTERED,
                layoutObjects.get(LayoutElementsKeys.MAINTEXT_AREA).getArea(),
                Attributes.TEXTBOX_NORMAL_PAINT);
        layoutObjects.put(LayoutElementsKeys.GAMEOVER_TEXT, gameOverText);
    }
}
