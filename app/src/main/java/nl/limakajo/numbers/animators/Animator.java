package nl.limakajo.numbers.animators;

public abstract class Animator {
    protected long animationTime;
    protected long startingTime;
    protected boolean animating;

    public abstract void adjustAnimatorParameters(float factor);

    public abstract void setAnimatorParametersToTarget();

    public long getStartingTime() {
        return startingTime;
    }

    public boolean isAnimating() {
        return animating;
    }

}
