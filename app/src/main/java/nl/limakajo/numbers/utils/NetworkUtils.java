package nl.limakajo.numbers.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import nl.limakajo.numbers.data.NumbersContract;
import nl.limakajo.numbers.main.MainActivity;
import nl.limakajo.numbers.numbersgame.Level;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author M.W.Bouwkamp
 */

public class NetworkUtils {
    private static final String GET_URL = "http://limakajo.mygamesonline.org/getlevels.php";
    private static final String SET_URL = "http://limakajo.mygamesonline.org/setlevels.php";
    private static final String SERVER = "fdb23.runhosting.com";
    private static final String USER = "2877361_numbers";
    private static final String PASS = "Numbersjuig448";
    private static final String DB = "2877361_numbers";

    private static final String TAG = NetworkUtils.class.getName();

    public static String getLevelsJSONFromServer() {
        HttpURLConnection httpURLConnection = null;
        URL url = null;
        String result = null;

        try {
            url = new URL(GET_URL);
            Log.i(TAG, "Url created successfully");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            httpURLConnection = (HttpURLConnection) Objects.requireNonNull(url).openConnection();
//			httpURLConnection.setReadTimeout(READ_TIMEOUT);
//			httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setRequestMethod("POST");

            // setDoInput and setDoOutput method depict handling of both send and receive
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            // Append parameters to URL
            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("user", USER);
            builder.appendQueryParameter("pass", PASS);
            builder.appendQueryParameter("server", SERVER);
            builder.appendQueryParameter("db", DB);

            String query = builder.build().getEncodedQuery();

            // Open connection for sending data
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            httpURLConnection.connect();
            Log.i(TAG, "HttpURLConnection connected successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get result from server
        try {
            int response_code = Objects.requireNonNull(httpURLConnection).getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = httpURLConnection.getInputStream();
                Scanner scanner = new Scanner(input);
                scanner.useDelimiter("\\A");
                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    result = scanner.next();
                }
                Log.i(TAG, "Result read successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(httpURLConnection).disconnect();
            Log.i(TAG, "HttpURLConnection closed successfully");
        }
        Log.i(TAG, "Levels successfully downloaded from server");
        return result;
    }

    public static void sendLevelsToServer(Context context, LinkedList<Level> levels) {
        for (Level level: levels) {
            String result = executeHttpPost(level.toString(), Integer.toString(level.getUserTime()));
            if (result.equals("successful")) {
                DatabaseUtils.updateTableLevelsLevelStatusForSpecificLevel(MainActivity.getContext(), level, GameUtils.LevelState.COMPLETED);
            }
        }
    }

    private static String executeHttpPost(String numbers, String userTime) {

        HttpURLConnection httpURLConnection = null;
        URL url;

        try {
            url = new URL(SET_URL);
            Log.i(TAG, "Url created successfully");
            httpURLConnection = (HttpURLConnection)url.openConnection();

            //conn.setReadTimeout(READ_TIMEOUT);
            //conn.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setRequestMethod("POST");

            // setDoInput and setDoOutput method depict handling of both send and receive
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            // Append parameters to URL
            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("user", USER);
            builder.appendQueryParameter("pass", PASS);
            builder.appendQueryParameter("server", SERVER);
            builder.appendQueryParameter("db", DB);
            builder.appendQueryParameter("numbers", numbers);
            builder.appendQueryParameter("usertime", userTime);

            String query = builder.build().getEncodedQuery();

            // Open connection for sending data
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            httpURLConnection.connect();
            Log.i(TAG, "HttpURLConnection connected successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            int response_code = Objects.requireNonNull(httpURLConnection).getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {
                Log.i(TAG, "Data sent successfully");
                return("successful");

            }else{
                Log.i(TAG, "Data not sent successfully");
                return("unsuccessful");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "exception";
        } finally {
            Objects.requireNonNull(httpURLConnection).disconnect();
        }

    }

}
