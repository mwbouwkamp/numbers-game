package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Layout;

import java.util.EnumMap;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.data.NumbersContract;
import nl.limakajo.numbers.gameObjects.GameObject;
import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;

/**
 * @author M.W.Bouwkamp
 */
public class LevelCompleteLayout extends NoGamePlayLayout {

    private EnumMap<LayoutElements, LayoutObject> layoutObjects;

    public LevelCompleteLayout() {
        layoutObjects = new EnumMap<>(super.getLayoutObjects());

        //Level complete text
        LayoutObject levelCompleteText = new TextBox(
                MainActivity.getContext().getString(R.string.empty_text),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        layoutObjects.get(LayoutElements.FULLSCREEN).getArea().left,
                        layoutObjects.get(LayoutElements.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE,
                        layoutObjects.get(LayoutElements.FULLSCREEN).getArea().right,
                        layoutObjects.get(LayoutElements.LOGO_AREA).getArea().bottom + 3 * Attributes.MARGE + 200),
                Attributes.TEXTBOX_LARGE_PAINT);

        //Stars
        LayoutObject starsArea = new ScreenArea(
                new Rect(
                        layoutObjects.get(LayoutElements.LOGO_AREA).getArea().left,
                        levelCompleteText.getArea().bottom + Attributes.MARGE,
                        layoutObjects.get(LayoutElements.LOGO_AREA).getArea().right,
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
        layoutObjects.put(LayoutElements.LEVELCOMPLETE_TEXT, levelCompleteText);
        layoutObjects.put(LayoutElements.STARS_AREA, starsArea);
        layoutObjects.put(LayoutElements.STAR1_TEXT, star1Text);
        layoutObjects.put(LayoutElements.STAR2_TEXT, star2Text);
        layoutObjects.put(LayoutElements.STAR3_TEXT, star3Text);

    }

    public EnumMap<LayoutElements, LayoutObject> getLayoutObjects() {
        return layoutObjects;
    }

    public TextBox getTextBox(LayoutElements layoutElement) {
        return (TextBox) layoutObjects.get(layoutElement);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (LayoutObject layoutObject : layoutObjects.values()) {
            layoutObject.draw(canvas);
        }
    }
}
