package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;

import nl.limakajo.numbers.animators.PaintAnimator;
import nl.limakajo.numbers.animators.ScaleAnimator;
import nl.limakajo.numbers.gameObjects.Tile;
import nl.limakajo.numbers.gameObjects.Wave;
import nl.limakajo.numbers.gameObjects.WavePool;
import nl.limakajo.numbers.layouts.GamePlayLayout;
import nl.limakajo.numbers.layouts.LayoutElementsKeys;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.gameObjects.TilePool;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;
import nl.limakajo.numbers.main.AnimatorThread;
import nl.limakajo.numberslib.numbersGame.Level;
import nl.limakajo.numberslib.utils.GameConstants;

import java.util.Arrays;
import java.util.List;

/**
 * @author M.W.Bouwkamp
 */

public class GameplayScene extends Scene {

    private Tile tilePressed, firstTile, secondTile;
    private boolean onShelf;
    private Point clickPosition;
    private Point tileStart;
    private int numPlus, numMin, numMult, numDiv;
    private String statusBarText;
    private long startTime;
    private WavePool wavePool;
    private TilePool tilePool;

    private GamePlayLayout gamePlayLayout;

    private final SceneManager sceneManager;
    private final AnimatorThread animatorThread;

    GameplayScene(SceneManager sceneManager, AnimatorThread animatorThread) {
        this.sceneManager = sceneManager;
        this.animatorThread = animatorThread;
        this.gamePlayLayout = new GamePlayLayout();
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

        //Initialize variables
        wavePool = new WavePool();
        tilePressed = null;
        firstTile = null;
        secondTile = null;
        onShelf = true;
        clickPosition = new Point(0, 0);
        numPlus = 0;
        numMin = 0;
        numMult = 0;
        numDiv = 0;
        statusBarText = "";

        //Construct a level and update the ScreenLayout goal accordingly
        Level newLevel = DatabaseUtils.getLevelWithAverageTimeCloseToUserAverageTime(MainActivity.getContext());
        newLevel.setUserTime(GameConstants.TIMEPENALTY);
        MainActivity.getGame().setLevel(newLevel);

        //Update level usertime to TIMEPENALTY
        DatabaseUtils.updateTableLevelsUserTimeForSpecificLevel(MainActivity.getContext(), newLevel);

        //Update status of levels, so that old ACTIVE levels become ready for uploading (UPLOAD) and newLevel becomes the new ACTIVE level
        DatabaseUtils.updateTableLevelsLevelStatusForSpecificCurrentStatus(MainActivity.getContext(), GameUtils.LevelState.ACTIVE, GameUtils.LevelState.UPLOAD);
        DatabaseUtils.updateTableLevelsLevelStatusForSpecificLevel(MainActivity.getContext(), newLevel, GameUtils.LevelState.ACTIVE);

        tilePool = new TilePool();
        for (int i = 0; i < GameConstants.NUMTILES; i++) {
            Tile tileToAdd = new Tile(MainActivity.getGame().getLevel().getHand()[i]);
            animatorThread.add(tileToAdd.addToShelf(tilePool));
        }
        gamePlayLayout.getTextBox(LayoutElementsKeys.GOAL_TEXT).setText(Integer.toString(MainActivity.getGame().getLevel().getGoal()));
        gamePlayLayout.getTextBox(LayoutElementsKeys.NUM_STARS_TEXT).setText("A" + Integer.toString(MainActivity.getPlayer().getNumStars()));
        gamePlayLayout.getTextBox(LayoutElementsKeys.NUM_LIVES_TEXT).setText("B" + Integer.toString(MainActivity.getPlayer().getNumLives()));
        setInitiating(false);
    }

