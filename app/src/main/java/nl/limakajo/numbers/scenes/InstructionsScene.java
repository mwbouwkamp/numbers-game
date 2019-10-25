package nl.limakajo.numbers.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import nl.limakajo.numbers.animators.PaintAnimationStarter;
import nl.limakajo.numbers.animators.PositionAnimationStarter;
import nl.limakajo.numbers.animators.ScaleAnimationStarter;
import nl.limakajo.numbers.gameObjects.Tile;
import nl.limakajo.numbers.gameObjects.TilePool;
import nl.limakajo.numbers.gameObjects.Wave;
import nl.limakajo.numbers.gameObjects.WavePool;
import nl.limakajo.numbers.layouts.GamePlayLayout;
import nl.limakajo.numbers.layouts.LayoutElementsKeys;
import nl.limakajo.numbers.main.AnimatorThread;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.Attributes;
import nl.limakajo.numberslib.numbersGame.Level;
import nl.limakajo.numberslib.utils.GameConstants;

import static nl.limakajo.numberslib.utils.GameConstants.NUMTILES;

/**
 * @author M.W.Bouwkamp
 */

public class InstructionsScene extends Scene {

    private Tile tilePressed, firstTile, secondTile;
    private boolean onShelf;
    private Point clickPosition;
    private Point tileStart;
    private int numPlus, numMin, numMult, numDiv;
    private String statusBarText;
    private long startTime;
    private WavePool wavePool;
    private TilePool tilePool;

    private final GamePlayLayout gamePlayLayout;

    private final SceneManager sceneManager;
    private final AnimatorThread animatorThread;

    LinearLayout instructionsLayout;
    TextView instructionsTextView;
    String instructionsText;

