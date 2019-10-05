package nl.limakajo.numbers.gameObjects;

public class WavePool extends GameObjectPool<Wave>{

    @Override
    public void update() {
        for (Wave wave: gameObjectPool) {
            wave.update();
        }
    }

}
