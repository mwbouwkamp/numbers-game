package nl.limakajo.numbers.animators;

import android.graphics.Paint;
import android.graphics.Point;

import nl.limakajo.numbers.gameObjects.AnimatesScale;
import nl.limakajo.numbers.gameObjects.GameObject;

public class ScaleAnimator extends Animator<AnimatesScale, Float> {
    private float startingScale;
    private float currentScale;
    private float targetScale;

    public ScaleAnimator(long animationTime) {
        this.animationTime = animationTime;
    }

    /**
     * Initializes the ScaleAnimator for animation of the scale of the GameObject
     *
     * @param animatesScale        The starting GameObject
     * @param targetScale       The target Position of the gameObject
     */
    @Override
    public void init(AnimatesScale animatesScale, Float targetScale) {
        this.startingScale = animatesScale.getScale();
        this.currentScale = animatesScale.getScale();
        this.targetScale = targetScale;
        this.animating = true;
        startingTime = System.nanoTime();
    }

    public  float getCurrentScale() {
        return currentScale;
    }

    @Override
    public void update(float factor) {
        currentScale = targetScale * (1 - factor) + startingScale * factor;
    }

    @Override
    public void setToTarget() {
        currentScale = targetScale;
    }
}
