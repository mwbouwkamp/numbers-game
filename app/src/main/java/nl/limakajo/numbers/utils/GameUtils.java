package nl.limakajo.numbers.utils;

import nl.limakajo.numbers.main.MainActivity;

/**
 * Created by mwbou on 30-12-2017.
 */

public class GameUtils {

    //Game contstants
    public final static long TIME_TO_NEW_LIFE = 300000;
    public final static int MAX_NUMLIVES = 15;
    public static final long TIMER = 60000;
    public static final int TIMEPENALTY = 120000;
    public static final int NUMTILES = 6;
    static final int[] NUMBERS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 75, 100};

    //GameStates
    public enum GameState {
        MENU_STATE {
            public String toString(){
                return "MenuState";
            }
        },
        GAME_STATE {
            public String toString(){
                return "GameState";
            }
        },
        LEVEL_COMPLETE_STATE {
            public String toString(){
                return "LevelComplete";
            }
        },
        GAME_OVER_STATE {
            public String toString(){
                return "GameOver";
            }
        }
    }
}
