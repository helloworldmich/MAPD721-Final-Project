package com.example.pjk.mapd_721_final_project.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pjk.mapd_721_final_project.R;
import com.example.pjk.mapd_721_final_project.dialogs.NewCheckin;
import com.example.pjk.mapd_721_final_project.dialogs.UpdateAccount;
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

    TextView textViewMeTotalFavorites;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_me, container, false);

        textViewMeTotalCities = rootview.findViewById(R.id.textViewMeTotalCities);
        textViewMeTotalCountries = rootview.findViewById(R.id.textViewMeTotalCountries);
        textViewMeTotalCheckin = rootview.findViewById(R.id.textViewMeTotalCheckin);
        textViewMeTotalFavorites = rootview.findViewById(R.id.textViewMeTotalFavorites);
        Button buttonMeLogout = rootview.findViewById(R.id.buttonMeLogout);
        Button buttonMeUpdateAccount = rootview.findViewById(R.id.buttonMeUpdateAccount);


        loadCounts();

        buttonMeLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shareContent();

            }
        });

        buttonMeUpdateAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UpdateAccount popupWindow = new UpdateAccount(getContext());
                popupWindow.show();

            }
        });

        return rootview;
    }

    private void shareContent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Check out this amazing content!";
        String shareSubject = "Sharing my Checkin";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private void loadCounts()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user/"+username+"/checkin");

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


        Query query = ref.orderByChild("isFavorite").equalTo("true");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long favoriteCount = snapshot.getChildrenCount();

                textViewMeTotalFavorites.setText(favoriteCount + " Favorite Places");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }
}