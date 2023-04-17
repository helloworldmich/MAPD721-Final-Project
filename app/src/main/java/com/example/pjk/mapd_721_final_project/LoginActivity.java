package com.example.pjk.mapd_721_final_project;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
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

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ActivityResultLauncher signInLauncher; // added
    ActionCodeSettings actionCodeSettings;

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

        TextView textViewThridPartyLogin = findViewById(R.id.textViewThridPartyLogin);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // moved upper to here
        signInLauncher = registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                    @Override
                    public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                        onSignInResult(result);
                    }
                }
        );

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

        textViewThridPartyLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                thirdPartySignIn();
            }
        });

    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {

            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Log.d("mich", user.toString()); //output: com.google.firebase.auth.internal.zzz@3ac4783
            Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class); //MainScreen is the page after login
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    // correction: to open screen for signing in by google, email or other providers
    private void thirdPartySignIn() {

        try {
            ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setAndroidPackageName(
                            /* yourPackageName= */ "com.ymc.myfinalproj2",
                            /* installIfNotAvailable= */ true,
                            /* minimumVersion= */ "12")
                    .setHandleCodeInApp(true) // This must be set to true to make loggin state throughout navigation
                    .setUrl("android-final-proj-f7ee5.firebaseapp.com") // This URL needs to be whitelisted // wanderly.page.link
                    .build();

            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder()
                            .enableEmailLinkSignIn()
                            .setActionCodeSettings(actionCodeSettings)
                            .build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();
            signInLauncher.launch(signInIntent);

        } catch(Exception e){
            Log.e("mich", "exception", e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
}