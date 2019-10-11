package nl.limakajo.numbers.animators;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import nl.limakajo.numbers.gameObjects.Animates;
import nl.limakajo.numbers.gameObjects.AnimatesPaint;
import nl.limakajo.numbers.gameObjects.GameObject;

public class PaintAnimator extends Animator<AnimatesPaint, Paint> {

    public enum PaintAnimatorParams {
        RED,
        GREEN,
        BLUE,
        ALPHA,
        STROKE_WIDTH
    }

    private Paint startingPaint;
    private Paint currentPaint;
    private Paint targetPaint;
    private List<PaintAnimatorParams> paintAnimatorParams;

    public PaintAnimator(long animationTime) {
        this.animationTime = animationTime;
    }

    @Override
    public void init(AnimatesPaint animatesPaint, Paint targetScale) {
        this.startingPaint = animatesPaint.getPaint();
        this.currentPaint = startingPaint;
        this.targetPaint = targetScale;
    }

    public void setPaintAnimatorParams(List<PaintAnimatorParams> paintAnimatorParams) {
        this.paintAnimatorParams = paintAnimatorParams;
    }

    public Paint getCurrentPaint() {
        return new Paint(currentPaint);
    }

    @Override
    public void update(float factor) {
        currentPaint = new Paint(startingPaint);
        int alpha = currentPaint.getAlpha();
        int red = Color.red(currentPaint.getColor());
        int green = Color.green(currentPaint.getColor());
        int blue  = Color.blue(currentPaint.getColor());
        boolean colorChanged = false;
        if (paintAnimatorParams.contains(PaintAnimatorParams.ALPHA)) {
            alpha = (int) (targetPaint.getAlpha() * (1 - factor) + startingPaint.getAlpha() * factor);
            colorChanged = true;
        }
        if (paintAnimatorParams.contains(PaintAnimatorParams.RED)) {
            red = (int) (Color.red(targetPaint.getColor()) * (1 - factor) + Color.red(startingPaint.getColor()) * factor);
            colorChanged = true;
        }
        if (paintAnimatorParams.contains(PaintAnimatorParams.GREEN)) {
            green = (int) (Color.green(targetPaint.getColor()) * (1 - factor) + Color.green(startingPaint.getColor()) * factor);
            colorChanged = true;
        }
        if (paintAnimatorParams.contains(PaintAnimatorParams.BLUE)) {
            blue = (int) (Color.blue(targetPaint.getColor()) * (1 - factor) + Color.blue(startingPaint.getColor()) * factor);
            colorChanged = true;
        }
        currentPaint.setARGB(alpha, red, green, blue);
        if (paintAnimatorParams.contains(PaintAnimatorParams.STROKE_WIDTH)) {
            currentPaint.setStrokeWidth(targetPaint.getStrokeWidth() * (1 - factor) + startingPaint.getStrokeWidth() * factor);
        }
    }

    @Override
    public void setToTarget() {
        currentPaint = targetPaint;
    }
}
