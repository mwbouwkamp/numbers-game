package nl.limakajo.numbers.localData;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Class that contains the contract for the database structure
 *
 * @author M.W.Bouwkamp
 */
public class NumbersContract {

    public static final String CONTENT_AUTHORITY = "nl.limakajo.numbers";
    static final String PATH_LEVELS = "levels";
    private static final String PATH_SPECIFIC_LEVEL = "level";

    /**
     * Class that contains the contract for table with level information
     */
    public final static class TableLevels implements BaseColumns {

        public static final Uri BASE_CONTENT_URI_LEVELS = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + PATH_LEVELS);
        static final Uri CONTENT_URI = BASE_CONTENT_URI_LEVELS.buildUpon()
                .appendPath(PATH_SPECIFIC_LEVEL)
                .build();

        //Table Name
        static final String TABLE_NAME = "levelinfo";

        //Column names
        public static final String KEY_NUMBERS = "numbers";
        public static final String KEY_USER_TIME = "usertime";
        public static final String KEY_AVERAGE_TIME = "averagetime";
        public static final String KEY_LEVEL_STATUS = "levelstatus"; //Status can be "U" unplayed, "A" Active level and "U" Completed level that needs to be uploaded and "C" Completed level
    }
}
