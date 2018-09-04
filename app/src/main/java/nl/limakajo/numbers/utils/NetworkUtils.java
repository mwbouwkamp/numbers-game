package nl.limakajo.numbers.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import nl.limakajo.numbers.data.NumbersContract;
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

    public static final String GET_URL = "http://limakajo.16mb.com/getlevels.php";
    public static final String SET_URL = "http://limakajo.16mb.com/setlevels.php";
    public static final String SERVER = "mysql.hostinger.nl";
    public static final String USER = "u790400871_nmbr";
    public static final String PASS = "juig448";
    public static final String DB = "u790400871_nmbr";

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
            if (result.compareTo("-1") != 0) {
                String selection = NumbersContract.TableLevels.KEY_NUMBERS + " = ?";
                String[] args = {level.toString()};
                int numDeleted = context.getContentResolver().delete(NumbersContract.TableCompletedLevels.BASE_CONTENT_URI_COMPLETED_LEVELS, selection, args);
            }
        }
    }

    public static String executeHttpPost(String numbers, String userTime) {

        HttpURLConnection httpURLConnection = null;
        URL url;

        try {

            url = new URL(GET_URL);
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









		/*
		@SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		// Request parameters and other properties.
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("server", SERVER));
		postParams.add(new BasicNameValuePair("user", USER));
		postParams.add(new BasicNameValuePair("pass", PASS));
		postParams.add(new BasicNameValuePair("db", DB));
		postParams.add(new BasicNameValuePair("numbers", numbers));
		postParams.add(new BasicNameValuePair("usertime", userTime));
		try {
		    httpPost.setEntity(new UrlEncodedFormEntity(postParams, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		    // writing error to Log
		    e.printStackTrace();
		}
		*//*
		 * Execute the HTTP Request
		 *//*
		try {
		    HttpResponse response = httpClient.execute(httpPost);
		    HttpEntity respEntity = response.getEntity();
		    if (respEntity != null) {
		        // EntityUtils to get the response content
		        result =  EntityUtils.toString(respEntity);
		    }
		} catch (ClientProtocolException e) {
		    // writing exception to log
		    e.printStackTrace();
		} catch (IOException e) {
		    // writing exception to log
		    e.printStackTrace();
		}
		return result;
	*/
    }

}
