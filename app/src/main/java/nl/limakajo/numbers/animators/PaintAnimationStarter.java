package nl.limakajo.numbers.animators;

import android.graphics.Paint;

import nl.limakajo.numbers.gameObjects.AnimatesPaint;
import nl.limakajo.numbers.main.AnimatorThread;

public class PaintAnimationStarter extends AnimationStarter<AnimatesPaint, Paint> {
    @Override
    public void startAnimation(AnimatesPaint animates, AnimatorThread animatorThread, Paint start, Paint target, long animationTime, long delayTime) {
        PaintAnimator animator = new PaintAnimator(animationTime);
        animator.init(start, target);
        animates.setPaintAnimator(animator);
        animator.startAnimation(delayTime);
        animatorThread.add(animator);
    }

}

