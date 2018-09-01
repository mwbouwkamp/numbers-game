package nl.limakajo.numbers.sync;

import android.content.Context;

import nl.limakajo.numbers.numbersgame.Level;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.JSONUtils;
import nl.limakajo.numbers.utils.NetworkUtils;

import java.security.InvalidParameterException;
import java.util.LinkedList;

/**
 * Created by mwbou on 12-12-2017.
 */

public class NumbersSyncTasks {

    public static final String ACTION_UPLOAD_LEVELS = "upload-levels";
    public static final String ACTION_DOWNLOAD_LEVELS = "download-levels";

    synchronized public static void sync(Context context, String action) {
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
        String levelsFromServer = NetworkUtils.getLevelsJSONFromServer();
        LinkedList<Level> levels = JSONUtils.getLevelsFromJson(levelsFromServer);
        if (levels != null) {
            DatabaseUtils.setLevels(context, levels);
        }
    }

    private static void uploadLevels(Context context) {
        LinkedList<Level> levels = DatabaseUtils.getCompletedLevels(context);
        if (levels != null) {
            NetworkUtils.sendLevelsToServer(context, levels);
        }
    }


}

