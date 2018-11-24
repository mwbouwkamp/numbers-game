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
        yTextSize = startingPaint.getTextSize() * rectToFit.height() / bounds.height();
    }

    /**
     * GETTERS AND SETTERS
     */

    public float getXTextSize() {
        return xTextSize;
    }

    public float getYTextSize() {
        return yTextSize;
    }
}
