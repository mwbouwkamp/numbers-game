package nl.limakajo.numbers.main;

import android.content.Context;
import android.content.SharedPreferences;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.numbersgame.Player;
import nl.limakajo.numbers.utils.GameUtils;
import nl.limakajo.numberslib.GameConstants;

import java.util.Date;

/**
 * @author M.W.Bouwkamp
 */
public class NumLivesThread extends Thread {

	private final SharedPreferences prefs;
	private final Context context;
	private final Player player;
	private Date lastCheckNumLives;
	private boolean running;


	public NumLivesThread(Context context, Player player) {
		this.context = context;
		this.player = player;
		prefs = context.getSharedPreferences(context.getString(R.string.prefs_name), Context.MODE_PRIVATE);
		lastCheckNumLives = new Date(prefs.getLong(context.getString(R.string.prefs_last_update_number_of_lifes_key), context.getResources().getInteger(R.integer.doesnt_exist)));
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(999);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long timePassedLastCheckNumLives = new Date().getTime() - lastCheckNumLives.getTime();
			if (timePassedLastCheckNumLives > GameConstants.TIME_TO_NEW_LIFE) {
				if (player.getNumLives() < GameConstants.MAX_NUMLIVES) {
					player.increaseNumLives(1);
					lastCheckNumLives = new Date(lastCheckNumLives.getTime() + GameConstants.TIME_TO_NEW_LIFE);
				}
			}
			if (player.getNumLives() == GameConstants.MAX_NUMLIVES) {
				lastCheckNumLives = new Date();
			}
			prefs.edit().putLong(context.getString(R.string.prefs_last_update_number_of_lifes_key), lastCheckNumLives.getTime()).apply();
            prefs.edit().putInt(context.getString(R.string.prefs_number_of_lives_key), player.getNumLives()).apply();
            prefs.edit().putInt(context.getString(R.string.prefs_number_of_stars_key), player.getNumStars()).apply();
		}
	}

	public Date getLastCheckNumLives() {
		return lastCheckNumLives;
	}

	public void onPause() {
		setRunning(false);
	}

	public void onResume() {
		setRunning(true);
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
