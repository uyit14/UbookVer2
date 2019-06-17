package com.ubook.ubookapp.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Language {
    @SerializedName("is_default")
    @Expose
    private boolean mIsDefault;

    @SerializedName("code")
    @Expose
    private String mCode;

    @SerializedName("enabled")
    @Expose
    private boolean mEnabled;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("id")
    @Expose
    private int mID;

    public boolean ismIsDefault() {
        return mIsDefault;
    }

    public void setmIsDefault(boolean mIsDefault) {
        this.mIsDefault = mIsDefault;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public boolean ismEnabled() {
        return mEnabled;
    }

    public void setmEnabled(boolean mEnabled) {
        this.mEnabled = mEnabled;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }
}
