package nl.limakajo.numbers.animators;

import nl.limakajo.numbers.gameObjects.GameObject;

public abstract class Animator<T> {
    protected long animationTime;
    protected long startingTime;
    protected boolean animating;

    public abstract void update(float factor);

    public abstract void setToTarget();

    public abstract void init(GameObject gameObject, T targetParameter);

    public long getStartingTime() {
        return startingTime;
    }

    public void startAnimation() {
        this.animating = true;
        startingTime = System.nanoTime();
    }

    public boolean isAnimating() {
        return animating;
    }

    public void stopAnimating() {
        animating = false;
    }

}
