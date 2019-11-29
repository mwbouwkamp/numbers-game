package nl.limakajo.numbers.scenes;

import nl.limakajo.numbers.gameObjects.Tile;

public class Calculator {
    private int numPlus, numMin, numMult, numDiv;

    public Calculator() {
        reset();
    }

    public void reset() {
        numPlus = 0;
        numMin = 0;
        numMult = 0;
        numDiv = 0;
    }

    public void addPlus() {
        numPlus++;
        numMin = 0;
        numMult = 0;
        numDiv = 0;
    }

    public void addMin() {
        numMin++;
        numPlus = 0;
        numMult = 0;
        numDiv = 0;
    }

    public void addMult() {
        numMult++;
        numPlus = 0;
        numMin = 0;
        numDiv = 0;
    }

    public void addDiv() {
        numDiv++;
        numPlus = 0;
        numMin = 0;
        numMult = 0;
    }

    public boolean executePlus() {
        return numPlus == 2;
    }

    public boolean executeMin() {
        return numMin == 2;
    }

    public boolean executeMult() {
        return numMult== 2;
    }

    public boolean executeDiv() {
        return numDiv == 2;
    }

}
