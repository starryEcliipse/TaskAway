package com.example.taskaway;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;


/**
 * This class enables us to get a json object representation of our location
 *
 * Source: https://stackoverflow.com/questions/4567216/geocoder-getfromlocationname-returns-only-null/4950178#4950178
 * Accessed on: April 3, 2018
 * @author Diane Boytang
 */

public class GetLocationJson extends AsyncTask <String, Void, String>{



    @Override
    protected String doInBackground(String... strings) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection;
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(20000);
            InputStream is = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is);

            int data = inputStreamReader.read();
            while (data != -1) {
                char curr = (char) data;
                result += curr;
                data = inputStreamReader.read();
            }
            return result;
        }catch (SocketTimeoutException e){
            Log.i("ASYNC TASK", "Failed to get location from Google server");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("GETLOCATIONJSON", "Async task returned null result");
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(result != null) {
            Log.i("GETLOCATIONJSON", "result was NOT null");
            try {
                JSONObject locationObject = new JSONObject(result);
                JSONObject locationGeo = locationObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                Intent i = new Intent();


            } catch (JSONException e) {
                Log.i("GETLOCATIONJSON", "result was NOT null BUT catch was called");
                e.printStackTrace();
            }
        }

    }


}
