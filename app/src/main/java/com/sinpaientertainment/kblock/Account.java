package com.sinpaientertainment.kblock;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import com.sinpaientertainment.kblock.R;
import com.sinpaientertainment.kblock.Services.Backend;
import com.sinpaientertainment.kblock.Services.CommanActivity;
import com.sinpaientertainment.kblock.Services.CommonMethod;
import com.sinpaientertainment.kblock.Services.RefrenceUrl;
import com.sinpaientertainment.kblock.Utility.PermissionUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;





/**
 * Created by sinpai on 10/17/2016.
 */

public class Account extends CommanActivity {
    public static Account main;
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};
    boolean checklocation = false;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    public static Double MyLat, MyLong;
    Location location;
    GoogleCloudMessaging gcm;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    // Declaring a Location Manager
    public static String deviceid;
    public static ProgressDialog pDialog;
    public static AQuery aq = null;
    public static Context ctxx = null;
    boolean canGetLocation = false;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_main);
        main = this;
        ctxx = Account.this;
        aq = new AQuery(ctxx);

//        if (checkPermission()) {
//
//            Toast.makeText(this, "Permission already granted.", Toast.LENGTH_LONG).show();
//            getMyCurrentLocation();
//        } else {
//
//            if (!checkPermission()) {
//                requestPermission();
//            } else {
//                getMyCurrentLocation();
//            }
//        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_COARSE_LOCATION, true);
        } else {
            // Access to the location has been granted to the app.
            getMyCurrentLocation();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getMyCurrentLocation();
        }


        gcm = GoogleCloudMessaging.getInstance(Account.this);
        deviceid = getRegistrationId(Account.this);
        if (deviceid.isEmpty()) {
            registerInBackground();
        }
        if (deviceid == null) {
            deviceid = "";
        }
        if (Backend.CurrentUser() != null) {

        } else toLogin();
    }

    public void toLogin() {
        getFragmentManager().beginTransaction().replace(R.id.account_holder, new LoginFragment()).commit();
    }

    public void toRegister() {
        getFragmentManager().beginTransaction().replace(R.id.account_holder, new RegisterFragment()).commit();
    }

    public void toHome() {
        startActivity(new Intent(this, Home.class));
        finish();
    }

    public static class LoginFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.account_login, container, false);

            TextView error = (TextView) view.findViewById(R.id.error);
            final EditText usernae = (EditText) view.findViewById(R.id.username);
            final EditText password = (EditText) view.findViewById(R.id.password);
            final Switch remembrme = (Switch) view.findViewById(R.id.rememberme);

            Button submit = (Button) view.findViewById(R.id.submit);
            Button noaccount = (Button) view.findViewById(R.id.noaccount);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (usernae.getText().toString().trim().matches("")) {
                        CommonMethod.ShowMsgOn(Account.main, RefrenceUrl.KEY_MSG_ERROR, "Please enter username", RefrenceUrl.KEY_OK);
                    } else if (password.getText().toString().trim().matches("")) {
                        CommonMethod.ShowMsgOn(Account.main, RefrenceUrl.KEY_MSG_ERROR, "Please enter password", RefrenceUrl.KEY_OK);
                    } else {
                        Login1(Account.main, remembrme.isChecked(), usernae.getText().toString().trim(), password.getText().toString().trim(), deviceid, MyLat + "", MyLong + "");
                    } //Backend.Login(Account.main, remembrme.isChecked(), usernae.getText().toString(), password.getText().toString(),deviceid,MyLat+"",MyLong+"");
                }
            });
            noaccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Account.main.toRegister();
                }
            });
            return view;
        }
    }

    public static class RegisterFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.account_register, container, false);

            TextView error = (TextView) view.findViewById(R.id.error);
            final EditText fullname = (EditText) view.findViewById(R.id.fullname);
            final EditText username = (EditText) view.findViewById(R.id.username);
            final EditText email = (EditText) view.findViewById(R.id.email);
            final EditText password = (EditText) view.findViewById(R.id.password);

            Button submit = (Button) view.findViewById(R.id.submit);
            Button haveaccount = (Button) view.findViewById(R.id.haveaccount);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    if (fullname.getText().toString().trim().matches("")) {
                        CommonMethod.ShowMsgOn(Account.main, RefrenceUrl.KEY_MSG_ERROR, "Please enter fullname", RefrenceUrl.KEY_OK);
                    } else if (username.getText().toString().trim().matches("")) {
                        CommonMethod.ShowMsgOn(Account.main, RefrenceUrl.KEY_MSG_ERROR, "Please enter username", RefrenceUrl.KEY_OK);
                    } else if (email.getText().toString().trim().matches("")) {
                        CommonMethod.ShowMsgOn(Account.main, RefrenceUrl.KEY_MSG_ERROR, "Please enter email", RefrenceUrl.KEY_OK);

                    } else if (password.getText().toString().trim().matches("")) {
                        CommonMethod.ShowMsgOn(Account.main, RefrenceUrl.KEY_MSG_ERROR, "Please enter password", RefrenceUrl.KEY_OK);
                    } else {
                        Register2(Account.main, fullname.getText().toString().trim(), username.getText().toString().trim(), email.getText().toString().trim(), password.getText().toString().trim(), deviceid, MyLat + "", MyLong + "");
                    }
                }
            });
            haveaccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Account.main.toLogin();
                }
            });
            return view;
        }
    }

    void getMyCurrentLocation() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new MyLocationListener();
        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        //don't start listeners if no provider is enabled
        //if(!gps_enabled && !network_enabled)
        //return false;

        if (gps_enabled) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
            }
        }

        if (gps_enabled) {
            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (network_enabled && location == null) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);

        }

        if (network_enabled && location == null) {
            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (location != null) {

            MyLat = location.getLatitude();
            MyLong = location.getLongitude();


        } else {
            Location loc = getLastKnownLocation(Account.this);
            if (location != null) {

                MyLat = loc.getLatitude();
                MyLong = loc.getLongitude();


            } else {
                MyLat = 0.0;//0.0;
                MyLong = 0.0;//0.0;
            }
        }
        locManager.removeUpdates(locListener); // removes the periodic updates from location listener to //avoid battery drainage. If you want to get location at the periodic intervals call this method using //pending intent.

        try {
// Getting address from found locations.
            Geocoder geocoder;

            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            if (MyLat != null && MyLong != null) {

            }
            System.out.println("MyLat" + MyLat);
            System.out.println("MyLong" + MyLong);
            //setSettingMyLat("MyLat",MyLat+"");
            // setSettingMyLong("MyLong",MyLong+"");
            // CommanActivity.setSetting(LoginActivity.this, "regid", deviceid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //textView2.setText(""+MyLat);
        // textView3.setText(""+MyLong);
        // textView1.setText(" StateName " + StateName +" CityName " + CityName +" CountryName " + CountryName);
    }


    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            if (location != null) {
            }
        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }

    public static Location getLastKnownLocation(Context context) {
        Location location = null;
        LocationManager locationmanager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List list = locationmanager.getAllProviders();
        boolean i = false;
        Iterator iterator = list.iterator();
        do {
            //System.out.println("---------------------------------------------------------------------");
            if (!iterator.hasNext())
                break;
            String s = (String) iterator.next();
            //if(i != 0 && !locationmanager.isProviderEnabled(s))
            if (i != false && !locationmanager.isProviderEnabled(s))
                continue;
            // System.out.println("provider ===> "+s);

            Location location1 = null;
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                location1 = locationmanager.getLastKnownLocation(s);
            }


            if (location1 == null)
                continue;
            if (location != null) {
                //System.out.println("location ===> "+location);
                //System.out.println("location1 ===> "+location);
                float f = location.getAccuracy();
                float f1 = location1.getAccuracy();
                if (f >= f1) {
                    long l = location1.getTime();
                    long l1 = location.getTime();
                    if (l - l1 <= 600000L)
                        continue;
                }
            }
            location = location1;
            // System.out.println("location  out ===> "+location);
            //System.out.println("location1 out===> "+location);
            i = locationmanager.isProviderEnabled(s);
            // System.out.println("---------------------------------------------------------------------");
        } while (true);
        return location;
    }

    private void registerInBackground() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(ctxx);
                    }
                    deviceid = gcm.register("946229090571");
                    //  deviceid = gcm.register("946229090571");
                    Log.e("regId..........", deviceid);
                    msg = "Devic registered, registration ID=" + deviceid;

