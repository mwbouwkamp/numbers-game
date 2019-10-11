package nl.limakajo.numbers.layouts;

import android.graphics.Point;
import android.util.Size;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

/**
 * Class that represents the LevelCompleteLayout
 *
 * @author M.W.Bouwkamp
 */
public class LevelCompleteLayout extends LogoWithAreaForTextLayout {

    /**
     * Constructs a LevelCompleteLayout
     */
    public LevelCompleteLayout() {
        //Level complete text
        LayoutObject levelCompleteText = new TextBox(
                MainActivity.getContext().getString(R.string.empty_text),
                Attributes.TextAllignment.XYCENTERED,
                new Point(
                        layoutObjects.get(LayoutElementsKeys.MAINTEXT_AREA).getArea().left,
                        layoutObjects.get(LayoutElementsKeys.MAINTEXT_AREA).getArea().top),
                new Size(
                        layoutObjects.get(LayoutElementsKeys.MAINTEXT_AREA).getArea().width(),
                        layoutObjects.get(LayoutElementsKeys.MAINTEXT_AREA).getArea().height()
                ),
                Attributes.TEXTBOX_NORMAL_PAINT);

        //Stars
        LayoutObject starsArea = new ScreenArea(
                new Point(
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().left,
                        levelCompleteText.getArea().bottom + Attributes.MARGIN),
                new Size(
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().width(),
                        5 * Attributes.MARGIN),
                Attributes.BG_PAINT);
        LayoutObject star1StrokeText = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Point(
                        starsArea.getArea().left,
                        starsArea.getArea().top),
                new Size(
                        starsArea.getArea().width() / 3,
                        starsArea.getArea().height()),
                Attributes.STARS_PAINT_STROKE_START);
        LayoutObject star1FillText = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Point(
                        star1StrokeText.getArea().left,
                        star1StrokeText.getArea().top),
                new Size(
                        star1StrokeText.getArea().width(),
                        star1StrokeText.getArea().height()),
                Attributes.STARS_PAINT_FILL_START);
        LayoutObject star2StrokeText = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Point(
                        starsArea.getArea().left + starsArea.getArea().width() / 3,
                        starsArea.getArea().top),
                new Size(
                        starsArea.getArea().width() / 3,
                        starsArea.getArea().height()),
                Attributes.STARS_PAINT_STROKE_START);
        LayoutObject star2FillText = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Point(
                        star2StrokeText.getArea().left,
                        star2StrokeText.getArea().top),
                new Size(
                        star2StrokeText.getArea().width(),
                        star2StrokeText.getArea().height()),
                Attributes.STARS_PAINT_FILL_START);
        LayoutObject star3StrokeText = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Point(
                        starsArea.getArea().left + 2 * starsArea.getArea().width() / 3,
                        starsArea.getArea().top),
                new Size(
                        starsArea.getArea().width() / 3,
                        starsArea.getArea().height()),
                Attributes.STARS_PAINT_STROKE_START);
        LayoutObject star3FillText = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Point(
                        star3StrokeText.getArea().left,
                        star3StrokeText.getArea().top),
                new Size(
                        star3StrokeText.getArea().width(),
                        star3StrokeText.getArea().height()),
                Attributes.STARS_PAINT_FILL_START);
        layoutObjects.put(LayoutElementsKeys.LEVELCOMPLETE_TEXT, levelCompleteText);
        layoutObjects.put(LayoutElementsKeys.STARS_AREA, starsArea);
        layoutObjects.put(LayoutElementsKeys.STAR1_STROKE_TEXT, star1StrokeText);
        layoutObjects.put(LayoutElementsKeys.STAR1_FILL_TEXT, star1FillText);
        layoutObjects.put(LayoutElementsKeys.STAR2_STROKE_TEXT, star2StrokeText);
        layoutObjects.put(LayoutElementsKeys.STAR2_FILL_TEXT, star2FillText);
        layoutObjects.put(LayoutElementsKeys.STAR3_STROKE_TEXT, star3StrokeText);
        layoutObjects.put(LayoutElementsKeys.STAR3_FILL_TEXT, star3FillText);

    }
}
