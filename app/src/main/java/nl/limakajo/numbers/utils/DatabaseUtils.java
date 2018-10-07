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

    /**
     * Retrieves all levels from SQLite database
     *
     * @param context context
     * @return levels levels
     */
    public static LinkedList<Level> getLevels(Context context) {
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
     * Retrieves all completed levels from SQLite database
     *
     * @param context context
     * @return completed levels
     */
    public static LinkedList<Level> getCompletedLevels(Context context) {
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
    public static void setLevels(Context context, LinkedList<Level> levels) {
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


    public static Level getLevel(Context context) {
        Level toReturn = null;
        LinkedList<Level> levels = getLevels(MainActivity.getContext());
        //TODO: Refactor userAverageTime: move to Player
        int userAverageTime = getAverageTime(MainActivity.getContext());
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
        LinkedList<Level> levels = getLevels(context);
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
        MainActivity.getContext().getContentResolver().update(
                NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS,
                cv,
                selection,
                args);
    }

    /**
     * Updates userTime for a level in table levels in SQLite database
     * @param context context
     * @param level level
     * @param userTime userTime
     */
    public static void updateTableCompletedLevelsUserTime(Context context, Level level, int userTime) {
        ContentValues cv = new ContentValues();
        cv.put(NumbersContract.TableCompletedLevels.KEY_NUMBERS, level.toString());
        cv.put(NumbersContract.TableCompletedLevels.KEY_USER_TIME, userTime);
        MainActivity.getContext().getContentResolver().insert(
                NumbersContract.TableCompletedLevels.BASE_CONTENT_URI_COMPLETED_LEVELS,
                cv);
    }

}
