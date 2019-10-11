package nl.limakajo.numbers.animators;

import nl.limakajo.numbers.gameObjects.Animates;

public abstract class Animator<S extends Animates, T> {
    protected T startingState;
    protected T currentState;
    protected T targetState;

    protected long animationTime;
    protected long startingTime;
    protected boolean animating;

    public abstract void update(float factor);

    public void init(T startingState, T targetState) {
        this.startingState = startingState;
        this.currentState = startingState;
        this.targetState = targetState;
    }

    public void startAnimation() {
        this.animating = true;
        startingTime = System.nanoTime();
    }

    public void startAnimation(long delay) {
        this.animating = true;
        startingTime = System.nanoTime() + delay * 1000000;
    }

    public  T getCurrentState() {
        return currentState;
    }

    public void stopAnimating() {
        animating = false;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public long getAnimationTime() {
        return animationTime;
    }

    public boolean isAnimating() {
        return animating;
    }

    public void setToTarget() {
        currentState = targetState;
    }

}
