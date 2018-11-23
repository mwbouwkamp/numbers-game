package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.EnumMap;

import nl.limakajo.numbers.gameObjects.LayoutObjectInterface;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

public class BasicLayout implements LayoutInterface {

    protected EnumMap<LayoutElementsKeys, LayoutObjectInterface> layoutObjects = new EnumMap<LayoutElementsKeys, LayoutObjectInterface>(LayoutElementsKeys.class);

    BasicLayout() {

        //Fullscreen
        LayoutObjectInterface fullscreen = new ScreenArea (
                new Rect(
                        0,
                        0,
                        MainActivity.getDevice().getWidth(),
                        MainActivity.getDevice().getHeight()),
                Attributes.BG_PAINT);
        layoutObjects.put(LayoutElementsKeys.FULLSCREEN, fullscreen);
    }

    public EnumMap<LayoutElementsKeys, LayoutObjectInterface> getLayoutObjects() {
        return layoutObjects;
    }

    public TextBox getTextBox(LayoutElementsKeys layoutElement) {
        return (TextBox) layoutObjects.get(layoutElement);
    }

    public ScreenArea getScreenArea(LayoutElementsKeys layoutElement) {
        return (ScreenArea) layoutObjects.get(layoutElement);
    }

    public void draw(Canvas canvas) {
        for (LayoutObjectInterface layoutObject : layoutObjects.values()) {
            layoutObject.draw(canvas);
        }
    }
}

