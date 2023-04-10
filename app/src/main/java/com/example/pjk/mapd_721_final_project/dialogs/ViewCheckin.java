package com.example.pjk.mapd_721_final_project.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pjk.mapd_721_final_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ViewCheckin extends Dialog {

    Activity a;
    EditText editTextViewCheckinTitle;
    TextView textViewViewCheckinLong;
    TextView textViewViewCheckinLat;
    TextView textViewViewCheckinCity;
    TextView textViewViewCheckinCountry;
    TextView textViewViewCheckinAddress;
    TextView textViewViewCheckinID;
    EditText editTextViewCheckinRemarks;
    String username;

    String longitude;
    String latitude;

    Switch switchFavorite;

    public ViewCheckin(Context context, Activity a, String checkinID) {
        super(a);

        // Set the layout for the popup window
        setContentView(R.layout.dialog_view_checkin);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        editTextViewCheckinTitle = findViewById(R.id.editTextViewCheckinTitle);
        switchFavorite = findViewById(R.id.switchFavorite);
        textViewViewCheckinLong = findViewById(R.id.textViewViewCheckinLong);
        textViewViewCheckinLat = findViewById(R.id.textViewViewCheckinLat);
        textViewViewCheckinCity  = findViewById(R.id.textViewViewCheckinCity);
        textViewViewCheckinID = findViewById(R.id.textViewViewCheckinID);
        textViewViewCheckinCountry = findViewById(R.id.textViewViewCheckinCountry);
        textViewViewCheckinAddress = findViewById(R.id.textViewViewCheckinAddress);
        editTextViewCheckinRemarks = findViewById(R.id.editTextViewCheckinRemarks);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference checkinRef = database.getReference("user/" + username + "/checkin/" + checkinID);

        checkinRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Retrieve the data for the check-in ID
                    String city = snapshot.child("city").getValue(String.class);
                    String country = snapshot.child("country").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String desc = snapshot.child("desc").getValue(String.class);
                    latitude = snapshot.child("latitude").getValue(String.class);
                    longitude = snapshot.child("longitude").getValue(String.class);
                    String postal = snapshot.child("postal").getValue(String.class);
                    String remarks = snapshot.child("remarks").getValue(String.class);
                    String isFavorite = snapshot.child("isFavorite").getValue(String.class);
                    String time = snapshot.child("time").getValue(String.class);
                    long timestamp = snapshot.child("timestamp").getValue(Long.class);
                    String title = snapshot.child("title").getValue(String.class);

                    editTextViewCheckinTitle.setText(title);
                    editTextViewCheckinRemarks.setText(remarks);
                    textViewViewCheckinID.setText(checkinID);
                    textViewViewCheckinLong.setText(longitude);
                    textViewViewCheckinLat.setText(latitude);
                    textViewViewCheckinCity.setText(city);
                    textViewViewCheckinCountry.setText(country);
                    textViewViewCheckinAddress.setText(desc);
                    switchFavorite.setChecked(Boolean.valueOf(isFavorite));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });


        Button buttonViewCheckinOpenMap = findViewById(R.id.buttonViewCheckinOpenMap);
        Button buttonViewCheckinDelete = findViewById(R.id.buttonViewCheckinDelete);
        Button buttonViewCheckinShare = findViewById(R.id.buttonViewCheckinShare);
        buttonViewCheckinOpenMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String location = latitude+","+longitude; // the location you want to show on the map
                String label = "Marker Here"; // the label you want to show for the location
                String uriBegin = "geo:" + location;
                String query = location + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                System.out.println(uriString);
                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                v.getContext().startActivity(mapIntent);

            }
        });

        switchFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String checkinId = checkinID;
                DatabaseReference isFavoriteRef = database.getReference("user/" + username + "/checkin/" + checkinId + "/isFavorite");

                if(isChecked)
                {
                    isFavoriteRef.setValue("true");
                }
                else
                {
                    isFavoriteRef.setValue("false");
                }

            }
        });


        buttonViewCheckinDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String checkinId = checkinID;

                DatabaseReference checkinRef = FirebaseDatabase.getInstance()
                        .getReference("user/" + username + "/checkin/" + checkinId);

                checkinRef.removeValue();
                Toast.makeText(getContext(), "Checkin Deleted!", Toast.LENGTH_SHORT).show();
                dismiss();

            }
        });

        buttonViewCheckinShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                shareContent();

            }
        });


    }


    private void shareContent() {
        String title = editTextViewCheckinTitle.getText().toString().trim();
        String cityCountry = textViewViewCheckinCity.getText().toString().trim() + ", " + textViewViewCheckinCountry.getText().toString().trim();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = title + " - " + cityCountry;
        String shareSubject = "Sharing my Checkin";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        this.getContext().startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId);
    }

}