//					storeRegistrationId(context, regid);
                    CommanActivity.setSetting(ctxx, "regid", deviceid);
                    Log.i("MYGCM", deviceid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }


            protected void onPostExecute(String msg) {
                // mDisplay.append(msg + "\n");
                Log.i("MYGCM", msg);
            }

        }.execute();

        /**
         * Sends the registration ID to your server over HTTP, so it can use
         * GCM/HTTP or CCS to send messages to your app. Not needed for this
         * demo since the device sends upstream messages to a server that echoes
         * back the message using the 'from' address in the message.
         */

    }

    private String getRegistrationId(Context context) {

        String registrationId = CommanActivity.getSetting(context, "regid", "");

        if (registrationId.isEmpty()) {
            Log.i("Registration", "Registration not found.");
            return "";
        }

        return registrationId;
    }

    public static void Register2(final Context ctxx, String Fname, String Uname, String email, String pass, String devicetoken, String lat, String lng) {


        pDialog = new ProgressDialog(ctxx);
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);


        if (haveNetworkConnection(ctxx)) {

            HashMap<String, String> params = new HashMap<String, String>();
            //params.put("action","Login");

            params.put(RefrenceUrl.KEY_FIRST_NAME, Fname);
            params.put(RefrenceUrl.KEY_USER_NAME, Uname);
            params.put(RefrenceUrl.KEY_EMAIL, email);
            params.put(RefrenceUrl.KEY_PASSWORD, pass);
            params.put(RefrenceUrl.KEY_DEVICETOKN, devicetoken);
            params.put(RefrenceUrl.KEY_DEVICEID, "android");
            params.put(RefrenceUrl.KEY_LAT, lat);
            params.put(RefrenceUrl.KEY_LONG, lng);
            params.put(RefrenceUrl.KEY_PROFILE, "");

            Log.d("params", params + "");

            aq.progress(pDialog).ajax(RefrenceUrl.weburl + RefrenceUrl.METHOD_REG, params, JSONObject.class, new AjaxCallback<JSONObject>() {

                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    String Messagr, Response;
                    if (json != null) {
                        Log.d("json:", json + "");
                        try {
                            // Messagr = json.getString(AppContact.KEY_SERVICE_NAME);
                            Response = json.getString(RefrenceUrl.KEY_RES_CODE);

                            if (Response.equalsIgnoreCase("0")) {


                                CommonMethod.ShowMsgOn(Account.main, RefrenceUrl.KEY_MSG_ERROR, "Registration successful", RefrenceUrl.KEY_OK);
                                Account.main.toLogin();


                            } else {


                                String error_msg = json.getString(RefrenceUrl.KEY_RES_ERROR);
                                CommonMethod.ShowMsgOn(ctxx, RefrenceUrl.KEY_MSG, error_msg, RefrenceUrl.KEY_OK);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else

                    {
                        CommonMethod.ShowMsgOn(ctxx, RefrenceUrl.KEY_MSG, RefrenceUrl.KEY_MSG_DATA_ERROR, RefrenceUrl.KEY_OK);
                    }

                }
            });

        } else {
            CommonMethod.ShowMsgOn(ctxx, RefrenceUrl.KEY_MSG, RefrenceUrl.KEY_MSG_NETWORK_ERROR, RefrenceUrl.KEY_OK);
        }
    }

    public static void Login1(final Context ctxx, boolean chek, String Uname, String pass, String devicetoken, String lat, String lng) {


        pDialog = new ProgressDialog(ctxx);
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);


        if (haveNetworkConnection(ctxx)) {

            HashMap<String, String> params = new HashMap<String, String>();
            //params.put("action","Login");


            params.put(RefrenceUrl.KEY_USER_NAME, Uname);
            params.put(RefrenceUrl.KEY_PASSWORD, pass);
            params.put(RefrenceUrl.KEY_DEVICETOKN, devicetoken);
            params.put(RefrenceUrl.KEY_DEVICEID, "android");
            params.put(RefrenceUrl.KEY_LAT, lat);
            params.put(RefrenceUrl.KEY_LONG, lng);


            Log.d("params", params + "");

            aq.progress(pDialog).ajax(RefrenceUrl.weburl + RefrenceUrl.METHOD_LOGIN, params, JSONObject.class, new AjaxCallback<JSONObject>() {

                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    String Messagr, Response;
                    if (json != null) {
                        Log.d("json:", json + "");
                        try {
                            // Messagr = json.getString(AppContact.KEY_SERVICE_NAME);
                            Response = json.getString(RefrenceUrl.KEY_RES_CODE);

                            if (Response.equalsIgnoreCase("0")) {

                                JSONObject onject = json.getJSONObject(RefrenceUrl.KEY_DATA);

                                CommanActivity.setValue(ctxx, RefrenceUrl.KEY_EMAIL, onject.getString(RefrenceUrl.KEY_EMAIL));
                                CommanActivity.setValue(ctxx, RefrenceUrl.KEY_USER_NAME, onject.getString(RefrenceUrl.KEY_USER_NAME));
                                CommanActivity.setValue(ctxx, RefrenceUrl.KEY_USER_ID, onject.getString(RefrenceUrl.KEY_USER_ID));
                                CommanActivity.setValue(ctxx, RefrenceUrl.KEY_PASSWORD, onject.getString(RefrenceUrl.KEY_PASSWORD));
                                CommanActivity.setValue(ctxx, RefrenceUrl.KEY_LAT, onject.getString(RefrenceUrl.KEY_LAT));
                                CommanActivity.setValue(ctxx, RefrenceUrl.KEY_LONG, onject.getString(RefrenceUrl.KEY_LONG));
                                Account.main.toHome();


                            } else {


                                String error_msg = json.getString(RefrenceUrl.KEY_RES_ERROR);
                                CommonMethod.ShowMsgOn(ctxx, RefrenceUrl.KEY_MSG, error_msg, RefrenceUrl.KEY_OK);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else

                    {
                        CommonMethod.ShowMsgOn(ctxx, RefrenceUrl.KEY_MSG, RefrenceUrl.KEY_MSG_DATA_ERROR, RefrenceUrl.KEY_OK);
                    }

                }
            });

        } else {
            CommonMethod.ShowMsgOn(ctxx, RefrenceUrl.KEY_MSG, RefrenceUrl.KEY_MSG_NETWORK_ERROR, RefrenceUrl.KEY_OK);
        }
    }

 /* private boolean isReadStorageAllowed() {
        //Getting the permission status
       int result = ctxx.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }*/

    //Requesting permission
  /*  private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,ACCESS_FINE_LOCATION)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == PERMISSION_REQUEST_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                checklocation=true;
                //Displaying a toast
                getMyCurrentLocation();

            }else{

                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }*/

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    //boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted) {
                        getMyCurrentLocation();

                     //   Toast.makeText(this, "Permission granted now you can read the Loction", Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(this, "Permission granted now you can read the Loction", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                            LOCATION_PERMISSION_REQUEST_CODE);
                                }

                               /*showMessageOKCancel("You need to allow access to both the permissions",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                                            LOCATION_PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });*/
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Account.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}
