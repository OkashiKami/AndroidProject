package com.sinpaientertainment.kblock.managers;

import android.graphics.Bitmap;

public class DrawerEntry {
    public String name;
    public Bitmap icon;


    public DrawerEntry(Bitmap icon , String name) {
        super();
        this.name = name;
        this.icon = icon;
    }
}
