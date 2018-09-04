package nl.limakajo.numbers.utils;

import android.content.res.Resources;
import android.graphics.Typeface;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.main.MainActivity;

/**
 * @author M.W.Bouwkamp
 */

public class Attributes {

    private static final Resources RESOURCES = MainActivity.getContext().getResources();

    //Dimensions
    public static final int MARGE = 25;
    public static final int GOAL_HEIGHT = (int) (MainActivity.getDevice().getHeight() / 4.5);
    public static final int FOOTER_HEIGHT = 40;

    //Fonts
    public static final Typeface TYPEFACE_CALIBRI = Typeface.createFromAsset(MainActivity.getContext().getAssets(), "fonts/Calibri.ttf");
    public static final Typeface TYPEFACE_NUMBERSGAME = Typeface.createFromAsset(MainActivity.getContext().getAssets(), "fonts/NumbersFont.otf");

    //	GamePlayLayout Attributes
    public static final int BG_COLOR = RESOURCES.getColor(R.color.colorBackground);
    public static final int GOAL_COLOR = RESOURCES.getColor(R.color.colorGoal);
    public static final int FOOTER_COLOR = RESOURCES.getColor(R.color.colorFooter);
    public static final int SHELF_COLOR = RESOURCES.getColor(R.color.colorShelf);
    public static final int PLUS_COLOR = RESOURCES.getColor(R.color.colorPlus);
    public static final int PLUS_COLOR_2 = RESOURCES.getColor(R.color.colorPlus2);
    public static final int MIN_COLOR = RESOURCES.getColor(R.color.colorMin);
    public static final int MIN_COLOR_2 = RESOURCES.getColor(R.color.colorMin2);
    public static final int MULT_COLOR = RESOURCES.getColor(R.color.colorMult);
    public static final int MULT_COLOR_2 = RESOURCES.getColor(R.color.colorMult2);
    public static final int DIV_COLOR = RESOURCES.getColor(R.color.colorDiv);
    public static final int DIV_COLOR_2 = RESOURCES.getColor(R.color.colorDiv2);
    public static final int GAME_OVER_COLOR = RESOURCES.getColor(R.color.colorGameOver);
    public static final int LEVEL_COMPLETE_COLOR = RESOURCES.getColor(R.color.colorLevelComplete);

    //Tile Attributes
    public static final int[] TILE_COLORS = {
            RESOURCES.getColor(R.color.colorTileOne),
            RESOURCES.getColor(R.color.colorTileTwo),
            RESOURCES.getColor(R.color.colorTileThree),
            RESOURCES.getColor(R.color.colorTileFour),
            RESOURCES.getColor(R.color.colorTileFive),
            RESOURCES.getColor(R.color.colorTileSix),
    };
    public static final int TILE_WIDTH = (int) ((MainActivity.getDevice().getWidth() - 9 * MARGE) / 6);
    public static final int[] TILE_XCOORDS = new int[] {
            (int) (0.5 * (TILE_WIDTH) + 2 * MARGE),
            (int) (1.5 * (TILE_WIDTH) + 3 * MARGE),
            (int) (2.5 * (TILE_WIDTH) + 4 * MARGE),
            (int) (3.5 * (TILE_WIDTH) + 5 * MARGE),
            (int) (4.5 * (TILE_WIDTH) + 6 * MARGE),
            (int) (5.5 * (TILE_WIDTH) + 7 * MARGE),
            6   * (TILE_WIDTH) + 8 * MARGE};
    public static final int TILE_YCOORD = 3 * MARGE + GOAL_HEIGHT + TILE_WIDTH /2;
    public static final long TILE_ANIMATION_TIME = 500;
    public enum TextAllignment {
        NOTCENTERED,
        XCENTERED,
        YCENTERED,
        XYCENTERED
    }

    //Wave Attributes
    public static final int WAVE_RED = 200;
    public static final int WAVE_GREEN = 200;
    public static final int WAVE_BLUE = 200;
    public static final int WAVE_ALPHA_START = 180;
    public static final int WAVE_STROKE_START = 3;
    public static final int WAVE_STROKE_END = 60;
    public static final int WAVE_ANIMATION_TIME = 300;
}
