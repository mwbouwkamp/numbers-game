package nl.limakajo.numbers.layouts;

import android.graphics.Rect;

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
public class MenuLayout extends LogoLayout {

    /**
     * Constructs a MenuLayout
     */
    public MenuLayout() {
        //Menu Text
        LayoutObject menuText = new TextBox(
                MainActivity.getContext().getString(R.string.menu_start_text),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        (int) (0.25 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE,
                        (int) (layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width() - 0.25 * layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width()),
                        //TODO: height (200) is now hardcoded
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE + 200),
                Attributes.TEXTBOX_LARGE_PAINT);
        layoutObjects.put(LayoutElementsKeys.MENU_TEXT, menuText);
    }
}
