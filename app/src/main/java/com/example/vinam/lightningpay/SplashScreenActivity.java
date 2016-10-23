package com.example.vinam.lightningpay;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.drive.Permission;

public class SplashScreenActivity extends AppCompatActivity {
    private boolean successFullyLocAccepted = false;
    private boolean successFullyInternetAccepted = false;
    private boolean successFullyNetworkAccepted = false;
    private boolean successFullyCameraAccepted = false;
    private boolean successFullyExternalAccepted = false;
    private static final int MY_LOC_REQUEST_CODE = 0;
    private static final int MY_INTERNET_REQUEST_CODE = 1;
    private static final int MY_ACCESS_NETWORK_CODE = 2;
    private static final int MY_WRITE_EXTENRAL_STORAGE_CODE =3;
    private static final int MY_CAMERA_CODE = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        checkForPermissions();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(successFullyLocAccepted ){
                        startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                        break;
                    }
                }
            }
        }).start();
    }

    public void checkForPermissions(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this,"Please accept to access your location",Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},MY_LOC_REQUEST_CODE);
            }
        }
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.INTERNET)){
                Toast.makeText(this,"Please accept to access your Internet",Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.INTERNET},MY_INTERNET_REQUEST_CODE);
            }
        }
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_NETWORK_STATE)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_NETWORK_STATE)){
                Toast.makeText(this,"Please accept to access your network state",Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE},MY_ACCESS_NETWORK_CODE);
            }
        }
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(getApplicationContext(),"Please accept to access your external storage",Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_WRITE_EXTENRAL_STORAGE_CODE);
            }
        }
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                Toast.makeText(getApplicationContext(),"Please accept to access your camera",Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},MY_CAMERA_CODE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_LOC_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    successFullyLocAccepted = true;
                }
                break;
            case MY_INTERNET_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    successFullyInternetAccepted = true;
                }
                break;
            case MY_ACCESS_NETWORK_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    successFullyNetworkAccepted = true;
                }
                break;
            case MY_WRITE_EXTENRAL_STORAGE_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    successFullyExternalAccepted = true;
                }
                break; case MY_CAMERA_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    successFullyCameraAccepted = true;
                }
                break;

        }
    }
}
