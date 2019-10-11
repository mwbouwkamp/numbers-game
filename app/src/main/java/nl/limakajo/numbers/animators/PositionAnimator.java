package nl.limakajo.numbers.animators;

import android.graphics.Point;

import nl.limakajo.numbers.gameObjects.Animates;
import nl.limakajo.numbers.gameObjects.AnimatesPosition;
import nl.limakajo.numbers.gameObjects.GameObject;

public class PositionAnimator extends Animator<AnimatesPosition, Point>{
    private Point startingPosition;
    private Point currentPosition;
    private Point targetPosition;

    public PositionAnimator(long animationTime) {
        this.animationTime = animationTime;
    }

    /**
     * Initializes the PositionAnimator for animation of the Position of the GameObject
     *
     * @param animatesPosition            The GameObject
     * @param targetPosition        The target Position of the gameObject
     */
    @Override
    public void init(AnimatesPosition animatesPosition, Point targetPosition) {
        this.startingPosition = animatesPosition.getPosition();
        this.currentPosition = animatesPosition.getPosition();
        this.targetPosition = targetPosition;
    }

    public  Point getCurrentPosition() {
        return new Point(currentPosition);
    }

    @Override
    public void update(float factor) {
        currentPosition = new Point(
                (int) (targetPosition.x * (1 - factor) + startingPosition.x * factor),
                (int) (targetPosition.y * (1 - factor) + startingPosition.y * factor));

    }

    @Override
    public void setToTarget() {
        currentPosition = targetPosition;
    }
}
