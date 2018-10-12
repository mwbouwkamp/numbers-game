package nl.limakajo.numbers.layouts;

import android.graphics.Canvas;
import android.graphics.Rect;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.GameObject;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.GameUtils;

import java.util.EnumMap;

/**
 * @author M.W.Bouwkamp
 */

public class GamePlayLayout {

	private EnumMap<GamePlayGameObjectKeys, GameObject> gameObjects;

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
		ScreenArea numStarsArea = new ScreenArea(
				new Rect(
						headerArea.getArea().left,
						headerArea.getArea().top,
						timerArea.getArea().left,
						headerArea.getArea().top + Attributes.GOAL_HEIGHT / 3),
				Attributes.EMPTY_PAINT);
		ScreenArea numLivesArea = new ScreenArea(
				new Rect(
						timerArea.getArea().right,
						headerArea.getArea().top,
						headerArea.getArea().right,
						headerArea.getArea().top + Attributes.GOAL_HEIGHT / 3),
				Attributes.EMPTY_PAINT);
		ScreenArea footerArea = new ScreenArea(
				new Rect(
						playArea.getArea().left,
						playArea.getArea().bottom - Attributes.FOOTER_HEIGHT,
						playArea.getArea().right,
						playArea.getArea().bottom),
				Attributes.EMPTY_PAINT);
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
						footerArea.getArea().top - Attributes.MARGE),
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
		TextBox numStarsText = new TextBox(
				"A",
				Attributes.TextAllignment.XLEFT_YCENTERED,
				numStarsArea.getArea(),
				Attributes.TEXTBOX_NUMLIVES_PAINT);
		TextBox numLivesText = new TextBox(
				"B",
				Attributes.TextAllignment.XRIGHT_YCENTERED,
				numLivesArea.getArea(),
				Attributes.TEXTBOX_NUMLIVES_PAINT);
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

		gameObjects = new EnumMap<>(GamePlayGameObjectKeys.class);
		gameObjects.put(GamePlayGameObjectKeys.FULLSCREEN_AREA, fullscreen);
		gameObjects.put(GamePlayGameObjectKeys.PLAY_AREA, playArea);
		gameObjects.put(GamePlayGameObjectKeys.HEADER_AREA, headerArea);
		gameObjects.put(GamePlayGameObjectKeys.TIMER_AREA, timerArea);
		gameObjects.put(GamePlayGameObjectKeys.NUM_LIVES_AREA, numLivesArea);
		gameObjects.put(GamePlayGameObjectKeys.NUM_STARS_AREA, numStarsArea);
		gameObjects.put(GamePlayGameObjectKeys.SHELF_AREA, shelfArea);
		gameObjects.put(GamePlayGameObjectKeys.OPERATORS_AREA, operatorsArea);
		gameObjects.put(GamePlayGameObjectKeys.PLUS_AREA, plusArea);
		gameObjects.put(GamePlayGameObjectKeys.PLUS2_AREA, plusArea2);
		gameObjects.put(GamePlayGameObjectKeys.MIN_AREA, minArea);
		gameObjects.put(GamePlayGameObjectKeys.MIN2_AREA, minArea2);
		gameObjects.put(GamePlayGameObjectKeys.MULT_AREA, multArea);
		gameObjects.put(GamePlayGameObjectKeys.MULT2_AREA, multArea2);
		gameObjects.put(GamePlayGameObjectKeys.DIV_AREA, divArea);
		gameObjects.put(GamePlayGameObjectKeys.DIV2_AREA, divArea2);

		gameObjects.put(GamePlayGameObjectKeys.GOAL_TEXT, goalText);
		gameObjects.put(GamePlayGameObjectKeys.FOOTER_TEXT, footerText);
		gameObjects.put(GamePlayGameObjectKeys.PLUS_TEXT, plusText);
		gameObjects.put(GamePlayGameObjectKeys.MIN_TEXT, minText);
		gameObjects.put(GamePlayGameObjectKeys.MULT_TEXT, multText);
		gameObjects.put(GamePlayGameObjectKeys.DIV_TEXT, divText);
		gameObjects.put(GamePlayGameObjectKeys.NUM_LIVES_TEXT, numLivesText);
		gameObjects.put(GamePlayGameObjectKeys.NUM_STARS_TEXT, numStarsText);
	}

	public ScreenArea getScreenArea(GamePlayGameObjectKeys gameOverObjectKey) {
		return (ScreenArea) gameObjects.get(gameOverObjectKey);
	}

	public TextBox getTextBox(GamePlayGameObjectKeys gameOverObjectKey) {
		return (TextBox) gameObjects.get(gameOverObjectKey);
	}

	public void draw(Canvas canvas) {
		gameObjects.get(GamePlayGameObjectKeys.FULLSCREEN_AREA).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.PLUS_AREA).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.PLUS2_AREA).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.MIN_AREA).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.MIN2_AREA).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.MULT_AREA).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.MULT2_AREA).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.DIV_AREA).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.DIV2_AREA).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.DIV2_AREA).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.GOAL_TEXT).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.FOOTER_TEXT).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.PLUS_TEXT).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.MIN_TEXT).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.MULT_TEXT).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.DIV_TEXT).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.NUM_LIVES_TEXT).draw(canvas);
		gameObjects.get(GamePlayGameObjectKeys.NUM_STARS_TEXT).draw(canvas);
	}

}
