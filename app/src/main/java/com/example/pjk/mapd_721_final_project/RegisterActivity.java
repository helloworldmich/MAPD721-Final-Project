package com.example.pjk.mapd_721_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pjk.mapd_721_final_project.data.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference databaseReferencUser;
    EditText editTextRegisterUserName;
    EditText editTextRegisterName;
    EditText editTextRegisterPassword1;
    EditText editTextRegisterPassword2;
    EditText editTextRegisterEmail;

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

        databaseReferencUser = FirebaseDatabase.getInstance().getReference().child("user");

        editTextRegisterUserName = findViewById(R.id.editTextRegisterUserName);
        editTextRegisterName = findViewById(R.id.editTextRegisterName);
        editTextRegisterPassword1 = findViewById(R.id.editTextRegisterPassword1);
        editTextRegisterPassword2 = findViewById(R.id.editTextRegisterPassword2);
        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);


        String username = editTextRegisterUserName.getText().toString().trim();
        String password = editTextRegisterPassword1.getText().toString().trim();
        String name = editTextRegisterName.getText().toString().trim();
        String email = editTextRegisterEmail.getText().toString().trim();

        User user = new User(username, password, name, email);

        databaseReferencUser.child(username).child("login").setValue(user);
        Toast.makeText(RegisterActivity.this, "New Account Successfully Registered!", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}