package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Acts as an Activity to view the location details of a Task
 *
 * Source: https://developers.google.com/maps/documentation/android-api/start
 * @author Diane Boytang
 * Created on 2018-03-26
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap locationMap;
    String userName;
    String user_id;
    String task_id;
    String task_location;
    LatLng task_location_coords;
    private Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get information needed to display location
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        user_id = intent.getStringExtra("userid");
        task_id = intent.getStringExtra("task_id");
        task_location = intent.getStringExtra("location");
        task_location_coords = this.getTask_location_coords(getApplicationContext(), task_location);
    }

    /**
     * Method to get latitude and longitude from the string location of the task
     * SOURCE: https://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address
     *         Accessed March 27, 2018
     * @param context - context
     * @param strAddress - string containing the address of the task location
     * @return LatLng object containing the latitude and the longitude coordinates of the task location
     */

    public LatLng getTask_location_coords(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng latLng = null;

        Log.i("MAPACTIVITY", "Our strAddress is: "+strAddress);

        try {
            address = coder.getFromLocationName(strAddress, 100);
            Log.i("MAPACTIVITY", "Address.get(0) is: "+ address.get(0));
            Log.i("MAPACTIVITY", "Address is: "+ address);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {
            Log.i("MAPACTIVITY", "Catch was called in getTask_location_coords");
            ex.printStackTrace();
        }

        Log.i("MAPACTIVITY", "Function passed; getTask_location_coords");
        return latLng;
    }

    /**
     * Does the parsing of the address using the google api
     * @param googleMap
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (task_location_coords == null){
            try{
                String task_location_encoded = URLEncoder.encode(task_location, "UTF-8");
                String location_uri ="https://maps.googleapis.com/maps/api/geocode/json?address=" + task_location_encoded + "&key=AIzaSyBOflugbssWI1J6qUsPPt7-rEeF01MKOuY";
                GetLocationJson locationJson = new GetLocationJson();
                locationJson.execute(location_uri);

                String json = locationJson.get();

                if (json == null){
                    Toast.makeText(getApplicationContext(), "Could not get data from Google Maps server", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject jsonObject = new JSONObject(json);

                JSONObject locationGeo = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                String lat = locationGeo.getString("lat");
                String lng = locationGeo.getString("lng");

                Double latitude = Double.parseDouble(lat);
                Double longitude = Double.parseDouble(lng);

                LatLng location_coords = new LatLng(latitude, longitude);

                locationMap = googleMap;

                locationMap.addMarker(new MarkerOptions().position(location_coords).title("Task Location"));
                locationMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location_coords, 10.0f));



                Log.i("MAPACTIVITYJSON", "Location info from Google api: " + locationJson.get());


            } catch (Exception e){
                e.printStackTrace();
            }


        }
        else {
            locationMap = googleMap;

            locationMap.addMarker(new MarkerOptions().position(task_location_coords).title("Task Location"));
            locationMap.moveCamera(CameraUpdateFactory.newLatLngZoom(task_location_coords, 10.0f));
        }
    }
}

