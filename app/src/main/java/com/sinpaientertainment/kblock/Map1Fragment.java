package com.sinpaientertainment.kblock;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sinpaientertainment.kblock.R;
import com.sinpaientertainment.kblock.Services.CommanActivity;
import com.sinpaientertainment.kblock.Services.CommonMethod;
import com.sinpaientertainment.kblock.Services.GPSTracker;
import com.sinpaientertainment.kblock.Services.RefrenceUrl;
import com.sinpaientertainment.kblock.Services.UserlistData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yashco on 10/22/2016.
 */
public class Map1Fragment extends Fragment implements OnMapReadyCallback, LocationListener {


    GoogleMap m_pmap;
    public GPSTracker gps;
    boolean mflag = true;
    double latitude1;
    double longitude1;
    private ArrayList<LatLng> points;
    private Marker customMarker;
    GoogleApiClient mGoogleApiClient;
    Polyline line;
    Context ctx = null;
    ProgressDialog pDialog;

    public static AQuery aq = null;
    ArrayList<UserlistData> userlistget = new ArrayList<UserlistData>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout, container, false);
        ctx = getActivity();
        aq = new AQuery(ctx);
        com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        m_pmap = mapFragment.getMap();
        // m_pmap.setMyLocationEnabled(true);
        points = new ArrayList<LatLng>();
        // UserList(getActivity());
        /********/

        int permissionCheck = ContextCompat.checkSelfPermission(Home.main,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Home.main,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            StartMap();


        }
        return view;
    }

    private void StartMap() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);



        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        if (location != null) {
            gps.onLocationChanged(location);

            locationManager.requestLocationUpdates(bestProvider, 5, 0, this);

        }

        gps = new GPSTracker(Home.main) {
            @Override
            public void onLocationChanged(Location location) {
                super.onLocationChanged(location);


                if (null != m_pmap && mflag) {
                    mflag = false;
                    double ddd = 0.0001f;

                    LatLng sydney1 = new LatLng(location.getLatitude(), location.getLongitude());
                    points.add(sydney1);
                    String name = CommanActivity.getValue(Home.main, RefrenceUrl.KEY_USER_NAME, "");
                    // redrawLine(name, sydney1);
                    // disp(name+"ppppppppp");

                    //LatLng sydney2 = new LatLng(location.getLatitude() + ddd, location.getLongitude());
                    // LatLng sydney3 = new LatLng(location.getLatitude(), location.getLongitude() + ddd);
                    // LatLng sydney4 = new LatLng(location.getLatitude() + ddd, location.getLongitude() + ddd);

                    // LatLng sydney5 = new LatLng(location.getLatitude() - ddd, location.getLongitude());
                    // LatLng sydney6 = new LatLng(location.getLatitude(), location.getLongitude() - ddd);
                    //  LatLng sydney7 = new LatLng(location.getLatitude() - ddd, location.getLongitude() - ddd);


                    //  latitude1 = location.getLatitude();
                    //  longitude1 = location.getLongitude();
                    //m_pmap.clear();
//
                    //  customMarker = m_pmap.addMarker(new MarkerOptions()
                    //  .position(sydney1)
                    //.title(name)
                    ///   );

                    //  addMarker();
                    // redrawLine();
                      /*  m_pmap.addMarker(new MarkerOptions()
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
                        );*/


                }
                LatLng sydney2 = new LatLng(location.getLatitude(), location.getLongitude());
                points.add(sydney2);
                String name = CommanActivity.getValue(Home.main, RefrenceUrl.KEY_USER_NAME, "");
                redrawLine(name, sydney2);
                // disp(name + "ppppppppp");

            }

        };
    }

    public void disp(String s) {
        Toast.makeText(Home.main, s, Toast.LENGTH_SHORT).show();
    }

    private void redrawLine(String title, LatLng loc) {


        // m_pmap.clear();
        PolylineOptions options = new PolylineOptions().width(5).color(Color.RED).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);

        }
        //   m_pmap.setMyLocationEnabled(true);
        //   m_pmap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 19));
        customMarker = m_pmap.addMarker(new MarkerOptions().title(title).position(loc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        line = m_pmap.addPolyline(options);
        // addMarker();

        //add Polyline
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
                    Uri uri = Uri.fromParts("package", Home.main.getPackageName(), null);
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

        if (ActivityCompat.checkSelfPermission(Home.main, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.main, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // m_pmap.setMyLocationEnabled(true);

        if (mflag && gps != null && gps.getLocation() != null && (gps.getLatitude() > 0.0f || gps.getLongitude() > 0.0f)) {
            Location location = gps.getLocation();
            mflag = false;
            double ddd = 0.0001f;
            LatLng sydney1 = new LatLng(location.getLatitude(), location.getLongitude());


            m_pmap.setMyLocationEnabled(true);
            m_pmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1, 32));
            String name = CommanActivity.getValue(Home.main, RefrenceUrl.KEY_USER_NAME, "");
            //m_pmap.clear();
            customMarker = m_pmap.addMarker(new MarkerOptions()
                    .position(sydney1)
                    .title(name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            addMarker();

        }


    }

    private void addMarker() {

        String name = CommanActivity.getValue(Home.main, RefrenceUrl.KEY_USER_NAME, "");


        if (userlistget.size() > 0) {
            for (int k = 0; k < userlistget.size(); k++) {
                UserlistData listuser = userlistget.get(k);
                Double lat = 0.0;
                Double lng = 0.0;
                if (listuser.latitude.equalsIgnoreCase("")) {
                    double ddd = 0.0001f;
                    lat = 0.0 + 0.0001f;
                } else {
                    lat = Double.parseDouble(listuser.latitude);
                }
                if (listuser.latitude.equalsIgnoreCase("")) {
                    lng = 0.0 + 0.0001f;
                } else {
                    lng = Double.parseDouble(listuser.longitude);
                }
                LatLng sydney;
                if (k % 2 == 1) {
                    sydney = new LatLng(lat + 0.0001f, lng);
                } else {
                    sydney = new LatLng(lat - 0.0001f, lng);

                }
                System.out.println(lat + "h" + lng);


                String username = listuser.username;

                if (ActivityCompat.checkSelfPermission(Home.main, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.main, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    return;
                }
                m_pmap.setMyLocationEnabled(true);
                // m_pmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 27));
                m_pmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 32));
                //m_pmap.clear();

                if (username.equalsIgnoreCase(name)) {

                } else {
                    customMarker = m_pmap.addMarker(new MarkerOptions()
                            .position(sydney)
                            .title(username)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                }
                //  arrorright.setTag(listprofsnal.id+""+listprofsnal);
                //  mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


            }
        }
            /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    relative.setVisibility(View.VISIBLE);
                    //  Toast.makeText(HomeActivityClient.this,"marker"+marker.getTitle()+ marker.getId(),Toast.LENGTH_SHORT).show();;
                    String title = marker.getTitle().toString();
                    if(profisnallist.size()>0) {
                        for (int k = 0; k < profisnallist.size(); k++) {

                            ProfiesnalData listprofsnal = profisnallist.get(k);
                            String name=listprofsnal.firstname +" " + listprofsnal.lastname;
                            if(title.equalsIgnoreCase(name)){
                                String bookid="",iscompleted="";
                                if(listprofsnal.isbook.equalsIgnoreCase("")){
                                    bookid="0";
                                }else{
                                    bookid=listprofsnal.isbook;

                                }
                                if(listprofsnal.isCompleted==-1){
                                    iscompleted="0";
                                }else{
                                    iscompleted=listprofsnal.isCompleted+"";
                                }
                                Intent intent = new Intent(ctx, ArtistProfileActivity.class);
                                intent.putExtra(AppContact.KEY_PROFESSNAL_ID,listprofsnal.id+"" );
                                intent.putExtra(AppContact.CON_USER,user);
                                intent.putExtra(AppContact.KEY_CATEGORY_ID, catogoryid);
                                intent.putExtra(AppContact.CON_PROFESSION, listprofsnal);
                                intent.putExtra(AppContact.KEY_IS_BOOK,bookid);
                                intent.putExtra(AppContact.KEY_IS_COMPLET,iscompleted);
                                startActivity(intent);

                            }

                        }

                    }*/

    }

    public void UserList(final Context ctx) {


        ProgressDialog pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);


        if (haveNetworkConnection(ctx)) {

            HashMap<String, String> params = new HashMap<String, String>();
            //params.put("action","Login");


            Log.d("params", params + "");

            aq.progress(pDialog).ajax(RefrenceUrl.weburl + RefrenceUrl.METHOD_USER_LIST, JSONObject.class, new AjaxCallback<JSONObject>() {

                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    String Messagr, Response;
                    if (json != null) {
                        Log.d("json:", json + "");
                        try {
                            // Messagr = json.getString(AppContact.KEY_SERVICE_NAME);
                            Response = json.getString(RefrenceUrl.KEY_RES_CODE);

                            if (Response.equalsIgnoreCase("0")) {

                                JSONArray jarray = json.getJSONArray(RefrenceUrl.KEY_DATA);

                                userlistget.clear();
                                for (int i = 0; i < jarray.length(); i++) {

                                    JSONObject object = jarray.getJSONObject(i);
                                    UserlistData userlist = new UserlistData(object);
                                    userlistget.add(userlist);
                                }
                                addMarker();

                            } else {


                                String error_msg = json.getString(RefrenceUrl.KEY_RES_ERROR);
                                CommonMethod.ShowMsgOn(ctx, RefrenceUrl.KEY_MSG, error_msg, RefrenceUrl.KEY_OK);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else

                    {
                        CommonMethod.ShowMsgOn(ctx, RefrenceUrl.KEY_MSG, RefrenceUrl.KEY_MSG_DATA_ERROR, RefrenceUrl.KEY_OK);
                    }

                }
            });

        } else {
            CommonMethod.ShowMsgOn(ctx, RefrenceUrl.KEY_MSG, RefrenceUrl.KEY_MSG_NETWORK_ERROR, RefrenceUrl.KEY_OK);
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



    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
