package com.ubook.ubookapp.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ubook.ubookapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ubook.ubookapp.utils.Constants.MAPVIEW_BUNDLE_KEY;


public class HomeFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.mvUbook)
    MapView mvUbook;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initMapView(savedInstanceState);
        return view;
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
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 1)).title("Marker"));
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
        googleMap.setMyLocationEnabled(true);
    }
}
