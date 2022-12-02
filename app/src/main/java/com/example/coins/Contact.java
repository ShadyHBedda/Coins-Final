package com.example.coins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
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

    public void onClickSettings(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void onClickSend(View view){
        Intent intent = new Intent(this, Settings.class);

        EditText enterSubject = findViewById(R.id.subject);
        String subject = enterSubject.getText().toString();

        TextView wrongSubject = findViewById(R.id.wrongSubject);
        wrongSubject.setText("");

        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        EditText enterEmail = findViewById(R.id.emailAddress);
        String email = enterEmail.getText().toString();

        TextView wrongEmail = findViewById(R.id.wrongEmail3);
        wrongEmail.setText("");

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        Boolean valid = true;

        if (!matcher.matches()){
            valid = false;
            wrongEmail.setText("Please enter a valid email");
        }
        if (subject.equals("")) {
            valid = false;
            wrongSubject.setText("Please enter subject");
        }
        if (valid) {
            startActivity(intent);
            Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();
        }
    }

}