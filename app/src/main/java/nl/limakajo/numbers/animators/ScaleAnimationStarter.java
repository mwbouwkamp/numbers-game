package nl.limakajo.numbers.animators;

import nl.limakajo.numbers.gameObjects.AnimatesScale;
import nl.limakajo.numbers.main.AnimatorThread;

public class ScaleAnimationStarter extends AnimationStarter<AnimatesScale, Float> {
    @Override
    public void startAnimation(AnimatesScale animates, AnimatorThread animatorThread, Float start, Float target, long animationTime, long delayTime) {
        ScaleAnimator animator = new ScaleAnimator(animationTime);
        animator.init(start, target);
        animates.setScaleAnimator(animator);
        animator.startAnimation(delayTime);
        animatorThread.add(animator);
    }

}

