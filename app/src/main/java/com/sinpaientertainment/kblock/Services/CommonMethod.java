package com.sinpaientertainment.kblock.Services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by yashco on 5/20/2016.
 */
public class CommonMethod {

    CommonMethod(){

    }

    public static void ShowMsgOn(Context con,String title,String msg,String buttonname){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                con);
         alertDialogBuilder
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(buttonname,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
