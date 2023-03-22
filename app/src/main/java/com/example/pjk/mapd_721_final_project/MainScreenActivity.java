package com.example.pjk.mapd_721_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pjk.mapd_721_final_project.fragments.HistoryFragment;
import com.example.pjk.mapd_721_final_project.fragments.MapFragment;
import com.example.pjk.mapd_721_final_project.fragments.MeFragment;
import com.example.pjk.mapd_721_final_project.fragments.SettingsFragment;
import com.example.pjk.mapd_721_final_project.fragments.WeatherFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainScreenActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    WeatherFragment weatherFragment = new WeatherFragment();
    MapFragment mapFragment = new MapFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    MeFragment meFragment = new MeFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, weatherFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.menuWeather:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, weatherFragment).commit();
                        return true;
                    case R.id.menuCurrentMap:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment).commit();
                        return true;
                    case R.id.menuHistoryCheckin:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, historyFragment).commit();
                        return true;
                    case R.id.menuMe:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, meFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                // handle menu item click here
//                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}