package com.sinpaientertainment.kblock.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.sinpaientertainment.kblock.R;

import java.io.InputStream;


public class ImageTaskManager extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public static boolean isLoaded;

    public ImageTaskManager(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }

    public static void maskIt (Context ctx, ImageView view )
    {
        ImageView mImageView= view;


        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0,
                view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        Bitmap bmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);


        mImageView.setBackgroundResource(R.drawable.profile_circle);
        Bitmap original = bmap;
        Bitmap mask = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.mask_white);
        Bitmap mask1 = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.mask);
        original = Bitmap.createScaledBitmap(original, mask.getWidth(),mask.getHeight(), true);

        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        mCanvas.drawBitmap(mask1, 0, 0, null);
        Bitmap mask2 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.mask);
        mCanvas.drawBitmap(mask2, 0, 0, null);
        mImageView.setImageBitmap(result);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        isLoaded = true;
    }
}