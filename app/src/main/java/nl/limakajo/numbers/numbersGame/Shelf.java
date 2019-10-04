package nl.limakajo.numbers.numbersGame;

import android.graphics.Point;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import nl.limakajo.numbers.gameObjects.Tile;
import nl.limakajo.numbers.utils.Animator;
import nl.limakajo.numbers.utils.Attributes;


/**
 * Class representing the Tiles on the shelf
 *
 * @author M.W.Bouwkamp
 */
public class Shelf {

    private LinkedList<Tile> tilesOnShelf;

    /**
     * Constructs a Shelf
     */
    public Shelf() {
        tilesOnShelf = new LinkedList<>();
    }


    /**
     * Updates the Tiles on the Shelf
     */
    public void update() {
        try {
            for (Tile tile : tilesOnShelf) {
                tile.update();
            }
        } catch (ConcurrentModificationException | NoSuchElementException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public List<Animator> startAnimating() {
        List<Animator> animators = new LinkedList<>();
        int i = 0;
        for (Tile tile: tilesOnShelf) {
            Point targetPosition = new Point(Attributes.TILE_XCOORDS[i], Attributes.TILE_YCOORD);
            if (!tile.getPosition().equals(targetPosition)) {
                Animator animatorToAdd = new Animator(Attributes.TILE_ANIMATION_TIME);
                animatorToAdd.initPositionAnimation(tile.getPosition(), targetPosition);
                animators.add(animatorToAdd);
                tile.setAnimator(animatorToAdd);
                System.out.println("ANIMATOR added with position: " + animatorToAdd.getCurrentPosition() + " and target " + animatorToAdd.getTargetPosition());
            }
            i++;
        }
        System.out.println("ANIMATORS added: " + animators.size());
        return animators;
    }

    /**
     * Removes a Tile from the Shelf
     *
     * @param tileToRemove      the Tile to remove
     */
    public void removeTile(Tile tileToRemove) {
        tilesOnShelf.remove(tileToRemove);
    }

    /**
     * GETTERS
     */
    public LinkedList<Tile> getTilesOnShelf() {
        return tilesOnShelf;
    }

    public int getAvailablePosition() {
        return tilesOnShelf.size();
    }

    public int addTile(Tile tile) {
        tilesOnShelf.add(tile);
        return tilesOnShelf.size() - 1;
    }
}
