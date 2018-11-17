package nl.limakajo.numbers.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

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
    private static LinkedList<Level> queryAllLevels(Context context) {
        String[] projection = {
                NumbersContract.TableLevels.KEY_NUMBERS,
                NumbersContract.TableLevels.KEY_USER_TIME,
                NumbersContract.TableLevels.KEY_AVERAGE_TIME};
        Cursor cursor = context.getContentResolver().query(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, projection, null, null, null, null);
        return getLevelsFromCursor(cursor);
    }

    public static LinkedList<Level> getLevelsWithStatus(Context context, GameUtils.LevelState levelState) {
        String selection = NumbersContract.TableLevels.KEY_LEVEL_STATUS + " = ?";
        String[] args = {Character.toString(levelState.asChar())};
        String[] projection = {
                NumbersContract.TableLevels.KEY_NUMBERS,
                NumbersContract.TableLevels.KEY_USER_TIME,
                NumbersContract.TableLevels.KEY_AVERAGE_TIME};
        Cursor cursor = context.getContentResolver().query(NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS, projection, selection, args, null, null);
        return getLevelsFromCursor(cursor);
    }

    @NonNull
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
                contentValues.put(NumbersContract.TableLevels.KEY_LEVEL_STATUS, Character.toString(GameUtils.LevelState.NEW.asChar()));
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
        LinkedList<Level> levels = getLevelsWithStatus(context, GameUtils.LevelState.NEW);
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
        context.getContentResolver().update(
                NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS,
                cv,
                selection,
                args);
    }

    /**
     * Updates levelStatus for a level in table levels in SQLite database
     * @param context
     * @param level
     * @param newStatus
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
     * Update levelStatus of levels with a specific current status
     * @param context
     * @param oldStatus
     * @param newStatus
     */
    public static void updateTableLevelsLevelStatusForSpecificCurrentStatus(Context context, GameUtils.LevelState oldStatus, GameUtils.LevelState newStatus) {
        String selection = NumbersContract.TableLevels.KEY_LEVEL_STATUS + " = ?";
        String[] args = {Character.toString(oldStatus.asChar())};
        //TODO: Refactor. DRY!
        ContentValues cv = new ContentValues();
        cv.put(NumbersContract.TableLevels.KEY_LEVEL_STATUS, Character.toString(newStatus.asChar()));
        context.getContentResolver().update(
                NumbersContract.TableLevels.BASE_CONTENT_URI_LEVELS,
                cv,
                selection,
                args);

    }


}