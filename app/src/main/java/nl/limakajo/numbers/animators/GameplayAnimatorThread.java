package nl.limakajo.numbers.animators;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import nl.limakajo.numbers.utils.Attributes;

public class GameplayAnimatorThread extends Thread {

    List<Animator> animators;
    boolean running;

    public GameplayAnimatorThread() {
        this.animators = new ArrayList<>();
    }

    @Override
    public void run() {
        while (running) {
            Iterator<Animator> animatorIterator = animators.iterator();
            while (animatorIterator.hasNext()) {
                Animator animator = animatorIterator.next();
                float timePassed = (System.nanoTime() - animator.getStartingTime()) / 1000000;
                float factor = 1 - timePassed / (float) (Attributes.TILE_ANIMATION_TIME);
                if (animator.isAnimating()) {
                    try {
                        animator.adjustAnimatorParameters(factor);
                    } catch (ConcurrentModificationException e) {
                        e.printStackTrace();
                    }
                }
                if (timePassed >= Attributes.TILE_ANIMATION_TIME) {
                    animator.setAnimatorParametersToTarget();
                    animatorIterator.remove();
                }
            }
        }
    }

    public void add(PositionAnimator positionAnimator) {
        animators.add(positionAnimator);
    }

    public void addAll(List<PositionAnimator> positionAnimators) {
        this.animators.addAll(positionAnimators);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void remove(PositionAnimator positionAnimator) {
        animators.remove(positionAnimator);
    }

}
