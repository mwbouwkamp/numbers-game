package nl.limakajo.numbers.layouts;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Size;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.LayoutObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numberslib.utils.GameConstants;

/**
 * Class that represents the GamePlayLayout
 *
 * @author M.W.Bouwkamp
 */
public class GamePlayLayout extends BasicLayout {

	/**
	 * Constructs GamePlayLayout
	 */
	public GamePlayLayout() {
		//Playable area
        LayoutObject playArea = new ScreenArea (
				new Point(
						Attributes.MARGIN,
						Attributes.MARGIN),
				new Size(
						MainActivity.getDevice().getWidth() - 2 * Attributes.MARGIN,
						MainActivity.getDevice().getHeight() - 2 * Attributes.MARGIN),
				Attributes.NO_DRAW);

        //Header
		LayoutObject headerArea = new ScreenArea (
				new Point(
						playArea.getArea().left,
						playArea.getArea().top),
				new Size(
						playArea.getArea().width(),
						Attributes.GOAL_HEIGHT),
				Attributes.NO_DRAW);
		LayoutObject goalText = new TextBox(
                MainActivity.getContext().getString(R.string.empty_text),
                Attributes.TextAllignment.XYCENTERED,
                new Point(
                		layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().left,
						headerArea.getArea().top + headerArea.getArea().height() / 3 + Attributes.MARGIN),
				new Size (
						layoutObjects.get(LayoutElementsKeys.FULLSCREEN).getArea().width(),
						headerArea.getArea().height() / 3 - 2 * Attributes.MARGIN),
                Attributes.TEXTBOX_NORMAL_PAINT);
		LayoutObject timerArea = new ScreenArea (
				new Point(
						headerArea.getArea().left + (headerArea.getArea().width() - Attributes.GOAL_HEIGHT) / 2,
						headerArea.getArea().top),
				new Size(
						Attributes.GOAL_HEIGHT,
						Attributes.GOAL_HEIGHT),
				Attributes.NO_DRAW);
		LayoutObject numStarsText = new TextBox(
                "A",
                Attributes.TextAllignment.XLEFT_YCENTERED,
                new Point(
                        headerArea.getArea().left,
                        headerArea.getArea().top),
				new Size(
                        timerArea.getArea().left - headerArea.getArea().left,
                        Attributes.GOAL_HEIGHT / 6),
                Attributes.TEXTBOX_NUMLIVES_PAINT);
		LayoutObject numLivesText = new TextBox(
                "B",
                Attributes.TextAllignment.XRIGHT_YCENTERED,
                new Point(
                        timerArea.getArea().right,
                        headerArea.getArea().top),
				new Size(
                        headerArea.getArea().right - timerArea.getArea().right,
                        Attributes.GOAL_HEIGHT / 6),
                Attributes.TEXTBOX_NUMLIVES_PAINT);

		//TilePool
		LayoutObject shelfArea = new ScreenArea(
				new Point(
						playArea.getArea().left,
						headerArea.getArea().bottom + Attributes.MARGIN),
				new Size(
						playArea.getArea().width(),
						(playArea.getArea().width() - Attributes.MARGIN) / GameConstants.NUMTILES),
				Attributes.NO_DRAW);

		//Footer
		LayoutObject footerText = new TextBox(
                MainActivity.getContext().getString(R.string.empty_text),
                Attributes.TextAllignment.XYCENTERED,
                new Point(
                        playArea.getArea().left,
                        playArea.getArea().bottom - Attributes.FOOTER_HEIGHT),
				new Size(
                        playArea.getArea().width(),
                        Attributes.FOOTER_HEIGHT),
                Attributes.TEXTBOX_NORMAL_PAINT);

        //Operators
		LayoutObject operatorsArea = new ScreenArea(
                new Point(
                        playArea.getArea().left,
                        shelfArea.getArea().bottom + Attributes.MARGIN),
				new Size(
                        playArea.getArea().width(),
                        footerText.getArea().top - shelfArea.getArea().bottom - 2 * Attributes.MARGIN),
                Attributes.NO_DRAW);
		LayoutObject plusArea = new ScreenArea(
                new Point(
                        operatorsArea.getArea().left,
                        operatorsArea.getArea().top),
				new Size(
						(operatorsArea.getArea().width() - Attributes.MARGIN) / 2,
						(operatorsArea.getArea().height() - Attributes.MARGIN) / 2),
                Attributes.PLUS_PAINT);
		LayoutObject plusArea2 = new ScreenArea(
                new Point(
                        plusArea.getArea().left,
                        plusArea.getArea().top + (10 * plusArea.getArea().height()) / 11),
				new Size(
                        plusArea.getArea().width(),
						plusArea.getArea().height() / 11),
                Attributes.PLUS_PAINT_2);
		LayoutObject minArea = new ScreenArea(
                new Point(
                        plusArea.getArea().right + Attributes.MARGIN,
                        operatorsArea.getArea().top),
				new Size(
						plusArea.getArea().width(),
						plusArea.getArea().height()),
                Attributes.MIN_PAINT);
		LayoutObject minArea2 = new ScreenArea(
                new Point(
                        minArea.getArea().left,
                        minArea.getArea().top + (10 * minArea.getArea().height()) / 11),
				new Size(
						plusArea.getArea().width(),
						plusArea.getArea().height() / 11),
                Attributes.MIN_PAINT_2);
		LayoutObject multArea = new ScreenArea(
                new Point(
                        plusArea.getArea().left,
                        plusArea.getArea().bottom + Attributes.MARGIN),
				new Size(
						plusArea.getArea().width(),
						plusArea.getArea().height()),
                Attributes.MULT_PAINT);
		LayoutObject multArea2 = new ScreenArea(
                new Point(
                        multArea.getArea().left,
                        multArea.getArea().top + (10 * multArea.getArea().height()) / 11),
				new Size(
						plusArea.getArea().width(),
						plusArea.getArea().height() / 11),
                Attributes.MULT_PAINT_2);
		LayoutObject divArea = new ScreenArea(
                new Point(
                        minArea.getArea().left,
                        multArea.getArea().top),
				new Size(
						plusArea.getArea().width(),
						plusArea.getArea().height()),
                Attributes.DIV_PAINT);
		LayoutObject divArea2 = new ScreenArea(
                new Point(
                        divArea.getArea().left,
                        divArea.getArea().top + (10 * divArea.getArea().height()) / 11),
				new Size(
						plusArea.getArea().width(),
						plusArea.getArea().height() / 11),
                Attributes.DIV_PAINT_2);
		Rect plusTextRect = getRelativeRect(plusArea.getArea(), Attributes.RELATIVE_SIZE_TEXTBOX);
		LayoutObject plusText = new TextBox(
				MainActivity.getContext().getString(R.string.gameplay_plus_sign),
				Attributes.TextAllignment.XYCENTERED,
				new Point(
						plusTextRect.left,
						plusTextRect.top),
				new Size(
						plusTextRect.width(),
						plusTextRect.height()),
				Attributes.TEXT_BOX_OPERATOR_PAINT);
		Rect minTextRect = getRelativeRect(minArea.getArea(), Attributes.RELATIVE_SIZE_TEXTBOX);
		LayoutObject minText = new TextBox(
                MainActivity.getContext().getString(R.string.gameplay_min_sign),
                Attributes.TextAllignment.XYCENTERED,
				new Point(
						minTextRect.left,
						minTextRect.top),
				new Size(
						minTextRect.width(),
						minTextRect.height()),
                Attributes.TEXT_BOX_OPERATOR_PAINT);
		Rect multTextRect = getRelativeRect(multArea.getArea(), Attributes.RELATIVE_SIZE_TEXTBOX);
		LayoutObject multText = new TextBox(
                MainActivity.getContext().getString(R.string.gameplay_mult_sign),
                Attributes.TextAllignment.XYCENTERED,
				new Point(
						multTextRect.left,
						multTextRect.top),
				new Size(
						multTextRect.width(),
						multTextRect.height()),
                Attributes.TEXT_BOX_OPERATOR_PAINT);
		Rect divTextRect = getRelativeRect(divArea.getArea(), Attributes.RELATIVE_SIZE_TEXTBOX);
		LayoutObject divText = new TextBox(
                MainActivity.getContext().getString(R.string.gameplay_div_sign),
                Attributes.TextAllignment.XYCENTERED,
				new Point(
						divTextRect.left,
						divTextRect.top),
				new Size(
						divTextRect.width(),
						divTextRect.height()),
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

	private Rect getRelativeRect(Rect rect, float factor) {
		return new Rect(
				(int) (rect.left + (1 - factor) / 2 * rect.width()),
				(int) (rect.top + (1 - factor) / 2 * rect.height()),
				(int) (rect.right - (1 - factor) / 2 * rect.width()),
				(int) (rect.bottom - (1 - factor) / 2 * rect.height()));
	}
}