    @Override
    public void update() {
        gamePlayLayout.getTextBox(LayoutElementsKeys.FOOTER_TEXT).setText(statusBarText);
        if (System.currentTimeMillis() - startTime > GameConstants.TIMER){
            sceneManager.setScene(new GameOverScene(sceneManager, animatorThread));
        }
        for (Tile tile: tilePool.getGameObjects()) {
            if (tile.getNumber() == MainActivity.getGame().getLevel().getGoal()) {
                MainActivity.getGame().getLevel().setUserTime((int)(System.currentTimeMillis() - startTime));
                sceneManager.setScene(new LevelCompleteScene(sceneManager, animatorThread));
            }
        }
        tilePool.update();
        wavePool.update();
    }


    /**
     * Takes track of the relevance of the position of a Tile in play
     *
     * @param tile the Tile in play
     * @return Tile or null depending on outcome. The Tile itself is returns if the Tile is on an operator ScreenArea or null if the Tile is returned to the tilePool
     */
    private Tile consequenceTilePosition(Tile tile) {
        if (tile.inArea(gamePlayLayout.getScreenArea(LayoutElementsKeys.PLUS_AREA))) {
            numPlus++;
            numMin = 0;
            numMult = 0;
            numDiv = 0;
            return tile;
        } else if (tile.inArea(gamePlayLayout.getScreenArea(LayoutElementsKeys.MIN_AREA))) {
            numMin++;
            numPlus = 0;
            numMult = 0;
            numDiv = 0;
            return tile;
        } else if (tile.inArea(gamePlayLayout.getScreenArea(LayoutElementsKeys.MULT_AREA))) {
            numMult++;
            numPlus = 0;
            numMin = 0;
            numDiv = 0;
            return tile;
        } else if (tile.inArea(gamePlayLayout.getScreenArea(LayoutElementsKeys.DIV_AREA))) {
            numDiv++;
            numPlus = 0;
            numMin = 0;
            numMult = 0;
            return tile;
        } else if (tile.inArea(gamePlayLayout.getScreenArea(LayoutElementsKeys.HEADER_AREA))) {
            Tile[] tilesAfterCrunching = tile.crunch();
            for (Tile tileAfterCrunching: tilesAfterCrunching) {
                animatorThread.add(tileAfterCrunching.addToShelf(tilePool));
            }
            numPlus = 0;
            numMin = 0;
            numMult = 0;
            numDiv = 0;
            return null;
        } else {
            animatorThread.add(tile.addToShelf(tilePool));
            return null;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        System.out.println("DRAWING GAMEPLAY");
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
        RectF rect = new RectF(gamePlayLayout.getScreenArea(LayoutElementsKeys.TIMER_AREA).getArea());
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
        clickPosition.x = (int) event.getX();
        clickPosition.y = (int) event.getY();
        if (firstTile != null) {
            if (firstTile.isClicked(clickPosition)) {
                setClicekdTile(firstTile);
            }
        }
        for (Tile tile: tilePool.getGameObjects()) {
            if (tile.isClicked(clickPosition)) {
                animatorThread.remove(tile.getPositionAnimator());
                setClicekdTile(tile);
            }
        }
    }

    private void setClicekdTile(Tile tileClicked) {
        tilePressed = tileClicked;
        tileStart = new Point(tilePressed.getPosition());
        statusBarText = tilePressed.toString();
    }

    /**
     * Orchestrates what to do with a dragging movement in the running GameState
     *
     * @param event event
     */
    private void runningTouchDragged(MotionEvent event) {
        if (tilePressed != null) {
            if (tilePressed != firstTile) {
                if (onShelf && !tilePressed.inArea(gamePlayLayout.getScreenArea(LayoutElementsKeys.SHELF_AREA))) {
                    onShelf = false;
                    tilePool.remove(tilePressed);
                    animatorThread.addAll(tilePool.startAnimating());
                    if (firstTile == null) {
                        firstTile = tilePressed;
                        tilePool.remove(tilePressed);
                    } else {
                        secondTile = tilePressed;
                        tilePool.remove(tilePressed);
                    }
                }
            }
            tilePressed.setPosition(new Point(tileStart.x + (int) event.getX() - clickPosition.x, tileStart.y + (int) event.getY() - clickPosition.y));
        }
    }

    /**
     * Orchestrates what to do when the screen is released in the running GameState
     *
     * @param event event
     */
    private void runningTouchUp(MotionEvent event) {
        statusBarText = "";
        if (tilePressed != null) {
            createWave(tilePressed.getPosition());
            if (firstTile == null && secondTile == null) {
                //TODO: Make this logic work again!
//                tilePressed.setPosition(tilePressed.getOriginalPosition());
            } else if (secondTile == null) {
                firstTile = consequenceTilePosition(firstTile);
            } else {
                secondTile = consequenceTilePosition(secondTile);
            }
        }
        tilePressed = null;
        onShelf = true;
        if (secondTile != null) {
            //Convert into a switch
            if (numPlus == 2) {
                calculate('+');
            } else if (numMin == 2) {
                calculate('-');
            } else if (numMult == 2) {
                calculate('*');
            } else if (numDiv == 2) {
                calculate('/');
            } else {
                animatorThread.add(firstTile.addToShelf(tilePool));
                firstTile = secondTile;
                secondTile = null;
            }
        }
    }

    /**
     * Creates a Wave and its WaveAnimator and adds them to the WavePool and Animators in the gamePlayAnimatorThread, respectively
     *
     * @param position      the Position of the wave
     */
    private void createWave(Point position) {
        Wave waveToAdd = new Wave(position);
        ScaleAnimator scaleAnimatorToAdd = new ScaleAnimator(Attributes.WAVE_ANIMATION_TIME);
        scaleAnimatorToAdd.init(waveToAdd.getScale(), 10.0f);
        waveToAdd.setScaleAnimator(scaleAnimatorToAdd);
        scaleAnimatorToAdd.startAnimation();
        animatorThread.add(scaleAnimatorToAdd);
        PaintAnimator paintAnimatorToAdd = new PaintAnimator(Attributes.WAVE_ANIMATION_TIME);
        paintAnimatorToAdd.init(waveToAdd.getPaint(), Attributes.WAVE_PAINT_END);
        waveToAdd.setPaintAnimator(paintAnimatorToAdd);
        paintAnimatorToAdd.startAnimation();
        animatorThread.add(paintAnimatorToAdd);
        wavePool.add(waveToAdd);
    }

    /**
     * Performs the actual calculations, making new Tiles based on the operation or returns Tiles to the tilePool when the operation is not valid
     *
     * @param operator operator
     */
    private void calculate(char operator) {
        int newValue = 0;
        switch (operator) {
            case '+':
                newValue = firstTile.getNumber() + secondTile.getNumber();
                break;
            case '-':
                if (firstTile.getNumber() >= secondTile.getNumber()) {
                    newValue = firstTile.getNumber() - secondTile.getNumber();
                } else {
                    statusBarText = "Results in negative integer";
                    animatorThread.add(firstTile.addToShelf(tilePool));
                    animatorThread.add(secondTile.addToShelf(tilePool));
                    firstTile = null;
                    secondTile = null;
                }
                break;
            case '*':
                newValue = firstTile.getNumber() * secondTile.getNumber();
                break;
            case '/':
                if (firstTile.getNumber() % secondTile.getNumber() == 0) {
                    newValue = firstTile.getNumber() / secondTile.getNumber();
                } else {
                    statusBarText = "Results in non-integer";
                    animatorThread.add(firstTile.addToShelf(tilePool));
                    animatorThread.add(secondTile.addToShelf(tilePool));
                    firstTile = null;
                    secondTile = null;
                }
                break;
        }
        if (newValue > 0) {
            Tile[] compositionNewTile = {firstTile, secondTile};
            Tile toAdd = new Tile(newValue, compositionNewTile, firstTile.getColorIndex() + secondTile.getColorIndex() + 1);
            animatorThread.add(toAdd.addToShelf(tilePool));
            firstTile = null;
            secondTile = null;
            numPlus = 0;
            numMin = 0;
            numMult = 0;
            numDiv = 0;
        }
    }

}
