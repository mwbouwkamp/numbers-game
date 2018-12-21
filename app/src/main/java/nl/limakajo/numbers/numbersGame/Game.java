package nl.limakajo.numbers.numbersGame;

import nl.limakajo.numberslib.numbersGame.Level;

public class Game {

    private Level level;

    public Game() {
        this.level = null;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
