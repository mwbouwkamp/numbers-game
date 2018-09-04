package nl.limakajo.numbers.utils;

/**
 * @author M.W.Bouwkamp
 */

public class GameUtils {

    //Game contstants
    public final static long TIME_TO_NEW_LIFE = 300000;
    public final static int MAX_NUMLIVES = 15;
    public static final long TIMER = 60000;
    public static final int TIMEPENALTY = 120000;
    public static final int NUMTILES = 6;

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
