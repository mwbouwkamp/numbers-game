package nl.limakajo.numbers.animators;

import nl.limakajo.numbers.gameObjects.Animates;
import nl.limakajo.numbers.main.AnimatorThread;

public abstract class AnimationStarter<S extends Animates, T> {

    public abstract void startAnimation(S animates, AnimatorThread animatorThread, T start, T target, long animationTime, long delayTime);
}
