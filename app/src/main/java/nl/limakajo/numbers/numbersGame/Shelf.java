package nl.limakajo.numbers.numbersGame;

import android.graphics.Point;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import nl.limakajo.numbers.animators.PositionAnimator;
import nl.limakajo.numbers.gameObjects.Tile;
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

    /**
     * populates and returns a list of Animators for the tiles that need to start animating because they are not on their respective correct positions on the shelf
     *
     * @return      List of Animators for the required animations
     */
    public List<PositionAnimator> startAnimating() {
        List<PositionAnimator> positionAnimators = new LinkedList<>();
        int i = 0;
        for (Tile tile: tilesOnShelf) {
            Point targetPosition = new Point(Attributes.TILE_XCOORDS[i], Attributes.TILE_YCOORD);
            if (!tile.getPosition().equals(targetPosition)) {
                PositionAnimator positionAnimatorToAdd = new PositionAnimator(Attributes.TILE_ANIMATION_TIME);
                positionAnimatorToAdd.initPositionAnimation(tile.getPosition(), targetPosition);
                positionAnimators.add(positionAnimatorToAdd);
                tile.setPositionAnimator(positionAnimatorToAdd);
            }
            i++;
        }
        return positionAnimators;
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
     * Adds a tile to the shelf and returns the position of that Tile of the shelf
     *
     * @param tile      the Tile to add
     * @return          the position of the added Tile on the shelf
     */
    public int addTile(Tile tile) {
        tilesOnShelf.add(tile);
        return tilesOnShelf.size() - 1;
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

}
