package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.EnumMap;

import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

public class BasicLayout {

    protected EnumMap<LayoutElementsKeys, LayoutObject> layoutObjects = new EnumMap<LayoutElementsKeys, LayoutObject>(LayoutElementsKeys.class);

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

    public EnumMap<LayoutElementsKeys, LayoutObject> getLayoutObjects() {
        return layoutObjects;
    }

    public TextBox getTextBox(LayoutElementsKeys layoutElement) {
        return (TextBox) layoutObjects.get(layoutElement);
    }

    public void draw(Canvas canvas) {
        for (LayoutObject layoutObject : layoutObjects.values()) {
            layoutObject.draw(canvas);
        }
    }
}

