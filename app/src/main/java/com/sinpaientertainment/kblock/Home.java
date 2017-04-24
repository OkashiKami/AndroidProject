package com.sinpaientertainment.kblock;

import android.Manifest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import  android.widget.*;


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
import com.sinpaientertainment.kblock.Services.CommonMethod;
import com.sinpaientertainment.kblock.Services.GPSTracker;
import com.sinpaientertainment.kblock.Services.CommanActivity;
import com.sinpaientertainment.kblock.Services.UserlistData;
import com.sinpaientertainment.kblock.managers.DrawerArrayAdapter;
import com.sinpaientertainment.kblock.managers.DrawerEntry;
import com.sinpaientertainment.kblock.Services.RefrenceUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Object;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import  java.io.*;
import  java.net.*;
import java.util.List;

/**
 * Created by sinpai on 10/17/2016.
 */

public class Home extends CommanActivity {
    public static Home main;
    Context ctx = null;
    ProgressDialog pDialog;
    public static   String titlee="";
    public static AQuery aq = null;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        main = this;
        ctx = Home.this;
        aq = new AQuery(ctx);


        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));

        setUpDrawer();
        Update();
        toHome();
    }


    public void Update() {
        //BackendlessUser user = Backendless.UserService.CurrentUser();
        //  if(user != null){
        String wantUsername = CommanActivity.getValue(ctx, RefrenceUrl.KEY_USER_NAME, "");
        titlee= CommanActivity.getValue(ctx, RefrenceUrl.KEY_USER_NAME, "");//(String) user.getProperty("username");
        String haveUsername = ((TextView) findViewById(R.id.drawerUsernameTV)).getText().toString();
        if (!haveUsername.equals(wantUsername)) {
            ((TextView) findViewById(R.id.drawerUsernameTV)).setText(wantUsername);
        }
        String wantEmail = CommanActivity.getValue(ctx, RefrenceUrl.KEY_EMAIL, "");
        String haveEmail = ((TextView) findViewById(R.id.drawerEmailTV)).getText().toString();
        if (!haveEmail.equals(wantEmail)) {
            ((TextView) findViewById(R.id.drawerEmailTV)).setText(wantEmail);
        }
        try {
            CircleImageView imageView = (CircleImageView) findViewById(R.id.profileimage);
            //new ImageTaskManager(imageView).execute(user.getProperty("profileimage").toString());
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        // }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Update();
            }
        }, 100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.out.println("Up home button was clicked!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /************************************************/
    /***************** DRAWER ***********************/

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    private void setUpDrawer() {
        final ListView list = (ListView) findViewById(R.id.drawer_list);
        final DrawerArrayAdapter adp = new DrawerArrayAdapter(this, R.layout.drawer_list_item);
        final Button btnlogout = (Button) findViewById(R.id.drawerLogoutButton);
        list.setAdapter(adp);
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adp);
        adp.registerDataSetObserver(new DataSetObserver() {
            public void OnChanged() {
                super.onChanged();
                list.setSelection(adp.getCount() - 1);
            }
        });
        adp.clear();

        adp.add(new DrawerEntry(null, "Home"));
        adp.add(new DrawerEntry(null, "Messaging"));
        adp.add(new DrawerEntry(null, "Maps"));
        adp.add(new DrawerEntry(null, "Tunes"));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Home.main.toHome();
                        break;
                    case 1:
                        Home.main.toMessage();
                        break;
                    case 2:
                        Home.main.toMaps();
                        break;
                    case 3:
                        Home.main.toTuens();
                        break;
                    default:
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        ((DrawerLayout) findViewById(R.id.mainDrawerLayout)).closeDrawers();
                        break;
                }
            }
        });


        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = CommanActivity.getValue(ctx, RefrenceUrl.KEY_USER_ID, "");
                LogOut(userid);
            }
        });
    }

    public void toHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.home_holder, new HomeFragment()).commit();
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((DrawerLayout) findViewById(R.id.mainDrawerLayout)).closeDrawers();
    }
    public void toMaps() {
        getSupportFragmentManager().beginTransaction().replace(R.id.home_holder, new Map1Fragment()).commit();
        getSupportActionBar().setTitle("Maps");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((DrawerLayout) findViewById(R.id.mainDrawerLayout)).closeDrawers();
    }
    public void toMessage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.home_holder, new MessageFragment()).commit();
        getSupportActionBar().setTitle("Messaging");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((DrawerLayout) findViewById(R.id.mainDrawerLayout)).closeDrawers();
    }
    public void toTuens() {
        //getSupportFragmentManager().beginTransaction().replace(R.id.home_holder, new MessageFragment()).commit();
        getSupportActionBar().setTitle("Tunes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((DrawerLayout) findViewById(R.id.mainDrawerLayout)).closeDrawers();
    }

    public static class HomeFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.home, container, false);


            return view;
        }
    }

    public static class MessageFragment extends Fragment {

        static BufferedReader inFromUser;
        static Socket clientSocket;
        static List<String> mylist = new ArrayList<>();

        static EditText editText;
        static ImageButton handle;
        static Button msgSubmit;


        static ListView listmessage;
        static ArrayAdapter<String> adapter;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.messageing, container, false);

            editText = (EditText) view.findViewById(R.id.msgMessage);
            handle = (ImageButton) view.findViewById(R.id.handle);
            msgSubmit = (Button) view.findViewById(R.id.msgSubmit);

            listmessage = (ListView) view.findViewById(R.id.list_messages);
            adapter = new ArrayAdapter<>(Home.main, android.R.layout.simple_list_item_1, mylist);
            listmessage.setAdapter(adapter);

            try
            {
                inFromUser = new BufferedReader( new InputStreamReader(System.in));
                clientSocket = new Socket("127.0.0.1", 6789);
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            SlidingDrawer sdrawer = (SlidingDrawer) view.findViewById(R.id.sdrawer);
            sdrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
                @Override
                public void onDrawerClosed() {
                    handle.setBackground(getResources().getDrawable(R.drawable.ic_expand));
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            });
            sdrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
                @Override
                public void onDrawerOpened() {
                    handle.setBackground(getResources().getDrawable(R.drawable.ic_line));
                }
            });

            msgSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  try
                  {
                      inFromUser = new BufferedReader( new InputStreamReader(System.in));
                      clientSocket = new Socket("localhost", 6789);
                      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                      String sentace = editText.getText().toString();
                      outToServer.writeBytes(sentace + '\n');
                      String modifiedSentence = editText.getText().toString();
                      mylist.add("FROM SERVER: " + modifiedSentence);
                      adapter.notify();
                      clientSocket.close();
                  }
                  catch (UnknownHostException e) {
                      e.printStackTrace();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
                }
            });

            return view;
        }
    }

    public static class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {
        GoogleMap m_pmap;
        public GPSTracker gps;
        boolean mflag = true;
        double latitude1;
        double longitude1;

        private ArrayList<LatLng> points;
        private Marker customMarker;
        GoogleApiClient mGoogleApiClient;
        Polyline line;

        ArrayList<UserlistData> userlistget = new ArrayList<UserlistData>();

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.map_layout, container, false);

            com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getActivity().getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            m_pmap = mapFragment.getMap();
            // m_pmap.setMyLocationEnabled(true);
            points = new ArrayList<LatLng>();

            String lng=CommanActivity.getValue(getActivity(),RefrenceUrl.KEY_LONG,"");
            String lat=CommanActivity.getValue(getActivity(),RefrenceUrl.KEY_LAT,"");


            longitude1 =Double.parseDouble(lng);
            latitude1 =Double.parseDouble(lat);

            UserList(getActivity());
            /********/
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);

            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
                if (location != null) {
                    onLocationChanged(location);
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        //locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
                        locationManager.requestLocationUpdates(bestProvider, 5, 0, this);
                    }
                }
                //locationManager.requestLocationUpdates(bestProvider, 5, 0, this);

            }
