package nl.limakajo.numbers.gameObjects;

import nl.limakajo.numbers.animators.Animator;
import nl.limakajo.numbers.animators.ScaleAnimator;

public interface AnimatesScale extends Animates {
    void setScaleAnimator(ScaleAnimator scaleAnimator);
    float getScale();
}
