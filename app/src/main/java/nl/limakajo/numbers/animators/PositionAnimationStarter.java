package nl.limakajo.numbers.animators;

import android.graphics.Paint;
import android.graphics.Point;

import nl.limakajo.numbers.gameObjects.AnimatesPaint;
import nl.limakajo.numbers.gameObjects.AnimatesPosition;
import nl.limakajo.numbers.main.AnimatorThread;

public class PositionAnimationStarter extends AnimationStarter<AnimatesPosition, Point> {
    @Override
    public void startAnimation(AnimatesPosition animates, AnimatorThread animatorThread, Point start, Point target, long animationTime, long delayTime) {
        PositionAnimator animator = new PositionAnimator(animationTime);
        animator.init(start, target);
        animates.setPositionAnimator(animator);
        animator.startAnimation(delayTime);
        animatorThread.add(animator);
    }

}

