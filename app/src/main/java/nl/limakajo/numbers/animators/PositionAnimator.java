package nl.limakajo.numbers.animators;

import android.graphics.Point;

public class PositionAnimator extends Animator {
    private Point startingPosition;
    private Point currentPosition;
    private Point targetPosition;

    public PositionAnimator(long animationTime) {
        this.animationTime = animationTime;
    }

    /**
     * Initializes the PositionAnimator for animation of the Position of the GameObject
     *
     * @param startingPosition      The starting Position of the GameObject
     * @param targetPosition        The target Position of the gameObject
     */
    public void initPositionAnimation(Point startingPosition, Point targetPosition) {
        this.startingPosition = startingPosition;
        this.currentPosition = startingPosition;
        this.targetPosition = targetPosition;
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
