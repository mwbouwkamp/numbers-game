package nl.limakajo.numbers.layouts;

import android.graphics.Rect;

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
                layoutObjects.get(LayoutElementsKeys.MAINTEXT_AREA).getArea(),
                Attributes.TEXTBOX_NORMAL_PAINT);

        //Stars
        LayoutObject starsArea = new ScreenArea(
                new Rect(
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().left,
                        levelCompleteText.getArea().bottom + Attributes.MARGE,
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().right,
                        levelCompleteText.getArea().bottom + 6 * Attributes.MARGE),
                Attributes.BG_PAINT);
        LayoutObject star1Text = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        starsArea.getArea().left,
                        starsArea.getArea().top,
                        starsArea.getArea().left + starsArea.getArea().width() / 3,
                        starsArea.getArea().bottom),
                Attributes.TEXT_BOX_STARS_PAINT);
        LayoutObject star2Text = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        starsArea.getArea().left + starsArea.getArea().width() / 3,
                        starsArea.getArea().top,
                        starsArea.getArea().left + 2 * starsArea.getArea().width() / 3,
                        starsArea.getArea().bottom),
                Attributes.TEXT_BOX_STARS_PAINT);
        LayoutObject star3Text = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        starsArea.getArea().left + 2 * starsArea.getArea().width() / 3,
                        starsArea.getArea().top,
                        starsArea.getArea().right,
                        starsArea.getArea().bottom),
                Attributes.TEXT_BOX_STARS_PAINT);
        layoutObjects.put(LayoutElementsKeys.LEVELCOMPLETE_TEXT, levelCompleteText);
        layoutObjects.put(LayoutElementsKeys.STARS_AREA, starsArea);
        layoutObjects.put(LayoutElementsKeys.STAR1_TEXT, star1Text);
        layoutObjects.put(LayoutElementsKeys.STAR2_TEXT, star2Text);
        layoutObjects.put(LayoutElementsKeys.STAR3_TEXT, star3Text);

    }
}
