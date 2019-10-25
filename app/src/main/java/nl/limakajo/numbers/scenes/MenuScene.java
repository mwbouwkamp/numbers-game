package nl.limakajo.numbers.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import nl.limakajo.numbers.layouts.LayoutElementsKeys;
import nl.limakajo.numbers.main.AnimatorThread;
import nl.limakajo.numbers.layouts.MenuLayout;

/**
 * @author M.W.Bouwkamp
 */

public class MenuScene extends Scene {

    private final SceneManager sceneManager;
    private final AnimatorThread animatorThread;
    private final MenuLayout menuLayout;

    MenuScene(SceneManager sceneManager, AnimatorThread animatorThread) {
        this.sceneManager = sceneManager;
        menuLayout = new MenuLayout();
        this.animatorThread = animatorThread;
    }

    @Override
    public void init() {
        this.animatorThread.removeAll();
        setInitiating(false);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        menuLayout.draw(canvas);
    }

    @Override
    public void terminate() {

    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (menuLayout.getTextBox(LayoutElementsKeys.HELP_TEXT).getArea().contains((int) event.getX(), (int) event.getY())) {
            sceneManager.setScene(new InstructionsScene(sceneManager, animatorThread));
        }
        else {
            sceneManager.setScene(new GameplayScene(sceneManager, animatorThread));
        }
    }
}
