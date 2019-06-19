package com.ubook.ubookapp.home;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.clustering.ClusterManager;
import com.ubook.ubookapp.R;
import com.ubook.ubookapp.base.BaseFragment;
import com.ubook.ubookapp.helper.MyClusterManagerRenderer;
import com.ubook.ubookapp.helper.UbookHelper;
import com.ubook.ubookapp.network.model.ClusterMarker;
import com.ubook.ubookapp.network.model.ListShops;
import com.ubook.ubookapp.network.model.Shop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ubook.ubookapp.utils.Constants.ERROR_DIALOG_REQUEST;
import static com.ubook.ubookapp.utils.Constants.MAPVIEW_BUNDLE_KEY;
import static com.ubook.ubookapp.utils.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.ubook.ubookapp.utils.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;


public class HomeFragment extends BaseFragment implements OnMapReadyCallback, IHome.View {

    @BindView(R.id.mvUbook)
    MapView mvUbook;
    @BindView(R.id.svLocation)
    SearchView svLocation;
    //
    private List<Shop> arrShops = new ArrayList<>();
    //private ArrayList<UserLocation> mUserLocations = new ArrayList<>();
    private GoogleMap mGoogleMap;
    private ClusterManager<ClusterMarker> mClusterManager;
    private MyClusterManagerRenderer mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();
    //private UserLocation mUserPosition;
    private Shop shop;
    private LatLngBounds mMapBoundary;
    private HomePresenter homePresenter;
    private Double Lat, Lng;

    FusedLocationProviderClient fusedLocationProviderClient;
    private boolean mLocationPermissionGranted = false;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        homePresenter = new HomePresenter(this);
        //initData();
        ButterKnife.bind(this, view);
        initMapView(savedInstanceState);
        searchLocation();
        return view;
    }


    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    protected void setDataToUI() {

    }

    @Override
    public void addActionClickListener() {

    }


//    private void initData() {
//        mUserLocations = new ArrayList<>();
//        mUserLocations.add(new UserLocation("Uy",10.8041983, 106.7199983 ,R.drawable.icon_notify));
//        mUserLocations.add(new UserLocation("Phong",10.1041983, 106.6199983 ,R.drawable.icon_notify));
//        mUserLocations.add(new UserLocation("Dat",10.2041983, 106.5199983 ,R.drawable.icon_notify));
//        mUserLocations.add(new UserLocation("Quang",10.3041983, 106.4199983 ,R.drawable.icon_notify));
//        mUserLocations.add(new UserLocation("Phuoc",10.4041983, 106.3199983 ,R.drawable.icon_notify));
//        mUserLocations.add(new UserLocation("Sang",10.5041983, 106.2199983 ,R.drawable.icon_notify));
//        mUserLocations.add(new UserLocation("Ngan",10.6041983, 106.1199983 ,R.drawable.icon_notify));
//        mUserLocations.add(new UserLocation("Thi",10.7041983, 106.8199983 ,R.drawable.icon_notify));
//        mUserLocations.add(new UserLocation("Thy",10.9041983, 106.9199983 ,R.drawable.icon_notify));
//        //
//        setUserPosition();
//    }

    //search
    private void searchLocation() {
        svLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                for (Shop shop : arrShops) {
                    if (shop.getName().equals(newText)) {
                        setCameraView(shop.getGeoLat(), shop.getGeoLng());
                    }
                }
                return false;
            }
        });
    }

    //setup mapview
    private void initMapView(Bundle savedInstanceState) {
        Bundle mapviewBundle = null;
        if (savedInstanceState != null) {
            mapviewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mvUbook.onCreate(mapviewBundle);
        mvUbook.getMapAsync(this);
    }

    //add marker
    private void addMapMarker() {
        if (mGoogleMap != null) {

            if (mClusterManager == null) {
                mClusterManager = new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(), mGoogleMap);
            }
            if (mClusterManagerRenderer == null) {
                mClusterManagerRenderer = new MyClusterManagerRenderer(
                        getActivity(),
                        mGoogleMap,
                        mClusterManager

                );
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }

            for (Shop shop : arrShops) {
                Log.d("uytai", arrShops.size() + "_");

                try {
                    String snippet = "";

                    snippet = shop.getName();


                    int avatar = R.drawable.ic_home_black_24dp; // set the default avatar
                    //if want to custom marker for each user
//                    try{
//                        avatar = userLocation.getAvatar();
//                    }catch (NumberFormatException e){
//
//                    }
                    ClusterMarker newClusterMarker = new ClusterMarker(
                            new LatLng(shop.getGeoLat(), shop.getGeoLng()),
                            shop.getName(),
                            snippet,
                            avatar
                    );
                    mClusterManager.addItem(newClusterMarker);
                    mClusterMarkers.add(newClusterMarker);

                } catch (NullPointerException e) {
                    Log.e("uytai", "addMapMarkers: NullPointerException: " + e.getMessage());
                }

            }
            mClusterManager.cluster();
            setCameraView(Lat, Lng);
        }

    }

    //
    private void setUserPosition() {
//        for (Shop shop : listShops) {
//            if (shop.getName().equals("Uy")) {
//                mUserPosition = userLocation;
//            }
//        }
    }

    //move camera
    private void setCameraView(Double lat, Double lng) {

        // Set a boundary to start
        double bottomBoundary = lat - .1;
        double leftBoundary = lng - .1;
        double topBoundary = lat + .1;
        double rightBoundary = lng + .1;

        mMapBoundary = new LatLngBounds(
                new LatLng(bottomBoundary, leftBoundary),
                new LatLng(topBoundary, rightBoundary)
        );

        mGoogleMap.setOnMapLoadedCallback(() -> mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary, 30)));
    }

    @Override
    public void onStart() {
        super.onStart();
        mvUbook.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mvUbook.onResume();
        if (checkMapServices()) {
            if (mLocationPermissionGranted) {
                //
            } else {
                getLocationPermission();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mvUbook.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mvUbook.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvUbook.onDestroy();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mvUbook != null && mvUbook.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mvUbook.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 0, 200);
        }
        mGoogleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getShopByLocationSuccess(ListShops listShops) {
        if(listShops!=null){
            arrShops = listShops.getShops();
            addMapMarker();
        }
    }

    @Override
    public void getShopByLocationFail(String messageError) {
        showDialogError(541, messageError);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("uytai", "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    //getChatrooms();
                    getLastKnowLocation();
                } else {
                    getLocationPermission();
                }
            }
        }
    }

    /////////////////////////
    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    private void getLastKnowLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                homePresenter.requestGetShopByLocation(0, 0, 0, geoPoint.getLongitude(), geoPoint.getLatitude(), UbookHelper.getTimezone());
                Lat = geoPoint.getLatitude();
                Lng = geoPoint.getLongitude();
                Log.d("uytai", "LAT: " + geoPoint.getLatitude());
                Log.d("uytai", "LNG: " + geoPoint.getLongitude());
            }
        });
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            //getChatrooms();
            getLastKnowLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK() {
        Log.d("uytai", "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d("uytai", "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d("uytai", "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
