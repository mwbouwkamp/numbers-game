package nl.limakajo.numbers.animators;

import android.graphics.Point;

import nl.limakajo.numbers.gameObjects.GameObject;

public class PositionAnimator extends Animator<Point>{
    private Point startingPosition;
    private Point currentPosition;
    private Point targetPosition;

    public PositionAnimator(long animationTime) {
        this.animationTime = animationTime;
    }

    /**
     * Initializes the PositionAnimator for animation of the Position of the GameObject
     *
     * @param gameObject            The GameObject
     * @param targetPosition        The target Position of the gameObject
     */
    @Override
    public void initAnimationParameters(GameObject gameObject, Point targetPosition) {
        this.startingPosition = gameObject.getPosition();
        this.currentPosition = gameObject.getPosition();
        this.targetPosition = targetPosition;
        this.animating = true;
        startingTime = System.nanoTime();
    }

    public  Point getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void adjustAnimatorParameters(float factor) {
        currentPosition = new Point(
                (int) (targetPosition.x * (1 - factor) + startingPosition.x * factor),
                (int) (targetPosition.y * (1 - factor) + startingPosition.y * factor));

    }

    @Override
    public void setAnimatorParametersToTarget() {
        currentPosition = targetPosition;
    }
}
