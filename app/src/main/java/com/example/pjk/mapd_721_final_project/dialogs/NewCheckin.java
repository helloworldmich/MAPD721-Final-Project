package com.example.pjk.mapd_721_final_project.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pjk.mapd_721_final_project.R;
import com.example.pjk.mapd_721_final_project.data.Checkin;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewCheckin extends Dialog {

    SharedPreferences sharedPreferences;
    TextView textViewNewCheckinLong;
    TextView textViewNewCheckinLat;
    TextView textViewNewCheckinCity;
    TextView textViewNewCheckinCountry;
    TextView textViewNewCheckinAddress;
    EditText editTextNewCheckinRemarks;
    EditText editTextNewCheckinTitle;
    String username;
    String postalCode;
    private DatabaseReference databaseReferencCheckin;

    public NewCheckin(Context context) {
        super(context);

        // Set the layout for the popup window
        setContentView(R.layout.dialog_new_checkin);

        sharedPreferences = context.getSharedPreferences("checkin", Context.MODE_PRIVATE);
        String longitude = sharedPreferences.getString("longitude", "");
        String latitude = sharedPreferences.getString("latitude", "");
        String city = sharedPreferences.getString("city", "");
        String country = sharedPreferences.getString("country", "");
        String address = sharedPreferences.getString("address", "");
        postalCode = sharedPreferences.getString("postal", "");

        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        textViewNewCheckinLong = findViewById(R.id.textViewNewCheckinLong);
        textViewNewCheckinLat = findViewById(R.id.textViewNewCheckinLat);
        textViewNewCheckinCity  = findViewById(R.id.textViewNewCheckinCity);
        textViewNewCheckinCountry = findViewById(R.id.textViewNewCheckinCountry);
        textViewNewCheckinAddress = findViewById(R.id.textViewNewCheckinAddress);
        editTextNewCheckinRemarks = findViewById(R.id.editTextNewCheckinRemarks);
        editTextNewCheckinTitle = findViewById(R.id.editTextNewCheckinTitle);

        textViewNewCheckinLong.setText(longitude);
        textViewNewCheckinLat.setText(latitude);
        textViewNewCheckinCity.setText(city);
        textViewNewCheckinCountry.setText(country);
        textViewNewCheckinAddress.setText(address);

        // Set the width and height of the popup window
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button checkin = findViewById(R.id.buttonNewCheckin);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                Date currentTime = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(currentTime);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String timeString = timeFormat.format(currentTime);

                String date = dateString;
                String time = timeString;
                String sLongitude = textViewNewCheckinLong.getText().toString().trim();
                String sLatitude = textViewNewCheckinLat.getText().toString().trim();
                String desc = textViewNewCheckinAddress.getText().toString().trim();
                String remarks = editTextNewCheckinRemarks.getText().toString().trim();
                String title = editTextNewCheckinTitle.getText().toString().trim();
                String city = textViewNewCheckinCity.getText().toString().trim();
                String postal = postalCode;
                String country = textViewNewCheckinCountry.getText().toString().trim();;
                String isFavorite = "false";

                if(title.isEmpty())
                {
                    Toast.makeText(getContext(), "Please enter Title!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    databaseReferencCheckin = FirebaseDatabase.getInstance().getReference("user");
                    String checkinId = databaseReferencCheckin.push().getKey();
                    long timestamp = System.currentTimeMillis();
                    Checkin checkin = new Checkin(checkinId,title, date, time, sLongitude, sLatitude, city,country, desc, postal,isFavorite,remarks,timestamp);
                    databaseReferencCheckin.child(username).child("checkin").child(checkinId).setValue(checkin);
                    Toast.makeText(getContext(), "Successfully Checked in!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

            }
        });
    }

}
