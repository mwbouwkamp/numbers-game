package nl.limakajo.numbers.numbersgame;

import java.util.LinkedList;

import nl.limakajo.numbers.gameObjects.Tile;


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
     * Adds a Tile to the Shelf and returns the position of the Tile on the Shelf
     *
     * @param tileToAdd     the Tile to add
     * @return              position of the Tile on the Shelf
     */
    public int addTile(Tile tileToAdd) {
        tilesOnShelf.add(tileToAdd);
        return tilesOnShelf.size() - 1;
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
}
