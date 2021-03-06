package com.example.denny.appcourse_hw8_4102056034;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle bundle = this.getIntent().getExtras();
        float results[]=new float[1];

        // Add a marker in Sydney and move the camera
        LatLng start = new LatLng(bundle.getDouble("startlat"), bundle.getDouble("startlong"));
        LatLng end = new LatLng(bundle.getDouble("endlat"), bundle.getDouble("endlong"));
        mMap.addMarker(new MarkerOptions().position(start).title("起點阿阿阿阿"));
        mMap.addMarker(new MarkerOptions().position(end).title("終點阿阿阿阿"));
        mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(start)
                .add(end));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
        Location.distanceBetween(bundle.getDouble("startlat"), bundle.getDouble("startlong"), bundle.getDouble("endlat"), bundle.getDouble("endlong"), results);
        Toast.makeText(getApplicationContext(),""+results[0],Toast.LENGTH_SHORT).show();
    }
}