//            int permissionCheck = ContextCompat.checkSelfPermission(Home.main,
//                    Manifest.permission.ACCESS_FINE_LOCATION);
//
//            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(Home.main,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
//            } else {
//                StartMap();
//            }

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                StartMap();
            }

            return view;
        }

        private void StartMap() {


            gps = new GPSTracker(Home.main) {
                @Override
                public void onLocationChanged(Location location) {
                    super.onLocationChanged(location);
                    latitude1=location.getLatitude();
                    latitude1= location.getLongitude();

                  if (null != m_pmap && mflag) {
                        mflag = false;
                        double ddd = 0.0001f;

                        LatLng sydney1 = new LatLng(location.getLatitude(), location.getLongitude());
                        points.add(sydney1);
                        String name = CommanActivity.getValue(Home.main, RefrenceUrl.KEY_USER_NAME, "");
                        redrawLine(name, sydney1);


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

                    if (customMarker != null) {
                        customMarker.remove();
                    }

                    //titlee =CommanActivity.getValue(getActivity(),RefrenceUrl.KEY_USER_NAME,"");
                    customMarker = m_pmap.addMarker(new MarkerOptions().title(titlee).position(sydney2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                   // disp( "ppppppppp");

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
                latitude1=location.getLatitude();
                longitude1=location.getLongitude();
                //LatLng sydney2 = new LatLng(location.getLatitude() + ddd, location.getLongitude());
                // LatLng sydney3 = new LatLng(location.getLatitude(), location.getLongitude() + ddd);
                // LatLng sydney4 = new LatLng(location.getLatitude() + ddd, location.getLongitude() + ddd);

                // LatLng sydney5 = new LatLng(location.getLatitude() - ddd, location.getLongitude());
                // LatLng sydney6 = new LatLng(location.getLatitude(), location.getLongitude() - ddd);
                //  LatLng sydney7 = new LatLng(location.getLatitude() - ddd, location.getLongitude() - ddd);
                m_pmap.setMyLocationEnabled(true);
                m_pmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1, 32));
                String name = CommanActivity.getValue(Home.main, RefrenceUrl.KEY_USER_NAME, "");
                //m_pmap.clear();
                customMarker = m_pmap.addMarker(new MarkerOptions()
                        .position(sydney1)
                        .title(name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                addMarker();
               /* m_pmap.addMarker(new MarkerOptions()
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


//        m_pmap = map;
//        m_pmap.addMarker(new MarkerOptions()
//                .position(new LatLng(22.0000,22.0000))
//                .title("Marker")
//                );
//        m_pmap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


//        GoogleMapOptions options = new GoogleMapOptions();
//        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
//                .compassEnabled(true)
//                .rotateGesturesEnabled(true)
//                .tiltGesturesEnabled(true);
        }

        private void addMarker() {

            String name = CommanActivity.getValue(Home.main, RefrenceUrl.KEY_USER_NAME, "");
           /* LatLng sydney1 = new LatLng(latitude1, longitude1);
            m_pmap.setMyLocationEnabled(true);
            m_pmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1, 32));

            //m_pmap.clear();

            customMarker = m_pmap.addMarker(new MarkerOptions()
                    .position(sydney1)
                    .title(name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));*/

            Double lat = 0.0;
            Double lng = 0.0;
            if (userlistget.size() > 0) {
                for (int k = 0; k<userlistget.size(); k++) {
                    UserlistData listuser = userlistget.get(k);

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
                        sydney = new LatLng(lat,lng);
                    } else {
                        sydney = new LatLng(lat,lng);

                    }
                    System.out.println(lat + "hjjjjjjjjj" + lng);


                    String username = listuser.username;

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
                    m_pmap.setMyLocationEnabled(true);
                    m_pmap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 19));
                   // m_pmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 19));
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
            String userid=CommanActivity.getValue(ctx,RefrenceUrl.KEY_USER_ID,"");


            if (haveNetworkConnection(ctx)) {

                HashMap<String, String> params = new HashMap<String, String>();
                params.put(RefrenceUrl.KEY_LAT,latitude1+"");
                params.put(RefrenceUrl.KEY_LONG,longitude1+"");
                params.put(RefrenceUrl.KEY_USER_ID,userid);
                params.put(RefrenceUrl.KEY_RADISE,"100");
                Log.d("params", params + "");

                aq.progress(pDialog).ajax(RefrenceUrl.weburl + RefrenceUrl.METHOD_USER_LIST,params, JSONObject.class, new AjaxCallback<JSONObject>() {

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

            latitude1=location.getLatitude();
            latitude1= location.getLongitude();
            System.out.println("locationchange");

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

    public  void LogOut(String id) {


        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);


        if (checknetwork()) {

            HashMap<String, String> params = new HashMap<String, String>();
            //params.put("action","Login");


            params.put(RefrenceUrl.KEY_USER_ID,id);

            Log.d("params", params + "");

            aq.progress(pDialog).ajax(RefrenceUrl.weburl+RefrenceUrl.METHOD_LOGOUT, params, JSONObject.class, new AjaxCallback<JSONObject>() {

                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    String Messagr, Response;
                    if (json != null) {
                        Log.d("json:", json + "");
                        try {
                            // Messagr = json.getString(AppContact.KEY_SERVICE_NAME);
                            Response = json.getString(RefrenceUrl.KEY_RES_CODE);

                            if (Response.equalsIgnoreCase("0")) {

                               // JSONObject onject = json.getJSONObject(RefrenceUrl.KEY_DATA);

                                CommanActivity.setValue(ctx,RefrenceUrl.KEY_EMAIL,"");
                                CommanActivity.setValue(ctx,RefrenceUrl.KEY_USER_NAME,"");
                                CommanActivity.setValue(ctx,RefrenceUrl.KEY_USER_ID,"");
                                CommanActivity.setValue(ctx,RefrenceUrl.KEY_PASSWORD,"");
                                CommanActivity.setValue(ctx,RefrenceUrl.KEY_LAT,"");
                                CommanActivity.setValue(ctx,RefrenceUrl.KEY_LONG,"");
                                Intent intent = new Intent(Home.this, Account.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();


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

        }



        else {
            CommonMethod.ShowMsgOn(ctx, RefrenceUrl.KEY_MSG, RefrenceUrl.KEY_MSG_NETWORK_ERROR, RefrenceUrl.KEY_OK);
        }
    }





}

