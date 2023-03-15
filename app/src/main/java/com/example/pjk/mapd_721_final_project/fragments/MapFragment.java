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
import android.widget.Toast;

import com.example.pjk.mapd_721_final_project.GpsTracker;
import com.example.pjk.mapd_721_final_project.MainScreenActivity;
import com.example.pjk.mapd_721_final_project.R;
import com.example.pjk.mapd_721_final_project.RegisterActivity;
import com.example.pjk.mapd_721_final_project.data.Checkin;
import com.example.pjk.mapd_721_final_project.data.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private GpsTracker gpsTracker;
    private DatabaseReference databaseReferencCheckin;
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

        //databaseReferencCheckin = FirebaseDatabase.getInstance().getReference().child("user").child("checkin");
        databaseReferencCheckin = FirebaseDatabase.getInstance().getReference("user/checkin");
        String checkinId = databaseReferencCheckin.push().getKey();

        String date = "testDate";
        String time = "testTime";
        String sLongitude = "123";
        String sLatitude = "421";
        String desc = "this is my description test";
        String remarks = "this is my remarks and its longer than my description. yes no yes yes";

        Checkin checkin = new Checkin(date, time, sLongitude, sLatitude, desc, remarks);
      //  databaseReferencCheckin.setValue(checkin);
        databaseReferencCheckin.child(checkinId).setValue(checkin);
        Toast.makeText(getContext(), "New Account Successfully Registered!", Toast.LENGTH_SHORT).show();
    }
}