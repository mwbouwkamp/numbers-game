package nl.limakajo.numbers.utils;

import android.text.TextUtils;

import nl.limakajo.numbers.numbersgame.Level;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * @author M.W.Bouwkamp
 */

public class JSONUtils {

    private static final String NUMBERS = "numbers";
    private static final String AVERAGE_TIME = "averagetime";

    public static LinkedList<Level> getLevelsFromJson(String jsonString) {
        LinkedList<Level> levels = new LinkedList<>();
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            System.out.println("JSONSTRING:" + jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);
            levels = new LinkedList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String numbersString = object.getString(NUMBERS);
                int tile1 = Integer.parseInt(numbersString.substring(0, 3));
                int tile2 = Integer.parseInt(numbersString.substring(3, 6));
                int tile3 = Integer.parseInt(numbersString.substring(6, 9));
                int tile4 = Integer.parseInt(numbersString.substring(9, 12));
                int tile5 = Integer.parseInt(numbersString.substring(12, 15));
                int tile6 = Integer.parseInt(numbersString.substring(15, 18));
                int[] hand = {tile1, tile2, tile3, tile4, tile5, tile6};
                int goal = Integer.parseInt(numbersString.substring(18, 21));
                int averageTime = Integer.parseInt(object.getString(AVERAGE_TIME));
                levels.add(new Level(hand, goal, averageTime));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return levels;
    }
}
