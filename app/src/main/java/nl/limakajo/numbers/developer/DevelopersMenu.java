package nl.limakajo.numbers.developer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.main.GamePanel;
import nl.limakajo.numbers.main.NumLivesThread;
import nl.limakajo.numbers.numbersgame.Player;

/**
 * @author M.W.Bouwkamp
 */
public class DevelopersMenu {

    private final Context context;
    private final Player player;
    private final NumLivesThread checkNumberOfLivesThread;
    private Button playButton;
    private Button addButton;
    private EditText averageUserTimeText;
    private EditText numLivesText;
    private EditText lastCheckNumLivesText;

    public DevelopersMenu(final Context context, final Player player, final NumLivesThread checkNumberOfLivesThread) {
        this.context = context;
        this.player = player;
        this.checkNumberOfLivesThread = checkNumberOfLivesThread;
        final Activity activity = (Activity)context;
        activity.setContentView(R.layout.menu);

        playButton = (Button) activity.findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                activity.setContentView(new GamePanel(context));
            }
        });

        addButton = (Button) activity.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO: At some point this button can be used to test the ad function
            }
        });

        //EditText
        averageUserTimeText = (EditText) activity.findViewById(R.id.userAverageTime);
        averageUserTimeText.setText(Integer.toString(Math.round(player.getUserAverageTime() / 1000)));
        numLivesText = (EditText) activity.findViewById(R.id.userNumLives);
        numLivesText.setText(Integer.toString(player.getNumLives()));
        lastCheckNumLivesText = (EditText) activity.findViewById(R.id.userLastCheckNumLives);


        final Thread numLivesThread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(999);
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (player.getNumLives() <= 0) {
                                    //TODO: Start advertising
                                }
                                long timePassedLastCheckNumLives = new Date().getTime() - checkNumberOfLivesThread.getLastCheckNumLives().getTime();
                                lastCheckNumLivesText.setText(Long.toString(timePassedLastCheckNumLives / 60000) + ":" + Long.toString(timePassedLastCheckNumLives % 60000 / 1000));
                                numLivesText.setText(Integer.toString(player.getNumLives()));

                            }
                        });
                    }
                }
                catch (InterruptedException e) {
                }
            }
        };
        numLivesThread.start();
    }

}
