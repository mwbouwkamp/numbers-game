package nl.limakajo.numbers.gameObjects;

import android.graphics.Paint;
import android.graphics.Rect;

import junit.framework.Assert;

import org.junit.Test;

import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.PaintComparator;

import static org.junit.Assert.*;

public class TextBoxTest {

    @Test
    public void createTextBoxAndGetAttributesTest() {
        String text = "test";
        Attributes.TextAllignment textAllignment = Attributes.TextAllignment.XYCENTERED;
        Rect rect = new Rect(0,0,100,100);
        Paint paint = new Paint();
        TextBox textBox = new TextBox(text, textAllignment, rect, paint);
        Assert.assertEquals("Retrieve the right text", "text", textBox.getText());
        Assert.assertEquals("Retrieve the right allignment", Attributes.TextAllignment.XYCENTERED, textBox.getAlignment());
        Assert.assertEquals("Retrieve the right Rect", rect, textBox.getArea());
        Assert.assertEquals("Retrieve a Paint with the right attributes", 0, new PaintComparator().compare(paint, textBox.getPaint()));
    }

}