package com.ubook.ubookapp.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.maps.android.clustering.ClusterManager;
import com.ubook.ubookapp.R;
import com.ubook.ubookapp.helper.MyClusterManagerRenderer;
import com.ubook.ubookapp.model.ClusterMarker;
import com.ubook.ubookapp.model.UserLocation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ubook.ubookapp.utils.Constants.MAPVIEW_BUNDLE_KEY;


public class HomeFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.mvUbook)
    MapView mvUbook;
    @BindView(R.id.svLocation)
    SearchView svLocation;
    //
    private ArrayList<UserLocation> mUserLocations = new ArrayList<>();
    private GoogleMap mGoogleMap;
    private ClusterManager<ClusterMarker> mClusterManager;
    private MyClusterManagerRenderer mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();
    private UserLocation mUserPosition;
    private LatLngBounds mMapBoundary;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initData();
        ButterKnife.bind(this, view);
        initMapView(savedInstanceState);
        searchLocation();
        return view;
    }

    private void initData() {
        mUserLocations = new ArrayList<>();
        mUserLocations.add(new UserLocation("Uy",10.8041983, 106.7199983 ,R.drawable.icon_notify));
        mUserLocations.add(new UserLocation("Phong",10.1041983, 106.6199983 ,R.drawable.icon_notify));
        mUserLocations.add(new UserLocation("Dat",10.2041983, 106.5199983 ,R.drawable.icon_notify));
        mUserLocations.add(new UserLocation("Quang",10.3041983, 106.4199983 ,R.drawable.icon_notify));
        mUserLocations.add(new UserLocation("Phuoc",10.4041983, 106.3199983 ,R.drawable.icon_notify));
        mUserLocations.add(new UserLocation("Sang",10.5041983, 106.2199983 ,R.drawable.icon_notify));
        mUserLocations.add(new UserLocation("Ngan",10.6041983, 106.1199983 ,R.drawable.icon_notify));
        mUserLocations.add(new UserLocation("Thi",10.7041983, 106.8199983 ,R.drawable.icon_notify));
        mUserLocations.add(new UserLocation("Thy",10.9041983, 106.9199983 ,R.drawable.icon_notify));
        //
        setUserPosition();
    }

    //search
    private void searchLocation(){
        svLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                for (UserLocation userLocation : mUserLocations) {
                    if (userLocation.getName().equals(newText)){
                        setCameraView(userLocation.getLat(), userLocation.getLng());
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
    private void addMapMarker(){
        if(mGoogleMap != null){

            if(mClusterManager == null){
                mClusterManager = new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(), mGoogleMap);
            }
            if(mClusterManagerRenderer == null){
                mClusterManagerRenderer = new MyClusterManagerRenderer(
                        getActivity(),
                        mGoogleMap,
                        mClusterManager

                );
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }

            for(UserLocation userLocation: mUserLocations){
                Log.d("uytai", mUserLocations.size() + "_");

                try{
                    String snippet = "";
                    if(userLocation.getName().equals("Uy")){
                        snippet = "Nhà bạn";
                    }
                    else{
                        snippet = "Nhà " + userLocation.getName();
                    }

                    int avatar = R.drawable.ic_home_black_24dp; // set the default avatar
                    //if want to custom marker for each user
//                    try{
//                        avatar = userLocation.getAvatar();
//                    }catch (NumberFormatException e){
//
//                    }
                    ClusterMarker newClusterMarker = new ClusterMarker(
                            new LatLng(userLocation.getLat(), userLocation.getLng()),
                            userLocation.getName(),
                            snippet,
                            avatar
                    );
                    mClusterManager.addItem(newClusterMarker);
                    mClusterMarkers.add(newClusterMarker);

                }catch (NullPointerException e){
                    Log.e("uytai", "addMapMarkers: NullPointerException: " + e.getMessage() );
                }

            }
            mClusterManager.cluster();
            setCameraView(mUserPosition.getLat(), mUserPosition.getLng());
        }

    }

    //
    private void setUserPosition() {
        for (UserLocation userLocation : mUserLocations) {
            if (userLocation.getName().equals("Uy")){
                mUserPosition = userLocation;
            }
        }
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
        addMapMarker();
        googleMap.setMyLocationEnabled(true);
    }
}
