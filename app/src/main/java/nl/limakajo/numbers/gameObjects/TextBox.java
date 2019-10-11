package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Size;

import nl.limakajo.numbers.animators.PaintAnimator;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.FontScaler;
import nl.limakajo.numbers.utils.PaintComparator;

/**
 * Class that represents a text box
 *
 * @author M.W.Bouwkamp
 */
public class TextBox extends LayoutObject implements AnimatesPaint {

    private String text;
    private final Attributes.TextAllignment alignment;
    private Point textPosition;
    private PaintAnimator paintAnimator;

    /**
     * Constructs a TextBox
     *
     * @param text          String containing the text
     * @param alignment     Attributes.TextAllignment defining how the text will be aligned in the TextBox
     * @param position  Top-left position of the Layoutobject
     * @param size      Size of the LayoutObject
     * @param paint         Paint defining the graphical attributes
     */
    public TextBox(String text, Attributes.TextAllignment alignment, Point position, Size size, Paint paint) {
        super(position, size, paint);
        this.alignment = alignment;
        setText(text);
        this.paintAnimator = new PaintAnimator(0);
    }

    /**
     * Sets the text to a new value and adjusts the alignement
     *
     * @param text      String containing the new text
     */
    public void setText(String text) {
        this.text = text;
        paint.setTextSize(new FontScaler(text, paint, getArea()).getTextSize());
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
                textPosition.x = getArea().left + getArea().width() / 2;
                textPosition.y = getArea().top + getArea().height() / 2 + bounds.height() / 2;
                break;
            case XCENTERED_YTOP:
                paint.setTextAlign(Paint.Align.CENTER);
                textPosition.x = getArea().left + getArea().width() / 2;
                textPosition.y = getArea().top + Attributes.MARGIN;
                break;
            case XLEFT_YCENTERED:
                paint.setTextAlign(Paint.Align.LEFT);
                textPosition.x = getArea().left + bounds.width() + Attributes.MARGIN;
                textPosition.y = getArea().top + getArea().height() / 2 + bounds.height() / 2;
                break;
            case XRIGHT_YCENTERED:
                paint.setTextAlign(Paint.Align.RIGHT);
                textPosition.x = getArea().right - Attributes.MARGIN;
                textPosition.y = getArea().top + getArea().height() / 2 + bounds.height() / 2;
                break;
            default:
                paint.setTextAlign(Paint.Align.LEFT);
                textPosition.x = getArea().left + Attributes.MARGIN;
                textPosition.y = getArea().top + Attributes.MARGIN;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (new PaintComparator().compare(paint, Attributes.NO_DRAW) == -1) {
            canvas.drawText(text, textPosition.x, textPosition.y, paint);
        }
    }

    @Override
    public void update() {
        if (paintAnimator.isAnimating()) {
            paint = paintAnimator.getCurrentState();
        }
    }

    @Override
    public void setPaintAnimator(PaintAnimator paintAnimator) {
        this.paintAnimator = paintAnimator;
    }


    /**
     * GETTERS
     */

    public String getText() {
        return text;
    }

    public Attributes.TextAllignment getAlignment() {
        return alignment;
    }

}

