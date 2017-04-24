package com.sinpaientertainment.kblock.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinpaientertainment.kblock.R;

import java.util.ArrayList;
import java.util.List;

public class DrawerArrayAdapter extends ArrayAdapter<DrawerEntry>{
    private TextView nameText;
    private ImageView iconImage;
    private List<DrawerEntry> MessageList = new ArrayList<DrawerEntry>();
    private LinearLayout layout;
    public DrawerArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    public void add(DrawerEntry object) {
        // TODO Auto-generated method stub

        MessageList.add(object);
        super.add(object);
    }

    public int getCount()
    {
        return this.MessageList.size();
    }

    public DrawerEntry getItem(int index){

        return this.MessageList.get(index);
    }

    public View getView(int position,View ConvertView, ViewGroup parent){

        View v = ConvertView;
        if(v==null){

            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v =inflater.inflate(R.layout.drawer_list_item, parent,false);

        }

        layout = (LinearLayout)v.findViewById(R.id.drawer_layout);
        DrawerEntry entryobj = getItem(position);
        nameText = (TextView)v.findViewById(R.id.textItem);
        if(nameText != null)
            nameText.setText(entryobj.name);
        iconImage = (ImageView) v.findViewById(R.id.iconItem);
        if(iconImage != null)
            iconImage.setImageBitmap(entryobj.icon);
        return v;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
