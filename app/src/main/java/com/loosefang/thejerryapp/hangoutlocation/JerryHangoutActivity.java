package com.loosefang.thejerryapp.hangoutlocation;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loosefang.thejerryapp.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Tony on 12/27/2014.
 */
public class JerryHangoutActivity extends FragmentActivity{
    private static final int GPS_ERRORDIALOG_REQUEST = 9001;

    GoogleMap mMap;

    private String[] locationList = {"La Granja, Weston, FL", "Discount Auto Parts, FL", "South Beach", "Peru", "North Carolina", "Falcon Pub", "Ciudad Juarez"};
    private Map<Integer, String> locationMap = new HashMap<>();
    private Integer randomLoc;
    public String location;



    private String getLocation(){
        Random generator = new Random();

        for(int i = 0; i < locationList.length; i++) {
            Log.i("LOC", locationList[i]);
            locationMap.put(i, locationList[i]);
        }

        randomLoc = generator.nextInt(locationList.length);
        Log.i("LOC", String.valueOf(randomLoc));
        location = locationMap.get(randomLoc);
        Log.i("LOC", location);

        return location;
    }

    public String getSnippet(){
        String snippet;

/*
        0 "La Granja, Plantation, Florida",
        1 "Car Shop, Plantation, Florida",
        2 "South Beach",
        3 "Peru, Plantation",
        4 "North Carolina",
        5 "Falcon Pub, Plantation, Florida",
        6 "Ciudad Juarez"
         */
        switch (randomLoc) {
            case 0: snippet = "Mmm white sauce";
                    break;
            case 1: snippet = "Take car here when break";
                break;
            case 2: snippet = "Collins Beach!  Take picture";
                break;
            case 3: snippet = "You find my village?";
                break;
            case 4: snippet = "This where all my girlfriend live";
                break;
            case 5: snippet = "Is DIJ :(";
                break;
            case 6: snippet = "How I get to America continent";
                break;
            default: snippet = ":Hyena laugh: not sure what is";
        }

        return snippet;
    }


    private static float ZOOM = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(servicesOK()){
            setContentView(R.layout.jerry_hangout_activity);

            if(initMap()){
                try {
                    geoLocate();
//                    mMap.setMyLocationEnabled(true);
               } catch (Exception e) {Log.i("LOC Exception", "Msg: " + e.getMessage() + " LocalMsg: " + e.getLocalizedMessage());}
            }
            else {
                Toast.makeText(this, "Map not available =/", Toast.LENGTH_SHORT).show();
            }

        } else{
            setContentView(R.layout.main_activity_layout);
        }

    }

    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean servicesOK() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }
        else if(GooglePlayServicesUtil.isUserRecoverableError(isAvailable)){
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "Can't connect to Google Play services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean initMap(){
        if(mMap == null){
            SupportMapFragment mapFrag = (SupportMapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFrag.getMap();
        }
        return (mMap != null);
    }

    public void geoLocate() throws IOException {
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(getLocation(), locationList.length);
        Address add = list.get(0);
        String snippet = getSnippet();

        double lat = add.getLatitude();
        double lng = add.getLongitude();

        if(location == "Falcon Pub") {
            lat = 26.085252;
            lng = -80.251881;
            ZOOM = 15;
        }

        if(location == "Peru") {
            ZOOM = 5;
        }
        if(location == "North Carolina") {
            ZOOM = 6;
        }
        if(location == "Ciudad Juarez") {
            ZOOM = 5;
        }
        if(location == "Discount Auto Parts, FL" || location == "La Granja, Weston, FL" || location == "South Beach") {
            ZOOM = 12;
        }

        gotoLocation(lat,lng, ZOOM);

        MarkerOptions options = new MarkerOptions()
                                                   .title(location)
                                                   .position(new LatLng(lat, lng))
                                                   .snippet(snippet);

        Log.i("MARKER", "Title: " + options.getTitle() + " - " + "Snippet: " + options.getSnippet());

        mMap.addMarker(options);

    }
}
