package nl.limakajo.numbers.utils;

import android.graphics.Paint;
import android.graphics.Point;

import nl.limakajo.numbers.gameObjects.GameObjectInterface;

public class Animator {
    private long startingTime;
    private int animationTime;
    private Point currentPosition;
    private Point targetPosition;
    private Paint currentPaint;
    private Paint targetPaint;
    private double currentScale;
    private double targetScale;

    public Animator(long startingTime, int animationTime, Point currentPosition, Point targetPosition, Paint currentPaint, Paint targetPaint, double currentScale, double targetScale) {
        this.startingTime = startingTime;
        this.animationTime = animationTime;
        this.currentPosition = currentPosition;
        this.targetPosition = targetPosition;
        this.currentPaint = currentPaint;
        this.targetPaint = targetPaint;
        this.currentScale = currentScale;
        this.targetScale = targetScale;
    }

    public class AnimatorBuilder {
        private long startingTime;
        private int animationTime;
        private Point currentPosition;
        private Point targetPosition;
        private Paint currentPaint;
        private Paint targetPaint;
        private double currentScale;
        private double targetScale;

        public AnimatorBuilder(GameObjectInterface gameObject, long startingTime, int animationTime) {
            this.startingTime = startingTime;
            this.animationTime = animationTime;
//            this.currentPosition =
        }
    }
}
