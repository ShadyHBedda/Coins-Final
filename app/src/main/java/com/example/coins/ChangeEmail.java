package com.example.coins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeEmail extends AppCompatActivity {

    final String TAG = "ChangeEmail";

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
            wrongCurrentEmail.setText("Please enter a valid email.");
        }
        if (!matcher2.matches()){
            valid = false;
            wrongNewEmail.setText("Please enter a valid email.");
        }
        if (!currentEmail.equals("") && !newEmail.equals("") && matcher1.matches() && matcher2.matches() && currentEmail.equals(newEmail)){
            valid = false;
            wrongNewEmail.setText("Please enter NEW email.");
        }
        if (valid) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User email address updated.");
                            }
                        }
                    });

            Toast.makeText(this, "Email changed successfully.", Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }
    }
}