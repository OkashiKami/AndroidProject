package com.sinpaientertainment.kblock.not_used;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sinpaientertainment.kblock.R;
import com.sinpaientertainment.kblock.Services.GPSTracker;

/**
 * Created by yashcosys on 28/09/16.
 */

public class MapView extends FragmentActivity implements OnMapReadyCallback,LocationListener {
    GoogleMap m_pmap;
    public GPSTracker gps;
    boolean mflag = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /********/
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            StartMap();
        }
    }

    private void StartMap() {
        gps = new GPSTracker(this) {
            @Override
            public void onLocationChanged(Location location) {
                super.onLocationChanged(location);
                if (null != m_pmap && mflag) {
                    mflag = false;
                    double ddd = 0.0001f;
                    LatLng sydney1 = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLng sydney2 = new LatLng(location.getLatitude() + ddd, location.getLongitude());
                    LatLng sydney3 = new LatLng(location.getLatitude(), location.getLongitude() + ddd);
                    LatLng sydney4 = new LatLng(location.getLatitude() + ddd, location.getLongitude() + ddd);

                    LatLng sydney5 = new LatLng(location.getLatitude() - ddd, location.getLongitude());
                    LatLng sydney6 = new LatLng(location.getLatitude(), location.getLongitude() - ddd);
                    LatLng sydney7 = new LatLng(location.getLatitude() - ddd, location.getLongitude() - ddd);

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    m_pmap.setMyLocationEnabled(true);
                    m_pmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1, 32));

                    //m_pmap.clear();
                    m_pmap.addMarker(new MarkerOptions()
                            .position(sydney1)
                            .title("Marker1")
                    );
                    m_pmap.addMarker(new MarkerOptions()
                            .position(sydney2)
                            .title("Marker2")
                    );
                    m_pmap.addMarker(new MarkerOptions()
                            .position(sydney3)
                            .title("Marker3")
                    );
                    m_pmap.addMarker(new MarkerOptions()
                            .position(sydney4)
                            .title("Marker4")
                    );
                    m_pmap.addMarker(new MarkerOptions()
                            .position(sydney5)
                            .title("Marker5")
                    );
                    m_pmap.addMarker(new MarkerOptions()
                            .position(sydney6)
                            .title("Marker6")
                    );
                    m_pmap.addMarker(new MarkerOptions()
                            .position(sydney7)
                            .title("Marker7")
                    );



                }
            }

        };
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    StartMap();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onMapReady(GoogleMap map) {

        m_pmap = map;
        m_pmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        m_pmap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        m_pmap.setMyLocationEnabled(true);

        if(mflag && gps!=null && gps.getLocation()!=null && (gps.getLatitude()>0.0f || gps.getLongitude()>0.0f))
        {
            Location location = gps.getLocation();
                    mflag = false;
            double ddd = 0.0001f;
            LatLng sydney1 = new LatLng(location.getLatitude(), location.getLongitude());
            LatLng sydney2 = new LatLng(location.getLatitude() + ddd, location.getLongitude());
            LatLng sydney3 = new LatLng(location.getLatitude(), location.getLongitude() + ddd);
            LatLng sydney4 = new LatLng(location.getLatitude() + ddd, location.getLongitude() + ddd);

            LatLng sydney5 = new LatLng(location.getLatitude() - ddd, location.getLongitude());
            LatLng sydney6 = new LatLng(location.getLatitude(), location.getLongitude() - ddd);
            LatLng sydney7 = new LatLng(location.getLatitude() - ddd, location.getLongitude() - ddd);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            m_pmap.setMyLocationEnabled(true);
            m_pmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1, 32));

            //m_pmap.clear();
            m_pmap.addMarker(new MarkerOptions()
                    .position(sydney1)
                    .title("Marker1")
            );
            m_pmap.addMarker(new MarkerOptions()
                    .position(sydney2)
                    .title("Marker2")
            );
            m_pmap.addMarker(new MarkerOptions()
                    .position(sydney3)
                    .title("Marker3")
            );
            m_pmap.addMarker(new MarkerOptions()
                    .position(sydney4)
                    .title("Marker4")
            );
            m_pmap.addMarker(new MarkerOptions()
                    .position(sydney5)
                    .title("Marker5")
            );
            m_pmap.addMarker(new MarkerOptions()
                    .position(sydney6)
                    .title("Marker6")
            );
            m_pmap.addMarker(new MarkerOptions()
                    .position(sydney7)
                    .title("Marker7")
            );
        }


    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
