package nl.limakajo.numbers.animators;

import android.graphics.Point;

public class PositionAnimator extends Animator<Point>{

    /**
     * Constructs a PositionAnmiator
     *
     * @param animationTime     the time it takes to animate
     */
    public PositionAnimator(long animationTime) {
        this.animationTime = animationTime;
    }

    @Override
    public void update(float factor) {
        currentState = new Point(
                (int) (targetState.x * (1 - factor) + startingState.x * factor),
                (int) (targetState.y * (1 - factor) + startingState.y * factor));

    }

}
