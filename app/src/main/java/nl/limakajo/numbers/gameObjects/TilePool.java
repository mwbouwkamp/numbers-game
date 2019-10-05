package nl.limakajo.numbers.gameObjects;

import android.graphics.Canvas;
import android.graphics.Point;

import java.util.LinkedList;
import java.util.List;

import nl.limakajo.numbers.animators.Animator;
import nl.limakajo.numbers.animators.PositionAnimator;
import nl.limakajo.numbers.utils.Attributes;


/**
 * Class representing the Tiles on the shelf
 *
 * @author M.W.Bouwkamp
 */
public class TilePool extends GameObjectPool<Tile> {

    /**
     * Constructs a TilePool
     */
    public TilePool() {
        gameObjectPool = new LinkedList<Tile>();
    }

    /**
     * populates and returns a list of Animators for the tiles that need to start animating because they are not on their respective correct positions on the shelf
     *
     * @return      List of Animators for the required animations
     */
    public List<Animator> startAnimating() {
        List<Animator> positionAnimators = new LinkedList<>();
        int i = 0;
        for (Tile tile: gameObjectPool) {
            Point targetPosition = new Point(Attributes.TILE_XCOORDS[i], Attributes.TILE_YCOORD);
            if (!tile.getPosition().equals(targetPosition)) {
                PositionAnimator positionAnimatorToAdd = new PositionAnimator(Attributes.TILE_ANIMATION_TIME);
                positionAnimatorToAdd.initAnimationParameters(tile, targetPosition);
                positionAnimators.add(positionAnimatorToAdd);
                tile.setPositionAnimator(positionAnimatorToAdd);
            }
            i++;
        }
        return positionAnimators;
    }

    @Override
    public void update() {
        for (Tile tile: gameObjectPool) {
            tile.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for (Tile tile: gameObjectPool) {
            tile.draw(canvas);
        }
    }


    /**
     * GETTERS
     */
    public List<Tile> getGameObjects() {
        return gameObjectPool;
    }

}
