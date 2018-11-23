package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.EnumMap;

import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

/**
 * Class that represents the BasicLayout
 *
 * @author M.W.Bouwkamp
 */
public class BasicLayout implements LayoutInterface {

    protected EnumMap<LayoutElementsKeys, LayoutObject> layoutObjects = new EnumMap<LayoutElementsKeys, LayoutObject>(LayoutElementsKeys.class);

    /**
     * Constructs a BasicLayout
     */
    BasicLayout() {

        //Fullscreen
        LayoutObject fullscreen = new ScreenArea (
                new Rect(
                        0,
                        0,
                        MainActivity.getDevice().getWidth(),
                        MainActivity.getDevice().getHeight()),
                Attributes.BG_PAINT);
        layoutObjects.put(LayoutElementsKeys.FULLSCREEN, fullscreen);
    }

    /**
     * Returns a given TextBox
     *
     * @param layoutElement     the TextBox to return indicated by LayoutElementsKey
     * @return                  TextBox
     */
    public TextBox getTextBox(LayoutElementsKeys layoutElement) {
        return (TextBox) layoutObjects.get(layoutElement);
    }

    /**
     * Returns a given ScreenArea
     *
     * @param layoutElement     the ScreenArea to return indicated by LayoutElementsKey
     * @return                  ScreenArea
     */
    public ScreenArea getScreenArea(LayoutElementsKeys layoutElement) {
        return (ScreenArea) layoutObjects.get(layoutElement);
    }

    @Override
    public void draw(Canvas canvas) {
        for (LayoutObject layoutObject : layoutObjects.values()) {
            layoutObject.draw(canvas);
        }
    }
}

