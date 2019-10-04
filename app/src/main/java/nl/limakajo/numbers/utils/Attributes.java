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
    public static final int MARGE = 25;
    public static final int GOAL_HEIGHT = (int) (MainActivity.getDevice().getHeight() / 4.5);
    public static final int FOOTER_HEIGHT = 40;

    //Fonts
    private static final Typeface TYPEFACE_CALIBRI = Typeface.createFromAsset(MainActivity.getContext().getAssets(), "fonts/Calibri.ttf");
    private static final Typeface TYPEFACE_NUMBERSGAME = Typeface.createFromAsset(MainActivity.getContext().getAssets(), "fonts/NumbersFont.otf");

    //	Layout Attributes
    public static final int BG_COLOR = RESOURCES.getColor(R.color.colorBackground);
    private static final int FONT_COLOR = RESOURCES.getColor(R.color.colorGoal);
    private static final int PLUS_COLOR = RESOURCES.getColor(R.color.colorPlus);
    private static final int PLUS_COLOR_2 = RESOURCES.getColor(R.color.colorPlus2);
    private static final int MIN_COLOR = RESOURCES.getColor(R.color.colorMin);
    private static final int MIN_COLOR_2 = RESOURCES.getColor(R.color.colorMin2);
    private static final int MULT_COLOR = RESOURCES.getColor(R.color.colorMult);
    private static final int MULT_COLOR_2 = RESOURCES.getColor(R.color.colorMult2);
    private static final int DIV_COLOR = RESOURCES.getColor(R.color.colorDiv);
    private static final int DIV_COLOR_2 = RESOURCES.getColor(R.color.colorDiv2);
    private static final int STARS_COLOR = RESOURCES.getColor(R.color.colorDiv);
    public static final int STARS_DARK_ALPHA = 40;

    public static final Paint BG_PAINT = new Paint(getBackGroundPaint());
    public static final Paint TEXTBOX_NORMAL_PAINT = new Paint(getTextBoxNormalFontPaint());
    public static final Paint TEXTBOX_NUMLIVES_PAINT = new Paint(getTextBoxNumbersGameFontPaint());
    public static final Paint TEXT_BOX_OPERATOR_PAINT = new Paint(getTextBoxOperatorPaint());
    public static final Paint TEXT_BOX_STARS_PAINT = new Paint(getTextBoxStarsPaint());
    public static final Paint PLUS_PAINT = new Paint(getPlusPaint());
    public static final Paint PLUS_PAINT_2 = new Paint(getPlus2Paint());
    public static final Paint MIN_PAINT = new Paint(getMinPaint());
    public static final Paint MIN_PAINT_2 = new Paint(getMin2Paint());
    public static final Paint MULT_PAINT = new Paint(getMultPaint());
    public static final Paint MULT_PAINT_2 = new Paint(getMult2Paint());
    public static final Paint DIV_PAINT = new Paint(getDivPaint());
    public static final Paint DIV_PAINT_2 = new Paint(getDiv2Paint());
    public static final Paint NO_DRAW = new Paint();

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
    public static final long TILE_ANIMATION_TIME = 750;
    public enum TextAllignment {
        XCENTERED_YTOP,
        XLEFT_YCENTERED,
        XYCENTERED,
        XRIGHT_YCENTERED
    }

    //Wave Attributes
    public static final int WAVE_RED = 200;
    public static final int WAVE_GREEN = 200;
    public static final int WAVE_BLUE = 200;
    public static final int WAVE_ALPHA_START = 180;
    public static final int WAVE_STROKE_START = 3;
    public static final int WAVE_STROKE_END = 60;
    public static final int WAVE_ANIMATION_TIME = 300;

    //LevelCompleteScene Attributes
    public static final long LEVELCOMPLETE_ANIMATION_TIME = 1000;
    public static final long LEVELCOMPLETE_TIME_BETWEEN_ANIMATIONS = 900;

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
    private static Paint getTextBoxStarsPaint() {
        Paint paint = getStrokePaint();
        paint.setTypeface(Attributes.TYPEFACE_NUMBERSGAME);
        paint.setColor(Attributes.STARS_COLOR);
        paint.setAlpha(40);
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

}
