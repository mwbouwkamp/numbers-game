package nl.limakajo.numbers.main;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import nl.limakajo.numbers.animators.Animator;

public class AnimatorThread extends Thread {

    private List<Animator> animators;
    boolean running;

    public AnimatorThread() {
        this.animators = new ArrayList<>();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Iterator<Animator> animatorIterator = animators.iterator();
                while (animatorIterator.hasNext()) {

                    System.out.println("ANIMATOR: " + animators.size());
                    Animator animator = animatorIterator.next();
                    float timePassed = (System.nanoTime() - animator.getStartingTime()) / 1000000;
                    if (timePassed < 0) {
                        timePassed = 0;
                    }
                    float factor = 1 - timePassed / (float) (animator.getAnimationTime());
                    animator.update(factor);
                    if (timePassed >= animator.getAnimationTime()) {
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

    public void removeAll() {
        this.animators = new ArrayList<>();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void remove(Animator animator) {
        animators.remove(animator);
    }

    public List<Animator> getAnimators() {
        return animators;
    }

}
