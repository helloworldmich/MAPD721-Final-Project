package com.example.pjk.mapd_721_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pjk.mapd_721_final_project.data.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference databaseReferencUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                register();
            }
        });

    }

    private void register() {
//        String password = editTextPassword.getText().toString().trim();

        databaseReferencUser = FirebaseDatabase.getInstance().getReference().child("user").child("login");
        String username = "testUserAdmin";
        String password = "thisisapassword123";

        User user = new User(username, password);
        databaseReferencUser.setValue(user);
        Toast.makeText(RegisterActivity.this, "New Account Successfully Registered!", Toast.LENGTH_SHORT).show();
    }

}