package nl.limakajo.numbers.data.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

import java.util.Objects;

import nl.limakajo.numbers.data.NumbersContract;
import nl.limakajo.numbers.data.NumbersProvider;

/**
 * Created by mwbou on 30-12-2017.
 */
public class NumbersProviderTest extends ProviderTestCase2<NumbersProvider> {


    private ContentValues[] multipleLevels, multipleCompletedLevels;
    private ContentValues singleLevel, singleActiveLevel, singleCompletedLevel;

    public NumbersProviderTest() {
        super(NumbersProvider.class, NumbersContract.CONTENT_AUTHORITY);
    }

    protected void setUp() throws Exception {
        super.setUp();
        ContentResolver contentResolver = getMockContentResolver();
        ContentValues level1 = new ContentValues();
        level1.put(NumbersContract.TableLevels.KEY_NUMBERS, "001002003004005006007008");
        level1.put(NumbersContract.TableLevels.KEY_AVERAGE_TIME, 10);
        level1.put(NumbersContract.TableLevels.KEY_USER_TIME, 10);
        ContentValues level2 = new ContentValues();
        level2.put(NumbersContract.TableLevels.KEY_NUMBERS, "002004006008010012014016");
        level2.put(NumbersContract.TableLevels.KEY_AVERAGE_TIME, 20);
        level2.put(NumbersContract.TableLevels.KEY_USER_TIME, 20);
        ContentValues level3 = new ContentValues();
        level3.put(NumbersContract.TableLevels.KEY_NUMBERS, "003006009012015018021024");
        level3.put(NumbersContract.TableLevels.KEY_AVERAGE_TIME, 30);
        level3.put(NumbersContract.TableLevels.KEY_USER_TIME, 30);
        multipleLevels = new ContentValues[] {
                level1,
                level2,
                level3
        };
        singleLevel = new ContentValues();
        singleLevel.put(NumbersContract.TableLevels.KEY_NUMBERS, "005010015020025030035040");
        singleLevel.put(NumbersContract.TableLevels.KEY_AVERAGE_TIME, 50);
        singleLevel.put(NumbersContract.TableLevels.KEY_USER_TIME, 50);

    }

    private ContentProvider setupDatabase(ContentProvider contentProvider) {
        contentProvider.bulkInsert(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, multipleLevels);
        return contentProvider;
    }

    public void testBulkInsertAndInsert() {
        //Setup
        ContentProvider contentProvider = getProvider();

        //Check bulkInsert for TableLevels
        int numInserted = contentProvider.bulkInsert(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, multipleLevels);
        assertEquals("testBulkInsertAndInsert: Correct number of levels inserted", 3, numInserted);

        //Check insert for TableLevels
        Uri insertURI = contentProvider.insert(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, singleLevel);
        assertTrue("testBulkInsertAndInsert: Level inserted successfully", ContentUris.parseId(insertURI) > 0);
    }

    public void testQuery() {
        //Setup
        ContentProvider contentProvider = getProvider();
        contentProvider = setupDatabase(contentProvider);

        //Check query for all levels in TableLevels without selection
        Cursor result = contentProvider.query(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, null, null, null, null);
        assertEquals("testQuery: Correct number of levels", 3, result.getCount());
        //Check query for all levels in TableLevels with userTime > 10
        result = contentProvider.query(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, null, NumbersContract.TableLevels.KEY_USER_TIME + " > ?", new String[] {"10"}, null);
        assertEquals("testQuery: Correct number of levels", 2, result.getCount());

        //Check query for specific level from TableLevels
        //existing level
        Uri uri = NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS;
        String level = "002004006008010012014016";
        uri = uri.buildUpon().appendPath(level).build();
        result = contentProvider.query(uri, null, null, null, null);
        Objects.requireNonNull(result).moveToFirst();
        assertEquals("testQuery: Correct level", level, result.getString(result.getColumnIndex(NumbersContract.TableLevels.KEY_NUMBERS)));
        //non-existing level
        level = "111222333444555666777888";
        uri = NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS;
        uri = uri.buildUpon().appendPath(level).build();
        result = contentProvider.query(uri, null, null, null, null);
        assertEquals("testQuery: No levels found with invalid query", 0, result.getCount());
    }

    public void testDelete() {
        //Setup
        ContentProvider contentProvider = getProvider();
        contentProvider = setupDatabase(contentProvider);

        //Delete specific level from TableLevels
        String level = "001002003004005006007008";
        Uri uri = NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS;
        uri = uri.buildUpon().appendPath(level).build();
        int numDeleted = contentProvider.delete(uri, null, null);
        assertEquals("testDelete: Correct number of deleted levels", 1, numDeleted);
        Cursor result = contentProvider.query(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, null, null, null, null);
        assertEquals("testDelete: correct number of levels left after delete", 2, result.getCount());
        result.moveToFirst();
        String levelNumbers = result.getString(result.getColumnIndex(NumbersContract.TableLevels.KEY_NUMBERS));
        assertFalse("testDelete: deleted level not present", levelNumbers.equals(level));
        while (result.moveToNext()) {
            levelNumbers = result.getString(result.getColumnIndex(NumbersContract.TableLevels.KEY_NUMBERS));
            assertFalse("testDelete: deleted level not present", levelNumbers.equals(level));
        }

        //Delete all remaining levels from TableLevels
        numDeleted = contentProvider.delete(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, null, null);
        assertEquals("testDelete: Correct number of deleted levels", 2, numDeleted);
    }

    public void testUpdate() {
        //Setup
        ContentProvider contentProvider = getProvider();
        contentProvider = setupDatabase(contentProvider);

        //Update userTime of all records in TableLevels
        ContentValues contentValues = new ContentValues();
        contentValues.put(NumbersContract.TableLevels.KEY_USER_TIME, "99");
        int numUpdated = contentProvider.update(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, contentValues, null, null);
        assertEquals("testUpdate: correct number updated", 3, numUpdated);
        Cursor result = contentProvider.query(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, null, null, null, null);
        Objects.requireNonNull(result).moveToFirst();
        String userTime = result.getString(result.getColumnIndex(NumbersContract.TableLevels.KEY_USER_TIME));
        assertEquals("testUpdate: correct userTime", "99", userTime);
        while (result.moveToNext()) {
            userTime = result.getString(result.getColumnIndex(NumbersContract.TableLevels.KEY_USER_TIME));
            assertEquals("testUpdate: correct userTime", "99", userTime);
        }

        //Update single userTime in TableLevels
        contentValues = new ContentValues();
        contentValues.put(NumbersContract.TableLevels.KEY_USER_TIME, "0");
        String level = "002004006008010012014016";
        Uri uri = NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS;
        uri = uri.buildUpon().appendPath(level).build();
        numUpdated = contentProvider.update(uri, contentValues, null, null);
        assertEquals("testUpdate: correct number updated", 1, numUpdated);
        result = contentProvider.query(uri, null, null, null, null);
        Objects.requireNonNull(result).moveToFirst();
        assertEquals("testUpdate: correct userTime", "0", result.getString(result.getColumnIndex(NumbersContract.TableLevels.KEY_USER_TIME)));
    }

}