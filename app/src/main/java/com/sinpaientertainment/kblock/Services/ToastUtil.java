package com.sinpaientertainment.kblock.Services;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by yashco on 1/16/2016.
 */
public class ToastUtil {

    public static void ToastCenter(Context context, String text) {
        Toast toast =Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
        toast.show();
    }
}
