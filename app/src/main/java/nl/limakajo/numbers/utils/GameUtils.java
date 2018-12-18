package nl.limakajo.numbers.utils;

/**
 * @author M.W.Bouwkamp
 */

public class GameUtils {

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

    //LevelStates
    public enum LevelState {
        NEW('N'),
        ACTIVE('A'),
        UPLOAD('U'),
        COMPLETED('C');

        public char asChar() {
            return asChar;
        }

        private final char asChar;

        LevelState(char asChar) {
            this.asChar = asChar;
        }
    }
}
