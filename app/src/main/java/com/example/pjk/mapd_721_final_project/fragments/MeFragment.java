package com.example.pjk.mapd_721_final_project.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pjk.mapd_721_final_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Set;


public class MeFragment extends Fragment {

    TextView textViewMeTotalCities;
    TextView textViewMeTotalCountries;
    TextView textViewMeTotalCheckin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_me, container, false);

        textViewMeTotalCities = rootview.findViewById(R.id.textViewMeTotalCities);
        textViewMeTotalCountries = rootview.findViewById(R.id.textViewMeTotalCountries);
        textViewMeTotalCheckin = rootview.findViewById(R.id.textViewMeTotalCheckin);

        loadCounts();

        return rootview;
    }

    private void loadCounts()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user/"+username+"/checkin");

     //   Query cityQuery = ref.orderByChild("checkin/city").equalTo(true);

// Attach a listener to the query to count the distinct values
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Use a set to keep track of the distinct values
                int count = (int) dataSnapshot.getChildrenCount();  //count
                Set<String> cities = new HashSet<>();
                Set<String> countries = new HashSet<>();

                // Loop through the data and add each distinct city to the set
                for (DataSnapshot checkinSnapshot : dataSnapshot.getChildren()) {
                    String city = checkinSnapshot.child("city").getValue(String.class);
                    if (city != null) {
                        cities.add(city);
                    }
                }
                for (DataSnapshot checkinSnapshot : dataSnapshot.getChildren()) {
                    String country = checkinSnapshot.child("country").getValue(String.class);
                    if (country != null) {
                        countries.add(country);
                    }
                }

                // Output the number of distinct
                System.out.println("Number of distinct cities: " + cities.size());
                textViewMeTotalCities.setText(cities.size() + " Total Cities Visited");
                System.out.println("Number of distinct countries: " + countries.size());
                textViewMeTotalCountries.setText(countries.size() + " Total Countries Visited");
                textViewMeTotalCheckin.setText(count + " Total Checkins");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }
}