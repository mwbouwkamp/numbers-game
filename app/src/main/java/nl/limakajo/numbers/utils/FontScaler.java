package nl.limakajo.numbers.utils;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Class that calculates the fontsize of a given text so that the text fits a Rect
 */
public class FontScaler {

    private float xTextSize;
    private float yTextSize;

    /**
     * Constructs a FontScaler
     *
     * @param text              the text to display
     * @param startingPaint     the original Paint
     * @param rectToFit         the rectangle the font needs to fit in
     */
    public FontScaler(String text, Paint startingPaint, Rect rectToFit) {
        Rect bounds = new Rect();
        startingPaint.getTextBounds(text, 0, text.length(), bounds);
        xTextSize = startingPaint.getTextSize() * rectToFit.width() / bounds.width();
        if (xTextSize < 10 || xTextSize > Float.MAX_VALUE) {
            xTextSize = 10;
        }
        yTextSize = startingPaint.getTextSize() * rectToFit.height() / bounds.height();
        if (yTextSize < 10 || yTextSize > Float.MAX_VALUE) {
            yTextSize = 10;
        }
    }

    public float getTextSize() {
        if (xTextSize < yTextSize) {
            return xTextSize;
        }
        else {
            return yTextSize;
        }
    }
}
