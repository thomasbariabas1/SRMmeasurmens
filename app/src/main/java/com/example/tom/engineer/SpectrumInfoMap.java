package com.example.tom.engineer;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.srmmeasurements.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class SpectrumInfoMap extends Fragment {

    GoogleMap mMap;
    double x_1;
    double y_1;
    double x_2;
    double y_2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.spectrumsearchmap, container, false);
        Bundle rs2 = getArguments();


        x_1 = rs2.getDouble("x_1");
        y_1 = rs2.getDouble("y_1");
        x_2 = rs2.getDouble("x_2");
        y_2 = rs2.getDouble("y_2");
        setUpMapIfNeeded();
        return rootView;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the
        // map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map3)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add lots of markers to the map.
        addMarkersToMap();
    }

    private void addMarkersToMap() {
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(x_1, y_1), 14.0f));
        if (x_2 == 0 && y_2 == 0) {
            LatLng ll = new LatLng(x_1, y_1);
            mMap.addMarker(new MarkerOptions().position(ll).snippet(Double.toString(x_1) + " " + Double.toString(y_1)));
        } else {

            LatLng ll = new LatLng(x_1, y_1);
            mMap.addMarker(new MarkerOptions().position(ll).snippet(x_1 + " " + y_1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            ll = new LatLng(x_2, y_2);
            mMap.addMarker(new MarkerOptions().position(ll).snippet(x_2 + " " + y_2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(x_1, y_1), new LatLng(x_2, y_2))
                    .width(5)
                    .color(Color.BLUE).geodesic(true));


        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) SpectrumInfo.fragmentManager
                    .findFragmentById(R.id.map3)).getMap(); // getMap is deprecated
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }



}





