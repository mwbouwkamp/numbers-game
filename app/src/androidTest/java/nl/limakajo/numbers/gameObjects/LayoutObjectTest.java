package nl.limakajo.numbers.gameObjects;

import android.graphics.Paint;
import android.graphics.Rect;

import junit.framework.Assert;

import org.junit.Test;

import nl.limakajo.numbers.utils.PaintComparator;

import static org.junit.Assert.*;

public class LayoutObjectTest {

    @Test
    public void createLayoutObjectAndGetAttributesTest() {
        Rect rect = new Rect(0,0,100,100);
        Paint paint = new Paint();
        LayoutObject layoutObject = new LayoutObject(rect, paint);
        Assert.assertEquals("Retrieve the right Rect", rect, layoutObject.getArea());
        Assert.assertEquals("Retrieve a Paint with the right attributes", 0, new PaintComparator().compare(paint, layoutObject.getPaint()));
    }
}