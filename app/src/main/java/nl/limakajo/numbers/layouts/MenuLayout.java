package nl.limakajo.numbers.layouts;

import android.graphics.Point;
import android.util.Size;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

/**
 * Class that represents the MenuLayout
 *
 * @author M.W.Bouwkamp
 */
public class MenuLayout extends LogoWithAreaForTextLayout {

    /**
     * Constructs a MenuLayout
     */
    public MenuLayout() {
        //Menu Text
        LayoutObject menuText = new TextBox(
                MainActivity.getContext().getString(R.string.menu_start_text),
                Attributes.TextAllignment.XYCENTERED,
                new Point(
                        layoutObjects.get(LayoutElementsKeys.MAINTEXT_AREA).getArea().left,
                        layoutObjects.get(LayoutElementsKeys.MAINTEXT_AREA).getArea().top
                ),
                new Size(
                        layoutObjects.get(LayoutElementsKeys.MAINTEXT_AREA).getArea().width(),
                        layoutObjects.get(LayoutElementsKeys.MAINTEXT_AREA).getArea().height()
                ),
                Attributes.TEXTBOX_NORMAL_PAINT);
        layoutObjects.put(LayoutElementsKeys.MENU_TEXT, menuText);
    }
}
