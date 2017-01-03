package com.claudiodegio.msv.model;

import android.graphics.drawable.Drawable;
import android.view.View;


public class Filter extends BaseElement {
    private int mId;
    private int mIconRefId;
    private Drawable mIconDrawable = null;
    private int mIconBgColor;
    private int mType;

    public Filter(int type, String name, int id, int iconRefId, int iconBgColor) {
        super(name);
        this.mType = type;
        this.mIconRefId = iconRefId;
        this.mId = id;
        this.mIconBgColor = iconBgColor;
        this.mIconDrawable = null;
    }

    public Filter(int type, String name, int id, Drawable iconDraweable, int iconBgColor) {
        super(name);
        this.mType = type;
        this.mIconRefId = View.NO_ID;
        this.mId = id;
        this.mIconBgColor = iconBgColor;
        this.mIconDrawable = iconDraweable;
    }

    public Filter(int type, String name) {
        this(type, name, View.NO_ID, View.NO_ID, View.NO_ID);
    }

    public int getType() {
        return mType;
    }

    public int getIconBgColor() {
        return mIconBgColor;
    }

    public int getIconRefId() {
        return mIconRefId;
    }

    public int getId() {
        return mId;
    }


    public boolean hasIconRefId(){
        return mIconRefId != View.NO_ID;
    }

    public Drawable getIconDrawable(){
        return mIconDrawable;
    }
}
