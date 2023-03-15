package com.example.pjk.mapd_721_final_project.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pjk.mapd_721_final_project.GpsTracker;
import com.example.pjk.mapd_721_final_project.MainScreenActivity;
import com.example.pjk.mapd_721_final_project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private GpsTracker gpsTracker;

    double longitude;
    double latitude;
    String cityName;
    String countryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        Button buttonCheckin = rootView.findViewById(R.id.buttonCheckin);

        buttonCheckin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                buttonCheckinClicked();
            }
        });


        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        try {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;

    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        GpsTracker gpsTracker = new GpsTracker(this.getContext());
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        cityName = gpsTracker.getCityName();
        countryName = gpsTracker.getCountryName();


//        System.out.println("long = " + longitude + " lat = " + latitude);
//        System.out.println("City Name = "  + cityName);
//        System.out.println("Country Name = "  + countryName);

        // Add a marker in Sydney and move the camera
        LatLng centennial = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(centennial).title(cityName));
        // below line is use to add custom marker on our map.

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centennial, 15));

    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    public void buttonCheckinClicked()
    {
        System.out.println("long = " + longitude + " lat = " + latitude);
        System.out.println("City Name = "  + cityName);
        System.out.println("Country Name = "  + countryName);
    }
}