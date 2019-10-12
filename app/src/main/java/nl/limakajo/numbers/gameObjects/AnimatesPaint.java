package nl.limakajo.numbers.gameObjects;

import android.graphics.Paint;

import nl.limakajo.numbers.animators.PaintAnimator;

public interface AnimatesPaint extends Animates {
    void setPaintAnimator(PaintAnimator paintAnimator);
    Paint getPaint();
}



