package nl.limakajo.numbers.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.main.MainActivity;

/**
 * @author M.W.Bouwkamp
 */

public class Attributes {

    private static final Resources RESOURCES = MainActivity.getContext().getResources();

    //Dimensions
    public static final int MARGIN = 25;
    public static final int GOAL_HEIGHT = (int) (MainActivity.getDevice().getHeight() / 4.5);
    public static final int FOOTER_HEIGHT = 40;

    //Fonts
    private static final Typeface TYPEFACE_CALIBRI = Typeface.createFromAsset(MainActivity.getContext().getAssets(), "fonts/Calibri.ttf");
    private static final Typeface TYPEFACE_NUMBERSGAME = Typeface.createFromAsset(MainActivity.getContext().getAssets(), "fonts/NumbersFont.otf");

    //	Layout Attributes
    public static final int BG_COLOR = RESOURCES.getColor(R.color.colorBackground);
    private static final int FONT_COLOR = RESOURCES.getColor(R.color.colorBasicText);
    private static final int PLUS_COLOR = RESOURCES.getColor(R.color.colorPlus);
    private static final int PLUS_COLOR_2 = RESOURCES.getColor(R.color.colorPlus2);
    private static final int MIN_COLOR = RESOURCES.getColor(R.color.colorMin);
    private static final int MIN_COLOR_2 = RESOURCES.getColor(R.color.colorMin2);
    private static final int MULT_COLOR = RESOURCES.getColor(R.color.colorMult);
    private static final int MULT_COLOR_2 = RESOURCES.getColor(R.color.colorMult2);
    private static final int DIV_COLOR = RESOURCES.getColor(R.color.colorDiv);
    private static final int DIV_COLOR_2 = RESOURCES.getColor(R.color.colorDiv2);
    private static final int STARS_COLOR = RESOURCES.getColor(R.color.colorDiv);

    public static final Paint BG_PAINT = getBackGroundPaint();
    public static final Paint TEXTBOX_NORMAL_PAINT = getTextBoxNormalFontPaint();
    public static final Paint TEXTBOX_NUMLIVES_PAINT = getTextBoxNumbersGameFontPaint();
    public static final Paint TEXT_BOX_OPERATOR_PAINT = getTextBoxOperatorPaint();
    public static final Paint PLUS_PAINT = getPlusPaint();
    public static final Paint PLUS_PAINT_2 = getPlus2Paint();
    public static final Paint MIN_PAINT = getMinPaint();
    public static final Paint MIN_PAINT_2 = getMin2Paint();
    public static final Paint MULT_PAINT = getMultPaint();
    public static final Paint MULT_PAINT_2 = getMult2Paint();
    public static final Paint DIV_PAINT = getDivPaint();
    public static final Paint DIV_PAINT_2 = getDiv2Paint();
    public static final Paint NO_DRAW = new Paint();

    public static final float RELATIVE_SIZE_TEXTBOX = 0.6f;

    //Tile Attributes
    public static final int[] TILE_COLORS = {
            RESOURCES.getColor(R.color.colorTileOne),
            RESOURCES.getColor(R.color.colorTileTwo),
            RESOURCES.getColor(R.color.colorTileThree),
            RESOURCES.getColor(R.color.colorTileFour),
            RESOURCES.getColor(R.color.colorTileFive),
            RESOURCES.getColor(R.color.colorTileSix),
    };
    public static final int TILE_WIDTH = (MainActivity.getDevice().getWidth() - 9 * MARGIN) / 6;
    public static final int[] TILE_XCOORDS = new int[] {
            (int) (0.5 * (TILE_WIDTH) + 2 * MARGIN),
            (int) (1.5 * (TILE_WIDTH) + 3 * MARGIN),
            (int) (2.5 * (TILE_WIDTH) + 4 * MARGIN),
            (int) (3.5 * (TILE_WIDTH) + 5 * MARGIN),
            (int) (4.5 * (TILE_WIDTH) + 6 * MARGIN),
            (int) (5.5 * (TILE_WIDTH) + 7 * MARGIN),
            6   * (TILE_WIDTH) + 8 * MARGIN};
    public static final int TILE_YCOORD = 3 * MARGIN + GOAL_HEIGHT + TILE_WIDTH /2;
    public static final long TILE_ANIMATION_TIME = 750;

    public enum TextAllignment {
        XCENTERED_YTOP,
        XLEFT_YCENTERED,
        XYCENTERED,
        XRIGHT_YCENTERED
    }

    //Wave Attributes
    public static final float RADIUS = Attributes.TILE_WIDTH / 2;
    public static final int WAVE_RED = 200;
    public static final int WAVE_GREEN = 200;
    public static final int WAVE_BLUE = 200;
    public static final int WAVE_ALPHA_START = 180;
    public static final int WAVE_ALPHA_END = 0;
    public static final int WAVE_STROKE_START = 3;
    public static final int WAVE_STROKE_END = 60;
    public static final int WAVE_ANIMATION_TIME = 300;
    public static final Paint WAVE_PAINT_START = getStartingWavePaint();
    public static final Paint WAVE_PAINT_END = getEndWavePaint();

    //Stars Attributes
    public static final int STARS_ALPHA_START = 40;
    public static final int STARS_ALPHA_END = 255;
    public static final Paint STARS_PAINT_STROKE_START = getStarsPaintStrokeStart();
    public static final Paint STARS_PAINT_STROKE_END = getStarsPaintStrokeEnd();
    public static final Paint STARS_PAINT_FILL_START = getStarsPaintFillStart();
    public static final Paint STARS_PAINT_FILL_END = getStarsPaintFillEnd();
    public static final long STAR_ANIMATION_TIME = 1000;
    public static final long STAR_ANIMATION_DELAY = 900;
    public static final long STAR_FILL_ANIMATION_DELAY = 800;


