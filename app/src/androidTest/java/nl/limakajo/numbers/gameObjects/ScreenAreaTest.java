package nl.limakajo.numbers.gameObjects;

import android.graphics.Paint;
import android.graphics.Rect;

import junit.framework.Assert;

import org.junit.Test;

import nl.limakajo.numbers.utils.PaintComparator;

import static org.junit.Assert.*;

public class ScreenAreaTest {

    @Test
    public void createScreenAreaAndGetAttributesTest() {
        Rect rect = new Rect(0,0,100,100);
        Paint paint = new Paint();
        ScreenArea screenArea = new ScreenArea(rect, paint);
        Assert.assertEquals("Retrieve the right Rect", rect, screenArea.getArea());
        Assert.assertEquals("Retrieve a Paint with the right attributes", 0, new PaintComparator().compare(paint, screenArea.getPaint()));
    }

    //TODO: Test for draw method
}