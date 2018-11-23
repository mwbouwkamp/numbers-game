package nl.limakajo.numbers.layouts;

import android.graphics.Rect;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.LayoutObjectInterface;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

/**
 * @author M.W.Bouwkamp
 */
public class LevelCompleteLayout extends NoGamePlayLayout {

    public LevelCompleteLayout() {
        //Level complete text
        LayoutObjectInterface levelCompleteText = new TextBox(
                MainActivity.getContext().getString(R.string.empty_text),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().left,
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE,
                        layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().right,
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE + 200),
                Attributes.TEXTBOX_LARGE_PAINT);

        //Stars
        LayoutObjectInterface starsArea = new ScreenArea(
                new Rect(
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().left,
                        levelCompleteText.getArea().bottom + Attributes.MARGE,
                        layoutObjects.get(LayoutElementsKeys.LOGO_AREA).getArea().right,
                        levelCompleteText.getArea().bottom + 6 * Attributes.MARGE),
                Attributes.BG_PAINT);
        LayoutObjectInterface star1Text = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        starsArea.getArea().left,
                        starsArea.getArea().top,
                        starsArea.getArea().left + starsArea.getArea().width() / 3,
                        starsArea.getArea().bottom),
                Attributes.TEXT_BOX_STARS_PAINT);
        LayoutObjectInterface star2Text = new TextBox(
                MainActivity.getContext().getString(R.string.star),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        starsArea.getArea().left + starsArea.getArea().width() / 3,
                        starsArea.getArea().top,
                        starsArea.getArea().left + 2 * starsArea.getArea().width() / 3,
                        starsArea.getArea().bottom),
                Attributes.TEXT_BOX_STARS_PAINT);
        LayoutObjectInterface star3Text = new TextBox(
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
