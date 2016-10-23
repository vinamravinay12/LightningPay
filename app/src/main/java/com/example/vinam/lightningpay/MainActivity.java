package com.example.vinam.lightningpay;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinam.lightningpay.dummy.ItemContent;
import com.example.vinam.lightningpay.dummy.ReminderContent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ReminderFragment.OnListFragmentInteractionListener,
        ItemFragment.OnListFragmentInteractionListener{
    private static final String TAG = "LocationManager";
    private GoogleApiClient googleApiClient;
    private Context mContext;
    private LocationRequest locationRequest;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private TextView no_item_text;
    private LinearLayout reminderFragmentLayout,itemFragmentLayout;



    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //"Scan" button
        /*final Button button = (Button) findViewById(R.id.button_scan);
        //Set action to button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a new Intent to send to QR Droid
                Intent qrDroid = new Intent(Services.SCAN); //Set action "la.droid.qr.scan"

                //Send intent and wait result
                try {
                    startActivityForResult(qrDroid, ACTIVITY_RESULT_QR_DRDROID);
                } catch (ActivityNotFoundException activity) {
                    //	Services.qrDroidRequired(Scan.this);
                }
            }
        });

*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        reminderFragmentLayout = (LinearLayout)findViewById(R.id.fragment_container);
        itemFragmentLayout = (LinearLayout)findViewById(R.id.item_container);
        no_item_text = (TextView)findViewById(R.id.no_item_text);
       // loc_text = (TextView)findViewById(R.id.loc_text);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


       //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent qrDroid = new Intent( Services.SCAN ); //Set action "la.droid.qr.scan"

                //Send intent and wait result
                try {
                    startActivityForResult(qrDroid, ACTIVITY_RESULT_QR_DRDROID);
                } catch (ActivityNotFoundException activity) {
                    //	Services.qrDroidRequired(Scan.this);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(checkGooglePlayServices(this)){
            buildGoogleApiClient(this);
            createLocationRequest(this);
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        ItemFragment itemFragment = new ItemFragment();
        fragmentTransaction.add(R.id.fragment_container,itemFragment,"sssd");
       // LinearLayout fragmentLayout = (LinearLayout)findViewById(R.id.fragment_container);
       // itemFragmentLayout.setVisibility(View.VISIBLE);
       // reminderFragmentLayout.setVisibility(View.GONE);
        //no_item_text.setVisibility(View.GONE);
        Log.d(TAG,"location got size " + ReminderContent.ITEMS.size() + " :: "+ItemContent.ITEMS.size() );
        Fragment fragment = fragmentManager.findFragmentById(R.id.reminder_frag);
        if(ReminderContent.ITEMS.size() ==0 && ItemContent.ITEMS.size() == 0){
            reminderFragmentLayout.setVisibility(View.GONE);
            itemFragmentLayout.setVisibility(View.GONE);
            no_item_text.setVisibility(View.VISIBLE);

        } else if(ReminderContent.ITEMS.size() == 0 && ItemContent.ITEMS.size() > 0){
            reminderFragmentLayout.setVisibility(View.GONE);
            itemFragmentLayout.setVisibility(View.VISIBLE);
            no_item_text.setText("no reminders added");

        } else if(ReminderContent.ITEMS.size() > 0 && ItemContent.ITEMS.size() == 0){
            reminderFragmentLayout.setVisibility(View.VISIBLE);
            itemFragmentLayout.setVisibility(View.GONE);
            no_item_text.setVisibility(View.VISIBLE);
        }

        fragmentTransaction.commit();

    }


    @Override
    /**
     * Reads data scanned by user and returned by QR Droid
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( ACTIVITY_RESULT_QR_DRDROID==requestCode && null!=data && data.getExtras()!=null ) {
            //Read result from QR Droid (it's stored in la.droid.qr.result)
            String result = data.getExtras().getString(Services.RESULT);
            //Just set result to EditText to be able to view it
            //EditText resultTxt = (EditText) findViewById(R.id.result);
            //resultTxt.setText( result );
            //resultTxt.setVisibility(View.VISIBLE);
        }
    }














    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            ItemFragment itemFragment = new ItemFragment();
            fragmentTransaction.add(R.id.fragment_container,itemFragment,"sssd");
            //LinearLayout fragmentLayout = (LinearLayout)findViewById(R.id.fragment_container);
            //fragmentLayout.setVisibility(View.VISIBLE);
            if(ItemContent.ITEMS.size() > 0) {
                itemFragmentLayout.setVisibility(View.VISIBLE);
                reminderFragmentLayout.setVisibility(View.GONE);
                no_item_text.setVisibility(View.GONE);
            }else {
                itemFragmentLayout.setVisibility(View.GONE);
                reminderFragmentLayout.setVisibility(View.GONE);
                no_item_text.setVisibility(View.VISIBLE);
            }
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            ReminderFragment reminderFragment = new ReminderFragment();
            fragmentTransaction.add(R.id.fragment_container,reminderFragment,"sssd");
            //LinearLayout fragmentLayout = (LinearLayout)findViewById(R.id.fragment_container);
            //fragmentLayout.setVisibility(View.VISIBLE);
            if(ReminderContent.ITEMS.size() > 0) {
                reminderFragmentLayout.setVisibility(View.VISIBLE);
                itemFragmentLayout.setVisibility(View.GONE);
                no_item_text.setVisibility(View.GONE);
            }else {
                reminderFragmentLayout.setVisibility(View.GONE);
                itemFragmentLayout.setVisibility(View.GONE);
                no_item_text.setVisibility(View.VISIBLE);
                no_item_text.setText("no reminders added");
            }
            fragmentTransaction.commit();


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disconnect();
    }
    private boolean checkGooglePlayServices(Context context) {
        int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        Log.d(TAG, "connection got result " + result + " :: " + ConnectionResult.SUCCESS);
        if (result != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(result)) {
                Log.e(TAG, "Error in opening Google Play Services");
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.",
                        Toast.LENGTH_LONG).show();
                // finish();
            }
            return false;
        }
        return true;
    }
    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    private void buildGoogleApiClient(Context context){
        if(googleApiClient == null){
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            setGoogleApiClient(googleApiClient);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(getGoogleApiClient() != null && getGoogleApiClient().isConnected()) {
            locationRequest = LocationRequest.create()
                    .setInterval(10000)
                    .setFastestInterval(10000)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(getGoogleApiClient(), locationRequest, this);

            }catch(Exception e) {
                Log.e(TAG, "connection location services connection problem" + e.getMessage());
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG,"connection suspended");
        connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG,"google api client connection failed" + connectionResult.getErrorMessage());

    }

    @Override
    public void onLocationChanged(Location location) {

        if(location != null){
            LightningPayApplication.setLocation(location);
            Log.d(TAG,"location got " + location);
            Log.d(TAG,"location got 2 " + LightningPayApplication.getLocation());
           // loc_text.setText("latitude= "+LightningPayApplication.getLocation().getLatitude()+" :: Longitude= "+LightningPayApplication.getLocation().getLongitude());
        }


    }

    public  void connect(){
        if(getGoogleApiClient() != null && !getGoogleApiClient().isConnected())
            getGoogleApiClient().connect();
    }
    public void disconnect(){
        if(getGoogleApiClient() != null && getGoogleApiClient().isConnected()){
            getGoogleApiClient().disconnect();
        }
    }
    public void createLocationRequest(Context context){



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(googleApiClient != null && googleApiClient.isConnected()) {
            if (locationRequest != null) {
                Log.d(TAG, "connection got connected in resume");

                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(getGoogleApiClient(), locationRequest, this);

                } catch (Exception e) {
                    Log.e(TAG, "connection location services connection problem" + e.getMessage());
                }
            }
        }
    }


    @Override
    public void onListFragmentInteraction(ItemContent.DummyItem item) {
       if(item == null){
            no_item_text.setVisibility(View.VISIBLE);
            itemFragmentLayout.setVisibility(View.GONE);
           reminderFragmentLayout.setVisibility(View.GONE);

        }

    }
    @Override
    public void onListFragmentInteraction(ReminderContent.ReminderItem item) {
        if(item == null){
            no_item_text.setVisibility(View.VISIBLE);
            no_item_text.setText("no reminders set");
            itemFragmentLayout.setVisibility(View.GONE);
            reminderFragmentLayout.setVisibility(View.GONE);
        }

    }
}
