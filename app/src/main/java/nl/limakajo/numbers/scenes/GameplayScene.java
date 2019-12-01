package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

import nl.limakajo.numbers.animators.PaintAnimationStarter;
import nl.limakajo.numbers.animators.PositionAnimationStarter;
import nl.limakajo.numbers.animators.ScaleAnimationStarter;
import nl.limakajo.numbers.gameObjects.Tile;
import nl.limakajo.numbers.gameObjects.Wave;
import nl.limakajo.numbers.gameObjects.WavePool;
import nl.limakajo.numbers.layouts.GamePlayLayout;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.gameObjects.TilePool;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;
import nl.limakajo.numbers.main.AnimatorThread;
import nl.limakajo.numberslib.numbersGame.Level;
import nl.limakajo.numberslib.utils.GameConstants;

import static nl.limakajo.numbers.layouts.LayoutElementsKeys.*;

/**
 * @author M.W.Bouwkamp
 */

public class GameplayScene extends Scene {

    private Tile tilePressed, firstTile, secondTile;
    private boolean onShelf;
    private Point tileStart;
    private String statusBarText;
    private long startTime;
    private WavePool wavePool;
    private TilePool tilePool;

    private final GamePlayLayout gamePlayLayout;

    private final SceneManager sceneManager;
    private final AnimatorThread animatorThread;
    private final Calculator calculator;

    private enum TouchUpScenarios {
        RESET,
        GIVE_UP,
        NO_TILE_PRESSED,
        ON_SHELF,
        IN_CRUNCH,
        FIRST_TILE_PRESSED,
        SECOND_TILE_PRESSED,
        NULL
    }

    GameplayScene(SceneManager sceneManager, AnimatorThread animatorThread) {
        this.sceneManager = sceneManager;
        this.animatorThread = animatorThread;
        this.gamePlayLayout = new GamePlayLayout();
        this.calculator = new Calculator();
    }

    /**
     * Game initializer
     *
     * Starts GameThread, initializes variables and sets the initial GameState
     */
    public void init() {
        this.animatorThread.removeAll();

        startTime = System.currentTimeMillis();

        //Make sure that player loses a life, even when games gets to end before completing a level or running out of time
        MainActivity.getPlayer().decreaseNumLives();

        initializeVariables();
        Level newLevel = constructLevel();
        setUsertimeToPenalty(newLevel);

        //Update status of levels, so that old ACTIVE levels become ready for uploading (UPLOAD) and newLevel becomes the new ACTIVE level
        DatabaseUtils.updateTableLevelsLevelStatusForSpecificCurrentStatus(MainActivity.getContext(), GameUtils.LevelState.ACTIVE, GameUtils.LevelState.UPLOAD);
        DatabaseUtils.updateTableLevelsLevelStatusForSpecificLevel(MainActivity.getContext(), newLevel, GameUtils.LevelState.ACTIVE);

        createTilePool();
        gamePlayLayout.getTextBox(GOAL_TEXT).setText(Integer.toString(MainActivity.getGame().getLevel().getGoal()));
        gamePlayLayout.getTextBox(NUM_STARS_TEXT).setText("A" + MainActivity.getPlayer().getNumStars());
        gamePlayLayout.getTextBox(NUM_LIVES_TEXT).setText("B" + MainActivity.getPlayer().getNumLives());
        setInitiating(false);
    }

    private void setUsertimeToPenalty(Level newLevel) {
        DatabaseUtils.updateTableLevelsUserTimeForSpecificLevel(MainActivity.getContext(), newLevel);
    }

    @NonNull
    private Level constructLevel() {
        Level newLevel = DatabaseUtils.getLevelWithAverageTimeCloseToUserAverageTime(MainActivity.getContext());
        newLevel.setUserTime(GameConstants.TIMEPENALTY);
        MainActivity.getGame().setLevel(newLevel);
        return newLevel;
    }

    /**
     * Initialize all variables
     */
    private void initializeVariables() {
        wavePool = new WavePool();
        tilePressed = null;
        firstTile = null;
        secondTile = null;
        onShelf = true;
        calculator.reset();
        statusBarText = "";
    }

    @Override
    public void update() {
        gamePlayLayout.getTextBox(FOOTER_TEXT).setText(statusBarText);
        checkForGameOver();
        checkForLevelComplete();
        tilePool.update();
        wavePool.update();
    }

