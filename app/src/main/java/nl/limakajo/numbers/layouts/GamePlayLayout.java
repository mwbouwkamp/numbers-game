package nl.limakajo.numbers.layouts;

import android.graphics.Rect;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.GameUtils;

/**
 * @author M.W.Bouwkamp
 */

public class GamePlayLayout extends BasicLayout {

	public GamePlayLayout() {
		//Playable area
        LayoutObject playArea = new ScreenArea (
				new Rect(
						Attributes.MARGE,
						Attributes.MARGE,
						MainActivity.getDevice().getWidth() - Attributes.MARGE,
						MainActivity.getDevice().getHeight() - Attributes.MARGE),
				Attributes.NO_DRAW);

        //Header
		LayoutObject headerArea = new ScreenArea (
				new Rect(
						playArea.getArea().left,
						playArea.getArea().top,
						playArea.getArea().right,
						playArea.getArea().top + Attributes.GOAL_HEIGHT),
				Attributes.NO_DRAW);
		LayoutObject goalText = new TextBox(
                MainActivity.getContext().getString(R.string.empty_text),
                Attributes.TextAllignment.XYCENTERED,
                headerArea.getArea(),
                Attributes.TEXTBOX_LARGE_PAINT);
		LayoutObject timerArea = new ScreenArea (
				new Rect(
						headerArea.getArea().left + (headerArea.getArea().width() - Attributes.GOAL_HEIGHT) / 2,
						headerArea.getArea().top,
						headerArea.getArea().left + (headerArea.getArea().width() + Attributes.GOAL_HEIGHT) / 2,
						headerArea.getArea().bottom),
				Attributes.NO_DRAW);
		LayoutObject numStarsText = new TextBox(
                "A",
                Attributes.TextAllignment.XLEFT_YCENTERED,
                new Rect(
                        headerArea.getArea().left,
                        headerArea.getArea().top,
                        timerArea.getArea().left,
                        headerArea.getArea().top + Attributes.GOAL_HEIGHT / 3),
                Attributes.TEXTBOX_NUMLIVES_PAINT);
		LayoutObject numLivesText = new TextBox(
                "B",
                Attributes.TextAllignment.XRIGHT_YCENTERED,
                new Rect(
                        timerArea.getArea().right,
                        headerArea.getArea().top,
                        headerArea.getArea().right,
                        headerArea.getArea().top + Attributes.GOAL_HEIGHT / 3),
                Attributes.TEXTBOX_NUMLIVES_PAINT);

        //Footer
		LayoutObject footerText = new TextBox(
                MainActivity.getContext().getString(R.string.empty_text),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        playArea.getArea().left,
                        playArea.getArea().bottom - Attributes.FOOTER_HEIGHT,
                        playArea.getArea().right,
                        playArea.getArea().bottom),
                Attributes.TEXTBOX_SMALL_PAINT);

        //Shelf
		LayoutObject shelfArea = new ScreenArea(
                new Rect(
                        playArea.getArea().left,
                        headerArea.getArea().bottom + Attributes.MARGE,
                        playArea.getArea().right,
                        (int) (headerArea.getArea().bottom + 2.0 * Attributes.MARGE + (playArea.getArea().right - playArea.getArea().left - Attributes.MARGE) / GameUtils.NUMTILES) - Attributes.MARGE),
                Attributes.NO_DRAW);

