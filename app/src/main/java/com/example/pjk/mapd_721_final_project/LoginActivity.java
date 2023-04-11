package com.example.pjk.mapd_721_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReferencUser;
    EditText editTextTextLoginUsername;
    EditText editTextTextLoginPassword;
    SharedPreferences sharedPreferences;

    CheckBox checkBoxRememberMe;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        String rememberMe = sharedPreferences.getString("rememberMe", "");

        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView textViewRegister = findViewById(R.id.textViewRegister);
        editTextTextLoginUsername = findViewById(R.id.editTextTextLoginUsername);
        editTextTextLoginPassword = findViewById(R.id.editTextTextLoginPassword);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        if(rememberMe.equals("Y"))
        {
            editTextTextLoginUsername.setText(username);
            editTextTextLoginPassword.setText(password);
            checkBoxRememberMe.setChecked(true);
        }
        else
        {
            editTextTextLoginUsername.setText("");
            editTextTextLoginPassword.setText("");
            checkBoxRememberMe.setChecked(false);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Check for internet connectivity
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected()) {
                    Toast.makeText(LoginActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
                    return;
                }

                username = editTextTextLoginUsername.getText().toString().trim();
                String inputPassword = editTextTextLoginPassword.getText().toString().trim();

                databaseReferencUser = FirebaseDatabase.getInstance().getReference("user/" + username + "/login");

                databaseReferencUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            String password = snapshot.child("password").getValue(String.class);

                            System.out.println("input password = " + inputPassword + "db password = " + password);

                            if(password.equals(inputPassword))
                            {
                                sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                if(checkBoxRememberMe.isChecked())
                                {
                                    editor.putString("username", username);
                                    editor.putString("password", inputPassword);
                                    editor.putString("rememberMe", "Y");
                                }
                                else
                                {
                                    editor.putString("username", "");
                                    editor.putString("password", "");
                                    editor.putString("rememberMe", "N");
                                }

                                editor.apply();
                                startActivity(new Intent(getApplicationContext(),MainScreenActivity.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "User not Found!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("TAG", "database error: " + error.getMessage());
                        Toast.makeText(LoginActivity.this, "database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });


    }
}