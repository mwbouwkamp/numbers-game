package nl.limakajo.numbers.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import nl.limakajo.numbers.data.NumbersContract;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.numbersgame.Level;

import java.util.LinkedList;
import java.util.Objects;

/**
 * @author M.W.Bouwkamp
 */

public class DatabaseUtils {

    //TODO: This can be refactored: one queryAll, with switch to see from which table the data needs to be retrieved
    /**
     * Retrieves all levels from SQLite database
     *
     * @param context context
     * @return levels levels
     */
    public static LinkedList<Level> queryAllLevels(Context context) {
        String[] projection = {
                NumbersContract.TableLevels.KEY_NUMBERS,
                NumbersContract.TableLevels.KEY_USER_TIME,
                NumbersContract.TableLevels.KEY_AVERAGE_TIME};
        Cursor cursor = context.getContentResolver().query(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, projection, null, null, null, null);
        String numbersString;
        LinkedList<Level> levels = new LinkedList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                numbersString = cursor.getString(cursor.getColumnIndex(NumbersContract.TableLevels.KEY_NUMBERS));
                int userTime = Integer.parseInt(cursor.getString(cursor.getColumnIndex(NumbersContract.TableLevels.KEY_USER_TIME)));
                int averageTime = Integer.parseInt(cursor.getString(cursor.getColumnIndex(NumbersContract.TableLevels.KEY_AVERAGE_TIME)));
                levels.add(new Level(numbersString, averageTime, userTime));
            }
            while (cursor.moveToNext()) {
                numbersString = cursor.getString(cursor.getColumnIndex(NumbersContract.TableLevels.KEY_NUMBERS));
                int userTime = Integer.parseInt(cursor.getString(cursor.getColumnIndex(NumbersContract.TableLevels.KEY_USER_TIME)));
                int averageTime = Integer.parseInt(cursor.getString(cursor.getColumnIndex(NumbersContract.TableLevels.KEY_AVERAGE_TIME)));
                levels.add(new Level(numbersString, averageTime, userTime));
            }
            cursor.close();
        }
        return levels;
    }

    /**
     * Retrieves all active levels from SQLite database
     *
     * @param context context
     * @return completed levels
     */
    public static LinkedList<Level> queryAllActiveLevels(Context context) {
        String[] projection = {
                NumbersContract.TableActiveLevel.KEY_NUMBERS,
                NumbersContract.TableActiveLevel.KEY_USER_TIME, };
        Cursor cursor = context.getContentResolver().query(NumbersContract.TableActiveLevel.BASE_CONTENT_URI_ACTIVE_LEVEL, projection, null, null, null, null);
        String numbersString;
        LinkedList<Level> levels = new LinkedList<>();
        if (Objects.requireNonNull(cursor).moveToFirst()){
            numbersString = cursor.getString(cursor.getColumnIndex(NumbersContract.TableActiveLevel.KEY_NUMBERS));
            int userTime= Integer.parseInt(cursor.getString(cursor.getColumnIndex(NumbersContract.TableActiveLevel.KEY_USER_TIME)));
            levels.add(new Level(numbersString, 0, userTime));
        }
        while (cursor.moveToNext()) {
            numbersString = cursor.getString(cursor.getColumnIndex(NumbersContract.TableActiveLevel.KEY_NUMBERS));
            int userTime= Integer.parseInt(cursor.getString(cursor.getColumnIndex(NumbersContract.TableActiveLevel.KEY_USER_TIME)));
            levels.add(new Level(numbersString, 0, userTime));
        }
        cursor.close();
        return levels;
    }

    /**
     * Retrieves all completed levels from SQLite database
     *
     * @param context context
     * @return completed levels
     */
    public static LinkedList<Level> queryAllCompletedLevels(Context context) {
        String[] projection = {
                NumbersContract.TableCompletedLevels.KEY_NUMBERS,
                NumbersContract.TableCompletedLevels.KEY_USER_TIME, };
        Cursor cursor = context.getContentResolver().query(NumbersContract.TableCompletedLevels.BASE_CONTENT_URI_COMPLETED_LEVELS, projection, null, null, null, null);
        String numbersString;
        LinkedList<Level> levels = new LinkedList<>();
        if (Objects.requireNonNull(cursor).moveToFirst()){
            numbersString = cursor.getString(cursor.getColumnIndex(NumbersContract.TableCompletedLevels.KEY_NUMBERS));
            int userTime= Integer.parseInt(cursor.getString(cursor.getColumnIndex(NumbersContract.TableCompletedLevels.KEY_USER_TIME)));
            levels.add(new Level(numbersString, 0, userTime));
        }
        while (cursor.moveToNext()) {
            numbersString = cursor.getString(cursor.getColumnIndex(NumbersContract.TableCompletedLevels.KEY_NUMBERS));
            int userTime= Integer.parseInt(cursor.getString(cursor.getColumnIndex(NumbersContract.TableCompletedLevels.KEY_USER_TIME)));
            levels.add(new Level(numbersString, 0, userTime));
        }
        cursor.close();
        return levels;
    }

    /**
     * Updates levels in SQLite database
     *
     * @param context context
     * @param levels levels
     */
    public static void updateLevels(Context context, LinkedList<Level> levels) {
        String selection = NumbersContract.TableLevels.KEY_NUMBERS + " = ?";
        for (Level level : levels) {
            String[] args = {level.toString()};
            ContentValues contentValues = new ContentValues();
            contentValues.put(NumbersContract.TableLevels.KEY_AVERAGE_TIME, level.getAverageTime());
            int numUpdated = context.getContentResolver().update(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, contentValues, selection, args);
            if (numUpdated == 0) { //If numUpdated == 0 it must be a new level
                contentValues = new ContentValues();
                contentValues.put(NumbersContract.TableLevels.KEY_NUMBERS, level.toString());
                contentValues.put(NumbersContract.TableLevels.KEY_AVERAGE_TIME, level.getAverageTime());
                contentValues.put(NumbersContract.TableLevels.KEY_USER_TIME, 0);
                Uri uri = context.getContentResolver().insert(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, contentValues);
            }
        }
    }


    /**
     * Selects a level for which the averageTime is closest to the userAverageTime
     * @param context
     * @return
     */
    public static Level selectLevel(Context context) {
        Level toReturn = null;
        LinkedList<Level> levels = queryAllLevels(context);
        //TODO: Refactor userAverageTime: move to Player
        int userAverageTime = getAverageTime(context);
        int timeDifferenceSelectedLevel = 999999;
        for (Level level: levels) {
            if (level.getUserTime() == 0) {
                if (Math.abs(level.getAverageTime() - userAverageTime) < timeDifferenceSelectedLevel) {
                    toReturn = level;
                    timeDifferenceSelectedLevel = Math.abs(toReturn.getAverageTime() - userAverageTime);
                }
            }
        }
        return toReturn;
    }

    /**
     * Calculates and returns the averageTime it takes the user to complete levels
     * @param context context
     * @return averageTime
     */
    public static int getAverageTime(Context context) {
        LinkedList<Level> levels = queryAllLevels(context);
        int totalTime = 0;
        int numPlayed = 0;
        for (Level level: levels) {
            if (level.getUserTime() != 0) {
                totalTime += level.getUserTime();
                numPlayed++;
            }
        }
        if (numPlayed != 0) {
            return Math.round(totalTime / numPlayed);
        }
        else {
            return 0;
        }
    }

    /**
     * Updates userTime for a level in table levels in SQLite database
     * @param context context
     * @param level level
     * @param userTime userTime
     */
    public static void updateTableLevelsUserTime(Context context, Level level, int userTime) {
        String selection = NumbersContract.TableLevels.KEY_NUMBERS + " = ?";
        String[] args = {level.toString()};
        ContentValues cv = new ContentValues();
        cv.put(NumbersContract.TableLevels.KEY_USER_TIME, userTime);
        //TODO: All these MainActivity.getContext() can be changed to context (for all methods in this class)
        MainActivity.getContext().getContentResolver().update(
                NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS,
                cv,
                selection,
                args);
    }

    /**
     * Updates userTime for a level in table levels in SQLite database
     * @param context context
     * @param userTime userTime
     */
    public static void updateActiveLevelUserTime(Context context, int userTime) {
        LinkedList<Level> activeLevels = queryAllActiveLevels(context);
        if (activeLevels.size() > 1) {
            //Something went wrong apparently (these should be only one active level), so delete all entries
            for (Level level: activeLevels) {
                deleteActiveLevels(context);
            }
        }
        else if (activeLevels.size() == 1) {
            String selection = NumbersContract.TableActiveLevel.KEY_NUMBERS + " = ?";
            String[] args = {activeLevels.get(0).toString()};
            ContentValues cv = new ContentValues();
            cv.put(NumbersContract.TableActiveLevel.KEY_USER_TIME, userTime);
            MainActivity.getContext().getContentResolver().update(
                    NumbersContract.TableActiveLevel.BASE_CONTENT_URI_ACTIVE_LEVEL,
                    cv,
                    selection,
                    args);
        }
    }

    public static void transferActiveLevelToCompletedLevelIfExists(Context context) {
        LinkedList<Level> activeLevels = queryAllActiveLevels(context);
        if (activeLevels.size() > 1) {
            //Something went wrong apparently (these should be only one active level), so delete all entries
            for (Level level: activeLevels) {
                deleteActiveLevels(context);
            }
        }
        else if (activeLevels.size() == 1) {
            insertCompletedLevel(context, activeLevels.get(0));
            //TODO: For all deletes, like the following, make sure that the action before was successful
            deleteActiveLevels(context);
        }
    }

    public static void deleteActiveLevels(Context context) {
        int numDeleted = context.getContentResolver().delete(NumbersContract.TableActiveLevel.BASE_CONTENT_URI_ACTIVE_LEVEL, null, null);
    }

    public static void updateActiveLevel(Context context, Level level) {

    }

    public static void insertActiveLevel(Context context, Level level) {
        //TODO: Refactor creation of contentValues. This is also used in next method
        ContentValues contentValues = new ContentValues();
        contentValues.put(NumbersContract.TableActiveLevel.KEY_NUMBERS, level.toString());
        contentValues.put(NumbersContract.TableActiveLevel.KEY_USER_TIME, level.getUserTime());
        Uri uri = context.getContentResolver().insert(NumbersContract.TableActiveLevel.BASE_CONTENT_URI_ACTIVE_LEVEL, contentValues);
    }

    public static void insertCompletedLevel(Context context, Level level) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NumbersContract.TableCompletedLevels.KEY_NUMBERS, level.toString());
        contentValues.put(NumbersContract.TableCompletedLevels.KEY_USER_TIME, level.getUserTime());
        Uri uri = context.getContentResolver().insert(NumbersContract.TableCompletedLevels.BASE_CONTENT_URI_COMPLETED_LEVELS, contentValues);
    }
}



