package com.ubook.ubookapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;
import com.ubook.ubookapp.account.AccountFragment;
import com.ubook.ubookapp.helper.BottomNavigationBehavior;
import com.ubook.ubookapp.notify.NotifyFragment;
import com.ubook.ubookapp.home.HomeFragment;
import com.ubook.ubookapp.orders.OrdersFragment;

import static com.ubook.ubookapp.utils.Constants.ERROR_DIALOG_REQUEST;
import static com.ubook.ubookapp.utils.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.ubook.ubookapp.utils.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity {

    private boolean mLocationPermissionGranted = false;
    private static final String TAG = "uytai";
    //
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //
        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        Fragment fragment;
        fragment = new HomeFragment();
        loadFragment(fragment);
    }

    //


//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (checkMapServices()) {
//            if (mLocationPermissionGranted) {
//                //
//            } else {
//                getLocationPermission();
//            }
//        }
//    }

    //
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.menu_home:
                fragment = new HomeFragment();
                loadFragment(fragment);
                return true;
            case R.id.menu_orders:
                fragment = new OrdersFragment();
                loadFragment(fragment);
                return true;
            case R.id.menu_notify:
                fragment = new NotifyFragment();
                loadFragment(fragment);
                return true;
            case R.id.menu_account:
                fragment = new AccountFragment();
                loadFragment(fragment);
                return true;
        }
        return false;
    };

    //
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


//    //-------------------------------------------------------------------------------------------//
//    private boolean checkMapServices() {
//        if (isServicesOK()) {
//            if (isMapsEnabled()) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void getLastKnowLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                if (task.isSuccessful()) {
//                    Location location = task.getResult();
//                    GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
//                    setLat(geoPoint.getLatitude());
//                    setLng(geoPoint.getLongitude());
//                    Log.d("uytai", "LAT: " + geoPoint.getLatitude());
//                    Log.d("uytai", "LNG: " + geoPoint.getLongitude());
//                }
//            }
//        });
//    }
//
//    private void buildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }
//
//    public boolean isMapsEnabled() {
//        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            buildAlertMessageNoGps();
//            return false;
//        }
//        return true;
//    }
//
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//            //getChatrooms();
//            getLastKnowLocation();
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//    }
//
//    public boolean isServicesOK() {
//        Log.d(TAG, "isServicesOK: checking google services version");
//
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
//
//        if (available == ConnectionResult.SUCCESS) {
//            //everything is fine and the user can make map requests
//            Log.d(TAG, "isServicesOK: Google Play Services is working");
//            return true;
//        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
//            //an error occured but we can resolve it
//            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
//            dialog.show();
//        } else {
//            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        mLocationPermissionGranted = false;
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult: called.");
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ENABLE_GPS: {
//                if (mLocationPermissionGranted) {
//                    //getChatrooms();
//                    getLastKnowLocation();
//                } else {
//                    getLocationPermission();
//                }
//            }
//        }
//
//    }

}