    /**
     * adds a new LevelCompleteScene to the SceneManager when the goal has been reached
     */
    private void checkForLevelComplete() {
        for (Tile tile: tilePool.getGameObjects()) {
            if (tile.getNumber() == MainActivity.getGame().getLevel().getGoal()) {
                MainActivity.getGame().getLevel().setUserTime((int)(System.currentTimeMillis() - startTime));
                sceneManager.setScene(new LevelCompleteScene(sceneManager, animatorThread));
            }
        }
    }

    /**
     * adds a new GameOverScene to the SceneManager when the time is up
     */
    private void checkForGameOver() {
        if (System.currentTimeMillis() - startTime > GameConstants.TIMER){
            sceneManager.setScene(new GameOverScene(sceneManager, animatorThread));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        gamePlayLayout.draw(canvas);
        wavePool.draw(canvas);
        drawTiles(canvas);
        drawTimerRound(canvas);
    }

    /**
     * Draws all Tiles onto the canvas: Tiles on the tilePool and Tiles in play
     *
     * @param canvas canvas
     */
    private void drawTiles(Canvas canvas) {
        tilePool.draw(canvas);
        if (firstTile != null) {
            firstTile.draw(canvas);
        }
        if (secondTile != null) {
            secondTile.draw(canvas);
        }
    }

    /**
     * Draws the timer onto the canvas
     *
     * @param canvas canvas
     */
    private void drawTimerRound(Canvas canvas){
        Paint paint = new Paint();
        double timeFraction = (System.currentTimeMillis() - startTime) / (double) GameConstants.TIMER;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(16);
        RectF rect = new RectF(gamePlayLayout.getScreenArea(TIMER_AREA).getArea());
        paint.setColor(Color.rgb(80, 80, 80));
        canvas. drawArc(rect, 0, 360, false, paint);
        paint.setColor(Color.rgb((int) (255 * timeFraction), (int) (255 * (1 - timeFraction)), 0));
        canvas.drawArc(rect, 270, (float) ((1 - timeFraction) * 360), false, paint);
    }

    @Override
    public void terminate() {
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            runningTouchDown(event);
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            runningTouchDragged(event);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            runningTouchUp(event);
        }
    }

    /**
     * Orchestrates what to do when the screen is touched in the running GameState
     *
     * @param event event
     */
    private void runningTouchDown(MotionEvent event) {
        if (firstTile != null && firstTile.isClicked(new Point((int) event.getX(), (int) event.getY()))) {
            setClicekdTile(firstTile);
        }
        for (Tile tile: tilePool.getGameObjects()) {
            if (tile.isClicked(new Point((int) event.getX(), (int) event.getY()))) {
                setClicekdTile(tile);
            }
        }
    }

    private void setClicekdTile(Tile tileClicked) {
        tilePressed = tileClicked;
        tileStart = new Point(tilePressed.getPosition());
        if (null != tilePressed.getPositionAnimator()) {
            tilePressed.getPositionAnimator().stopAnimating();
            animatorThread.remove(tileClicked.getPositionAnimator());
        }
        statusBarText = tilePressed.toString();
    }

    /**
     * Orchestrates what to do with a dragging movement in the running GameState
     *
     * @param event event
     */
    private void runningTouchDragged(MotionEvent event) {
        if (tilePressed != null) {
            if (onShelf && !tilePressed.inArea(gamePlayLayout.getScreenArea(SHELF_AREA))) {
                onShelf = false;
                tilePool.remove(tilePressed);
                tilePool.startAnimating(animatorThread);
                if (firstTile == null) {
                    firstTile = tilePressed;
                } else {
                    secondTile = tilePressed;
                }
            }
            tilePressed.setPosition(new Point((int) event.getX(), (int) event.getY()));
            if (null != tilePressed.getPositionAnimator()){
                tilePressed.getPositionAnimator().setCurrentposition(tilePressed.getPosition());
            }
        }
    }

