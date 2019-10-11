package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;

import java.util.Iterator;
import java.util.LinkedList;

public class WavePool extends GameObjectPool<Wave>{

    /**
     * Constructs a WavePool
     */
    public WavePool() {
        gameObjectPool = new LinkedList<Wave>();
    }


    @Override
    public void update() {
        Iterator<Wave> waveIterator = gameObjectPool.iterator();
        while (waveIterator.hasNext()) {
            Wave wave = waveIterator.next();
            wave.update();
            if (!wave.getScaleAnimator().isAnimating()) {
                waveIterator.remove();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for (Wave wave: gameObjectPool) {
            wave.draw(canvas);
        }
    }

}
