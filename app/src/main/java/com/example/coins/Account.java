package com.example.coins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

//    public void onClickLogOut(View view){
//        Intent intent = new Intent(this, Logout.class);
//        startActivity(intent);
//    }
}