        //Operators
		LayoutObject operatorsArea = new ScreenArea(
                new Rect(
                        playArea.getArea().left,
                        shelfArea.getArea().bottom + Attributes.MARGE,
                        playArea.getArea().right,
                        footerText.getArea().top - Attributes.MARGE),
                Attributes.NO_DRAW);
		LayoutObject plusArea = new ScreenArea(
                new Rect(
                        operatorsArea.getArea().left,
                        operatorsArea.getArea().top,
                        operatorsArea.getArea().width() / 2 + Attributes.MARGE / 2,
                        operatorsArea.getArea().top + operatorsArea.getArea().height() / 2 - Attributes.MARGE / 2),
                Attributes.PLUS_PAINT);
		LayoutObject plusArea2 = new ScreenArea(
                new Rect(
                        plusArea.getArea().left,
                        plusArea.getArea().top + 10 * (plusArea.getArea().bottom - plusArea.getArea().top)/11,
                        plusArea.getArea().right,
                        plusArea.getArea().bottom),
                Attributes.PLUS_PAINT_2);
		LayoutObject minArea = new ScreenArea(
                new Rect(
                        plusArea.getArea().right + Attributes.MARGE,
                        operatorsArea.getArea().top,
                        operatorsArea.getArea().right,
                        plusArea.getArea().bottom),
                Attributes.MIN_PAINT);
		LayoutObject minArea2 = new ScreenArea(
                new Rect(
                        minArea.getArea().left,
                        minArea.getArea().top + 10 * (minArea.getArea().bottom - minArea.getArea().top)/11,
                        minArea.getArea().right,
                        minArea.getArea().bottom),
                Attributes.MIN_PAINT_2);
		LayoutObject multArea = new ScreenArea(
                new Rect(
                        plusArea.getArea().left,
                        plusArea.getArea().bottom + Attributes.MARGE,
                        plusArea.getArea().right,
                        operatorsArea.getArea().bottom),
                Attributes.MULT_PAINT);
		LayoutObject multArea2 = new ScreenArea(
                new Rect(
                        multArea.getArea().left,
                        multArea.getArea().top + 10 * (multArea.getArea().bottom - multArea.getArea().top)/11,
                        multArea.getArea().right,
                        multArea.getArea().bottom),
                Attributes.MULT_PAINT_2);
		LayoutObject divArea = new ScreenArea(
                new Rect(
                        minArea.getArea().left,
                        multArea.getArea().top,
                        minArea.getArea().right,
                        multArea.getArea().bottom),
                Attributes.DIV_PAINT);
		LayoutObject divArea2 = new ScreenArea(
                new Rect(
                        divArea.getArea().left,
                        divArea.getArea().top + 10 * (divArea.getArea().bottom - divArea.getArea().top)/11,
                        divArea.getArea().right,
                        divArea.getArea().bottom),
                Attributes.DIV_PAINT_2);
		LayoutObject plusText = new TextBox(
                MainActivity.getContext().getString(R.string.gameplay_plus_sign),
                Attributes.TextAllignment.XYCENTERED,
                plusArea.getArea(),
                Attributes.TEXT_BOX_OPERATOR_PAINT);
		LayoutObject minText = new TextBox(
                MainActivity.getContext().getString(R.string.gameplay_min_sign),
                Attributes.TextAllignment.XYCENTERED,
                minArea.getArea(),
                Attributes.TEXT_BOX_OPERATOR_PAINT);
		LayoutObject multText = new TextBox(
                MainActivity.getContext().getString(R.string.gameplay_mult_sign),
                Attributes.TextAllignment.XYCENTERED,
                multArea.getArea(),
                Attributes.TEXT_BOX_OPERATOR_PAINT);
		LayoutObject divText = new TextBox(
                MainActivity.getContext().getString(R.string.gameplay_div_sign),
                Attributes.TextAllignment.XYCENTERED,
                divArea.getArea(),
                Attributes.TEXT_BOX_OPERATOR_PAINT);

		layoutObjects.put(LayoutElementsKeys.PLAY_AREA, playArea);
		layoutObjects.put(LayoutElementsKeys.HEADER_AREA, headerArea);
		layoutObjects.put(LayoutElementsKeys.GOAL_TEXT, goalText);
		layoutObjects.put(LayoutElementsKeys.TIMER_AREA, timerArea);
		layoutObjects.put(LayoutElementsKeys.NUM_LIVES_TEXT, numLivesText);
		layoutObjects.put(LayoutElementsKeys.NUM_STARS_TEXT, numStarsText);
		layoutObjects.put(LayoutElementsKeys.FOOTER_TEXT, footerText);
		layoutObjects.put(LayoutElementsKeys.SHELF_AREA, shelfArea);
        layoutObjects.put(LayoutElementsKeys.OPERATORS_AREA, operatorsArea);
        layoutObjects.put(LayoutElementsKeys.PLUS_AREA, plusArea);
        layoutObjects.put(LayoutElementsKeys.PLUS2_AREA, plusArea2);
        layoutObjects.put(LayoutElementsKeys.MIN_AREA, minArea);
        layoutObjects.put(LayoutElementsKeys.MIN2_AREA, minArea2);
        layoutObjects.put(LayoutElementsKeys.MULT_AREA, multArea);
        layoutObjects.put(LayoutElementsKeys.MULT2_AREA, multArea2);
        layoutObjects.put(LayoutElementsKeys.DIV_AREA, divArea);
        layoutObjects.put(LayoutElementsKeys.DIV2_AREA, divArea2);
        layoutObjects.put(LayoutElementsKeys.PLUS_TEXT, plusText);
        layoutObjects.put(LayoutElementsKeys.MIN_TEXT, minText);
        layoutObjects.put(LayoutElementsKeys.MULT_TEXT, multText);
        layoutObjects.put(LayoutElementsKeys.DIV_TEXT, divText);
	}
}
