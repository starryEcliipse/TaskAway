package com.example.taskaway;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.test.ActivityInstrumentationTestCase2;

import com.google.android.gms.maps.model.LatLng;
import com.robotium.solo.Solo;

import org.junit.Test;


import java.util.List;


/**
 * Created to test the MapActivity
 * @author Diane Boytang
 */

public class MapActivityTest extends ActivityInstrumentationTestCase2<MapActivity> {

    private Solo solo;

    public MapActivityTest() {
        super(com.example.taskaway.MapActivity.class);
    }



    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testGetTask_location_coords() throws Exception{
        Geocoder geocoder = new Geocoder(getActivity());

        //Test specific address
        List<Address> address;
        LatLng latLng;

        String location_1 = "59 Main Boulevard, Sherwood Park, AB, Canada";
        address = geocoder.getFromLocationName(location_1, 100);
        Address location = address.get(0);
        latLng = new LatLng(location.getLatitude(), location.getLongitude() );
        LatLng location_1_latlng = new LatLng(53.5374022, -113.3087432);
        assertEquals(latLng, location_1_latlng);


        //Test an airport code
        List<Address> address_2;
        LatLng latlng_2;

        String location_2 = "YEG";
        LatLng location_2_latlng = new LatLng(53.307103, -113.5760576) ;
        address_2 = geocoder.getFromLocationName(location_1, 100);
        Address location_12 = address.get(0);
        latlng_2 = new LatLng(location_12.getLatitude(), location_12.getLongitude());
        assertEquals(latlng_2, location_2_latlng);


        //Test a general location
        List<Address> address_3;
        LatLng latlng_3;

        String location_3 = "Edmonton, AB";
        LatLng location_3_latlng = new LatLng(53.544389, -113.4909267);
        Address location_13 = address.get(0);
        latlng_3 = new LatLng(location_12.getLatitude(), location_12.getLongitude());
        assertEquals(latlng_3, location_3_latlng);
    }


    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    
}
