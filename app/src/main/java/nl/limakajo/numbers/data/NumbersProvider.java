package nl.limakajo.numbers.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class that takes care of all database operations
 *
 * @author M.W.Bouwkamp
 */
public class NumbersProvider extends ContentProvider {

    public static final int CODE_LEVELS = 100;
    public static final int CODE_SPECIFIC_LEVEL = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private NumbersDBHelper numbersDBHelper;

    private static final String TAG = NumbersProvider.class.getName();

    /**
     * Returns matcher for matching uris
     *
     * @return  matcher for matching uris
     */
    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = NumbersContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, NumbersContract.PATH_LEVELS, CODE_LEVELS);
        matcher.addURI(authority, NumbersContract.PATH_LEVELS + "/#", CODE_SPECIFIC_LEVEL);

        return matcher;
    }

    @Override
    /**
     * Creates numbersDBHelper and returns true
     *
     * @return true after creating numbersDBHelper
     */
    public boolean onCreate() {
        numbersDBHelper = new NumbersDBHelper(getContext());
        return true;
    }

    /**
     * Returns cursor after querying database
     *
     * @param uri               uri pointing to database
     * @param projection        database projection
     * @param selection         filter for selection
     * @param selectionArgs     arguments for selection filter
     * @param sortOrder         sort order
     * @return  cursor with query result
     */
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = numbersDBHelper.getReadableDatabase();
        Cursor cursorToReturn;
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
                selection = appendLevelToSelection(selection, uri, CODE_SPECIFIC_LEVEL);
                cursorToReturn = db.query(NumbersContract.TableLevels.TABLE_NAME,
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
        cursorToReturn.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        Log.i(TAG, "Query successfull, resulting in: " + cursorToReturn);
        return cursorToReturn;
    }

    @Override
    /**
     * Inserts data from single ContentValues into database and returns uri
     *
     * @param uri       uri pointing to database
     * @param values    values to insert
     * @return          uri
     */
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = numbersDBHelper.getWritableDatabase();
        Uri uriToReturn;
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
            default:
                throw new UnsupportedOperationException("Unsupported Uri: " + uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uriToReturn, null);
        Log.i(TAG, "Insert successfull, resulting in: " + uriToReturn);
        return uriToReturn;
    }

    @Override
    /**
     * Inserts, in bulk, data from multiple ContentValues into database and returns number of inserted rows
     *
     * @param uri       uri pointing to database
     * @param values    values to insert
     * @return          number of rows inserted into database
     */
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
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
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
                }
                Log.i(TAG, "BulkInsert successfull, inserted : " + rowsInserted);
                return rowsInserted;
            }
            default:
                Log.i(TAG, "Default bulkInsert : " + uri + " " + Arrays.toString(values));
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    /**
     * Deletes data from database and returns number of deleted rows
     *
     * @param uri               uri pointing to database
     * @param selection         filter for selection
     * @param selectionArgs     arguments for selection filter
     * @return                  number or rows deleted
     */
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = numbersDBHelper.getWritableDatabase();
        int numRowsDeleted;
        switch (sUriMatcher.match(uri)) {
            case (CODE_LEVELS): {
            /*
             * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
             * deleted. However, if we do pass null and delete all of the rows in the table, we won't
             * know how many rows were deleted. According to the documentation for SQLiteDatabase,
             * passing "1" for the selection will delete all rows and return the number of rows
             * deleted, which is what the caller of this method expects.
             */
                if (null == selection) {
                    selection = "1";
                }
                numRowsDeleted = db.delete(NumbersContract.TableLevels.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }
            case (CODE_SPECIFIC_LEVEL): {
                selection = appendLevelToSelection(selection, uri, CODE_SPECIFIC_LEVEL);
                numRowsDeleted = db.delete(NumbersContract.TableLevels.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unsupported Uri: " + uri);
        }
        if (numRowsDeleted > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        Log.i(TAG, "Delete successfull, deleted: " + numRowsDeleted);
        return numRowsDeleted ;
    }

    @Override
    /**
     * Updates data in database and returns the number of updated rows
     *
     * @param uri               uri pointing to database
     * @param values            values for update
     * @param selection         filter for selection
     * @param selectionArgs     arguments for filter
     * @return                  the number of rows updated
     */
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int numUpdated;
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
                selection = appendLevelToSelection(selection, uri, CODE_SPECIFIC_LEVEL);
                numUpdated = db.update(NumbersContract.TableLevels.TABLE_NAME,
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
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        Log.i(TAG, "Update successfull, updated: " + numUpdated);
        return numUpdated;
    }

    @Override
    /**
     * Returns null
     */
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * Returns selection filter based on existing selection extended with selection for level
     *
     * @param selection     orgininal selection
     * @param uri           uri pointing to database
     * @param table
     * @return              selection filter that includes selection for specific level
     */
    //TODO: This needs reviewing. Not sure if this works correctly or that you need to change both selection and selectionArgs
    //TODO: Check if table as an int is ok, or that this needs to be something else
    private String appendLevelToSelection(String selection, Uri uri, int table) {
        String selectionToReturn = "";
        String level = uri.getLastPathSegment();
        if (isValidLevel(level)) {
            switch (table) {
                case CODE_SPECIFIC_LEVEL:
                    if (!TextUtils.isEmpty(selection)) {
                        selectionToReturn = selection + " AND ";
                    }
                    selectionToReturn += NumbersContract.TableLevels.KEY_NUMBERS + " = '" + level + "'";
                    break;
                default: {
                    throw new UnsupportedOperationException("Unsupported Uri: " + uri);
                }
            }
        } else {
            throw new IllegalArgumentException("Level must be of format 001002003004005006007008");
        }
        return selectionToReturn;
    }

    /**
     * Returns true if level is of format 001002003004005006007008
     *
     * @param level     level that needs checking
     * @return          true if level has the correct format
     */
    //TODO: It would be better to switch to a different formatting of levels such as comma separated. This way split can be used.
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
