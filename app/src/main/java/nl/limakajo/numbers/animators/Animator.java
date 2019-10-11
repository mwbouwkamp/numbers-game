package nl.limakajo.numbers.animators;

import nl.limakajo.numbers.gameObjects.Animates;

public abstract class Animator<T> {

    protected T startingState;
    protected T currentState;
    protected T targetState;

    protected long animationTime;
    protected long startingTime;
    protected boolean animating;

    /**
     * Abstract function for updating the currentState
     *
     * @param factor        the factor between startingState and targetState
     */
    public abstract void update(float factor);

    /**
     * Initializes the Animator, setting the startingState and the targetState of the animation
     *
     * @param startingState     the startingState of the animation
     * @param targetState       the targetState of the animation
     */
    public void init(T startingState, T targetState) {
        this.startingState = startingState;
        this.currentState = startingState;
        this.targetState = targetState;
    }

    /**
     * Starts the animation
     */
    public void startAnimation() {
        this.animating = true;
        startingTime = System.nanoTime();
    }

    /**
     * Starts the animation with a delay
     *
     * @param delay     the delay after which the animation starts
     */
    public void startAnimation(long delay) {
        this.animating = true;
        startingTime = System.nanoTime() + delay * 1000000;
    }

    /**
     * Sets animating to false
     */
    public void stopAnimating() {
        animating = false;
    }

    /**
     * Sets the currentState to the targetState
     */
    public void setToTarget() {
        currentState = targetState;
    }


    //GETTER AND SETTERS

    public  T getCurrentState() {
        return currentState;
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

}
