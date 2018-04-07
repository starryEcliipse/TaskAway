package com.example.taskaway;

import android.app.Activity;
import android.location.Geocoder;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.google.android.gms.maps.model.LatLng;
import com.robotium.solo.Solo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


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
        String location_1 = "59 Main Boulevard, Sherwood Park, AB, Canada";
        LatLng location_1_latlng = new LatLng(53.5374022, -113.3087432);
        assertEquals(geocoder.getFromLocationName(location_1, 1), location_1_latlng);

        //Test an airport code
        String location_2 = "YEG";
        LatLng location_2_latlng = new LatLng(53.307103, -113.5760576) ;
        assertEquals(geocoder.getFromLocationName(location_2, 1), location_2_latlng);

        //Test a general location
        String location_3 = "Edmonton, AB";
        LatLng location_3_latlng = new LatLng(53.544389, -113.4909267);
        assertEquals(geocoder.getFromLocationName(location_3, 1), location_3_latlng);

    }





    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }




}
