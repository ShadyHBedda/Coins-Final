package com.example.coins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void onCreateNewClick(View view){
        Intent intent = new Intent(this, SignUp.class);

        EditText enterEmail = findViewById(R.id.email);
        String email = enterEmail.getText().toString();

        EditText enterPassword = findViewById(R.id.password);
        String password = enterPassword.getText().toString();

        if (email != ""){
            intent.putExtra("email", email);
        }

        if (password != ""){
            intent.putExtra("password", password);
        }

        startActivity(intent);
    }

    public void onLoginClick(View view){
        Intent intent = new Intent(this, Cryptocurrency.class);

        EditText enterPassword = findViewById(R.id.password);
        String password = enterPassword.getText().toString();

        TextView wrongPassword = findViewById(R.id.wrongPassword);
        wrongPassword.setText("");

        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        EditText enterEmail = findViewById(R.id.email);
        String email = enterEmail.getText().toString();

        TextView wrongEmail = findViewById(R.id.wrongEmail);
        wrongEmail.setText("");

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        Boolean valid = true;

        if (!matcher.matches()){
            valid = false;
            wrongEmail.setText("Please enter a valid email");
        }
        if (password.equals("")) {
            valid = false;
            wrongPassword.setText("Please enter password");
        }
        if (valid) {
            startActivity(intent);
        }
    }
}