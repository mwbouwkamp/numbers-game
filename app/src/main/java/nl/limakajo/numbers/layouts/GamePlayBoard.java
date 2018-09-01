package nl.limakajo.numbers.layouts;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.GameUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author M.W.Bouwkamp
 */

public class GamePlayBoard {

	private Map<String, ScreenArea> screenAreas;
	private Map<String, TextBox> textBoxes;

	/**
	 * Constructor
	 */
	public GamePlayBoard() {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.MITER);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(3);
		paint.setAlpha(100);
		paint.setStyle(Paint.Style.FILL);

		paint.setColor(Attributes.BG_COLOR);
		paint.setShader(new RadialGradient(MainActivity.getDevice().getWidth() / 2,
				MainActivity.getDevice().getHeight() / 2,
				Math.max(MainActivity.getDevice().getHeight(), MainActivity.getDevice().getWidth()),
				new int[]{Attributes.BG_COLOR, Color.BLACK},
				null,
				Shader.TileMode.MIRROR));
		ScreenArea fullscreen = new ScreenArea (
				new Rect(
						0,
						0,
						MainActivity.getDevice().getWidth(),
						MainActivity.getDevice().getHeight()),
				paint);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.MITER);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(3);
		paint.setAlpha(100);
		paint.setStyle(Paint.Style.FILL);
		ScreenArea playArea = new ScreenArea (
				new Rect(
						Attributes.MARGE,
						Attributes.MARGE,
						MainActivity.getDevice().getWidth() - Attributes.MARGE,
						MainActivity.getDevice().getHeight() - Attributes.MARGE),
				paint);
		ScreenArea headerArea = new ScreenArea (
				new Rect(
						playArea.getArea().left,
						playArea.getArea().top,
						playArea.getArea().right,
						playArea.getArea().top + Attributes.GOAL_HEIGHT),
				paint);
		ScreenArea timerArea = new ScreenArea (
				new Rect(
						headerArea.getArea().left + (headerArea.getArea().width() - Attributes.GOAL_HEIGHT) / 2,
						headerArea.getArea().top,
						headerArea.getArea().left + (headerArea.getArea().width() + Attributes.GOAL_HEIGHT) / 2,
						headerArea.getArea().bottom),
				paint);
		ScreenArea footerArea = new ScreenArea(
				new Rect(
						playArea.getArea().left,
						playArea.getArea().bottom - Attributes.FOOTER_HEIGHT,
						playArea.getArea().right,
						playArea.getArea().bottom),
				paint
				);
		paint.setStyle(Paint.Style.STROKE);
		paint.setTextSize(100);
		Typeface typeface = Attributes.TYPEFACE_CALIBRI;
		paint.setTypeface(typeface);
		paint.setColor(Attributes.GOAL_COLOR);
		TextBox goalText = new TextBox(
				MainActivity.getContext().getString(R.string.empty_text),
				Attributes.TextAllignment.XYCENTERED,
				headerArea.getArea(),
				paint);
		paint.setColor(Attributes.FOOTER_COLOR);
		paint.setTextSize(32);
		TextBox footerText = new TextBox(
				MainActivity.getContext().getString(R.string.empty_text),
				Attributes.TextAllignment.XYCENTERED,
				footerArea.getArea(),
				paint);
		paint.setTextSize(96);
		typeface = Attributes.TYPEFACE_NUMBERSGAME;
		paint.setTypeface(typeface);
		TextBox numLivesText = new TextBox(
				//TODO: Here the actual number of lifes needs to be displayed. A (in this font) is a star
				"A1",
				Attributes.TextAllignment.YCENTERED,
				footerText.getArea(),
				paint
		);
		paint.setColor(Attributes.SHELF_COLOR);
		ScreenArea shelfArea = new ScreenArea(
				new Rect(
						playArea.getArea().left,
						headerArea.getArea().bottom + Attributes.MARGE,
						playArea.getArea().right,
						(int) (headerArea.getArea().bottom + 2 * Attributes.MARGE + (playArea.getArea().right - playArea.getArea().left - Attributes.MARGE) / GameUtils.NUMTILES) - Attributes.MARGE),
				paint);
		ScreenArea operatorsArea = new ScreenArea(
				new Rect(
						playArea.getArea().left,
						shelfArea.getArea().bottom + Attributes.MARGE,
						playArea.getArea().right,
						footerText.getArea().top - Attributes.MARGE),
				paint);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Attributes.PLUS_COLOR);
		ScreenArea plusArea = new ScreenArea(
				new Rect(
						operatorsArea.getArea().left,
						operatorsArea.getArea().top,
						operatorsArea.getArea().width() / 2 + Attributes.MARGE / 2,
						operatorsArea.getArea().top + operatorsArea.getArea().height() / 2 - Attributes.MARGE / 2),
				paint);
		paint.setColor(Attributes.PLUS_COLOR_2);
		ScreenArea plusArea2 = new ScreenArea(
				new Rect(
						plusArea.getArea().left,
						plusArea.getArea().top + 10 * (plusArea.getArea().bottom - plusArea.getArea().top)/11,
						plusArea.getArea().right,
						plusArea.getArea().bottom),
				paint);
		paint.setColor(Attributes.MIN_COLOR);
		ScreenArea minArea = new ScreenArea(
				new Rect(
						plusArea.getArea().right + Attributes.MARGE,
						operatorsArea.getArea().top,
						operatorsArea.getArea().right,
						plusArea.getArea().bottom),
				paint);
		paint.setColor(Attributes.MIN_COLOR_2);
		ScreenArea minArea2 = new ScreenArea(
				new Rect(
						minArea.getArea().left,
						minArea.getArea().top + 10 * (minArea.getArea().bottom - minArea.getArea().top)/11,
						minArea.getArea().right,
						minArea.getArea().bottom),
				paint);
		paint.setColor(Attributes.MULT_COLOR);
		ScreenArea multArea = new ScreenArea(
				new Rect(
						plusArea.getArea().left,
						plusArea.getArea().bottom + Attributes.MARGE,
						plusArea.getArea().right,
						operatorsArea.getArea().bottom),
				paint);
		paint.setColor(Attributes.MULT_COLOR_2);
		ScreenArea multArea2 = new ScreenArea(
				new Rect(
						multArea.getArea().left,
						multArea.getArea().top + 10 * (multArea.getArea().bottom - multArea.getArea().top)/11,
						multArea.getArea().right,
						multArea.getArea().bottom),
				paint);
		paint.setColor(Attributes.DIV_COLOR);
		ScreenArea divArea = new ScreenArea(
				new Rect(
						minArea.getArea().left,
						multArea.getArea().top,
						minArea.getArea().right,
						multArea.getArea().bottom),
				paint);
		paint.setColor(Attributes.DIV_COLOR_2);
		ScreenArea divArea2 = new ScreenArea(
				new Rect(
						divArea.getArea().left,
						divArea.getArea().top + 10 * (divArea.getArea().bottom - divArea.getArea().top)/11,
						divArea.getArea().right,
						divArea.getArea().bottom),
				paint);
		paint.setTextSize(220);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Attributes.BG_COLOR);
		paint.setStrokeWidth(7);
		TextBox plusText = new TextBox(
				MainActivity.getContext().getString(R.string.plus_sign),
				Attributes.TextAllignment.XYCENTERED,
				plusArea.getArea(),
				paint);
		TextBox minText = new TextBox(
				MainActivity.getContext().getString(R.string.min_sign),
				Attributes.TextAllignment.XYCENTERED,
				minArea.getArea(),
				paint);
		TextBox multText = new TextBox(
				MainActivity.getContext().getString(R.string.mult_sign),
				Attributes.TextAllignment.XYCENTERED,
				multArea.getArea(),
				paint);
		TextBox divText = new TextBox(
				MainActivity.getContext().getString(R.string.div_sign),
				Attributes.TextAllignment.XYCENTERED,
				divArea.getArea(),
				paint);

		paint.setColor(Attributes.LEVEL_COMPLETE_COLOR);
		ScreenArea levelcomplete = new ScreenArea(
				new Rect(
						0,
						(playArea.getArea().top + playArea.getArea().bottom) / 2 - 200,
						MainActivity.getDevice().getWidth(),
						(playArea.getArea().top + playArea.getArea().bottom) / 2),
				paint);
		paint.setColor(Attributes.GAME_OVER_COLOR);
		ScreenArea gameover = new ScreenArea(
				new Rect(
						0,
						(playArea.getArea().top + playArea.getArea().bottom) / 2 - 200,
						MainActivity.getDevice().getWidth(),
						(playArea.getArea().top + playArea.getArea().bottom) / 2),
				paint);

		screenAreas = new HashMap<String, ScreenArea>();
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
		screenAreas.put("gameover", gameover);
		screenAreas.put("levelcomplete", levelcomplete);

		textBoxes = new HashMap<String, TextBox>();
		textBoxes.put("goalText", goalText);
		textBoxes.put("footerText", footerText);
		textBoxes.put("plusText", plusText);
		textBoxes.put("minText", minText);
		textBoxes.put("multText", multText);
		textBoxes.put("divText", divText);
		textBoxes.put("numLivesText", numLivesText);
	}

	public Map<String, ScreenArea> getScreenAreas() {
		return screenAreas;
	}

	public Map<String, TextBox> getTextBoxes() {
		return textBoxes;
	}

	public ScreenArea getScreenArea(String key) {
		return screenAreas.get(key);
	}

	public TextBox getTextBox(String key) {
		return textBoxes.get(key);
	}
}
