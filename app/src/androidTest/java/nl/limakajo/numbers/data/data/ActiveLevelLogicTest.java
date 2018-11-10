package nl.limakajo.numbers.data.data;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.net.Uri;
import android.os.Looper;
import android.support.test.rule.ActivityTestRule;
import android.test.ProviderTestCase2;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Rule;

import java.util.Objects;

import nl.limakajo.numbers.data.NumbersContract;
import nl.limakajo.numbers.data.NumbersProvider;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.numbersgame.Level;
import nl.limakajo.numbers.utils.DatabaseUtils;

/**
 * Created by mwbou on 30-12-2017.
 */
public class ActiveLevelLogicTest extends ProviderTestCase2<NumbersProvider> {


    private ContentValues[] multipleLevels, multipleCompletedLevels;
    private ContentValues activeLevel, completedLevel, singleLevel, singleActiveLevel, singleCompletedLevel;

    private DatabaseUtils databaseUtils;

    public ActiveLevelLogicTest() {
        super(NumbersProvider.class, NumbersContract.CONTENT_AUTHORITY);
    }

    @Rule
    public ActivityTestRule<MainActivity> myActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        myActivityTestRule.getActivity();

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

        singleActiveLevel = new ContentValues();
        singleActiveLevel.put(NumbersContract.TableActiveLevel.KEY_NUMBERS, "002004006008010012014016");
        singleActiveLevel.put(NumbersContract.TableActiveLevel.KEY_USER_TIME, "20");

        ContentValues completedLevel1 = new ContentValues();
        completedLevel1.put(NumbersContract.TableLevels.KEY_NUMBERS, "001002003004005006007008");
        completedLevel1.put(NumbersContract.TableLevels.KEY_USER_TIME, 10);
        ContentValues completedLevel2 = new ContentValues();
        completedLevel2.put(NumbersContract.TableLevels.KEY_NUMBERS, "002004006008010012014016");
        completedLevel2.put(NumbersContract.TableLevels.KEY_USER_TIME, 20);
        ContentValues completedLevel3 = new ContentValues();
        completedLevel3.put(NumbersContract.TableLevels.KEY_NUMBERS, "003006009012015018021024");
        completedLevel3.put(NumbersContract.TableLevels.KEY_USER_TIME, 30);
        multipleCompletedLevels = new ContentValues[] {
                completedLevel1,
                completedLevel2,
                completedLevel3
        };
        singleCompletedLevel = new ContentValues();
        singleCompletedLevel.put(NumbersContract.TableLevels.KEY_NUMBERS, "005010015020025030035040");
        singleCompletedLevel.put(NumbersContract.TableLevels.KEY_USER_TIME, 50);


        activeLevel = new ContentValues();
        level3.put(NumbersContract.TableLevels.KEY_NUMBERS, "004008012016020024028032");
        level3.put(NumbersContract.TableLevels.KEY_USER_TIME, 40);
        completedLevel = new ContentValues();
        level3.put(NumbersContract.TableLevels.KEY_NUMBERS, "005010015020025030035040");
        level3.put(NumbersContract.TableLevels.KEY_USER_TIME, 50);

    }

    private ContentProvider setupDatabase(ContentProvider contentProvider) {
        contentProvider.bulkInsert(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, multipleLevels);
        contentProvider.insert(NumbersContract.TableActiveLevel.BASE_CONTENT_URI_ACTIVE_LEVEL, singleActiveLevel);
        contentProvider.bulkInsert(NumbersContract.TableCompletedLevels.BASE_CONTENT_URI_COMPLETED_LEVELS, multipleCompletedLevels);
        return contentProvider;
    }

    public void testScenarioNoRemainingActiveLevelNoRecentlyCompletedLevelstestPickFirstLevel() {
        //Setup
        ContentProvider contentProvider = getProvider();
        contentProvider.bulkInsert(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, multipleLevels);

        assertTrue(true);

        //Test
        int userAverageTime = 9;
        String expectedLevel =  "001002003004005006007008";

        Level newLevel = DatabaseUtils.selectLevel(myActivityTestRule.getActivity().getApplicationContext());
        assertEquals("scenarioNoRemainingActiveLevelNoRecentlyCompletedLevelstestPickFirstLevel: correct level selected", newLevel.toString(), expectedLevel);
    }


}