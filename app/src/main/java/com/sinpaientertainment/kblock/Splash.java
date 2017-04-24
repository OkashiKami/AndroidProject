package com.sinpaientertainment.kblock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sinpaientertainment.kblock.R;
import com.sinpaientertainment.kblock.Services.CommanActivity;
import com.sinpaientertainment.kblock.Services.RefrenceUrl;

/**
 * Created by sinpai on 10/17/2016.
 */

public class Splash extends CommanActivity {
    public static Splash main;
    Context ctx =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        main = this;
        ctx=Splash.this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(CommanActivity.getValue(ctx, RefrenceUrl.KEY_EMAIL,"").equalsIgnoreCase("")){

                startActivity(new Intent(main, Home.class));
                main.finish();
            }else{
                    main.toHome();

            }
            }
        }, 1000);
    }
    public void toHome() {
        startActivity(new Intent(this, Home.class));
        finish();
    }
}
