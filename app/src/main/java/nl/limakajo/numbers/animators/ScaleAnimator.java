package nl.limakajo.numbers.animators;

import android.graphics.Point;

public class ScaleAnimator extends Animator {
    private float startingScale;
    private float currentScale;
    private float targetScale;

    public ScaleAnimator(long animationTime) {
        this.animationTime = animationTime;
    }

    /**
     * Initializes the ScaleAnimator for animation of the scale of the GameObject
     *
     * @param startingScale      The starting Position of the GameObject
     * @param targetScale        The target Position of the gameObject
     */
    public void initPositionAnimation(float startingScale, float targetScale) {
        this.startingScale = startingScale;
        this.currentScale = startingScale;
        this.targetScale = targetScale;
        startingTime = System.nanoTime();
    }

    public  float getCurrentScale() {
        return currentScale;
    }

    @Override
    public void adjustAnimatorParameters(float factor) {
        currentScale = targetScale * (1 - factor) + startingScale * factor;

    }

    @Override
    public void setAnimatorParametersToTarget() {
        currentScale = targetScale;
    }
}
