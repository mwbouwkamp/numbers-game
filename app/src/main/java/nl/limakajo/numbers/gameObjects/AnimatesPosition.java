package nl.limakajo.numbers.gameObjects;


import android.graphics.Point;

import nl.limakajo.numbers.animators.PositionAnimator;

public interface AnimatesPosition extends Animates {
    void setPositionAnimator(PositionAnimator paintAnimator);
    Point getPosition();
}
