package com.example.coins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account extends AppCompatActivity {

    FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Name, email address, and profile photo Url
            String email = currentUser.getEmail();
            TextView currentUserEmail = (TextView) findViewById(R.id.getEmail);
            currentUserEmail.setText(email);
        } else {
            TextView currentUserEmail = (TextView) findViewById(R.id.getEmail);
            currentUserEmail.setText("NULL");
        }
    }

//    public void onClickLogOut(View view){
//        Intent intent = new Intent(this, Logout.class);
//        startActivity(intent);
//    }
}