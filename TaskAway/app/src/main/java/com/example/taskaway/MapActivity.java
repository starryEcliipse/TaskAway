package com.example.taskaway;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
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

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return latLng;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationMap = googleMap;

        locationMap.addMarker(new MarkerOptions().position(task_location_coords).title("Task Location"));
        locationMap.moveCamera(CameraUpdateFactory.newLatLng(task_location_coords));
    }
}

