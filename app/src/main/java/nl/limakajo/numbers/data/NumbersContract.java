package nl.limakajo.numbers.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * NumbersContract class
 * Contains the contract for the database structure
 *
 * @author M.W.Bouwkamp
 */

public class NumbersContract {

    public static final String CONTENT_AUTHORITY = "nl.limakajo.numbers";

    //Path for specific tables
    public static final String PATH_LEVELS = "levels";
    public static final String PATH_COMPLETED_LEVELS = "completedlevels";

    //Path if specific level is required
    public static final String PATH_SPECIFIC_LEVEL = "level";

    /**
     * Class with contract for table with level information
     */
    public final static class TableLevels implements BaseColumns {

        public static final Uri BASE_CONTENT_URI_LEVELS = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + PATH_LEVELS);
        public static final Uri CONTENT_URI = BASE_CONTENT_URI_LEVELS.buildUpon()
                .appendPath(PATH_SPECIFIC_LEVEL)
                .build();

        //Table Name
        public static final String TABLE_NAME = "levelinfo";

        //Column names
        public static final String KEY_NUMBERS = "numbers";
        public static final String KEY_USER_TIME = "usertime";
        public static final String KEY_AVERAGE_TIME = "averagetime";
    }

    /**
     * Class with contract for table with information on recently completed levels
     */
    public final static class TableCompletedLevels implements BaseColumns {

        public static final Uri BASE_CONTENT_URI_COMPLETED_LEVELS = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + PATH_COMPLETED_LEVELS);
        public static final Uri CONTENT_URI = BASE_CONTENT_URI_COMPLETED_LEVELS.buildUpon()
                .appendPath(PATH_SPECIFIC_LEVEL)
                .build();

        //Table Name
        public static final String TABLE_NAME = "completedlevels";

        //Column names
        public static final String KEY_NUMBERS = "numbers";
        public static final String KEY_USER_TIME = "usertime";

    }

}
