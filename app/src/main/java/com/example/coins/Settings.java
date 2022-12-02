package com.example.coins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onClickMarket(View view){
        Intent intent = new Intent(this, Cryptocurrency.class);
        startActivity(intent);
    }

    public void onClickFavorite(View view){
        Intent intent = new Intent(this, Favorite.class);
        startActivity(intent);
    }

    public void onClickSearch(View view){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void onClickAccount(View view){
        Intent intent = new Intent(this, Account.class);
        startActivity(intent);
    }

    public void onClickChangeEmail(View view){
        Intent intent = new Intent(this, ChangeEmail.class);
        startActivity(intent);
    }

    public void onClickChangePassword(View view){
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }

    public void onClickContact(View view){
        Intent intent = new Intent(this, Contact.class);
        startActivity(intent);
    }

    public void onClickTerms(View view){
        Intent intent = new Intent(this, Terms.class);
        startActivity(intent);
    }

}