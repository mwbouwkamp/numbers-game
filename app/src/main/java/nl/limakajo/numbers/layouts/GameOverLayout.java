package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.EnumMap;

import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.utils.Attributes;

/**
 * @author M.W.Bouwkamp
 */
public class GameOverLayout extends NoGamePlayLayout {

    private EnumMap<LayoutElements, LayoutObject> layoutObjects;

    public GameOverLayout() {
        layoutObjects = new EnumMap<>(super.getLayoutObjects());

        //Game over text
        LayoutObject gameOverText = new TextBox(
                "Out of time",
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        layoutObjects.get(LayoutElements.FULLSCREEN).getArea().left,
                        layoutObjects.get(LayoutElements.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE,
                        layoutObjects.get(LayoutElements.FULLSCREEN).getArea().right,
                        layoutObjects.get(LayoutElements.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE + 200),
                Attributes.TEXTBOX_LARGE_PAINT);
        layoutObjects.put(LayoutElements.GAMEOVER_TEXT, gameOverText);
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
