package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.FontScaler;
import nl.limakajo.numbers.utils.PaintComparator;

/**
 * Class that represents a text box
 *
 * @author M.W.Bouwkamp
 */
public class TextBox extends LayoutObject {

    private String text;
    private final Attributes.TextAllignment alignment;
    private Point textPosition;

    /**
     * Constructs a TextBox
     *
     * @param text          String containing the text
     * @param alignment     Attributes.TextAllignment defining how the text will be aligned in the TextBox
     * @param rect          Rect defining the boundaries of the GameObjectInterface
     * @param paint         Paint defining the graphical attributes
     */
    public TextBox(String text, Attributes.TextAllignment alignment, Rect rect, Paint paint) {
        super(paint, rect);
        this.alignment = alignment;
        setText(text);
    }

    /**
     * Sets the text to a new value and adjusts the alignement
     *
     * @param text      String containing the new text
     */
    public void setText(String text) {
        this.text = text;
        paint.setTextSize(new FontScaler(text, paint, rect).getTextSize());
        setTextPosition();
    }

    /**
     * Sets the allignment of the text
     */
    private void setTextPosition() {
        textPosition = new Point();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        switch (alignment) {
            case XYCENTERED:
                paint.setTextAlign(Paint.Align.CENTER);
                textPosition.x = rect.left + rect.width() / 2;
                textPosition.y = rect.top + rect.height() / 2 + bounds.height() / 2;
                break;
            case XCENTERED_YTOP:
                paint.setTextAlign(Paint.Align.CENTER);
                textPosition.x = rect.left + rect.width() / 2;
                textPosition.y = rect.top + Attributes.MARGE;
                break;
            case XLEFT_YCENTERED:
                paint.setTextAlign(Paint.Align.LEFT);
                textPosition.x = rect.left + bounds.width() + Attributes.MARGE;
                textPosition.y = rect.top + rect.height() / 2 + bounds.height() / 2;
                break;
            case XRIGHT_YCENTERED:
                paint.setTextAlign(Paint.Align.RIGHT);
                textPosition.x = rect.right - Attributes.MARGE;
                textPosition.y = rect.top + rect.height() / 2 + bounds.height() / 2;
                break;
            default:
                paint.setTextAlign(Paint.Align.LEFT);
                textPosition.x = rect.left + Attributes.MARGE;
                textPosition.y = rect.top + Attributes.MARGE;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (new PaintComparator().compare(paint, Attributes.NO_DRAW) == -1) {
            canvas.drawText(text, textPosition.x, textPosition.y, paint);
        }
    }
}

