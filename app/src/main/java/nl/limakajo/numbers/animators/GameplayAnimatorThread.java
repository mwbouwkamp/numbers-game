package nl.limakajo.numbers.animators;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class GameplayAnimatorThread extends Thread {

    private List<Animator> animators;
    boolean running;

    public GameplayAnimatorThread() {
        this.animators = new ArrayList<>();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Iterator<Animator> animatorIterator = animators.iterator();
                while (animatorIterator.hasNext()) {
                    Animator animator = animatorIterator.next();
                    float timePassed = (System.nanoTime() - animator.getStartingTime()) / 1000000;
                    float factor = 1 - timePassed / (float) (animator.animationTime);
                    animator.update(factor);
                    if (timePassed >= animator.animationTime) {
                        animator.setToTarget();
                        animator.stopAnimating();
                        animatorIterator.remove();
                    }
                }
            } catch (ConcurrentModificationException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Animator animator) {
        animators.add(animator);
    }

    public void addAll(List<Animator> animators) {
        this.animators.addAll(animators);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void remove(Animator animator) {
        animators.remove(animator);
    }

}
