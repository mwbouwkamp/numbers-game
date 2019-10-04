package nl.limakajo.numbers.utils;

import android.graphics.Paint;
import android.graphics.Point;

import nl.limakajo.numbers.gameObjects.GameObject;

public class Animator {
    private long animationTime;
    private long startingTime;
    private boolean animatePosition;
    private Point startingPosition;
    private Point currentPosition;
    private Point targetPosition;
    private boolean animatePaint;
    private Paint startingPaint;
    private Paint currentPaint;
    private Paint targetPaint;
    private boolean animateScale;
    private float startingScale;
    private float currentScale;
    private float targetScale;

    public Animator(long animationTime) {
        this.animationTime = animationTime;
    }

    /**
     * Initializes the Animator for animation of the Position of the GameObject
     *
     * @param startingPosition      The starting Position of the GameObject
     * @param targetPosition        The target Position of the gameObject
     */
    public void initPositionAnimation(Point startingPosition, Point targetPosition) {
        this.startingPosition = startingPosition;
        this.currentPosition = startingPosition;
        this.targetPosition = targetPosition;
        animatePosition = true;
        startingTime = System.nanoTime();
    }

    /**
     * Initializes the Animator for animation of the Paint of the GameObject
     *
     * @param startingPaint      The starting Paint of the GameObject
     * @param targetPaint        The target Paint of the gameObject
     */
    public void initPaintAnimation(Paint startingPaint, Paint targetPaint) {
        this.startingPaint = startingPaint;
        this.currentPaint = startingPaint;
        this.targetPaint = targetPaint;
        this.animatePaint = true;
        startingTime = System.nanoTime();
    }

    /**
     * Initializes the Animator for animation of the scale of the GameObject
     *
     * @param startingScale      The starting scale of the GameObject
     * @param targetScale        The target scale of the gameObject
     */
    public void initScaleAnimation(float startingScale, float targetScale) {
        this.startingScale = startingScale;
        this.currentScale = startingScale;
        this.targetScale = targetScale;
        this.animateScale = true;
        startingTime = System.nanoTime();
    }

    public long getAnimationTime() {
        return animationTime;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public boolean animatePosition() {
        return animatePosition;
    }

    public Point getStartingPosition() {
        return startingPosition;
    }

    public  Point getCurrentPosition() {
        return currentPosition;
    }

    public Point getTargetPosition() {
        return targetPosition;
    }

    public boolean animatePaint() {
        return animatePaint;
    }

    public Paint getStartingPaint() {
        return startingPaint;
    }

    public Paint getCurrentPaint() {
        return currentPaint;
    }

    public Paint getTargetPaint() {
        return targetPaint;
    }

    public boolean animateScale() {
        return animateScale;
    }

    public float getStartingScale() {
        return startingScale;
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public float getTargetScale() {
        return targetScale;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setCurrentPaint(Paint currentPaint) {
        this.currentPaint = currentPaint;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
    }
}
