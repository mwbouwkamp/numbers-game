package nl.limakajo.numbers.scenes;

import nl.limakajo.numbers.gameObjects.Tile;
import nl.limakajo.numbers.layouts.LayoutElementsKeys;
import nl.limakajo.numbers.utils.Attributes;

public class Calculator {
    private int numPlus, numMin, numMult, numDiv, value;

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
    }

    public void addMin() {
        numMin++;
    }

    public void addMult() {
        numMult++;
    }

    public void addDiv() {
        numDiv++;
    }

    public void calculate(int value, LayoutElementsKeys layoutElementsKeys) {
        if (null != layoutElementsKeys) {
            switch (layoutElementsKeys) {
                case PLUS_AREA:
                    addPlus();
                    //TODO: Introduce max value to solve a level
                    if (numPlus == 2) {
                        this.value += value;
                    }
                    else {
                        this.value = value;
                        reset();
                        addPlus();
                    }
                    break;
                case MIN_AREA:
                    addMin();
                    if (numMin == 2) {
                        if (this.value - value > 0){
                            this.value -= value;
                        }
                        else {
                            reset();
                        }
                    }
                    else {
                        this.value = value;
                        reset();
                        addMin();
                    }
                    break;
                case MULT_AREA:
                    addMult();
                    //TODO: Introduce max value to solve a level
                    if (numMult == 2) {
                        this.value *= value;
                    }
                    else {
                        this.value = value;
                        reset();
                        addMult();
                    }
                    break;
                case DIV_AREA:
                    addDiv();
                    if (numDiv == 2) {
                        if (this.value % value == 0){
                            this.value /= value;
                        }
                        else {
                            reset();
                        }
                    }
                    else {
                        this.value = value;
                        reset();
                        addDiv();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public boolean calculatorInProgress() {
        return numPlus == 1 || numMin == 1 || numMult == 1 || numDiv == 1;
    }

    public boolean calculatorFinished() {
        return numPlus == 2 || numMin == 2 || numMult == 2 || numDiv == 2;
    }

    public boolean calculatorInactive() {
        return numPlus == 0 && numMin == 0 && numMult == 0 && numDiv == 0;
    }

    public int getValue() {
        return value;
    }

}
