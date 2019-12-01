package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Size;

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

    protected final EnumMap<LayoutElementsKeys, LayoutObject> layoutObjects = new EnumMap<>(LayoutElementsKeys.class);

    /**
     * Constructs a BasicLayout
     */
    BasicLayout() {

        //Fullscreen
        LayoutObject fullscreen = new ScreenArea (
                new Point(
                        0,
                        0),
                new Size(
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
    public LayoutObject getScreenArea(LayoutElementsKeys layoutElement) {
        return layoutObjects.get(layoutElement);
    }

    public EnumMap<LayoutElementsKeys, LayoutObject> getScreenAreas() {
        return layoutObjects;
    }

    @Override
    public void draw(Canvas canvas) {
        for (LayoutObject layoutObject : layoutObjects.values()) {
            layoutObject.draw(canvas);
        }
    }
}

