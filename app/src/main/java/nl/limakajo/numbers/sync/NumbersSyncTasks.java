package nl.limakajo.numbers.sync;

import android.content.Context;

import nl.limakajo.numbers.numbersgame.Level;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;
import nl.limakajo.numbers.utils.JDBCNetworkUtils;
import nl.limakajo.numbers.utils.JSONUtils;
import nl.limakajo.numbers.utils.HTTPNetworkUtils;

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
        LinkedList<Level> levels = JDBCNetworkUtils.getLevelsJSONFromServer();
        if (levels != null) {
            DatabaseUtils.updateLevelsAverageTimeForSpecificLevels(context, levels);
        }
    }

    private static void uploadLevels(Context context) {
        LinkedList<Level> levels = DatabaseUtils.getLevelsWithSpecificStatus(context, GameUtils.LevelState.UPLOAD);
        if (levels != null) {
            JDBCNetworkUtils.sendLevelsToServer(levels);
        }
    }


}

