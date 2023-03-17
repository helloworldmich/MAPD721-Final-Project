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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.pjk.mapd_721_final_project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public ViewCheckin(Context context, Activity a, String title, String longitude, String latitude, String city,
                       String country, String address, String checkinID, String remarks) {
        super(a);

        // Set the layout for the popup window
        setContentView(R.layout.dialog_view_checkin);

        editTextViewCheckinTitle = findViewById(R.id.editTextViewCheckinTitle);

        textViewViewCheckinLong = findViewById(R.id.textViewViewCheckinLong);
        textViewViewCheckinLat = findViewById(R.id.textViewViewCheckinLat);
        textViewViewCheckinCity  = findViewById(R.id.textViewViewCheckinCity);
        textViewViewCheckinID = findViewById(R.id.textViewViewCheckinID);
        textViewViewCheckinCountry = findViewById(R.id.textViewViewCheckinCountry);
        textViewViewCheckinAddress = findViewById(R.id.textViewViewCheckinAddress);
        editTextViewCheckinRemarks = findViewById(R.id.editTextViewCheckinRemarks);
        editTextViewCheckinTitle = findViewById(R.id.editTextViewCheckinTitle);

        editTextViewCheckinTitle.setText(title);
        editTextViewCheckinRemarks.setText(remarks);
        textViewViewCheckinID.setText(checkinID);
        textViewViewCheckinLong.setText(longitude);
        textViewViewCheckinLat.setText(latitude);
        textViewViewCheckinCity.setText(city);
        textViewViewCheckinCountry.setText(country);
        textViewViewCheckinAddress.setText(address);

        Button buttonViewCheckinOpenMap = findViewById(R.id.buttonViewCheckinOpenMap);
        Button buttonViewCheckinDelete = findViewById(R.id.buttonViewCheckinDelete);
        buttonViewCheckinOpenMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String location = latitude+","+longitude; // the location you want to show on the map
                String label = "Label"; // the label you want to show for the location
                String uriBegin = "geo:" + location;
                String query = location + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                v.getContext().startActivity(mapIntent);

            }
        });

        buttonViewCheckinDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                String checkinId = checkinID;

                DatabaseReference checkinRef = FirebaseDatabase.getInstance()
                        .getReference("user/" + username + "/checkin/" + checkinId);

                checkinRef.removeValue();
                Toast.makeText(getContext(), "Checkin Deleted!", Toast.LENGTH_SHORT).show();
                dismiss();

            }
        });


    }




    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId);
    }

}

