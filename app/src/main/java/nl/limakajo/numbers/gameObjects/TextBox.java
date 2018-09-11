package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import nl.limakajo.numbers.utils.Attributes;

/**
 * @author M.W.Bouwkamp
 */

public class TextBox implements GameObject {

    private String text;
    private final Attributes.TextAllignment alignment;
    private final Rect rect;
    private final Paint paint;
    private Point textPosition;

    public TextBox(String text, Attributes.TextAllignment alignment, Rect rect, Paint paint) {
        this.alignment = alignment;
        this.rect = rect;
        this.paint = new Paint(paint);
        setText(text);
    }

    public Rect getArea() {
        return rect;
    }

    public void setText(String text) {
        this.text = text;
        setTextPosition();
    }

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
                textPosition.x = rect.left + Attributes.MARGE;
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
        canvas.drawText(text, textPosition.x, textPosition.y, paint);
    }

    @Override
    public void update() {

    }

    public Paint getPaint() {
        return paint;
    }
}

