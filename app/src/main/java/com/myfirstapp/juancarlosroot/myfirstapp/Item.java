package com.myfirstapp.juancarlosroot.myfirstapp;

/**
 * Created by juancarlosroot on 6/26/16.
 */
public class Item {
    private String mTitle;
    private String mDescription;

    public Item(String mTitle, String mDescription) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
