package com.sinpaientertainment.kblock.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yashco on 6/28/2016.
 */
public class CommanActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    AppCompatActivity activity ;


    public static void setValue(Context ctx,String key, String value)
    {
        SharedPreferences settings =ctx.getSharedPreferences(key, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();
    }
    public static void setValueuser(Context ctx,Object key, Object value)
    {
        SharedPreferences settings =ctx.getSharedPreferences(key.toString(), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key.toString(), value.toString());

        // Commit the edits!
        editor.commit();
    }
  /*  public static void setValueNotifiy(Context ctx,String key, String value)
    {
        SharedPreferences settings =ctx.getSharedPreferences(AppContact.KEY_NOTIFICATION_COUNT, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();
    }
    public static String getValueNotifiy(Context ctx,String key, String def)
    {

        SharedPreferences settings = ctx.getSharedPreferences(AppContact.KEY_NOTIFICATION_COUNT, 0);
        return settings.getString(key, def);

    }*/
    public static String getValueuser(Context ctx,Object key, Object def)
    {

        SharedPreferences settings = ctx.getSharedPreferences(key.toString(), 0);
        return settings.getString(key.toString(), def.toString());

    }

    public static String getValue(Context ctx,String key, String def)
    {

        SharedPreferences settings = ctx.getSharedPreferences(key, 0);
        return settings.getString(key, def);

    }

  /*  public static void setSettingRoleID(Context ctx,String key, String value)
    {
        SharedPreferences settings =ctx.getSharedPreferences(AppContact.KEY_ROLE_ID, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();
    }
    public static void setSettingSocialID(Context ctx,String key, String value) {
        SharedPreferences settings =ctx.getSharedPreferences(AppContact.KEY_SOCIAL_ID, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();}

   public static void setSettingNotificationCount(Context ctx,String key, String value) {
        SharedPreferences settings =ctx.getSharedPreferences(AppContact.KEY_NOTIFICATION_COUNT, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();}
    public static String getSettingNotificationCount(Context ctx,String key, String def) {

        SharedPreferences settings = ctx.getSharedPreferences(AppContact.KEY_NOTIFICATION_COUNT, 0);
        return settings.getString(key, def);

    }
    public static void setSettingCreditCount(Context ctx,String key, String value) {
        SharedPreferences settings =ctx.getSharedPreferences(AppContact.KEY_CREDITS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();}
    public static String getSettingCreditCount(Context ctx,String key, String def) {

        SharedPreferences settings = ctx.getSharedPreferences(AppContact.KEY_CREDITS, 0);
        return settings.getString(key, def);

    }
     public  static String getSettingRoleID(Context ctx ,String key, String def) {

        SharedPreferences settings = ctx.getSharedPreferences(AppContact.KEY_ROLE_ID, 0);
        return settings.getString(key, def);

    }
    public  static String getSettingSocialID(Context ctx ,String key, String def) {

        SharedPreferences settings = ctx.getSharedPreferences(AppContact.KEY_SOCIAL_ID, 0);
        return settings.getString(key, def);

    }

    */
  public static String getSettingEmailID(Context ctx,String key, String def) {

      SharedPreferences settings = ctx.getSharedPreferences(RefrenceUrl.KEY_EMAIL, 0);
      return settings.getString(key, def);

  }
    public static void setSettingEmailID(Context ctx,String key, String value) {
        SharedPreferences settings =ctx.getSharedPreferences(RefrenceUrl.KEY_EMAIL, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();}
    public static String getSettingPassword(Context ctx,String key, String def) {

        SharedPreferences settings = ctx.getSharedPreferences(RefrenceUrl.KEY_PASSWORD, 0);
        return settings.getString(key, def);

    }
    public static void setSettingPassword(Context ctx ,String key, String value) {
        SharedPreferences settings =ctx.getSharedPreferences(RefrenceUrl.KEY_PASSWORD, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();}

    public static void setSetting(Context act, String key, String value)
    {
        SharedPreferences settings = act.getSharedPreferences(act.getApplicationInfo().packageName + "_preferences", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();
    }

    public static String getSetting(Context act, String key, String def)
    {

        SharedPreferences settings = act.getSharedPreferences(act.getApplicationInfo().packageName+"_preferences", 0);
        return settings.getString(key, def);
    }
    public  boolean checknetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else
            return false;
    }
    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = CommanActivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }
    public static  NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
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
