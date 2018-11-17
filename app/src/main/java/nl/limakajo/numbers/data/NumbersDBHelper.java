package nl.limakajo.numbers.data;

import android.content.Context;

import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.utils.DatabaseUtils;
import nl.limakajo.numbers.utils.GameUtils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * NumbersDBHelper
 * Contains all database operations
 * 
 * Features:
 * - operations to create databases with level information
 * - operations to read/write data from/to the server
 * 
 * @author M.W.Bouwkamp
 *
 */
class NumbersDBHelper extends SQLiteOpenHelper {

	//Database Name
    private static final String DATABASE_NAME = "numbersgame";

	//Database version
	private static final int DATABASE_VERSION = 2;

	//Logcat tag
	private static final String TAG = NumbersDBHelper.class.getName();

	/**
	 * Constructor
	 * @param context context
	 */
	NumbersDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.i(TAG, "Database created");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Create levels table
		final String CREATE_TABLE_LEVELS =
				"CREATE TABLE " +
						NumbersContract.TableLevels.TABLE_NAME +
						"("+
						NumbersContract.TableLevels.KEY_NUMBERS + " VARCHAR," +
						NumbersContract.TableLevels.KEY_AVERAGE_TIME + " INTEGER," +
						NumbersContract.TableLevels.KEY_USER_TIME + " INTEGER," +
						NumbersContract.TableLevels.KEY_LEVEL_STATUS + " VARCHAR );";
		db.execSQL(CREATE_TABLE_LEVELS);

		Log.i(TAG, "Tables created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + NumbersContract.TableLevels.TABLE_NAME);
		onCreate(db);
	}
}
