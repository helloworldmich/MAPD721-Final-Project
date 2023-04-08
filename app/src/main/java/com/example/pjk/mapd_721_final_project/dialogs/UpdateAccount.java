package com.example.pjk.mapd_721_final_project.dialogs;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pjk.mapd_721_final_project.LoginActivity;
import com.example.pjk.mapd_721_final_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateAccount extends Dialog {

    String username;
    SharedPreferences sharedPreferences;

    EditText editTextUpdateName;

    EditText editTextUpdateEmail;


    public UpdateAccount(@NonNull Context context) {
        super(context);

        setContentView(R.layout.dialog_update_account);

        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        Button buttonUpdateChangePassword = findViewById(R.id.buttonUpdateChangePassword);
        Button buttonUpdateSave = findViewById(R.id.buttonUpdateSave);
        editTextUpdateName = findViewById(R.id.editTextUpdateName);
        editTextUpdateEmail = findViewById(R.id.editTextUpdateEmail);

        // get a reference to the user's login data
        DatabaseReference loginRef = FirebaseDatabase.getInstance().getReference().child("user").child(username).child("login");

        loginRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get the name and email values from the DataSnapshot
                String name = dataSnapshot.child("name").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                editTextUpdateName.setText(name);
                editTextUpdateEmail.setText(email);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // handle any errors
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        buttonUpdateSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // get a reference to the user's login data
                DatabaseReference loginRef = FirebaseDatabase.getInstance().getReference().child("user").child(username).child("login");

                String newEmail = editTextUpdateEmail.getText().toString().trim();
                loginRef.child("email").setValue(newEmail);

                String newName = editTextUpdateName.getText().toString().trim();
                loginRef.child("name").setValue(newName);
                Toast.makeText(getContext(), "Account Successfully Updated!", Toast.LENGTH_SHORT).show();
                dismiss();

            }
        });

        buttonUpdateChangePassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // create a new alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change Password");

                // inflate the custom layout for the dialog
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_password, null);
                builder.setView(view);

                // get the edit text fields for the passwords
                final EditText editTextPassword1 = view.findViewById(R.id.editTextPassword1);
                final EditText editTextPassword2 = view.findViewById(R.id.editTextPassword2);

                // set up the positive button to save the changes
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get the new passwords and do something with them
                        String newPassword1 = editTextPassword1.getText().toString();
                        String newPassword2 = editTextPassword2.getText().toString();

                        if(newPassword1.equals(newPassword2))
                        {
                            DatabaseReference loginRef = FirebaseDatabase.getInstance().getReference().child("user").child(username).child("login");

                            loginRef.child("password").setValue(newPassword1);

                            Toast.makeText(getContext(), "Password Successfully Updated!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Passwords do not Match!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                // set up the negative button to cancel the changes
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss the dialog
                        dialog.dismiss();
                    }
                });

                // show the dialog
                builder.show();

            }
        });

    }
}
