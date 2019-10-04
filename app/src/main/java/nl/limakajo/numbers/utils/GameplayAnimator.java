package nl.limakajo.numbers.utils;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;

public class GameplayAnimator extends Thread {

    List<Animator> animators;
    boolean running;

    public GameplayAnimator() {
        this.animators = new ArrayList<>();
    }

    @Override
    public void run() {
        while (running) {
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Animator> stoppedAnimators = new LinkedList<>();
            for (Animator animator: animators) {
                float timePassed  = (System.nanoTime() - animator.getStartingTime()) / 1000000;
                float factor = 1 - timePassed /(float) (Attributes.TILE_ANIMATION_TIME);
                if (animator.animatePosition()) {
                    try {
                        animator.setCurrentPosition(new Point(
                                (int) (animator.getTargetPosition().x * (1 - factor) + animator.getStartingPosition().x * factor),
                                (int) (animator.getTargetPosition().y * (1 - factor) + animator.getStartingPosition().y * factor)));
                    } catch (ConcurrentModificationException e) {
                        e.printStackTrace();
                    }
                }
                if (animator.animatePaint()) {
                    // Update Paint
                }
                if (animator.animateScale()) {
                    // Update scale
                }
                if (timePassed >= Attributes.TILE_ANIMATION_TIME) {
                    stoppedAnimators.add(animator);
                    animator.setCurrentPosition(animator.getTargetPosition());
                    animator.setCurrentPaint(animator.getTargetPaint());
                    animator.setCurrentScale(animator.getTargetScale());
                }
            }
            if (stoppedAnimators.size() > 0) {
                animators.removeAll(stoppedAnimators);
            }
        }
    }

    public void add(Animator animator) {
        animators.add(animator);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void remove(Animator animator) {
        animators.remove(animator);
    }

    public void addAll(List<Animator> animators) {
        this.animators.addAll(animators);
        System.out.println("ANIMATOR num animators added: " + animators.size());
    }
}
