package nl.limakajo.numbers.animators;

import nl.limakajo.numbers.gameObjects.AnimatesScale;

public class ScaleAnimator extends Animator<AnimatesScale, Float> {

    public ScaleAnimator(long animationTime) {
        this.animationTime = animationTime;
    }

    @Override
    public void update(float factor) {
        currentState = targetState * (1 - factor) + startingState * factor;
    }

}
