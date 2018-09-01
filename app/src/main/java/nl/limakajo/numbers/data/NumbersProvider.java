package nl.limakajo.numbers.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * NumbersProvider Class
 * Takes care of all database related activities
 *
 * Created by M.W.Bouwkamp on 26-11-2017.
 */

public class NumbersProvider extends ContentProvider {

    public static final int CODE_LEVELS = 100;
    public static final int CODE_SPECIFIC_LEVEL = 101;
    public static final int CODE_COMPLETED_LEVELS = 200;
    public static final int CODE_SPECIFIC_COMPLETED_LEVEL = 201;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private NumbersDBHelper numbersDBHelper;

    private static final String TAG = NumbersProvider.class.getName();

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = NumbersContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, NumbersContract.PATH_LEVELS, CODE_LEVELS);
        matcher.addURI(authority, NumbersContract.PATH_LEVELS + "/#", CODE_SPECIFIC_LEVEL);
        matcher.addURI(authority, NumbersContract.PATH_COMPLETED_LEVELS, CODE_COMPLETED_LEVELS);
        matcher.addURI(authority, NumbersContract.PATH_COMPLETED_LEVELS + "/#", CODE_SPECIFIC_COMPLETED_LEVEL);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        numbersDBHelper = new NumbersDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = numbersDBHelper.getReadableDatabase();
        Cursor cursorToReturn = null;
        switch (sUriMatcher.match(uri)) {
            case CODE_LEVELS: {
                cursorToReturn = db.query(NumbersContract.TableLevels.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_SPECIFIC_LEVEL: {
                selection = appendLevelToSelection(selection, uri);
                cursorToReturn = db.query(NumbersContract.TableLevels.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_COMPLETED_LEVELS: {
                cursorToReturn = db.query(NumbersContract.TableCompletedLevels.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_SPECIFIC_COMPLETED_LEVEL: {
                selection = appendLevelToSelection(selection, uri);
                cursorToReturn = db.query(NumbersContract.TableCompletedLevels.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unsupported Uri: " + uri);
        }
        cursorToReturn.setNotificationUri(getContext().getContentResolver(), uri);
        Log.i(TAG, "Query successfull, resulting in: " + cursorToReturn);
        return cursorToReturn;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = numbersDBHelper.getWritableDatabase();
        Uri uriToReturn = null;
        switch (sUriMatcher.match(uri)) {
            case CODE_LEVELS: {
                long id = db.insert(NumbersContract.TableLevels.TABLE_NAME, null, values);
                if ( id > 0 ) {
                    uriToReturn = ContentUris.withAppendedId(NumbersContract.TableLevels.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case CODE_COMPLETED_LEVELS: {
                long id = db.insert(NumbersContract.TableCompletedLevels.TABLE_NAME, null, values);
                if ( id > 0 ) {
                    uriToReturn = ContentUris.withAppendedId(NumbersContract.TableCompletedLevels.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unsupported Uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uriToReturn, null);
        Log.i(TAG, "Insert successfull, resulting in: " + uriToReturn);
        return uriToReturn;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = numbersDBHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_LEVELS: {
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value: values) {
                        long id = db.insert(NumbersContract.TableLevels.TABLE_NAME, null, value);
                        if (id > 0) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                Log.i(TAG, "BulkInsert successfull, inserted : " + rowsInserted);
                return rowsInserted;
            }
            case CODE_COMPLETED_LEVELS: {
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value: values) {
                        long id = db.insert(NumbersContract.TableCompletedLevels.TABLE_NAME, null, value);
                        if (id > 0) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                Log.i(TAG, "BulkInsert successfull, inserted : " + rowsInserted);
                return rowsInserted;
            }
            default:
                Log.i(TAG, "Default bulkInsert : " + uri + " " + values);
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = numbersDBHelper.getWritableDatabase();
        int numRowsDeleted = 0;
        switch (sUriMatcher.match(uri)) {
            case (CODE_LEVELS): {
            /*
             * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
             * deleted. However, if we do pass null and delete all of the rows in the table, we won't
             * know how many rows were deleted. According to the documentation for SQLiteDatabase,
             * passing "1" for the selection will delete all rows and return the number of rows
             * deleted, which is what the caller of this method expects.
             */
                if (null == selection) selection = "1";
                numRowsDeleted = db.delete(NumbersContract.TableLevels.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }
            case (CODE_SPECIFIC_LEVEL): {
                selection = appendLevelToSelection(selection, uri);
                numRowsDeleted = db.delete(NumbersContract.TableLevels.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }
            case (CODE_COMPLETED_LEVELS): {
            /*
             * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
             * deleted. However, if we do pass null and delete all of the rows in the table, we won't
             * know how many rows were deleted. According to the documentation for SQLiteDatabase,
             * passing "1" for the selection will delete all rows and return the number of rows
             * deleted, which is what the caller of this method expects.
             */
                if (null == selection) selection = "1";
                numRowsDeleted = db.delete(NumbersContract.TableCompletedLevels.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }
            case (CODE_SPECIFIC_COMPLETED_LEVEL): {
                selection = appendLevelToSelection(selection, uri);
                numRowsDeleted = db.delete(NumbersContract.TableCompletedLevels.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unsupported Uri: " + uri);
        }
        if (numRowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.i(TAG, "Delete successfull, deleted: " + numRowsDeleted);
        return numRowsDeleted ;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int numUpdated = 0;
        SQLiteDatabase db = numbersDBHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_LEVELS: {
                numUpdated = db.update(NumbersContract.TableLevels.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case CODE_SPECIFIC_LEVEL: {
                selection = appendLevelToSelection(selection, uri);
                numUpdated = db.update(NumbersContract.TableLevels.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case CODE_COMPLETED_LEVELS: {
                numUpdated = db.update(NumbersContract.TableCompletedLevels.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case CODE_SPECIFIC_COMPLETED_LEVEL: {
                selection = appendLevelToSelection(selection, uri);
                numUpdated = db.update(NumbersContract.TableCompletedLevels.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unsupported Uri: " + uri);
            }
        }
        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.i(TAG, "Update successfull, updated: " + numUpdated);
        return numUpdated;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    private String appendLevelToSelection(String selection, Uri uri) {
        String selectionToReturn;
        String level = uri.getLastPathSegment();
        if (isValidLevel(level)) {
            if (TextUtils.isEmpty(selection)) {
                selectionToReturn = NumbersContract.TableCompletedLevels.KEY_NUMBERS + " = '" + level + "'";
            }
            else {
                selectionToReturn = selection + " AND " + NumbersContract.TableCompletedLevels.KEY_NUMBERS + " = '" + level + "'";
            }
        } else {
            throw new IllegalArgumentException("Level must be of format 001002003004005006007008");
        }
        return selectionToReturn;
    }

    /**
     * Checks if level is of format 001002003004005006007008
     * @param level
     * @return true if level has the correct format
     */
    private boolean isValidLevel(String level) {
        boolean booleanToReturn = true;
        if (level.length() != 24) {
            booleanToReturn = false;
        }
        String[] LevelNumbers = new String[] {
                level.substring(0,3),
                level.substring(3,6),
                level.substring(6,9),
                level.substring(9,12),
                level.substring(12,15),
                level.substring(15,18),
                level.substring(18,24)
        };
        for (String levelNumber: LevelNumbers) {
            try {
                int levelNumberAsInteger = Integer.parseInt(levelNumber);
            }
            catch (NumberFormatException e) {
                booleanToReturn = false;
            }
        }
        return booleanToReturn;
    }
}
