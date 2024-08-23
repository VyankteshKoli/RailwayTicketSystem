package com.example.railwayticketsystem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.railwayticketsystem.R;
import com.example.railwayticketsystem.ReadWriteUserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTextView, dobTextView, phoneTextView, genderTextView, emailTextView;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        nameTextView = findViewById(R.id.nameTextView);
        dobTextView = findViewById(R.id.dobTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        genderTextView = findViewById(R.id.genderTextView);
        emailTextView = findViewById(R.id.emailTextView);
        progressBar = findViewById(R.id.progressbar);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser == null) {
            // If user is not logged in, show a toast message
            Toast.makeText(ProfileActivity.this, "User is not logged in!", Toast.LENGTH_LONG).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        // Database reference to "Registered Users" node
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

        // Retrieve user data from database
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                    if (readUserDetails != null) {
                        nameTextView.setText(readUserDetails.NAME);
                        emailTextView.setText(readUserDetails.EMAIL);
                        dobTextView.setText(readUserDetails.DOB);
                        genderTextView.setText(readUserDetails.GENDER);
                        phoneTextView.setText(readUserDetails.PHONE);
                    }
                } else {

                    Toast.makeText(ProfileActivity.this, "User data not found!", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Toast.makeText(ProfileActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
