package nl.limakajo.numbers.gameObjects;

import java.util.List;

public abstract class GameObjectPool<GameObject> {

    protected List<GameObject> gameObjectPool;

    public void remove(GameObject gameObject) {
        gameObjectPool.remove(gameObject);
    }

    public int add(GameObject gameObject) {
        gameObjectPool.add(gameObject);
        return getSize() - 1;
    }

    public int getSize() {
        return gameObjectPool.size();
    }

    public abstract void update();
}
