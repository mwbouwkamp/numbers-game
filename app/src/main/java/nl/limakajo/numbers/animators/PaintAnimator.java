package nl.limakajo.numbers.animators;

import android.graphics.Color;
import android.graphics.Paint;

import nl.limakajo.numbers.gameObjects.AnimatesPaint;

public class PaintAnimator extends Animator<AnimatesPaint, Paint> {

    public PaintAnimator(long animationTime) {
        this.animationTime = animationTime;
    }

    @Override
    public void update(float factor) {
        currentState = new Paint(startingState);
        int alpha = (int) (targetState.getAlpha() * (1 - factor) + startingState.getAlpha() * factor);
        int red = (int) (Color.red(targetState.getColor()) * (1 - factor) + Color.red(startingState.getColor()) * factor);
        int green = (int) (Color.green(targetState.getColor()) * (1 - factor) + Color.green(startingState.getColor()) * factor);
        int blue = (int) (Color.blue(targetState.getColor()) * (1 - factor) + Color.blue(startingState.getColor()) * factor);
        currentState.setARGB(alpha, red, green, blue);
        currentState.setStrokeWidth(targetState.getStrokeWidth() * (1 - factor) + startingState.getStrokeWidth() * factor);
    }

}
