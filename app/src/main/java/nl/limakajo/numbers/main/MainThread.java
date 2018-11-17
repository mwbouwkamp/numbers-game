package nl.limakajo.numbers.main;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;

/**
 * @author M.W.Bouwkamp
 */

public class MainThread extends Thread {
    private static final int MAX_FPS = 30;
    private final SurfaceHolder surfaceHolder;
    private final GamePanel gamePanel;
    private boolean running;
    private static Canvas canvas;

    MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    gamePanel.update();
                    gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(null != canvas){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if(waitTime > 0) {
                    sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == MAX_FPS) {
                double averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
//                System.out.println(averageFPS);
            }

            int numNew = DatabaseUtils.getLevelsWithSpecificStatus(MainActivity.getContext(), GameUtils.LevelState.NEW).size();
            int numActive = DatabaseUtils.getLevelsWithSpecificStatus(MainActivity.getContext(), GameUtils.LevelState.ACTIVE).size();
            int numUpload = DatabaseUtils.getLevelsWithSpecificStatus(MainActivity.getContext(), GameUtils.LevelState.UPLOAD).size();
            int numComplete = DatabaseUtils.getLevelsWithSpecificStatus(MainActivity.getContext(), GameUtils.LevelState.COMPLETED).size();
            System.out.println(
                    "Level status count. NEW: " +
                    Integer.toString(numNew) +
                    ";  ACTIVE" +
                    Integer.toString(numActive) +
                    ";  UPLOAD" +
                    Integer.toString(numUpload) +
                    ";  COMPLETE" +
                    Integer.toString(numComplete) );
        }
    }

    public void onPause() {
        setRunning(false);
    }

    public void onResume() {
        setRunning(true);
    }

    void setRunning(boolean running) {
        this.running = running;
    }
}
