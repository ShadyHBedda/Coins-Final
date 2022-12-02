package com.example.coins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();

        EditText email = findViewById(R.id.emailSignUp);
        email.setText(intent.getStringExtra("email"));

        EditText password = findViewById(R.id.passwordSignUp);
        password.setText(String.valueOf(intent.getStringExtra("password")));
    }

    public void onSignUpClick(View view){
        EditText enterPassword = findViewById(R.id.passwordSignUp);
        String password = enterPassword.getText().toString();

        TextView wrongPassword = findViewById(R.id.wrongPasswordSignUp);
        wrongPassword.setText("");

        EditText verifyPassword = findViewById(R.id.verifySignUp);
        String verify = verifyPassword.getText().toString();

        TextView wrongVerify = findViewById(R.id.wrongVerifySignUp);
        wrongVerify.setText("");

        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        EditText enterEmail = findViewById(R.id.emailSignUp);
        String email = enterEmail.getText().toString();

        TextView wrongEmail = findViewById(R.id.wrongEmailSignUp);
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
        if (!verify.equals(password)){
            valid = false;
            wrongVerify.setText("Does not match password");
        }
        if (valid) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }

}