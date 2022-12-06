package com.example.coins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    private TextView emailView;
    private String email;
    private FirebaseAuth mAuthAccount;

    private static final String TAG = "Account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getSupportActionBar().setTitle("Account");

        emailView = findViewById(R.id.getEmail);

        mAuthAccount = FirebaseAuth.getInstance();
        FirebaseUser currentUserAccount = mAuthAccount.getCurrentUser();
        if (currentUserAccount == null) {
            Toast.makeText(Account.this, "Something went wrong! Account data unavailable.", Toast.LENGTH_SHORT).show();
        } else {
            showUserProfile(currentUserAccount);
        }
    }

    private void showUserProfile(FirebaseUser currentUserAccount) {
        String userIDAccount = currentUserAccount.getUid();

        // Extract user data from "Registered Users" database
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userIDAccount).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    email = currentUserAccount.getEmail();
                    emailView.setText(email);
                    if (email == null) {
                        email = readUserDetails.getEmail();
                        emailView.setText(email);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Account.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Something went wrong! Error: " + error.getMessage());
            }
        });
    }

    public void onClickLogOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}