    InstructionsScene(SceneManager sceneManager, AnimatorThread animatorThread) {
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

        int[] hand = new int[] {1, 2, 3, 4, 5, 6};
        int goal = 29; // (2 + 3) * 6 - 1
        Level newLevel = new Level(hand, goal, 0, 1, 1);
        MainActivity.getGame().setLevel(newLevel);

        createTilePool();
        gamePlayLayout.getTextBox(LayoutElementsKeys.GOAL_TEXT).setText(Integer.toString(MainActivity.getGame().getLevel().getGoal()));
        gamePlayLayout.getTextBox(LayoutElementsKeys.NUM_STARS_TEXT).setText("A" + 100);
        gamePlayLayout.getTextBox(LayoutElementsKeys.NUM_LIVES_TEXT).setText("B" + 3);



        Thread instructionThread = new Thread() {
            @Override
            public void run() {
                while (animatorThread.isAnimating()) {
                    pause(100);
                }
                long startTime = System.currentTimeMillis();
                pause(500);
                instructionsText = "Try to reach the goal (29) by combining numbers 1, 2, 3, 4, 5 and 6";
                waitRemainderInstructionDuration(startTime);

                instructionsText = "";
                pause(500);

                startTime = System.currentTimeMillis();
                instructionsText = "Add two numbers by moving the tiles to the plus area";
                pause(1500);
                demonstrateOperation(0, 1, LayoutElementsKeys.PLUS_AREA, 3f, 1.4f, '+');
                waitRemainderInstructionDuration(startTime);

                instructionsText = "";
                pause(500);

                startTime = System.currentTimeMillis();
                instructionsText = "Subtract two numbers by moving the tiles to the minus area";
                pause(1500);
                demonstrateOperation(1, 0, LayoutElementsKeys.MIN_AREA, 1.3f, 2.5f, '-');
                waitRemainderInstructionDuration(startTime);

                instructionsText = "";
                pause(500);

                startTime = System.currentTimeMillis();
                instructionsText = "Undo a combination by moving the tile to the header area";
                pause(1500);
                firstTile = tilePool.getGameObjects().get(2);
                statusBarText = firstTile.toString();
                animateTileFromShelfToArea(firstTile, LayoutElementsKeys.HEADER_AREA, 2.1f);
                while (animatorThread.isAnimating()) {
                    pause(100);
                }
                statusBarText = "";
                Tile[] tilesAfterCrunching = firstTile.crunch();
                for (Tile tileAfterCrunching: tilesAfterCrunching) {
                    animatorThread.add(tileAfterCrunching.addToShelf(tilePool));
                }
                firstTile = null;
                waitRemainderInstructionDuration(startTime);

                instructionsText = "";
                pause(500);

                startTime = System.currentTimeMillis();
                instructionsText = "Tap the stars area in the top left corner to reset";
                pause(1500);
                int[] hand = new int[] {1, 2, 3, 4, 5, 6};
                int goal = 29; // (2 + 3) * 6 - 1
                Level newLevel = new Level(hand, goal, 0, 1, 1);
                MainActivity.getGame().setLevel(newLevel);
                createTilePool();
                waitRemainderInstructionDuration(startTime);

                instructionsText = "";
                pause(500);

                startTime = System.currentTimeMillis();
                instructionsText = "Tap the number of lives area in the top right corner to give up (not demonstrated)";
                waitRemainderInstructionDuration(startTime);

                instructionsText = "";
                pause(500);

                startTime = System.currentTimeMillis();
                instructionsText = "Now let's solve this level!";
                pause(1500);
                demonstrateOperation(2, 1, LayoutElementsKeys.PLUS_AREA, 2.3f, 1.4f, '+');
                pause(1500);
                demonstrateOperation(3, 4, LayoutElementsKeys.MULT_AREA, 2.1f, 1.9f, '*');
                pause(1500);
                demonstrateOperation(3, 0, LayoutElementsKeys.MIN_AREA, 2f, 1.2f, '-');
                pause(150);
                waitRemainderInstructionDuration(startTime);

                startTime = System.currentTimeMillis();
                instructionsText = "Success!";
                waitRemainderInstructionDuration(startTime);
                sceneManager.setScene(new MenuScene(sceneManager, animatorThread));
            }

            private void demonstrateOperation(int firstPosition, int secondPosition, LayoutElementsKeys minArea, float offsetFirst, float offsetSecond, char operation) {
                firstTile = tilePool.getGameObjects().get(firstPosition);
                statusBarText = firstTile.toString();
                animateTileFromShelfToArea(firstTile, minArea, offsetFirst);
                while (animatorThread.isAnimating()) {
                    pause(100);
                }
                statusBarText = "";
                pause(1000);
                if (firstPosition < secondPosition) {
                    secondPosition--;
                }
                secondTile = tilePool.getGameObjects().get(secondPosition);
                statusBarText = secondTile.toString();
                animateTileFromShelfToArea(secondTile, minArea, offsetSecond);
                while (animatorThread.isAnimating()) {
                    pause(100);
                }
                statusBarText = "";
                calculate(operation);
                while (animatorThread.isAnimating()) {
                    pause(100);
                }
            }

            private void waitRemainderInstructionDuration(long startTime) {
                long instructionDuration = 6_000;
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime < instructionDuration) {
                    pause((int) (instructionDuration - elapsedTime));
                }
            }

            private void animateTileFromShelfToArea(Tile tile, LayoutElementsKeys layoutElementsKeys, float relPosition) {
                new PositionAnimationStarter().startAnimation(
                        tile,
                        animatorThread,
                        tile.getPosition(),
                        new Point(
                                gamePlayLayout.getScreenArea(layoutElementsKeys).getArea().left + (int) (gamePlayLayout.getScreenArea(layoutElementsKeys).getArea().width() / relPosition),
                                gamePlayLayout.getScreenArea(layoutElementsKeys).getArea().top + (int) (gamePlayLayout.getScreenArea(layoutElementsKeys).getArea().height() / relPosition)),
                        Attributes.TILE_ANIMATION_TIME,
                        0);
                tilePool.remove(tile);
                pause(100);
                tilePool.startAnimating(animatorThread);
            }

            private void pause(int millis) {
                try {
                    sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        instructionThread.start();

        setInitiating(false);





    }

    @Override
    public void update() {
        gamePlayLayout.getTextBox(LayoutElementsKeys.FOOTER_TEXT).setText(statusBarText);
        tilePool.update();
        if (null != firstTile) {
            firstTile.update();
        }
        if (null != secondTile) {
            secondTile.update();
        }
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
            return null;
        } else {
            animatorThread.add(tile.addToShelf(tilePool));
            return null;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        gamePlayLayout.draw(canvas);
        wavePool.draw(canvas);
        tilePool.draw(canvas);
        if (null != firstTile) {
            firstTile.draw(canvas);
        }
        if (null != secondTile) {
            secondTile.draw(canvas);
        }
        drawTimerRound(canvas);

        setupTextView();
        if (!"".equals(instructionsTextView.getText().toString())) {
            instructionsLayout.draw(canvas);
        }


    }

    private void setupTextView() {
        Context context = MainActivity.getContext();
        instructionsLayout = new LinearLayout(context);
        instructionsTextView = new TextView(context);
        instructionsTextView.setVisibility(View.VISIBLE);
        instructionsTextView.setText(instructionsText);
        instructionsTextView.setTextColor(Color.BLACK);
        instructionsTextView.setTextSize(28);
        instructionsTextView.setBackgroundColor(Color.argb(200, 255, 255, 255));
        instructionsTextView.setGravity(Gravity.CENTER);
        instructionsTextView.setSingleLine(false);
        Typeface typeface = Typeface.createFromAsset(MainActivity.getContext().getAssets(), "fonts/Calibri.ttf");
        instructionsTextView.setTypeface(typeface);
        instructionsTextView.setPadding(Attributes.MARGIN, Attributes.MARGIN, Attributes.MARGIN, Attributes.MARGIN);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(MainActivity.getDevice().getWidth() - 6 * Attributes.MARGIN, 2 * gamePlayLayout.getScreenArea(LayoutElementsKeys.PLUS_AREA).getArea().height() / 3);
        params.leftMargin = 3 * Attributes.MARGIN;
        params.topMargin = gamePlayLayout.getScreenArea(LayoutElementsKeys.PLUS2_AREA).getArea().top - 2 * Attributes.MARGIN;

        instructionsLayout.addView(instructionsTextView, params);
        instructionsLayout.measure(MainActivity.getDevice().getWidth(), MainActivity.getDevice().getHeight());
        instructionsLayout.layout(0, 0, MainActivity.getDevice().getWidth(), MainActivity.getDevice().getHeight());
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
        if (firstTile != null && firstTile.isClicked(clickPosition)) {
            setClicekdTile(firstTile);
        }
        for (Tile tile: tilePool.getGameObjects()) {
            if (tile.isClicked(clickPosition)) {
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
            if (onShelf && !tilePressed.inArea(gamePlayLayout.getScreenArea(LayoutElementsKeys.SHELF_AREA))) {
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
        if (gamePlayLayout.getScreenArea(LayoutElementsKeys.NUM_LIVES_TEXT).getArea().contains(clickPosition.x, clickPosition.y)) {
            sceneManager.setScene(new GameOverScene(sceneManager, animatorThread));
            return;
        }
        if (gamePlayLayout.getScreenArea(LayoutElementsKeys.NUM_STARS_TEXT).getArea().contains(clickPosition.x, clickPosition.y)) {
            createTilePool();
            return;
        }
        statusBarText = "";
        if (tilePressed != null) {
            createWave(tilePressed.getPosition());
            if (onShelf) {
                new PositionAnimationStarter().startAnimation(
                        tilePressed,
                        animatorThread,
                        tilePressed.getPosition(),
                        tileStart,
                        Attributes.TILE_ANIMATION_TIME, 0);
            } else {
                if (firstTile == tilePressed) {
                    numPlus = 0;
                    numMin = 0;
                    numMult = 0;
                    numDiv = 0;
                    firstTile = consequenceTilePosition(firstTile);
                }
                else if (secondTile == tilePressed) {
                    secondTile = consequenceTilePosition(secondTile);
                    if (numPlus == 2) {
                        calculate('+');
                    } else if (numMin == 2) {
                        calculate('-');
                    } else if (numMult == 2) {
                        calculate('*');
                    } else if (numDiv == 2) {
                        calculate('/');
                    } else {
                        if (!tilePressed.inArea(gamePlayLayout.getScreenArea(LayoutElementsKeys.HEADER_AREA))) {
                            animatorThread.add(firstTile.addToShelf(tilePool));
                            firstTile = secondTile;
                            secondTile = null;
                        }
                    }
                }
            }
        }
        onShelf = true;
        tilePressed = null;
    }

    private void createTilePool() {
        tilePool = new TilePool();
        firstTile = null;
        secondTile = null;
        for (int i = 0; i < NUMTILES; i++) {
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
