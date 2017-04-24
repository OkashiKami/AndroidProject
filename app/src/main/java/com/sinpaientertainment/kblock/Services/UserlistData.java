package com.sinpaientertainment.kblock.Services;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by yashco on 7/12/2016.
 */
public class UserlistData implements Serializable {
    public int id;
    public String firstname;
    public String address;
    public String profileimage;
    public String latitude;
    public String longitude;
    public String email;
    public String username;
    public UserlistData(){
        this.SetDefaultValues();
    }
    public UserlistData(JSONObject onject) {

        if (null != onject) {
            if (GetValueForKey(RefrenceUrl.KEY_USER_ID, onject).length() > 0) {
                id = Integer.parseInt(GetValueForKey(RefrenceUrl.KEY_USER_ID, onject));
            } else
                id = -1;


            firstname = GetValueForKey(RefrenceUrl.KEY_FIRST_NAME, onject);
            profileimage = GetValueForKey(RefrenceUrl.KEY_PROFILE, onject);
            latitude = GetValueForKey(RefrenceUrl.KEY_LAT, onject);
            longitude = GetValueForKey(RefrenceUrl.KEY_LONG, onject);
            username = GetValueForKey(RefrenceUrl.KEY_USER_NAME, onject);
            email = GetValueForKey(RefrenceUrl.KEY_EMAIL, onject);
        }
    }

    private String GetValueForKey(String key,JSONObject onject){

        String Value = "";
        if(key!=null && key.length()>0)
        {

            try {
                Value = onject.getString(key);
            } catch (JSONException e) {
                Value = "";
            }
        }

        return Value;
    }
    private void SetDefaultValues(){
        id = -1;

        firstname = "";
        email="";
        username="";
        latitude = "";
        longitude = "";
        profileimage = "";


    }

}
