package nl.limakajo.numbers.main;

import android.content.Context;
import android.content.SharedPreferences;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.numbersgame.Player;
import nl.limakajo.numbers.utils.GameUtils;

import java.util.Date;

/**
 * @author M.W.Bouwkamp
 */
public class NumLivesThread extends Thread {

	private final SharedPreferences prefs;
	private final Context context;
	private final Player player;
	private Date lastCheckNumLives;

	public NumLivesThread(Context context, Player player) {
		this.context = context;
		this.player = player;
		prefs = context.getSharedPreferences(context.getString(R.string.prefs_name), Context.MODE_PRIVATE);
		lastCheckNumLives = new Date(prefs.getLong(context.getString(R.string.prefs_last_update_number_of_lifes_key), context.getResources().getInteger(R.integer.doesnt_exist)));
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(999);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long timePassedLastCheckNumLives = new Date().getTime() - lastCheckNumLives.getTime();
			if (timePassedLastCheckNumLives > GameUtils.TIME_TO_NEW_LIFE) {
				if (player.getNumLives() < GameUtils.MAX_NUMLIVES) {
					player.increaseNumLives();
					lastCheckNumLives = new Date(lastCheckNumLives.getTime() + GameUtils.TIME_TO_NEW_LIFE);
				}
			}
			if (player.getNumLives() == GameUtils.MAX_NUMLIVES) {
				lastCheckNumLives = new Date();
			}
			prefs.edit().putLong(context.getString(R.string.prefs_last_update_number_of_lifes_key), lastCheckNumLives.getTime()).apply();
			prefs.edit().putInt(context.getString(R.string.prefs_number_of_lives_key), player.getNumLives()).apply();
		}
	}

	public Date getLastCheckNumLives() {
		return lastCheckNumLives;
	}

}
