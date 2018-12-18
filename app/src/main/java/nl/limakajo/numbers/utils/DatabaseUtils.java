package nl.limakajo.numbers.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.LinkedList;

import nl.limakajo.numbers.data.NumbersContract;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numberslib.Level;

/**
 * Class that holds all database operations
 *
 * @author M.W.Bouwkamp
 */
public class DatabaseUtils {

    /**
     * Returns all levels in the SQLite database table TableLevels that are listed in a certain LevelState
     *
     * @param context       application context
     * @param levelState    the LevelState of interest
     * @return              all levels in the SQLite database table TableLevels that match the provided levelState
     */
    public static LinkedList<Level> getLevelsWithSpecificStatus(Context context, GameUtils.LevelState levelState) {
        String selection = NumbersContract.TableLevels.KEY_LEVEL_STATUS + " = ?";
        String[] args = {Character.toString(levelState.asChar())};
        String[] projection = {
                NumbersContract.TableLevels.KEY_NUMBERS,
                NumbersContract.TableLevels.KEY_USER_TIME,
                NumbersContract.TableLevels.KEY_AVERAGE_TIME};
        Cursor cursor = context.getContentResolver().query(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, projection, selection, args, null, null);
        return getLevelsFromCursor(cursor);
    }

    /**
     * Returns all levels in a cursor
     *
     * @param cursor    the cursor that contains the level data
     * @return          all levels in th cursor
     */
    private static LinkedList<Level> getLevelsFromCursor(Cursor cursor) {
        String numbersString;
        LinkedList<Level> levels = new LinkedList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    numbersString = cursor.getString(cursor.getColumnIndex(NumbersContract.TableLevels.KEY_NUMBERS));
                    int userTime = Integer.parseInt(cursor.getString(cursor.getColumnIndex(NumbersContract.TableLevels.KEY_USER_TIME)));
                    int averageTime = Integer.parseInt(cursor.getString(cursor.getColumnIndex(NumbersContract.TableLevels.KEY_AVERAGE_TIME)));
                    levels.add(new Level(numbersString, averageTime, userTime));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return levels;
    }

    /**
     * Updates averageTime in the SQLite database table TableLevels for all Levels in levels
     * If a level does is not yet in the SQLite database, the level will be inserted
     *
     * @param context   application context
     * @param levels    the levels that need updating
     */
    public static void updateLevelsAverageTimeForSpecificLevels(Context context, LinkedList<Level> levels) {
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
                contentValues.put(NumbersContract.TableLevels.KEY_LEVEL_STATUS, Character.toString(GameUtils.LevelState.NEW.asChar()));
                Uri uri = context.getContentResolver().insert(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, contentValues);
            }
        }
    }

    /**
     * Returns a level that has not yet been played for which averageTime is closest to the userAverageTime
     * Difference is calculated by: Math.abs(level.getAverageTime() - userAverageTime))
     *
     * @param context   application context
     * @return          level that has not yet been played for which averageTime is closest to userAverageTime
     */
    public static Level getLevelWithAverageTimeCloseToUserAverageTime(Context context) {
        Level toReturn = null;
        LinkedList<Level> levels = getLevelsWithSpecificStatus(context, GameUtils.LevelState.NEW);
        int userAverageTime = MainActivity.getPlayer().getUserAverageTime();
        int timeDifferenceSelectedLevel = Integer.MAX_VALUE;
        for (Level level: levels) {
            if (Math.abs(level.getAverageTime() - userAverageTime) < timeDifferenceSelectedLevel) {
                toReturn = level;
                timeDifferenceSelectedLevel = Math.abs(toReturn.getAverageTime() - userAverageTime);
            }
        }
        return toReturn;
    }

    /**
     * Returns the average time it takes the user to complete a level.
     * The average time is calculated based on levels that have been played.
     * This means only those levels that are in LevelState COMPLETED or UPLOAD.
     *
     * @param context   application context
     * @return          the average time it takes the user to complete a level
     */
    public static int getAverageTimeFromCompletedLevels(Context context) {
        LinkedList<Level> levels = getLevelsWithSpecificStatus(context, GameUtils.LevelState.COMPLETED);
        levels.addAll(getLevelsWithSpecificStatus(context, GameUtils.LevelState.UPLOAD));
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
     * Updates the user time for a specific level in the SQLite database table TableLevels
     *
     * @param context   application context
     * @param level     level that needs to be updated
     */
    public static void updateTableLevelsUserTimeForSpecificLevel(Context context, Level level) {
        String selection = NumbersContract.TableLevels.KEY_NUMBERS + " = ?";
        String[] args = {level.toString()};
        ContentValues cv = new ContentValues();
        cv.put(NumbersContract.TableLevels.KEY_USER_TIME, level.getUserTime());
        context.getContentResolver().update(
                NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS,
                cv,
                selection,
                args);
    }

    /**
     * Updates levelStatus for a level in the SQLite database table TableLevels
     *
     * @param context       application context
     * @param level         level that needs to be updated
     * @param newStatus     new value for the status of the level
     */
    public static void updateTableLevelsLevelStatusForSpecificLevel(Context context, Level level, GameUtils.LevelState newStatus) {
        String selection = NumbersContract.TableLevels.KEY_NUMBERS + " = ?";
        String[] args = {level.toString()};
        ContentValues cv = new ContentValues();
        cv.put(NumbersContract.TableLevels.KEY_LEVEL_STATUS, Character.toString(newStatus.asChar()));
        context.getContentResolver().update(
                NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS,
                cv,
                selection,
                args);
    }

    /**
     * Updates levelStatus for a level in the SQLite database table TableLevels from one status to another
     *
     * @param context       application context
     * @param oldStatus     current status of the levels
     * @param newStatus     new status for the levels
     */
    public static void updateTableLevelsLevelStatusForSpecificCurrentStatus(Context context, GameUtils.LevelState oldStatus, GameUtils.LevelState newStatus) {
        String selection = NumbersContract.TableLevels.KEY_LEVEL_STATUS + " = ?";
        String[] args = {Character.toString(oldStatus.asChar())};
        ContentValues cv = new ContentValues();
        cv.put(NumbersContract.TableLevels.KEY_LEVEL_STATUS, Character.toString(newStatus.asChar()));
        context.getContentResolver().update(
                NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS,
                cv,
                selection,
                args);

    }
}