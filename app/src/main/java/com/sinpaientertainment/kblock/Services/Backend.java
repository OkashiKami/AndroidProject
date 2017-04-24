package com.sinpaientertainment.kblock.Services;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.sinpaientertainment.kblock.Account;
import com.sinpaientertainment.kblock.managers.Network;

/**
 * Created by sinpai on 10/17/2016.
 */

public class Backend{
    final static String applicationId ="B2649D3E-4AC9-DF4B-FF31-DAF30B5EE000";// "2BAF079A-2A8D-BEAA-FF3B-C7EC1C0FB600";
    final static String applicationSecreat = "1295AD5A-9983-903A-FFE8-86DA1B1F1F00";//"27BFCE2F-DDA2-E6F4-FF26-46B0FA04E200";
    final static String applicationVersion = "v1";
    final static String applicationSender = "212452026024";
    public static String deviceid;
    public static  ProgressDialog pDialog;
    public static AQuery aq = null;
    public static Context ctxx=null;
    static boolean isSetup = false;

    static void init (Context ctx){
        Backendless.setUrl(Network.getServerAddress());
        Backendless.initApp(ctx, applicationId, applicationSecreat, applicationVersion);
        isSetup = true;
    }

    public static BackendlessUser CurrentUser (){
        return Backendless.UserService.CurrentUser();
    }
    public static void Login(final Context ctx, boolean remember, String... params) {
        if(!isSetup){ init(ctx); }
        if(isSetup){
           Backendless.UserService.login(params[0], params[1], new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    Toast.makeText(ctx, "Login successful!", Toast.LENGTH_LONG).show();
                    Account.main.toHome();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(ctx, fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            },remember);

        }
    }
    public  void Register(final Context ctx, String... params){
        if(!isSetup) {init(ctx); }
        if (isSetup)  {
            String fullname_=params[0];
            String username_=params[1];
            String email_=params[2];
            String password_=params[3];
            //Register2(fullname_, username_, email_, password_, params[4], params[5], params[6]);
             BackendlessUser newUser = new BackendlessUser();
           newUser.setProperty("fullname", params[0]);
            newUser.setProperty("username", params[1]);
           newUser.setEmail(params[2]);
           newUser.setPassword(params[3]);

            if(newUser.getProperty("username").equals("")) { }
            else if(newUser.getEmail().equals("")) { }
            else if(newUser.getPassword().equals("")) { }
            else {
               Backendless.UserService.register(newUser, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser backendlessUser) {
                        Toast.makeText(ctx, "Registration successful", Toast.LENGTH_LONG).show();
                        Account.main.toLogin();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(ctx, fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}
