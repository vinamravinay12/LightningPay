package com.example.vinam.lightningpay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Shows three Tabs with options to Scan, Decode and Encode QR codes using
 * services provided by "QR Droid"
 * 
 * _________________________________________________________________________________
 * This is part of "QRDroidServices", by DroidLa. If you're creating an Android app
 * which uses one or more services provided by "QR Droid", you can use this code for
 * free, and modify it as you need, for personal and commercial use.
 * 
 * Any other use of this code is forbidden.
 * 
 * @author DroidLa
 * @version 1.0
 */
public class Services extends TabActivity {

	//Actions
	public static final String SCAN = "la.droid.qr.scan";

	//Parameters
	//SCAN / DECODE
	public static final String COMPLETE = "la.droid.qr.complete"; //Default: false

	//Result
	public static final String RESULT = "la.droid.qr.result";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        
        //Recycled objects
	    Resources res = getResources();
	    TabHost tabHost = getTabHost();
	    TabHost.TabSpec spec;
	    Intent intent;
 
	    //Scan Activity
	    intent = new Intent().setClass(this, Scan.class);
	    spec = tabHost.newTabSpec("Scan").setIndicator("", res.getDrawable(R.drawable.camera)).setContent(intent);
	    tabHost.addTab(spec);
	    


    }

    /**
     * Display a message stating that QR Droid is requiered, and lets the user download it for free
     * @param activity
     */


}