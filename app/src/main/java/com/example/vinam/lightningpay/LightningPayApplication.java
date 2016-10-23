package com.example.vinam.lightningpay;

import android.app.Application;
import android.content.Context;
import android.location.Location;

/**
 * Created by vinam on 10/22/2016.
 */

public class LightningPayApplication extends Application {
    private  Context  mContext;
    private static Location location;

    public  Context getContext(){
        mContext = getApplicationContext();
        return mContext;
    }


    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        LightningPayApplication.location = location;
    }

}