    @NonNull
    private static Paint getBasicPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setAlpha(100);
        return paint;
    }

    @NonNull
    private static Paint getFillPaint() {
        Paint paint = getBasicPaint();
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @NonNull
    private static Paint getStrokePaint() {
        Paint paint = getBasicPaint();
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    @NonNull
    private static Paint getTextBoxNormalFontPaint() {
        Paint paint = getFillPaint();
        paint.setTypeface(Attributes.TYPEFACE_CALIBRI);
        paint.setColor(Attributes.FONT_COLOR);
        return paint;
    }

    @NonNull
    private static Paint getTextBoxNumbersGameFontPaint() {
        Paint paint = getFillPaint();
        paint.setTypeface(Attributes.TYPEFACE_NUMBERSGAME);
        paint.setColor(Attributes.FONT_COLOR);
        return paint;
    }

    @NonNull
    private static Paint getTextBoxOperatorPaint() {
        Paint paint = getStrokePaint();
        paint.setTypeface(Attributes.TYPEFACE_NUMBERSGAME);
        paint.setColor(Attributes.BG_COLOR);
        paint.setStrokeWidth(7);
        return paint;
    }

    @NonNull
    private static Paint getStarsBasicStrokePaint() {
        Paint paint = getStrokePaint();
        paint.setTypeface(Attributes.TYPEFACE_NUMBERSGAME);
        paint.setColor(Attributes.STARS_COLOR);
        return paint;
    }

    @NonNull
    private static Paint getStarsBasicFillPaint() {
        Paint paint = getFillPaint();
        paint.setTypeface(Attributes.TYPEFACE_NUMBERSGAME);
        paint.setColor(Attributes.STARS_COLOR);
        return paint;
    }

    @NonNull
    private static Paint getStarsPaintStrokeStart() {
        Paint paint = getStarsBasicStrokePaint();
        paint.setAlpha(STARS_ALPHA_START);
        return paint;
    }


    @NonNull
    private static Paint getStarsPaintStrokeEnd() {
        Paint paint = getStarsBasicStrokePaint();
        paint.setAlpha(STARS_ALPHA_END);
        return paint;
    }

    @NonNull
    private static Paint getStarsPaintFillStart() {
        Paint paint = getStarsBasicFillPaint();
        paint.setAlpha(STARS_ALPHA_START);
        return paint;
    }


    @NonNull
    private static Paint getStarsPaintFillEnd() {
        Paint paint = getStarsBasicFillPaint();
        paint.setAlpha(STARS_ALPHA_END);
        return paint;
    }

    @NonNull
    private static Paint getBackGroundPaint() {
        Paint paint = getFillPaint();
        paint.setColor(Attributes.BG_COLOR);
        paint.setShader(new RadialGradient(MainActivity.getDevice().getWidth() / 2,
                MainActivity.getDevice().getHeight() / 2,
                Math.max(MainActivity.getDevice().getHeight(), MainActivity.getDevice().getWidth()),
                new int[]{Attributes.BG_COLOR, Color.BLACK},
                null,
                Shader.TileMode.MIRROR));
        return paint;
    }

    @NonNull
    private static Paint getPlusPaint() {
        Paint paint = getFillPaint();
        paint.setColor(Attributes.PLUS_COLOR);
        return paint;
    }

    @NonNull
    private static Paint getPlus2Paint() {
        Paint paint = getFillPaint();
        paint.setColor(Attributes.PLUS_COLOR_2);
        return paint;
    }

    @NonNull
    private static Paint getMinPaint() {
        Paint paint = getFillPaint();
        paint.setColor(Attributes.MIN_COLOR);
        return paint;
    }

    @NonNull
    private static Paint getMin2Paint() {
        Paint paint = getFillPaint();
        paint.setColor(Attributes.MIN_COLOR_2);
        return paint;
    }

    @NonNull
    private static Paint getMultPaint() {
        Paint paint = getFillPaint();
        paint.setColor(Attributes.MULT_COLOR);
        return paint;
    }

    @NonNull
    private static Paint getMult2Paint() {
        Paint paint = getFillPaint();
        paint.setColor(Attributes.MULT_COLOR_2);
        return paint;
    }

    @NonNull
    private static Paint getDivPaint() {
        Paint paint = getFillPaint();
        paint.setColor(Attributes.DIV_COLOR);
        return paint;
    }

    @NonNull
    private static Paint getDiv2Paint() {
        Paint paint = getFillPaint();
        paint.setColor(Attributes.DIV_COLOR_2);
        return paint;
    }

    public static Paint getStartingWavePaint() {
        Paint paint = getStrokePaint();
        paint.setARGB(Attributes.WAVE_ALPHA_START, Attributes.WAVE_RED, Attributes.WAVE_GREEN, Attributes.WAVE_BLUE);
        paint.setStrokeWidth(Attributes.WAVE_STROKE_START);
        return paint;
    }

    public static Paint getEndWavePaint() {
        Paint paint = getStrokePaint();
        paint.setARGB(Attributes.WAVE_ALPHA_END, Attributes.WAVE_RED, Attributes.WAVE_GREEN, Attributes.WAVE_BLUE);
        paint.setStrokeWidth(Attributes.WAVE_STROKE_END);
        return paint;
    }

}
