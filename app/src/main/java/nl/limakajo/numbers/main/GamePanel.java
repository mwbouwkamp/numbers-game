package nl.limakajo.numbers.main;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import nl.limakajo.numbers.scenes.SceneManager;


/**
 * Created by mwbou on 9-9-2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Context ctx;
    private MainThread thread;

    private SceneManager sceneManager;

    public GamePanel(Context context) {
        super(context);
        this.ctx = context;
        getHolder().addCallback(this);

        sceneManager = new SceneManager();
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) { e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        sceneManager.recieveTouch(event);
        return true;
    }

    public void update() {
        sceneManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        sceneManager.draw(canvas);
    }
}
