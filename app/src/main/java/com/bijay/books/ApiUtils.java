package com.bijay.books;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ApiUtils {
    private static final String TAG = ApiUtils.class.getSimpleName();
    private ApiUtils(){}

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String API_KEY ="AIzaSyBJgh4X6zMD9AlO_nosaN9JMeT-Q7BufbY";
    public static URL buildUrl(String title){
        URL url=null;
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter("q",title)
                        .appendQueryParameter("key", API_KEY)
                        .build();
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.i(TAG, "Malformed URL - Unable to retrive data");
        }
        return url;
    }

    public static String getJson(URL url) throws IOException{
        String jsonResponse="";
        HttpURLConnection connection;
        InputStream inputStream;
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15 * 1000);
        connection.setReadTimeout(10 * 1000);

        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            inputStream = connection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        }else{
            Log.i("MyHttp", "Response code = " + connection.getResponseCode());
        }
        connection.disconnect();
        return jsonResponse;
    }

    // Called by getJSON method.
    private static String readFromStream(InputStream inputStream) throws IOException{
        String line ="";
        StringBuilder output = new StringBuilder();
        BufferedReader reader = null;
        InputStreamReader inputStreamReader;
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
        }
        return output.toString();
    }

}
