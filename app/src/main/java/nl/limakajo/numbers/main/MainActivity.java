package nl.limakajo.numbers.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import nl.limakajo.numbers.R;
import nl.limakajo.numbers.numbersGame.Device;
import nl.limakajo.numbers.numbersGame.Game;
import nl.limakajo.numbers.numbersGame.Player;
import nl.limakajo.numbers.sync.NumbersSyncIntentService;
import nl.limakajo.numbers.sync.NumbersSyncTasks;
import nl.limakajo.numberslib.utils.GameConstants;

import java.util.Date;

/**
 * Main Activity
 *
 * @author M.W.Bouwkamp
 *
 * Launches the game after setting up the player and the device
 * Starts service for uploading previously completed levels (if there are any)
 * Starts service for downloading level information and update local database
 */
public class MainActivity extends Activity {

	private static Context context;
	private static Player player;
	private static Device device;
	private static Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//Setup activity
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Create Player
        player = new Player();

        //Create Device
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        device = new Device(size);

        //Create Game
        game = new Game();

		// launch services that take care of uploading levels if there are levels that need uploading and downloading levels to update local database
		launchUploadService();
		launchDownloadService();

		//Load preferences to update player information
		loadSharedPreferences();

        //Start a new thread that keeps the preference file up to date with respect to number of lives of player
		NumLivesThread numLivesThread = new NumLivesThread(this, player);
        numLivesThread.start();
        numLivesThread.setRunning(true);

        //Start a new tread that handels the animations
		AnimatorThread animatorThread = new AnimatorThread();
		animatorThread.start();
		animatorThread.setRunning(true);

		//Start the action
		GamePanel gamePanel = new GamePanel(context, animatorThread);
		setContentView(gamePanel);
	}

	/**
	 * Starts service that downloads levels
	 */
	public static void launchDownloadService() {
		Intent downloadLevelsIntent = new Intent(MainActivity.getContext(), NumbersSyncIntentService.class);
		downloadLevelsIntent.setAction(NumbersSyncTasks.ACTION_DOWNLOAD_LEVELS);
		context.startService(downloadLevelsIntent);
	}

	/**
	 * Starts service that uploads levels
	 */
	public static void launchUploadService() {
		Intent uploadLevelsIntent = new Intent(MainActivity.getContext(), NumbersSyncIntentService.class);
		uploadLevelsIntent.setAction(NumbersSyncTasks.ACTION_UPLOAD_LEVELS);
		context.startService(uploadLevelsIntent);
	}

	/**
	 * Loads preferences
	 * Checks if this is a normal run, first run, or update
	 * For normal run: updates Player based on saved data and elapsed time (add additional lives, if necessary)
	 * For update: updates Player based on saved data and elapsed time (add additional Levels, if necessary)
	 * For first run: sets Player's numLives to MAXNUMLIVES
	 * For first run: TODO: Download all levels, so check if internet connection. If not show message.
	 * Independent of the type of run: updates SharedPreferences to save Players's numLives and lastCheckNumLives
	 */
	public void loadSharedPreferences() {
	    // Get current version code of app
	    int currentVersionCode;
	    try {
	        currentVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
	    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
	        // handle exception
	        e.printStackTrace();
	        return;
	    }

	    // Get saved version code
	    SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs_name), MODE_PRIVATE);
	    int savedVersionCode = prefs.getInt(context.getString(R.string.prefs_version_code_key), context.getResources().getInteger(R.integer.doesnt_exist));

	    if (currentVersionCode == savedVersionCode) {
	    	System.out.println("Normal run");
			updatePlayer(prefs);
		}
	    else if (savedVersionCode == context.getResources().getInteger(R.integer.doesnt_exist)) {
	    	System.out.println("New install");

	    	//TODO: First run needs to be checked as the first time someone plays the game,
			// it is necessary to download level data. In other words: first time someone
			// is playing, they need internet access.

	    	player.setNumLives(GameConstants.MAX_NUMLIVES);
			player.setLastCheckNumLives(new Date());
	    } else if (currentVersionCode > savedVersionCode) {
	    	System.out.println("Upgrade");
			updatePlayer(prefs);
	    }

	    // Update the shared preferences
	    prefs.edit().putInt(context.getString(R.string.prefs_version_code_key), currentVersionCode).apply();
		prefs.edit().putInt(context.getString(R.string.prefs_number_of_lives_key), player.getNumLives()).apply();
		prefs.edit().putInt(context.getString(R.string.prefs_number_of_stars_key), player.getNumStars()).apply();
		prefs.edit().putLong(context.getString(R.string.prefs_last_update_number_of_lifes_key), player.getLastCheckNumLives().getTime()).apply();
	}

	/**
	 * updates Player based on saved data and elapsed time (add additional lives, if necessary)
	 *
	 * @param prefs prefs
	 */
	private void updatePlayer(SharedPreferences prefs) {
		//retrieve initial data
		player.setLastCheckNumLives(new Date(prefs.getLong(context.getString(R.string.prefs_last_update_number_of_lifes_key), context.getResources().getInteger(R.integer.doesnt_exist))));
		player.setNumLives(prefs.getInt(context.getString(R.string.prefs_number_of_lives_key), context.getResources().getInteger(R.integer.doesnt_exist)));
		player.setNumStars(prefs.getInt(context.getString(R.string.prefs_number_of_stars_key), context.getResources().getInteger(R.integer.doesnt_exist)));

		//Update for elapsed time
		//Calc numLivesToAdd
		long timeSinceLastCheck = new Date().getTime() - player.getLastCheckNumLives().getTime();
		int numLivesToAdd = (int) (timeSinceLastCheck / GameConstants.TIME_TO_NEW_LIFE);
		//player
		if (numLivesToAdd > 0) {
            if (numLivesToAdd + player.getNumLives() > GameConstants.MAX_NUMLIVES) {
                player.setNumLives(GameConstants.MAX_NUMLIVES);
            } else {
                player.setNumLives(player.getNumLives() + numLivesToAdd);
            }
        }
		//lastCheckNumLives
		if (numLivesToAdd > 0) {
            if (numLivesToAdd + player.getNumLives() > GameConstants.MAX_NUMLIVES) {
                player.setLastCheckNumLives(new Date());
            } else {
                player.setLastCheckNumLives(new Date(player.getLastCheckNumLives().getTime() + numLivesToAdd * GameConstants.TIME_TO_NEW_LIFE));
            }
        }
	}

	public static Player getPlayer() {
		return player;
	}

	public static Device getDevice() {
		return device;
	}

	public static Context getContext() {
		return context;
	}

	public static Game getGame() {
		return game;
	}

}
