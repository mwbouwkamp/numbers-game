package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;

import nl.limakajo.numbers.gameObjects.Tile;
import nl.limakajo.numbers.gameObjects.Wave;
import nl.limakajo.numbers.layouts.GamePlayLayout;
import nl.limakajo.numbers.layouts.LayoutElementsKeys;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.numbersgame.Level;
import nl.limakajo.numbers.numbersgame.Shelf;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @author M.W.Bouwkamp
 */

public class GameplayScene implements SceneInterface {

    private boolean initiating;

    private Tile tilePressed, firstTile, secondTile;
    private boolean onShelf;
    private Point clickPosition;
    private Point tileStart;
    private int numPlus, numMin, numMult, numDiv;
    private String statusBarText;
    private long startTime;
    private LinkedList<Wave> waves;
    private Shelf shelf;

    private GamePlayLayout gamePlayLayout;

    private final SceneManager sceneManager;

    GameplayScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        gamePlayLayout = new GamePlayLayout();
    }

    /**
     * Game initializer
     *
     * Starts GameThread, initializes variables and sets the initial GameState
     */
    public void init() {
        System.out.println("Init runned");
        startTime = System.currentTimeMillis();

        //Make sure that player loses a life, even when games gets to end before completing a level or running out of time
        MainActivity.getPlayer().decreaseNumLives();

        //Initialize variables
        waves = new LinkedList<>();
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
        newLevel.setUserTime(GameUtils.TIMEPENALTY);
        MainActivity.getGame().setLevel(newLevel);

        //Update level usertime to TIMEPENALTY
        DatabaseUtils.updateTableLevelsUserTimeForSpecificLevel(MainActivity.getContext(), newLevel);

        //Update status of levels, so that old ACTIVE levels become ready for uploading (UPLOAD) and newLevel becomes the new ACTIVE level
        DatabaseUtils.updateTableLevelsLevelStatusForSpecificCurrentStatus(MainActivity.getContext(), GameUtils.LevelState.ACTIVE, GameUtils.LevelState.UPLOAD);
        DatabaseUtils.updateTableLevelsLevelStatusForSpecificLevel(MainActivity.getContext(), newLevel, GameUtils.LevelState.ACTIVE);

        shelf = new Shelf();
        for (int i = 0; i < GameUtils.NUMTILES; i++) {
            Tile tileToAdd = new Tile(MainActivity.getGame().getLevel().getHand()[i]);
            addTileToShelf(tileToAdd);
        }
        gamePlayLayout.getTextBox(LayoutElementsKeys.GOAL_TEXT).setText(Integer.toString(MainActivity.getGame().getLevel().getGoal()));
        gamePlayLayout.getTextBox(LayoutElementsKeys.NUM_STARS_TEXT).setText("A" + Integer.toString(MainActivity.getPlayer().getNumStars()));
        gamePlayLayout.getTextBox(LayoutElementsKeys.NUM_LIVES_TEXT).setText("B" + Integer.toString(MainActivity.getPlayer().getNumLives()));
        initiating = false;
    }

    @Override
    public void update() {
        gamePlayLayout.getTextBox(LayoutElementsKeys.FOOTER_TEXT).setText(statusBarText);
        if (System.currentTimeMillis() - startTime > GameUtils.TIMER){
            sceneManager.setScene(new GameOverScene(sceneManager));
        }
        for (Tile tile: shelf.getTilesOnShelf()) {
            if (tile.getNumber() == MainActivity.getGame().getLevel().getGoal()) {
                MainActivity.getGame().getLevel().setUserTime((int)(System.currentTimeMillis() - startTime));
                sceneManager.setScene(new LevelCompleteScene(sceneManager));
            }
        }
        try {
            int i = 0;
            for (Tile tile : shelf.getTilesOnShelf()) {
                tile.setOriginalPosition(i);
                if (tile.getCurrentPosition().x != tile.getOriginalPosition().x && tile != tilePressed) {
                    tile.startAnimation();
                }
                i++;
                tile.update();
            }
        } catch (ConcurrentModificationException | NoSuchElementException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            for (Wave wave : waves) {
                wave.update();
                if (!wave.animates()) {
                    waves.remove(wave);
                }
            }
        } catch (ConcurrentModificationException | NoSuchElementException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    /**
     * Takes track of the relevance of the position of a Tile in play
     *
     * @param tile the Tile in play
     * @return Tile or null depending on outcome. The Tile itself is returns if the Tile is on an operator ScreenArea or null if the Tile is returned to the shelf
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
                addTileToShelf(tileAfterCrunching);
            }
            numPlus = 0;
            numMin = 0;
            numMult = 0;
            numDiv = 0;
            return null;
        } else {
            addTileToShelf(tile);
            return null;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        gamePlayLayout.draw(canvas);
        drawWaves(canvas);
        drawTiles(canvas);
        drawTimerRound(canvas);
    }

    /**
     * Draw Waves onto the canvas
     * @param canvas canvas
     */
    private void drawWaves(Canvas canvas) {
        for (Wave wave: waves) {
            if (wave != null) {
                wave.draw(canvas);
            }
        }
    }

    /**
     * Draws all Tiles onto the canvas: Tiles on the shelf and Tiles in play
     *
     * @param canvas canvas
     */
    private void drawTiles(Canvas canvas) {
        for (Tile tile: shelf.getTilesOnShelf()) {
            tile.draw(canvas);
        }
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
        double timeFraction = (System.currentTimeMillis() - startTime) / (double) GameUtils.TIMER;
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
        for (Tile tile: shelf.getTilesOnShelf()) {
            if (tile.isClicked(clickPosition)) {
                tile.stopAnimation();
                setClicekdTile(tile);
            }
        }
    }

    private void setClicekdTile(Tile tileClicked) {
        tilePressed = tileClicked;
        tileStart = new Point(tilePressed.getCurrentPosition());
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
                    for (Tile tile: shelf.getTilesOnShelf()) {
                        tile.stopAnimation();
                    }
                    if (firstTile == null) {
                        firstTile = tilePressed;
                        shelf.removeTile(tilePressed);
                    } else {
                        secondTile = tilePressed;
                        shelf.removeTile(tilePressed);
                    }
                    for (Tile tile: shelf.getTilesOnShelf()) {
                        tile.stopAnimation();
                    }
                }
            }
            tilePressed.setCurrentPosition(new Point(tileStart.x + (int) event.getX() - clickPosition.x, tileStart.y + (int) event.getY() - clickPosition.y));
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
            waves.add(new Wave(tilePressed.getCurrentPosition()));
            if (firstTile == null && secondTile == null) {
                tilePressed.setCurrentPosition(tilePressed.getOriginalPosition());
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
                addTileToShelf(firstTile);
                firstTile = secondTile;
                secondTile = null;
            }
        }
    }

    /**
     * Performs the actual calculations, making new Tiles based on the operation or returns Tiles to the shelf when the operation is not valid
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
                    addTileToShelf(firstTile);
                    addTileToShelf(secondTile);
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
                    addTileToShelf(firstTile);
                    addTileToShelf(secondTile);
                    firstTile = null;
                    secondTile = null;
                }
                break;
        }
        if (newValue > 0) {
            Tile[] compositionNewTile = {firstTile, secondTile};
            Tile toAdd = new Tile(newValue, compositionNewTile, firstTile.getColorIndex() + secondTile.getColorIndex() + 1);
            addTileToShelf(toAdd);
            firstTile = null;
            secondTile = null;
            numPlus = 0;
            numMin = 0;
            numMult = 0;
            numDiv = 0;
        }
    }

    private void addTileToShelf(Tile toAdd) {
        int position = shelf.addTile(toAdd);
        toAdd.toShelf(position);
    }

    @Override
    public boolean getInitiating() {
        return initiating;
    }

    @Override
    public void setInitiating(boolean initiating) {
        this.initiating = initiating;
    }
}
