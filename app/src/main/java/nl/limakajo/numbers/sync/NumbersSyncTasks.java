package nl.limakajo.numbers.sync;

import android.content.Context;

import org.json.JSONObject;

import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;
import nl.limakajo.numberslib.numbersGame.Level;
import nl.limakajo.numberslib.onlineData.JDBCNetworkUtils;
import nl.limakajo.numberslib.onlineData.NetworkContract;
import nl.limakajo.numberslib.utils.JsonUtils;

import java.security.InvalidParameterException;
import java.util.LinkedList;

/**
 * @author M.W.Bouwkamp
 */

public class NumbersSyncTasks {

    public static final String ACTION_UPLOAD_LEVELS = "upload-levels";
    public static final String ACTION_DOWNLOAD_LEVELS = "download-levels";

    synchronized static void sync(Context context, String action) {
        switch (action) {
            case ACTION_DOWNLOAD_LEVELS:
                downloadLevels(context);
                break;
            case ACTION_UPLOAD_LEVELS:
                uploadLevels(context);
                break;
            default:
                throw new InvalidParameterException("Invalid action: " + action);
        }
    }

    private static void downloadLevels(Context context) {
        JSONObject levelsJson = JDBCNetworkUtils.queryLevelJSON(NetworkContract.LevelData.TABLE_NAME);
        LinkedList<Level> levels = JsonUtils.jsonToLevels(levelsJson);
        if (levels != null) {
            DatabaseUtils.updateLevelsAverageTimeForSpecificLevels(context, levels);
        }
    }

    private static void uploadLevels(Context context) {
        LinkedList<Level> levels = DatabaseUtils.getLevelsWithSpecificStatus(context, GameUtils.LevelState.UPLOAD);
        JSONObject levelsJson = JsonUtils.levelsToJson(levels);
        JSONObject successfullyUploadedLevelsJson = JDBCNetworkUtils.insertLevels(NetworkContract.CompletedLevelData.TABLE_NAME, levelsJson);
        LinkedList<Level> successfullyUploadedLevels = JsonUtils.jsonToLevels(successfullyUploadedLevelsJson);
        for (Level level: successfullyUploadedLevels) {
            DatabaseUtils.updateTableLevelsLevelStatusForSpecificLevel(MainActivity.getContext(), level, GameUtils.LevelState.COMPLETED);
        }
    }
}

