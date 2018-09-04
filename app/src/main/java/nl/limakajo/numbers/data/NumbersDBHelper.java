package nl.limakajo.numbers.data;

import android.content.Context;
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
    static final String DATABASE_NAME = "numbersgame";

	//Database version
	static final int DATABASE_VERSION = 1;

	//Logcat tag
	private static final String TAG = NumbersDBHelper.class.getName();

	/**
	 * Constructor
	 * @param context context
	 */
	public NumbersDBHelper(Context context) {
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
						NumbersContract.TableLevels.KEY_USER_TIME + " INTEGER);";
		db.execSQL(CREATE_TABLE_LEVELS);

		//Create recently completed levels table
		final String CREATE_TABLE_COMPLETED_LEVELS =
				"CREATE TABLE " +
						NumbersContract.TableCompletedLevels.TABLE_NAME +
						"("+
						NumbersContract.TableCompletedLevels.KEY_NUMBERS + " VARCHAR," +
						NumbersContract.TableCompletedLevels.KEY_USER_TIME + " INTEGER);";
		db.execSQL(CREATE_TABLE_COMPLETED_LEVELS);

		Log.i(TAG, "Tables created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + NumbersContract.TableLevels.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + NumbersContract.TableCompletedLevels.TABLE_NAME);
		onCreate(db);
	}
}
