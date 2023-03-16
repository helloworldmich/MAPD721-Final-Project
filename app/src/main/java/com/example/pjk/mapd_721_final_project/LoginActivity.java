package com.example.pjk.mapd_721_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonLRegister = findViewById(R.id.buttonLRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editTextTextLoginUsername = findViewById(R.id.editTextTextLoginUsername);
                editTextTextLoginPassword = findViewById(R.id.editTextTextLoginPassword);

                String username = editTextTextLoginUsername.getText().toString().trim();
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
                                SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", username);
                                editor.apply();
                                startActivity(new Intent(getApplicationContext(),MainScreenActivity.class));
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

        buttonLRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });


    }
}