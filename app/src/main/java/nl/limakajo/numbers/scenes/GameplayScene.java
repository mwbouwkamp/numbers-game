package nl.limakajo.numbers.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.MotionEvent;

import nl.limakajo.numbers.gameObjects.Tile;
import nl.limakajo.numbers.gameObjects.Wave;
import nl.limakajo.numbers.layouts.GamePlayLayout;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.numbersgame.Level;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static nl.limakajo.numbers.utils.GameUtils.GameState.GAME_OVER_STATE;
import static nl.limakajo.numbers.utils.GameUtils.GameState.GAME_STATE;
import static nl.limakajo.numbers.utils.GameUtils.GameState.LEVEL_COMPLETE_STATE;

/**
 * @author M.W.Bouwkamp
 */

public class GameplayScene implements SceneInterface {

    private static GameUtils.GameState state;

    private Level level;

    private Tile tilePressed, firstTile, secondTile;
    private boolean onShelf;
    private Point clickPosition;
    private Point tileStart;
    private int numPlus, numMin, numMult, numDiv;
    private String statusBarText;
    private long startTime;
    private LinkedList<Wave> waves;
    private LinkedList<Tile> tilesOnShelf;

    private GamePlayLayout gamePlayLayout;

    private SceneManager sceneManager;

    GameplayScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        init();
    }

    /**
     * Game initializer
     *
     * Starts GameThread, initializes variables and sets the initial GameState
     */
    public void init() {
        state = GAME_STATE;
        startTime = System.currentTimeMillis();

        //Make sure that player loses a life, even when games gets to end before completing a level or running out of time
        //TODO: At the moment, if the players plays a single game and the app is stopped and restarted: two lifes are lost!
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
        level = getLevel();
        //TODO: After selecting a level, immediately set the usertime to the TIMEPENALTY, both in the table levels and completedlevels
        gamePlayLayout = new GamePlayLayout();
        tilesOnShelf = new LinkedList<>();
        for (int i = 0; i < GameUtils.NUMTILES; i++) {
            tilesOnShelf.add(new Tile(level.getHand()[i], i));
        }
        gamePlayLayout.getTextBox("goalText").setText(Integer.toString(level.getGoal()));
    }

    /**
     * Gets level information from local database
     * Picks the level for which the average time of all users so far is closest to the average time of the user
     */
    private Level getLevel() {
        Level toReturn = null;
        LinkedList<Level> levels = DatabaseUtils.getLevels(MainActivity.getContext());
        int userAverageTime = DatabaseUtils.getAverageTime(MainActivity.getContext());
        int timeDifferenceSelectedLevel = 999999;
        for (Level level: levels) {
            if (level.getUserTime() == 0) {
                if (Math.abs(level.getAverageTime() - userAverageTime) < timeDifferenceSelectedLevel) {
                    toReturn = level;
                    timeDifferenceSelectedLevel = Math.abs(toReturn.getAverageTime() - userAverageTime);
                }
            }
        }
        return toReturn;
    }

    @Override
    public void update() {
        if (state == GAME_STATE) {
            try {
                int i = 0;
                for (Tile tile: tilesOnShelf) {
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
                for (Wave wave: waves) {
                    wave.update();
                    if (!wave.animates()) {
                        waves.remove(wave);
                    }
                }
            } catch (ConcurrentModificationException | NoSuchElementException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        else if (state == GameUtils.GameState.GAME_OVER_STATE) {
            sceneManager.setScene(GAME_OVER_STATE.toString());
        }
        else if (state == GameUtils.GameState.LEVEL_COMPLETE_STATE) {
            sceneManager.setScene(LEVEL_COMPLETE_STATE.toString());
        }
    }


    /**
     * Takes track of the relevance of the position of a Tile in play
     *
     * @param tile the Tile in play
     * @return Tile or null depending on outcome. The Tile itself is returns if the Tile is on an operator ScreenArea or null if the Tile is returned to the shelf
     */
    private Tile consequenceTilePosition(Tile tile) {
        if (tile.inArea(gamePlayLayout.getScreenArea("plus"))) {
            numPlus++;
            numMin = 0;
            numMult = 0;
            numDiv = 0;
            return tile;
        } else if (tile.inArea(gamePlayLayout.getScreenArea("min"))) {
            numMin++;
            numPlus = 0;
            numMult = 0;
            numDiv = 0;
            return tile;
        } else if (tile.inArea(gamePlayLayout.getScreenArea("mult"))) {
            numMult++;
            numPlus = 0;
            numMin = 0;
            numDiv = 0;
            return tile;
        } else if (tile.inArea(gamePlayLayout.getScreenArea("div"))) {
            numDiv++;
            numPlus = 0;
            numMin = 0;
            numMult = 0;
            return tile;
        } else if (tile.inArea(gamePlayLayout.getScreenArea("header"))) {
            tile.crunch(tilesOnShelf);
            numPlus = 0;
            numMin = 0;
            numMult = 0;
            numDiv = 0;
            return null;
        } else {
            tile.toShelf(tilesOnShelf);
            return null;
        }
    }

    /**
     * Checks if network is available
     *
     * @param ctx context
     * @return true if network is available
     */
    public boolean isNetworkAvailable(Context ctx)
    {
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()&& cm.getActiveNetworkInfo().isAvailable()&& cm.getActiveNetworkInfo().isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Performs the actual calculations, making new Tiles based on the operation or returns Tiles to the shelf when the operation is not valid
     *
     * @param operator operator
     */
    private void calculate(char operator) {
        int newValue = 0;
        if (operator == '+') {
            newValue = firstTile.getNumber() + secondTile.getNumber();
        } else if (operator == '-') {
            if (firstTile.getNumber() >= secondTile.getNumber()) {
                newValue = firstTile.getNumber() - secondTile.getNumber();
            } else {
                statusBarText = "Results in negative integer";
                firstTile.toShelf(tilesOnShelf);
                secondTile.toShelf(tilesOnShelf);
                firstTile = null;
                secondTile = null;
            }
        } else if (operator == '*') {
            newValue = firstTile.getNumber() * secondTile.getNumber();
        } else if (operator == '/') {
            if (firstTile.getNumber() % secondTile.getNumber() == 0) {
                newValue = firstTile.getNumber() / secondTile.getNumber();
            } else {
                statusBarText = "Results in non-integer";
                firstTile.toShelf(tilesOnShelf);
                secondTile.toShelf(tilesOnShelf);
                firstTile = null;
                secondTile = null;
            }
        }
        if (newValue > 0) {
            Tile[] compositionNewTile = {firstTile, secondTile};
            Tile toAdd = new Tile(newValue, tilesOnShelf.size(), compositionNewTile, firstTile.getColorIndex() + secondTile.getColorIndex() + 1);
            toAdd.toShelf(tilesOnShelf);
            firstTile = null;
            secondTile = null;
            numPlus = 0;
            numMin = 0;
            numMult = 0;
            numDiv = 0;
            for (Tile tile: tilesOnShelf) {
                if (tile.getNumber() == level.getGoal()) {
                    state = GameUtils.GameState.LEVEL_COMPLETE_STATE;
                    MainActivity.getPlayer().increaseNumLives();
                    int userTime = (int)(System.currentTimeMillis() - startTime);
                    DatabaseUtils.updateTableLevelsUserTime(MainActivity.getContext(), level, userTime);
                    DatabaseUtils.updateTableCompletedLevelsUserTime(MainActivity.getContext(), level, userTime);
                    MainActivity.launchDownloadService();
                    MainActivity.launchUploadService();
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

        gamePlayLayout.getScreenAreas().get("fullscreen").draw(canvas);
        gamePlayLayout.getScreenAreas().get("plus").draw(canvas);
        gamePlayLayout.getScreenAreas().get("plus2").draw(canvas);
        gamePlayLayout.getScreenAreas().get("min").draw(canvas);
        gamePlayLayout.getScreenAreas().get("min2").draw(canvas);
        gamePlayLayout.getScreenAreas().get("mult").draw(canvas);
        gamePlayLayout.getScreenAreas().get("mult2").draw(canvas);
        gamePlayLayout.getScreenAreas().get("div").draw(canvas);
        gamePlayLayout.getScreenAreas().get("div2").draw(canvas);
        gamePlayLayout.getTextBoxes().get("goalText").draw(canvas);
        gamePlayLayout.getTextBoxes().get("footerText").draw(canvas);
        gamePlayLayout.getTextBoxes().get("plusText").draw(canvas);
        gamePlayLayout.getTextBoxes().get("minText").draw(canvas);
        gamePlayLayout.getTextBoxes().get("multText").draw(canvas);
        gamePlayLayout.getTextBoxes().get("divText").draw(canvas);
        gamePlayLayout.getTextBoxes().get("numLivesText").draw(canvas);



        //Changes GameState to gameover when  GameState is running and play time has elapsed
        if (state == GAME_STATE && System.currentTimeMillis() - startTime > GameUtils.TIMER){
            state = GameUtils.GameState.GAME_OVER_STATE;
            DatabaseUtils.updateTableLevelsUserTime(MainActivity.getContext(), level, GameUtils.TIMEPENALTY);
            DatabaseUtils.updateTableCompletedLevelsUserTime(MainActivity.getContext(), level, GameUtils.TIMEPENALTY);
            MainActivity.launchDownloadService();
            MainActivity.launchUploadService();
        }

//        drawNumLives(canvas);
        gamePlayLayout.getTextBox("footerText").setText(statusBarText);

        for (Wave wave: waves) {
            if (wave != null) {
                wave.draw(canvas);
            }
        }

        drawTiles(canvas);
        if (state == GAME_STATE) {
            drawTimerRound(canvas);
        }
        if (state == GameUtils.GameState.GAME_OVER_STATE) {
            //TODO: Check if this needs to be reimplemented
//            drawGameOver(canvas);
        }
        if (state == GameUtils.GameState.LEVEL_COMPLETE_STATE) {
            //TODO: Check if this needs to be reimplemented
//            drawLevelComplete(canvas);
        }
    }

    /**
     * Draws all Tiles onto the canvas: Tiles on the shelf and Tiles in play
     *
     * @param canvas canvas
     */
    private void drawTiles(Canvas canvas) {
        for (Tile tile: tilesOnShelf) {
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
        RectF rect = new RectF(gamePlayLayout.getScreenArea("timerRound").getArea());
        paint.setColor(Color.rgb(80, 80, 80));
        canvas. drawArc(rect, 0, 360, false, paint);
        paint.setColor(Color.rgb((int) (255 * timeFraction), (int) (255 * (1 - timeFraction)), 0));
        canvas.drawArc(rect, 270, (float) ((1 - timeFraction) * 360), false, paint);
    }



    @Override
    public void terminate() {
        sceneManager.setScene(GAME_STATE.toString());
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (state) {
            case GAME_STATE:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    runningTouchDown(event);
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    runningTouchDragged(event);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    runningTouchUp(event);
                }
                break;
            case LEVEL_COMPLETE_STATE:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    levelCompleteTouchDown(event);
                }
                break;
            case GAME_OVER_STATE:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    gameOverTouchDown(event);
                }
                break;
            default:
                break;
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
                tilePressed = firstTile;
                tileStart = new Point(tilePressed.getCurrentPosition());
                statusBarText = firstTile.toString();
            }
        }
        for (Tile tile: tilesOnShelf) {
            if (tile.isClicked(clickPosition)) {
                tile.stopAnimation();
                tilePressed = tile;
                tileStart = new Point(tilePressed.getCurrentPosition());
                statusBarText = tilePressed.toString();
            }
        }
        if (tilePressed != null) {
            tilePressed.stopAnimation();
        }
    }

    /**
     * Orchestrates what to do with a dragging movement in the running GameState
     *
     * @param event event
     */
    private void runningTouchDragged(MotionEvent event) {
        if (tilePressed != null) {
            if (tilePressed != firstTile) {
                if (onShelf && !tilePressed.inArea(gamePlayLayout.getScreenArea("shelf"))) {
                    onShelf = false;
                    for (Tile tile: tilesOnShelf) {
                        tile.stopAnimation();
                    }
                    if (firstTile == null) {
                        firstTile = tilePressed;
                        tilesOnShelf.remove(tilePressed);
                    } else {
                        secondTile = tilePressed;
                        tilesOnShelf.remove(tilePressed);
                    }
                    for (Tile tile: tilesOnShelf) {
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
            if (numPlus == 2) {
                calculate('+');
            } else if (numMin == 2) {
                calculate('-');
            } else if (numMult == 2) {
                calculate('*');
            } else if (numDiv == 2) {
                calculate('/');
            } else {
                firstTile.toShelf(tilesOnShelf);
                firstTile = secondTile;
                secondTile = null;
            }
        }
    }

    /**
     * Orchestrates what to do when the screen is released in the levelcomplete GameState
     *
     * @param event event
     */
    private void levelCompleteTouchDown(MotionEvent event) {
        if (gamePlayLayout.getScreenArea("levelcomplete").getArea().contains((int) event.getX(), (int) event.getY())) {
            state = GAME_STATE;
            init();
        }
    }

    /**
     * Orchestrates what to do when the screen is released in the gameover GameState
     *
     * @param event event
     */
    private void gameOverTouchDown(MotionEvent event) {
        if (gamePlayLayout.getScreenArea("gameover").getArea().contains((int) event.getX(), (int) event.getY())) {
            state = GAME_STATE;
            init();
        }
    }

}
