package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Layout;

import java.util.EnumMap;
import java.util.HashMap;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

/**
 * @author M.W.Bouwkamp
 */
public class MenuLayout extends NoGamePlayLayout {

    private EnumMap<LayoutElements, LayoutObject> layoutObjects;

    public MenuLayout() {
        layoutObjects = new EnumMap<>(super.getLayoutObjects());

        //Menu Text
        LayoutObject menuText = new TextBox(
                MainActivity.getContext().getString(R.string.menu_start_text),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        layoutObjects.get(LayoutElements.FULLSCREEN).getArea().left,
                        layoutObjects.get(LayoutElements.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE,
                        layoutObjects.get(LayoutElements.FULLSCREEN).getArea().right,
                        layoutObjects.get(LayoutElements.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE + 200),
                Attributes.TEXTBOX_LARGE_PAINT);
        layoutObjects.put(LayoutElements.MENU_TEXT, menuText);
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
