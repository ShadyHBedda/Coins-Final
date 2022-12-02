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

public class ChangeEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
    }

    public void onChangeClick(View view){
        Intent intent = new Intent(this, Settings.class);

        EditText current = findViewById(R.id.currentEmail);
        String currentEmail = current.getText().toString();

        TextView wrongCurrentEmail = findViewById(R.id.wrongCurrentEmail);
        wrongCurrentEmail.setText("");

        EditText newE = findViewById(R.id.newEmail);
        String newEmail = newE.getText().toString();

        TextView wrongNewEmail = findViewById(R.id.wrongNewEmail);
        wrongNewEmail.setText("");

        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher1 = pattern.matcher(currentEmail);
        Matcher matcher2 = pattern.matcher(newEmail);

        Boolean valid = true;

        if (!matcher1.matches()){
            valid = false;
            wrongCurrentEmail.setText("Please enter a valid email");
        }
        if (!matcher2.matches()){
            valid = false;
            wrongNewEmail.setText("Please enter a valid email");
        }
        if (!currentEmail.equals("") && !newEmail.equals("") && matcher1.matches() && matcher2.matches() && currentEmail.equals(newEmail)){
            valid = false;
            wrongNewEmail.setText("Please enter NEW email");
        }
        if (valid) {
            startActivity(intent);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}