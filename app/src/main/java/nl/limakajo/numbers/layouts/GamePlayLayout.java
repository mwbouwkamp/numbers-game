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

	public enum ObjectKeys {
		FULLSCREEN_AREA,

		PLAY_AREA,

        HEADER_AREA,
        GOAL_TEXT,
		TIMER_AREA,
        NUM_LIVES_TEXT,
        NUM_STARS_TEXT,

        SHELF_AREA,

        OPERATORS_AREA,
		PLUS_AREA,
		PLUS2_AREA,
		MIN_AREA,
		MIN2_AREA,
		MULT_AREA,
		MULT2_AREA,
		DIV_AREA,
		DIV2_AREA,
		PLUS_TEXT,
		MIN_TEXT,
		MULT_TEXT,
		DIV_TEXT,

        FOOTER_TEXT
	}

	private EnumMap<ObjectKeys, GameObject> gameObjects;

	public GamePlayLayout() {
        gameObjects = new EnumMap<>(ObjectKeys.class);

	    //Fullscreen
		ScreenArea fullscreen = new ScreenArea (
				new Rect(
						0,
						0,
						MainActivity.getDevice().getWidth(),
						MainActivity.getDevice().getHeight()),
				Attributes.BG_PAINT);
        gameObjects.put(ObjectKeys.FULLSCREEN_AREA, fullscreen);

		//Playable area
        ScreenArea playArea = new ScreenArea (
				new Rect(
						Attributes.MARGE,
						Attributes.MARGE,
						MainActivity.getDevice().getWidth() - Attributes.MARGE,
						MainActivity.getDevice().getHeight() - Attributes.MARGE),
				Attributes.NO_DRAW);
        gameObjects.put(ObjectKeys.PLAY_AREA, playArea);

        //Header
        ScreenArea headerArea = new ScreenArea (
				new Rect(
						playArea.getArea().left,
						playArea.getArea().top,
						playArea.getArea().right,
						playArea.getArea().top + Attributes.GOAL_HEIGHT),
				Attributes.NO_DRAW);
        TextBox goalText = new TextBox(
                MainActivity.getContext().getString(R.string.empty_text),
                Attributes.TextAllignment.XYCENTERED,
                headerArea.getArea(),
                Attributes.TEXTBOX_LARGE_PAINT);
        ScreenArea timerArea = new ScreenArea (
				new Rect(
						headerArea.getArea().left + (headerArea.getArea().width() - Attributes.GOAL_HEIGHT) / 2,
						headerArea.getArea().top,
						headerArea.getArea().left + (headerArea.getArea().width() + Attributes.GOAL_HEIGHT) / 2,
						headerArea.getArea().bottom),
				Attributes.NO_DRAW);
        TextBox numStarsText = new TextBox(
                "A",
                Attributes.TextAllignment.XLEFT_YCENTERED,
                new Rect(
                        headerArea.getArea().left,
                        headerArea.getArea().top,
                        timerArea.getArea().left,
                        headerArea.getArea().top + Attributes.GOAL_HEIGHT / 3),
                Attributes.TEXTBOX_NUMLIVES_PAINT);
        TextBox numLivesText = new TextBox(
                "B",
                Attributes.TextAllignment.XRIGHT_YCENTERED,
                new Rect(
                        timerArea.getArea().right,
                        headerArea.getArea().top,
                        headerArea.getArea().right,
                        headerArea.getArea().top + Attributes.GOAL_HEIGHT / 3),
                Attributes.TEXTBOX_NUMLIVES_PAINT);
        gameObjects.put(ObjectKeys.HEADER_AREA, headerArea);
        gameObjects.put(ObjectKeys.GOAL_TEXT, goalText);
        gameObjects.put(ObjectKeys.TIMER_AREA, timerArea);
        gameObjects.put(ObjectKeys.NUM_LIVES_TEXT, numLivesText);
        gameObjects.put(ObjectKeys.NUM_STARS_TEXT, numStarsText);

        //Footer
        TextBox footerText = new TextBox(
                MainActivity.getContext().getString(R.string.empty_text),
                Attributes.TextAllignment.XYCENTERED,
                new Rect(
                        playArea.getArea().left,
                        playArea.getArea().bottom - Attributes.FOOTER_HEIGHT,
                        playArea.getArea().right,
                        playArea.getArea().bottom),
                Attributes.TEXTBOX_SMALL_PAINT);
        gameObjects.put(ObjectKeys.FOOTER_TEXT, footerText);

        //Shelf
        ScreenArea shelfArea = new ScreenArea(
                new Rect(
                        playArea.getArea().left,
                        headerArea.getArea().bottom + Attributes.MARGE,
                        playArea.getArea().right,
                        (int) (headerArea.getArea().bottom + 2.0 * Attributes.MARGE + (playArea.getArea().right - playArea.getArea().left - Attributes.MARGE) / GameUtils.NUMTILES) - Attributes.MARGE),
                Attributes.NO_DRAW);
        gameObjects.put(ObjectKeys.SHELF_AREA, shelfArea);

        //Operators
        ScreenArea operatorsArea = new ScreenArea(
                new Rect(
                        playArea.getArea().left,
                        shelfArea.getArea().bottom + Attributes.MARGE,
                        playArea.getArea().right,
                        footerText.getArea().top - Attributes.MARGE),
                Attributes.NO_DRAW);
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
        gameObjects.put(ObjectKeys.OPERATORS_AREA, operatorsArea);
        gameObjects.put(ObjectKeys.PLUS_AREA, plusArea);
        gameObjects.put(ObjectKeys.PLUS2_AREA, plusArea2);
        gameObjects.put(ObjectKeys.MIN_AREA, minArea);
        gameObjects.put(ObjectKeys.MIN2_AREA, minArea2);
        gameObjects.put(ObjectKeys.MULT_AREA, multArea);
        gameObjects.put(ObjectKeys.MULT2_AREA, multArea2);
        gameObjects.put(ObjectKeys.DIV_AREA, divArea);
        gameObjects.put(ObjectKeys.DIV2_AREA, divArea2);
        gameObjects.put(ObjectKeys.PLUS_TEXT, plusText);
        gameObjects.put(ObjectKeys.MIN_TEXT, minText);
        gameObjects.put(ObjectKeys.MULT_TEXT, multText);
        gameObjects.put(ObjectKeys.DIV_TEXT, divText);
	}

	public ScreenArea getScreenArea(ObjectKeys gameOverObjectKey) {
		return (ScreenArea) gameObjects.get(gameOverObjectKey);
	}

	public TextBox getTextBox(ObjectKeys gameOverObjectKey) {
		return (TextBox) gameObjects.get(gameOverObjectKey);
	}

	public void draw(Canvas canvas) {
		for (GameObject gameObject: gameObjects.values()) {
			gameObject.draw(canvas);
		}
//		gameObjects.get(ObjectKeys.FULLSCREEN_AREA).draw(canvas);
//		gameObjects.get(ObjectKeys.PLUS_AREA).draw(canvas);
//		gameObjects.get(ObjectKeys.PLUS2_AREA).draw(canvas);
//		gameObjects.get(ObjectKeys.MIN_AREA).draw(canvas);
//		gameObjects.get(ObjectKeys.MIN2_AREA).draw(canvas);
//		gameObjects.get(ObjectKeys.MULT_AREA).draw(canvas);
//		gameObjects.get(ObjectKeys.MULT2_AREA).draw(canvas);
//		gameObjects.get(ObjectKeys.DIV_AREA).draw(canvas);
//		gameObjects.get(ObjectKeys.DIV2_AREA).draw(canvas);
//		gameObjects.get(ObjectKeys.DIV2_AREA).draw(canvas);
//		gameObjects.get(ObjectKeys.GOAL_TEXT).draw(canvas);
//		gameObjects.get(ObjectKeys.FOOTER_TEXT).draw(canvas);
//		gameObjects.get(ObjectKeys.PLUS_TEXT).draw(canvas);
//		gameObjects.get(ObjectKeys.MIN_TEXT).draw(canvas);
//		gameObjects.get(ObjectKeys.MULT_TEXT).draw(canvas);
//		gameObjects.get(ObjectKeys.DIV_TEXT).draw(canvas);
//		gameObjects.get(ObjectKeys.NUM_LIVES_TEXT).draw(canvas);
//		gameObjects.get(ObjectKeys.NUM_STARS_TEXT).draw(canvas);
	}
}
