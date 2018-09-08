package nl.limakajo.numbers.layouts;

import android.graphics.Rect;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.GameUtils;

import java.util.HashMap;

/**
 * @author M.W.Bouwkamp
 */

public class GamePlayLayout extends Layout {

	/**
	 * Constructor
	 */
	public GamePlayLayout() {
		ScreenArea fullscreen = new ScreenArea (
				new Rect(
						0,
						0,
						MainActivity.getDevice().getWidth(),
						MainActivity.getDevice().getHeight()),
				Attributes.BG_PAINT);
		ScreenArea playArea = new ScreenArea (
				new Rect(
						Attributes.MARGE,
						Attributes.MARGE,
						MainActivity.getDevice().getWidth() - Attributes.MARGE,
						MainActivity.getDevice().getHeight() - Attributes.MARGE),
				Attributes.EMPTY_PAINT);
		ScreenArea headerArea = new ScreenArea (
				new Rect(
						playArea.getArea().left,
						playArea.getArea().top,
						playArea.getArea().right,
						playArea.getArea().top + Attributes.GOAL_HEIGHT),
				Attributes.EMPTY_PAINT);
		ScreenArea timerArea = new ScreenArea (
				new Rect(
						headerArea.getArea().left + (headerArea.getArea().width() - Attributes.GOAL_HEIGHT) / 2,
						headerArea.getArea().top,
						headerArea.getArea().left + (headerArea.getArea().width() + Attributes.GOAL_HEIGHT) / 2,
						headerArea.getArea().bottom),
				Attributes.EMPTY_PAINT);
		ScreenArea footerArea = new ScreenArea(
				new Rect(
						playArea.getArea().left,
						playArea.getArea().bottom - Attributes.FOOTER_HEIGHT,
						playArea.getArea().right,
						playArea.getArea().bottom),
				Attributes.EMPTY_PAINT);
		TextBox goalText = new TextBox(
				MainActivity.getContext().getString(R.string.empty_text),
				Attributes.TextAllignment.XYCENTERED,
				headerArea.getArea(),
				Attributes.TEXTBOX_LARGE_PAINT);
		TextBox footerText = new TextBox(
				MainActivity.getContext().getString(R.string.empty_text),
				Attributes.TextAllignment.XYCENTERED,
				footerArea.getArea(),
				Attributes.TEXTBOX_SMALL_PAINT);
		TextBox numLivesText = new TextBox(
				//TODO: Here the actual number of lifes needs to be displayed. A (in this font) is a star
				"A1",
				Attributes.TextAllignment.YCENTERED,
				footerText.getArea(),
				Attributes.TEXTBOX_NUMLIVES_PAINT);
		ScreenArea shelfArea = new ScreenArea(
				new Rect(
						playArea.getArea().left,
						headerArea.getArea().bottom + Attributes.MARGE,
						playArea.getArea().right,
						(int) (headerArea.getArea().bottom + 2.0 * Attributes.MARGE + (playArea.getArea().right - playArea.getArea().left - Attributes.MARGE) / GameUtils.NUMTILES) - Attributes.MARGE),
				Attributes.EMPTY_PAINT);
		ScreenArea operatorsArea = new ScreenArea(
				new Rect(
						playArea.getArea().left,
						shelfArea.getArea().bottom + Attributes.MARGE,
						playArea.getArea().right,
						footerText.getArea().top - Attributes.MARGE),
				Attributes.EMPTY_PAINT);
		ScreenArea plusArea = new ScreenArea(
				new Rect(
						operatorsArea.getArea().left,
						operatorsArea.getArea().top,
						operatorsArea.getArea().width() / 2 + Attributes.MARGE / 2,
						operatorsArea.getArea().top + operatorsArea.getArea().height() / 2 - Attributes.MARGE / 2),
				Attributes.PLUS_PAINT);
		ScreenArea plusArea2 = new ScreenArea(
				new Rect(
						plusArea.getArea().left,
						plusArea.getArea().top + 10 * (plusArea.getArea().bottom - plusArea.getArea().top)/11,
						plusArea.getArea().right,
						plusArea.getArea().bottom),
				Attributes.PLUS_PAINT_2);
		ScreenArea minArea = new ScreenArea(
				new Rect(
						plusArea.getArea().right + Attributes.MARGE,
						operatorsArea.getArea().top,
						operatorsArea.getArea().right,
						plusArea.getArea().bottom),
				Attributes.MIN_PAINT);
		ScreenArea minArea2 = new ScreenArea(
				new Rect(
						minArea.getArea().left,
						minArea.getArea().top + 10 * (minArea.getArea().bottom - minArea.getArea().top)/11,
						minArea.getArea().right,
						minArea.getArea().bottom),
				Attributes.MIN_PAINT_2);
		ScreenArea multArea = new ScreenArea(
				new Rect(
						plusArea.getArea().left,
						plusArea.getArea().bottom + Attributes.MARGE,
						plusArea.getArea().right,
						operatorsArea.getArea().bottom),
				Attributes.MULT_PAINT);
		ScreenArea multArea2 = new ScreenArea(
				new Rect(
						multArea.getArea().left,
						multArea.getArea().top + 10 * (multArea.getArea().bottom - multArea.getArea().top)/11,
						multArea.getArea().right,
						multArea.getArea().bottom),
				Attributes.MULT_PAINT_2);
		ScreenArea divArea = new ScreenArea(
				new Rect(
						minArea.getArea().left,
						multArea.getArea().top,
						minArea.getArea().right,
						multArea.getArea().bottom),
				Attributes.DIV_PAINT);
		ScreenArea divArea2 = new ScreenArea(
				new Rect(
						divArea.getArea().left,
						divArea.getArea().top + 10 * (divArea.getArea().bottom - divArea.getArea().top)/11,
						divArea.getArea().right,
						divArea.getArea().bottom),
				Attributes.DIV_PAINT_2);
		TextBox plusText = new TextBox(
				MainActivity.getContext().getString(R.string.gameplay_plus_sign),
				Attributes.TextAllignment.XYCENTERED,
				plusArea.getArea(),
				Attributes.TEXT_BOX_OPERATOR_PAINT);
		TextBox minText = new TextBox(
				MainActivity.getContext().getString(R.string.gameplay_min_sign),
				Attributes.TextAllignment.XYCENTERED,
				minArea.getArea(),
				Attributes.TEXT_BOX_OPERATOR_PAINT);
		TextBox multText = new TextBox(
				MainActivity.getContext().getString(R.string.gameplay_mult_sign),
				Attributes.TextAllignment.XYCENTERED,
				multArea.getArea(),
				Attributes.TEXT_BOX_OPERATOR_PAINT);
		TextBox divText = new TextBox(
				MainActivity.getContext().getString(R.string.gameplay_div_sign),
				Attributes.TextAllignment.XYCENTERED,
				divArea.getArea(),
				Attributes.TEXT_BOX_OPERATOR_PAINT);

		screenAreas = new HashMap<>();
		screenAreas.put("fullscreen", fullscreen);
		screenAreas.put("play", playArea);
		screenAreas.put("header", headerArea);
		screenAreas.put("timerRound", timerArea);
		screenAreas.put("shelf", shelfArea);
		screenAreas.put("operators", operatorsArea);
		screenAreas.put("plus", plusArea);
		screenAreas.put("plus2", plusArea2);
		screenAreas.put("min", minArea);
		screenAreas.put("min2", minArea2);
		screenAreas.put("mult", multArea);
		screenAreas.put("mult2", multArea2);
		screenAreas.put("div", divArea);
		screenAreas.put("div2", divArea2);

		textBoxes = new HashMap<>();
		textBoxes.put("goalText", goalText);
		textBoxes.put("footerText", footerText);
		textBoxes.put("plusText", plusText);
		textBoxes.put("minText", minText);
		textBoxes.put("multText", multText);
		textBoxes.put("divText", divText);
		textBoxes.put("numLivesText", numLivesText);
	}
}