    /**
     * Orchestrates what to do when the screen is released in the running GameState
     *
     * @param event event
     */
    private void runningTouchUp(MotionEvent event) {
        TouchUpScenarios touchUpScenarios = getTouchUpScenario(event);
        statusBarText = "";
        switch (touchUpScenarios) {
            case GIVE_UP:
                sceneManager.setScene(new GameOverScene(sceneManager, animatorThread));
                break;
            case RESET:
                createTilePool();
                break;
            case NO_TILE_PRESSED:
                break;
            case ON_SHELF:
                new PositionAnimationStarter().startAnimation(
                        tilePressed,
                        animatorThread,
                        tilePressed.getPosition(),
                        tileStart,
                        Attributes.TILE_ANIMATION_TIME, 0);
                tilePressed = null;
                break;
            case IN_CRUNCH:
                Tile[] tiles = tilePressed.crunch();
                for (Tile tile: tiles) {
                    animatorThread.add(tile.addToShelf(tilePool));
                }
                if (tilePressed == firstTile) {
                    firstTile = null;
                }
                else if (tilePressed == secondTile) {
                    secondTile = null;
                }
                break;
            case FIRST_TILE_PRESSED:
                createWave(tilePressed.getPosition());
                calculator.calculate(tilePressed.getNumber(), tilePressed.inWhichOperatorArea(gamePlayLayout));
                if (calculator.calculatorInactive()) {
                    animatorThread.add(firstTile.addToShelf(tilePool));
                    firstTile = null;
                }
                break;
            case SECOND_TILE_PRESSED:
                createWave(tilePressed.getPosition());
                calculator.calculate(tilePressed.getNumber(), tilePressed.inWhichOperatorArea(gamePlayLayout));
                if (calculator.calculatorInactive()) {
                    animatorThread.add(firstTile.addToShelf(tilePool));
                    animatorThread.add(secondTile.addToShelf(tilePool));
                    firstTile = null;
                    secondTile = null;
                }
                else if (calculator.calculatorInProgress()) {
                    animatorThread.add(firstTile.addToShelf(tilePool));
                    firstTile = secondTile;
                    secondTile = null;
                }
                else if (calculator.calculatorFinished()) {
                    Tile[] compositionNewTile = {firstTile, secondTile};
                    Tile toAdd = new Tile(calculator.getValue(), compositionNewTile, firstTile.getColorIndex() + secondTile.getColorIndex() + 1);
                    animatorThread.add(toAdd.addToShelf(tilePool));
                    firstTile = null;
                    secondTile = null;
                    calculator.reset();
                }
                break;
            default:
                break;
        }
        onShelf = true;
        tilePressed = null;
    }

    private TouchUpScenarios getTouchUpScenario(MotionEvent event) {
        if (gamePlayLayout.getScreenArea(NUM_LIVES_TEXT).getArea().contains((int) event.getX(), (int) event.getY())) {
            return TouchUpScenarios.GIVE_UP;
        }
        else if (gamePlayLayout.getScreenArea(NUM_STARS_TEXT).getArea().contains((int) event.getX(), (int) event.getY())) {
            return TouchUpScenarios.RESET;
        }
        else if (null == tilePressed) {
            return TouchUpScenarios.NO_TILE_PRESSED;
        }
        else if (onShelf) {
            return TouchUpScenarios.ON_SHELF;
        }
        else if (tilePressed.inArea(gamePlayLayout.getScreenArea(HEADER_AREA))) {
            return TouchUpScenarios.IN_CRUNCH;
        }
        else if (tilePressed == firstTile) {
            return TouchUpScenarios.FIRST_TILE_PRESSED;
        }
        else if (tilePressed == secondTile) {
            return TouchUpScenarios.SECOND_TILE_PRESSED;
        }
        return TouchUpScenarios.NULL;
    }

    private void createTilePool() {
        tilePool = new TilePool();
        firstTile = null;
        secondTile = null;
        for (int i = 0; i < GameConstants.NUMTILES; i++) {
            Tile tileToAdd = new Tile(MainActivity.getGame().getLevel().getHand()[i]);
            animatorThread.add(tileToAdd.addToShelf(tilePool));
        }
    }

    /**
     * Creates a Wave and its WaveAnimator and adds them to the WavePool and Animators in the gamePlayAnimatorThread, respectively
     *
     * @param position      the Position of the wave
     */
    private void createWave(Point position) {
        Wave waveToAdd = new Wave(position);
        new ScaleAnimationStarter().startAnimation(
                waveToAdd,
                animatorThread,
                waveToAdd.getScale(),
                10.0f,
                Attributes.WAVE_ANIMATION_TIME,
                0
        );
        new PaintAnimationStarter().startAnimation(
                waveToAdd,
                animatorThread,
                Attributes.WAVE_PAINT_START,
                Attributes.WAVE_PAINT_END,
                Attributes.WAVE_ANIMATION_TIME,
                0
        );
        wavePool.add(waveToAdd);
    }
}
